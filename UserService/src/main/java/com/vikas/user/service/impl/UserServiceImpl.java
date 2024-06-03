package com.vikas.user.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.netflix.discovery.converters.Auto;
import com.vikas.user.service.entity.Hotel;
import com.vikas.user.service.entity.Rating;
import com.vikas.user.service.entity.User;
import com.vikas.user.service.exception.ResourceNotFoundException;
import com.vikas.user.service.repository.UserRepository;
import com.vikas.user.service.services.UserService;
import ch.qos.logback.classic.Logger;

@Service
public class UserServiceImpl implements UserService {

	private Logger logger = (Logger) LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserRepository userRepo;

	@Autowired
	@LoadBalanced
	private RestTemplate restTemplate;
	
//	public UserServiceImpl(RestTemplateBuilder restTemplateBuilder) {
//		this.restTemplate = restTemplateBuilder.build();
//	}

	@Override
	public User saveUser(User user) {
		// generate unique user Id
		String randomUserId = UUID.randomUUID().toString();
		System.out.println("ServiceImpl::saveUser::randomUserId " + randomUserId);
		user.setUserId(randomUserId);
		return userRepo.save(user);
	}

	@Override
	public List<User> getAllUser() {
		return userRepo.findAll();
	}

	@Override
	public User getSingleUser(String userId) {
		System.out.println("UserServiceImpl::getSingleUser::userId " + userId);
		User user = userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User with given id not found on server " + userId));
		System.out.println("UserServiceImpl::getSingleUser::user " + user);
		String userId1 = user.getUserId();
		System.out.println("UserServiceImpl::getSingleUser::userId1 " + userId1);

		// fetch rating of the above user from Rating Services
		String url = "http://RATING-SERVICE/ratings/users/" + userId1;
		System.out.println("UserServiceImpl::getSingleUser::url " + url);
		 Rating[] ratingOfUser = restTemplate.getForObject(url, Rating[].class);
		System.out.println("UserServiceImpl::getSingleUser::ratingOfUser " + ratingOfUser);
		
		List<Rating> ratings=Arrays.asList(ratingOfUser);
		System.out.println("UserServiceImpl::getSingleUser::ratings " + ratings.toString());
		List<Rating> ratingList = ratings.stream().map(rating -> {
			// API call to hotel service to get the hotel
			String hotelUrl = "http://HOTEL-SERVICE/hotels/get/" + rating.getHotelId();
			System.out.println("UserServiceImpl::getSingleUser::hotelUrl " + hotelUrl);
			ResponseEntity<Hotel> responseEntity = restTemplate.getForEntity(hotelUrl, Hotel.class);
			Hotel hotel = responseEntity.getBody();
			System.out.println("UserServiceImpl::getSingleUser::responseEntity** " + responseEntity + " hotel " + hotel);
			logger.info("Response status code: {}", responseEntity.getStatusCode());
			// Set the hotel to rating
			rating.setHotel(hotel);
			// Return the modified rating
			return rating;
		}).collect(Collectors.toList());
		System.out.println("UserServiceImpl::getSingleUser::ratingList " + ratingList);
		user.setRatings(ratingList);
		return user;
	}
	
//	@Override
//	public User getSingleUser(String userId) {
//	    logger.info("Fetching user with ID: {}", userId);
//
//	    User user = userRepo.findById(userId)
//	            .orElseThrow(() -> new ResourceNotFoundException("User with given ID not found: " + userId));
//
//	    String userId1 = user.getUserId();
//
//	    // Fetch ratings of the user from the Rating Service
//	    String ratingsUrl = "http://localhost:9092/ratings/users/" + userId1;
//	    logger.info("Fetching ratings from URL: {}", ratingsUrl);
//
//	    Rating[] ratingOfUser = restTemplate.getForObject(ratingsUrl, Rating[].class);
//
//	    if (ratingOfUser == null) {
//	        logger.warn("No ratings found for user with ID: {}", userId);
//	        user.setRatings(Collections.emptyList());
//	        return user;
//	    }
//
//	    List<Rating> ratings = Arrays.asList(ratingOfUser);
//	    logger.info("Ratings: {}", ratings);
//
//	    List<Rating> ratingList = ratings.stream().map(rating -> {
//	        // Check if hotelId is null
//	        if (rating.getHotelId() == null) {
//	            logger.warn("Hotel ID is null for rating: {}", rating);
//	            // Handle the case where hotelId is null
//	            // For example, you might set the hotel information as null or skip processing this rating
//	            return rating;
//	        }
//
//	        String hotelUrl = "http://localhost:9091/hotels/get/" + rating.getHotelId();
//	        logger.info("Fetching hotel from URL: {}", hotelUrl);
//
//	        try {
//	            ResponseEntity<Hotel> responseEntity = restTemplate.getForEntity(hotelUrl, Hotel.class);
//	            if (responseEntity.getStatusCode().is2xxSuccessful()) {
//	                Hotel hotel = responseEntity.getBody();
//	                rating.setHotel(hotel);
//	                return rating;
//	            } else {
//	                logger.warn("Hotel service returned status code: {}", responseEntity.getStatusCode());
//	                throw new ResourceNotFoundException("Hotel with ID not found: " + rating.getHotelId());
//	            }
//	        } catch (HttpClientErrorException.NotFound e) {
//	            logger.warn("Hotel not found for ID: {}", rating.getHotelId());
//	            throw new ResourceNotFoundException("Hotel with ID not found: " + rating.getHotelId());
//	        } catch (Exception e) {
//	            logger.error("Error fetching hotel details", e);
//	            throw new RuntimeException("Error fetching hotel details", e);
//	        }
//	    }).collect(Collectors.toList());
//
//	    user.setRatings(ratingList);
//	    return user;
//	}

	@Override
	public void deleteUser(String userId) {
		userRepo.deleteById(userId);
	}

	@Override
	public User updateUser(User user) {
		return userRepo.save(user);
	}
}
