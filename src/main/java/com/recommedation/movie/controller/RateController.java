package com.recommedation.movie.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.recommedation.movie.model.IdUserRate;
import com.recommedation.movie.model.Rate;
import com.recommedation.movie.recommend.Knn;
import com.recommedation.movie.repository.RateRepository;

@RestController
@RequestMapping(value = "/movies")
public class RateController {
	@Autowired
	RateRepository rateRepository;
	
	@GetMapping("/ratings")
	public ResponseEntity<List<Rate>> getAll(){
		List<Rate> ratings = rateRepository.findAll();
		for (Rate r : ratings) {
			IdUserRate id = r.getId();
			r.add(linkTo(methodOn(RateController.class).getRate(id.getMovieId(), id.getUserId())).withSelfRel());
		}
		return new ResponseEntity<List<Rate>>(ratings, HttpStatus.OK);
	}
	
	@GetMapping("/rate")
	public ResponseEntity<Rate> getRate(@RequestParam(name="movie_id") int movieId, @RequestParam(name="user_id") int userId) {
		Optional<Rate> rateO = rateRepository.findById(new IdUserRate(movieId, userId));
		if(!rateO.isPresent()) {
			return new ResponseEntity<Rate>(HttpStatus.NOT_FOUND);
		} else {
			rateO.get().add(linkTo(methodOn(RateController.class).getAll()).withRel("Users list"));
			return new ResponseEntity<Rate>(rateO.get(), HttpStatus.OK);
		}
	}
	
	@GetMapping("/rate/recommend/{userId}")
	public ResponseEntity<List<Integer>> getMoviesRecommed(@PathVariable(value = "userId") int userId){
		List<Rate> ratingsUser = rateRepository.findAllMoviesByUserId(userId);
		List<Rate> ratings = rateRepository.findAllMoviesCompare(userId);
		Knn knn = new Knn();
		List<Integer> recommends = knn.recommned(ratings, ratingsUser);
		return new ResponseEntity<List<Integer>>(recommends, HttpStatus.OK);
	}
	
	@PostMapping("/rate")
	public ResponseEntity<Rate> saveRate(@RequestBody Rate rate) {
		return new ResponseEntity<Rate>(rateRepository.save(rate), HttpStatus.CREATED);
	}
	
	@DeleteMapping("/rate")
	public ResponseEntity<?> deleteRate(@RequestParam(name="movieId") int movieId, @RequestParam(name="userId") int userId) {
		Optional<Rate> rateO = rateRepository.findById(new IdUserRate(movieId, userId));
		if(!rateO.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			rateRepository.delete(rateO.get());
			return new ResponseEntity<>(HttpStatus.OK);		
		}
	}
	
	@PutMapping("/rate")
	public ResponseEntity<Rate> updateRate(@RequestBody Rate rate) {
		return new ResponseEntity<Rate>(rateRepository.save(rate), HttpStatus.OK);
	}
}
