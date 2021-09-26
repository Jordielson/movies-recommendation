package com.recommedation.movie.service;

import java.util.List;
import java.util.Optional;

import com.recommedation.movie.model.IdUserRate;
import com.recommedation.movie.model.Rate;
import com.recommedation.movie.recommend.Knn;
import com.recommedation.movie.repository.RateRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RateService {
    
    @Autowired
    RateRepository rateRepository;

    public List<Rate> findAll() {
        return rateRepository.findAll();
    }

    public Rate findById(int movieId, int userId) {
        Optional<Rate> rateO = rateRepository.findById(new IdUserRate(movieId, userId));
		if(!rateO.isPresent()) {
			return null;
		} else {
			return rateO.get();
		}
    }

    public Rate save(Rate rate) {
        return rateRepository.save(rate);
    }

    public Rate update(Rate rate) {
        return rateRepository.save(rate);
    }

    public void delete(Rate rate) {
        rateRepository.delete(rate);
    }

    public List<Integer> recomendMovies(int userId) {
        List<Rate> ratingsUser = rateRepository.findAllMoviesByUserId(userId);
		List<Rate> ratingsMoviesCommom = rateRepository.findAllMoviesCommom(userId);

		if(ratingsMoviesCommom.size() > 0) {
			List<Integer> neighbors = Knn.findNeighbors(ratingsUser, ratingsMoviesCommom);
	
			List<Integer> recommends = rateRepository.findMoviesRecommends(userId, neighbors);
			
			return recommends;
		} else {
			return null;
		}
    }

    public List<Integer> recomendMovies() {
        List<Integer> moviesRecommends = rateRepository.moviesRecommends();
        return moviesRecommends;
    }
}
