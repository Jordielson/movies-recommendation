package com.recommedation.movie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.recommedation.movie.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{
    @Query(value = "SELECT * FROM user r WHERE r.email = ?1 LIMIT 1", nativeQuery = true)
    public User login(String email);
}