package com.recommedation.movie.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.recommedation.movie.model.IdUserRate;
import com.recommedation.movie.model.Rate;

public interface RateRepository extends JpaRepository<Rate, IdUserRate>{
	
	@Query(value = "SELECT * FROM rate r WHERE r.user_id = ?1", nativeQuery = true)
	public List<Rate> findAllMoviesByUserId(int userId);
	
	@Query(value = "SELECT * FROM rate ur WHERE ur.user_id <> ?1 AND  ur.user_id in \n" + 
			"(SELECT r.user_id  FROM rate r \n" + 
			"WHERE r.movie_id in (SELECT r2.movie_id from rate r2 WHERE r2.user_id = ?1))", nativeQuery = true)
	public List<Rate> findAllMoviesCompare(int userId);
}