package com.recommedation.movie.service;

import java.util.List;
import java.util.Optional;

import com.recommedation.movie.model.User;
import com.recommedation.movie.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    
    @Autowired
    UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(int id) {
        Optional<User> userO = userRepository.findById(id);
		if(!userO.isPresent()) {
			return null;
		} else {
			return userO.get();
		}
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public User update(User user) {
        return userRepository.save(user);
    }

    public void delete(User user) {
        userRepository.delete(user);
    }

    public User login(String email, String password) {
        User user = userRepository.login(email);
        if(user == null || !user.getPassword().equals(password)) {
            return null;
        } else {
            return user;
        }
    }
}
