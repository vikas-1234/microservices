package com.vikas.rating.service.servicesImpl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vikas.rating.service.entity.Rating;
import com.vikas.rating.service.repository.RatingRepository;
import com.vikas.rating.service.services.RatingService;

@Service
public class RatingServiceImpl  implements RatingService{

	@Autowired
	private RatingRepository rateRepo;
	
	@Override
	public Rating createRating(Rating rating) {
		String ratingId=UUID.randomUUID().toString();
		rating.setRatingId(ratingId);
		return rateRepo.save(rating);
	}

	@Override
	public List<Rating> getAllRatings() {
		return rateRepo.findAll();
	}

	@Override
	public List<Rating> getRatingByUserId(String userId) {
		return rateRepo.findByUserId(userId);
	}

	@Override
	public List<Rating> getRatingByHotelId(String hotelId) {
		return rateRepo.findByHotelId(hotelId);
	}

	@Override
	public void deleteRating(String ratingId) {
		rateRepo.deleteById(ratingId);
	}

}
