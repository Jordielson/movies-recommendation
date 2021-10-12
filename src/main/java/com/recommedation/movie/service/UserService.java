package com.recommedation.movie.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.recommedation.movie.model.User;
import com.recommedation.movie.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    public Object save(User user) {
        if(userRepository.FindByEmail(user.getEmail()) != null) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("message", "Email ja existe");
            return map;
        }
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User update(User user, String oldPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if(encoder.matches(oldPassword, findById(user.getId()).getPassword())){
            user.setPassword(encoder.encode(user.getPassword()));
            return userRepository.save(user);
        } else {
            return null;
        }
    }

    public void delete(User user) {
        userRepository.delete(user);
    }
}
