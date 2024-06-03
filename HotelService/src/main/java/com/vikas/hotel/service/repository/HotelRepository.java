package com.vikas.hotel.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.vikas.hotel.service.entity.Hotel;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, String> {

}
