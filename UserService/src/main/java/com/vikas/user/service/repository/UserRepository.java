package com.vikas.user.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.vikas.user.service.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String>{

	// if u want to implement any custom method or query
	
}
