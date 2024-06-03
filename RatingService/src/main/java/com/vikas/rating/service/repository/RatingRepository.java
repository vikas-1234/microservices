package com.vikas.rating.service.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.vikas.rating.service.entity.Rating;

@Repository
public interface RatingRepository extends MongoRepository<Rating, String> {

	
	// creating custom method here
	
	List<Rating> findByUserId(String userId);
	
	
	List<Rating> findByHotelId(String hotelId);
}
