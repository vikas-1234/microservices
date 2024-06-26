package com.vikas.rating.service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vikas.rating.service.entity.Rating;
import com.vikas.rating.service.services.RatingService;

@RestController
@RequestMapping("/ratings")
public class RatingController {
	
	@Autowired
	private RatingService ratingService;
	//create
	@PostMapping("/save")
	@PreAuthorize("hasAuthority('Admin')")
	public ResponseEntity<Rating> create(@RequestBody Rating rating){
		return ResponseEntity.status(HttpStatus.CREATED).body(ratingService.createRating(rating));
	}
	
	// get all ratings
	
	@GetMapping("/getAll")
	public ResponseEntity<List<Rating>> getRatings(){
		return ResponseEntity.ok(ratingService.getAllRatings());
	}
	
	// get all rating by userId
	@PreAuthorize("hasAuthority('SCOPE_internal') || hasAuthority('Admin')")
	@GetMapping("/users/{userId}")
	public ResponseEntity<List<Rating>> getRatingByUserId(@PathVariable("userId") String userId){
		return ResponseEntity.ok(ratingService.getRatingByUserId(userId));
	}
	// get rating by hotel id
	@GetMapping("/hotels/{hotelId}")
	public ResponseEntity<List<Rating>> getRatingByHotelId(@PathVariable("hotelId") String hotelId){
		return ResponseEntity.ok(ratingService.getRatingByHotelId(hotelId));
	}
	
	@DeleteMapping("/delete/{ratingId}")
	public String deteleRating(@PathVariable("ratingId") String ratingId){
		ratingService.deleteRating(ratingId);
		return "Rating Data Deleted Successfully!";
	}
}
