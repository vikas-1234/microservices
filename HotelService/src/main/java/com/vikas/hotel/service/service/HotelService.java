package com.vikas.hotel.service.service;

import java.util.List;
import com.vikas.hotel.service.entity.Hotel;

public interface HotelService {

	// create

	Hotel createHotel(Hotel hotel);

	// getAll

	List<Hotel> getAll();

	// getSingle

	Hotel getSingle(String id);

	void deleteHotel(String id);

	Hotel updateHotel(Hotel hotel);
}
