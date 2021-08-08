package com.recommedation.movie.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.recommedation.movie.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	
}