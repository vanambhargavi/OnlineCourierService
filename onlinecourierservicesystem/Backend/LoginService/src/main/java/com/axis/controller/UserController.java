package com.axis.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.axis.model.User;
import com.axis.repository.UserRepository;

@RestController
@RequestMapping("/product/user")
@CrossOrigin(origins="*")
public class UserController {
	
	@Autowired
	UserRepository userRepository;
	
    @PostMapping("/add")
	public ResponseEntity<User> createUser(@RequestBody User user){
    	 try {
				 User _user = userRepository.save(new User(user.getEmail(),user.getPassword()));
    		 return new ResponseEntity<>(_user,HttpStatus.CREATED); 
    	 }catch(Exception ex) {
    		 return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR); 
    	 }    	
	  }
	 
	  @GetMapping("/fetch")
	   public ResponseEntity<List<User>> getAllUsers(){
		   try {
			   List<User> users = new ArrayList<User>();
	    	   userRepository.findAll().forEach(users::add);
	    	   if(users.isEmpty()) {
	    		   return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    	   }
	    	   return new ResponseEntity<>(users,HttpStatus.OK);
		   }catch(Exception ex) {
			   return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR); 
		   }
		 }
	   
	   @GetMapping("/fetch_user/{id}")
	   public ResponseEntity<User> getUserById(@PathVariable("id") long id){
		  Optional<User> userData = userRepository.findById(id);
		  if(userData.isPresent()) {
			  return new ResponseEntity<>(userData.get(),HttpStatus.OK);
		  }else {
			  return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
		  }
	   }   
  
	   //@GetMapping("/signin")
	   // signIn(@RequestBody User user){
	   //  ---> body =>  email and pwd
	   //               findByEmail() --> { email and pwd } <- fetching from db
	   //      compare usersname and pwd
	   //		if(email and password is matches) return "valid_user"
	   //      else  return "invalid_user"
	   //
	   //}
	   
	   
	   @PostMapping("/validate")
	   public String validateUser(@RequestBody User user){
		   String msg = "";	
		   try {
			   User userData = userRepository.findUserByEmail(user.getEmail());
			   if( user.getEmail().equals(userData.getEmail()) && user.getPassword().equals(userData.getPassword())) {
					 msg = "valid";
				   }else {
					   return "invalid";
		       } 
		   }catch(Exception ex) {
			   	msg ="invalid";
		   } 	   
		   
		return msg; 		   
	   }
	   
	   @PutMapping("/update_user/{id}")
	   public ResponseEntity<User> updateUser(@PathVariable("id") long id,@RequestBody User user){
		   Optional<User> userData = userRepository.findById(id);
		   if(userData.isPresent()) {
			   User _user = userData.get();
			   _user.setEmail(user.getEmail());
			   _user.setPassword(user.getPassword());
			   return new ResponseEntity<>(userRepository.save(_user),HttpStatus.OK);
		   }else {
			   return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		   }
	   }
	   
	   @DeleteMapping("/delete_user/{id}")
	   public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") long id){
		   try {			   
			   userRepository.deleteById(id);
			   return new ResponseEntity<>(HttpStatus.NO_CONTENT); 
		   }catch(Exception ex) {
			  return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
		   }
	   }
}