package com.vikas.hotel.service.entity;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "hotels")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Hotel {

	@Id
	
	private String id;
	
	private String name;
	
	private String location;
	
	private String about;
	
	
	
}
