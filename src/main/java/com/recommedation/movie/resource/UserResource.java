package com.recommedation.movie.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import com.recommedation.movie.model.User;
import com.recommedation.movie.service.UserService;

@RestController
@RequestMapping(value = "/movies")
public class UserResource {
	@Autowired
	UserService userService;
	
	@GetMapping("/user")
	public ResponseEntity<User> getUserAuth(@AuthenticationPrincipal User user) {
		if(user == null) {
			return new ResponseEntity<User>(HttpStatus.FORBIDDEN);
		} else {
			return new ResponseEntity<User>(userService.findById(user.getId()), HttpStatus.OK);
		}
	}
	
	@PostMapping("/user")
	public ResponseEntity<?> registrationUser(@RequestBody User user) {
		Object obj = userService.save(user);
		if(obj instanceof User) {
			return new ResponseEntity<User>(((User) obj), HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(obj, HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("/user/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable(value="id") int id) {
		User user = userService.findById(id);
		if(user == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			userService.delete(user);
			return new ResponseEntity<>(HttpStatus.OK);		
		}
	}
	
	@PutMapping("/user")
	@ResponseBody
	public ResponseEntity<User> updateUser(@RequestBody Map<String, String> json) {
		String oldPassword = json.get("oldPassword");
		User user = new User(Integer.parseInt(json.get("id")), json.get("name"), json.get("email"), json.get("password"));
		User u = userService.update(user, oldPassword);
		if(u == null) {
			return new ResponseEntity<User>(HttpStatus.UNAUTHORIZED);
		} else {
			return new ResponseEntity<User>(u, HttpStatus.OK);
		}
	}
}
