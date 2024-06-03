package com.vikas.hotel.service.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.vikas.hotel.service.entity.Hotel;
import com.vikas.hotel.service.service.HotelService;

@RestController
@RequestMapping("/hotels")
public class HotelController {

	@Autowired
	private HotelService hotelService;

	@PostMapping("/save")
	@PreAuthorize("hasAuthority('Admin')")
	public ResponseEntity<Hotel> createHotel(@RequestBody Hotel hotel) {
		Hotel savedHotelData = hotelService.createHotel(hotel);
		System.out.println("HotelController::createHotel::savedHotelData " + savedHotelData);
		return new ResponseEntity<Hotel>(savedHotelData, HttpStatus.CREATED);
	}

	@PreAuthorize("hasAuthority('SCOPE_internal')")
	@GetMapping("/getAll")
	public ResponseEntity<List<Hotel>> getAll() {
		List<Hotel> savedHotelData = hotelService.getAll();
		System.out.println("HotelController::getAll::savedHotelData " + savedHotelData);
		return new ResponseEntity<List<Hotel>>(savedHotelData, HttpStatus.CREATED);
	}

	@PreAuthorize("hasAuthority('SCOPE_internal') || hasAuthority('Admin')")
	@GetMapping("/get/{id}")
	public ResponseEntity<Hotel> getSingle(@PathVariable("id") String id) {
		Hotel savedHotelData = hotelService.getSingle(id);
		System.out.println("HotelController::getSingle::savedHotelData " + savedHotelData);
		return new ResponseEntity<Hotel>(savedHotelData, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/delete/{id}")
	public String deleteHotel(@PathVariable("id") String id) {
		 hotelService.deleteHotel(id);
		return id;
	}
	
	@PutMapping("/updateHotel")
	public ResponseEntity<Hotel> updateHotel(@RequestBody Hotel hotel) {
		Hotel savedHotelData = hotelService.updateHotel(hotel);
		System.out.println("HotelController::updateHotel::savedHotelData " + savedHotelData);
		return new ResponseEntity<Hotel>(savedHotelData, HttpStatus.CREATED);
	}

}
