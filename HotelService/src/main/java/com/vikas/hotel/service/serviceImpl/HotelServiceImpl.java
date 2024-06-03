package com.vikas.hotel.service.serviceImpl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.vikas.hotel.service.entity.Hotel;
import com.vikas.hotel.service.exception.ResourceNotFoundException;
import com.vikas.hotel.service.repository.HotelRepository;
import com.vikas.hotel.service.service.HotelService;
@Service
public class HotelServiceImpl  implements HotelService{

	@Autowired
	private HotelRepository hotelRepo;
	
	@Override
	public Hotel createHotel(Hotel hotel) {
		
		String hotelId=UUID.randomUUID().toString();
		hotel.setId(hotelId);
		
		return hotelRepo.save(hotel);
	}

	@Override
	public List<Hotel> getAll() {
		return hotelRepo.findAll();
	}

	@Override
	public Hotel getSingle(String id) {
		return hotelRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Hotel with given id not found!!" + id));
	}

	@Override
	public void deleteHotel(String id) {
		hotelRepo.deleteById(id);
	}

	@Override
	public Hotel updateHotel(Hotel hotel) {
		String hotelId=UUID.randomUUID().toString();
		hotel.setId(hotelId);
		
		return hotelRepo.save(hotel);
	}

}
