package com.vikas.rating.service.services;

import java.util.List;

import com.vikas.rating.service.entity.Rating;

public interface RatingService {

	//Create 
	Rating createRating(Rating rating);
	
	// get all ratings
	List<Rating> getAllRatings();
	
	// get all radting by userId
	
	List<Rating> getRatingByUserId(String userId);
	
	// get rating by hotel id
	List<Rating> getRatingByHotelId(String hotelId);
	
	
	void deleteRating(String ratingId);
	
}
