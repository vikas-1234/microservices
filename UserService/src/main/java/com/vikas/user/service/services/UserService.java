package com.vikas.user.service.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.vikas.user.service.entity.User;

public interface UserService {
	
	User saveUser(User user);
	
	List<User> getAllUser();
	
	User getSingleUser(String userId);
	
	void deleteUser(String userId);
	
	User updateUser(User user);

}
