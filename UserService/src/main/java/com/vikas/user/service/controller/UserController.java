package com.vikas.user.service.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.vikas.user.service.entity.User;
import com.vikas.user.service.entity.User.UserBuilder;
import com.vikas.user.service.services.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;

@RestController
@RequestMapping("/users")
public class UserController {

	private Logger logger = (Logger) LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;

	// create

	@PostMapping("/save")
	public ResponseEntity<User> createUser(@RequestBody User user) {
		User savedUser = userService.saveUser(user);
		System.out.println("UserController::createUser::savedUser " + savedUser);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
	}

	// get singleUser data
//	int retryCount=1;

	@GetMapping("/get/{userId}")
//	@CircuitBreaker(name = "ratingHotelBreaker",fallbackMethod = "ratingHotelFallback")
//	@Retry(name = "ratingHotelBreaker",fallbackMethod = "ratingHotelFallback" )
//	@RateLimiter(name = "userRateLimitor",fallbackMethod = "ratingHotelFallback")
	public ResponseEntity<User> getSingleUser(@PathVariable("userId") String userId) {
//		logger.info("Retry Count : {} " ,retryCount);
//		retryCount++;
		User savedUser = userService.getSingleUser(userId);
		System.out.println("UserController::getSingleUser::savedUser " + savedUser);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
	}
	
	// creating fall back method for circuitbreaker
	public ResponseEntity<User> ratingHotelFallback(String userId, Exception ex){
//		logger.info("Fallback is executed because service is down :{} ", ex.getMessage());
		ex.printStackTrace();
		User user = User.builder()
				.email("dummy@gmail.com")
				.name("Dummy")
				.about("This user is created dummy because some services is down")
				.userId("141234").build();
		
		return new ResponseEntity<>(user, HttpStatus.OK);
		
		
	}
	
	
	// get all user data
	@GetMapping("/getAll")
	public ResponseEntity<List<User>> getSingleUser() {
		List<User> savedUser = userService.getAllUser();
		System.out.println("UserController::getSingleUser::savedUser " + savedUser);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
	}

	// delete User

	@DeleteMapping("/delete/{userId}")
	public String deleteUser(@PathVariable("userId") String userId) {
		userService.deleteUser(userId);
		return "Data Deleted Successfully";

	}

	// updating User Details

	@PutMapping("/update")
	public ResponseEntity<User> updateUser(@RequestBody User user) {
		User updatedSavedUser = userService.updateUser(user);
		System.out.println("UserController::updatedSavedUser::savedUser " + updatedSavedUser);
		return ResponseEntity.status(HttpStatus.CREATED).body(updatedSavedUser);
	}

}
