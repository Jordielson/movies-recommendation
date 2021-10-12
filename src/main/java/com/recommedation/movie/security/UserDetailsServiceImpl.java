package com.recommedation.movie.security;

import com.recommedation.movie.model.User;
import com.recommedation.movie.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u = userRepository.FindByEmail(username);

        if(u == null) {
            throw new UsernameNotFoundException("Usuario nao encontrado!");
        } else {
            return u;
        }
    }
    
}
