package com.recommedation.movie.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.recommedation.movie.model.IdUserRate;
import com.recommedation.movie.model.Rate;

public interface RateRepository extends JpaRepository<Rate, IdUserRate>{
	
	@Query(value = "SELECT * FROM rate r WHERE r.user_id = ?1", nativeQuery = true)
	public List<Rate> findAllMoviesByUserId(int userId);

	@Query(value = "SELECT 	* FROM rate r WHERE r.movie_id in " +
		"(SELECT ur.movie_id  FROM rate ur WHERE ur.user_id = ?1) " +
		"AND r.user_id <> ?1", nativeQuery = true)
	public List<Rate> findAllMoviesCommom(int userId);

	@Query(value = "SELECT movie_id FROM (SELECT r2.movie_id, AVG(rating) AS rating "+
		"FROM rate r2 "+
		"WHERE r2.movie_id not in "+
		"(SELECT ur.movie_id  FROM rate ur WHERE ur.user_id = ?1) "+
		"AND r2.user_id in ?2 "+
		"AND r2.rating > 5 "+
		"GROUP BY r2.movie_id) r "+
		"ORDER BY r.rating DESC LIMIT 100", nativeQuery = true)
	public List<Integer> findMoviesRecommends(int loginUserId, List<Integer> userCompareId);

	@Query(value = "SELECT r.movie_id FROM "+
		"(SELECT r2.movie_id, AVG(rating) AS rating "+
		"FROM rate r2 GROUP BY r2.movie_id) r "+
		"ORDER BY r.rating DESC LIMIT 100", nativeQuery = true)
	public List<Integer> moviesRecommends();

}