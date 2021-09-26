package com.recommedation.movie.resource;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
import com.recommedation.movie.service.RateService;

@RestController
@RequestMapping(value = "/movies")
public class RateResource {
	@Autowired
	RateService rateService;
	
	@GetMapping("/ratings")
	public ResponseEntity<List<Rate>> getAll(){
		List<Rate> ratings = rateService.findAll();
		for (Rate r : ratings) {
			IdUserRate id = r.getId();
			r.add(linkTo(methodOn(RateResource.class).getRate(id.getMovieId(), id.getUserId())).withSelfRel());
		}
		return new ResponseEntity<List<Rate>>(ratings, HttpStatus.OK);
	}
	
	@GetMapping("/rate")
	public ResponseEntity<Rate> getRate(@RequestParam(name="movie_id") int movieId, @RequestParam(name="user_id") int userId) {
		Rate rate = rateService.findById(movieId, userId);
		if(rate == null) {
			return new ResponseEntity<Rate>(HttpStatus.NOT_FOUND);
		} else {
			rate.add(linkTo(methodOn(RateResource.class).getAll()).withRel("Rates list"));
			return new ResponseEntity<Rate>(rate, HttpStatus.OK);
		}
	}
	
	@GetMapping("/rate/recommend/{userId}")
	public ResponseEntity<Page<Integer>> getMoviesRecommed(@PathVariable(value = "userId") int userId, Pageable pageable){
		List<Integer> recommends = rateService.recomendMovies(userId);

		if(recommends != null) {
			return new ResponseEntity<Page<Integer>>(createPage(pageable, recommends), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.SEE_OTHER);
		}
	}

	@GetMapping("/rate/recommend")
	public ResponseEntity<Page<Integer>> moviesRecommed(Pageable pageable) {
		List<Integer> moviesRecommends = rateService.recomendMovies();

		return new ResponseEntity<Page<Integer>>(createPage(pageable, moviesRecommends), HttpStatus.OK);
	}

	public Page<Integer> createPage(Pageable pageable, List<Integer> list) {
		final int start = (int)pageable.getOffset();
		final int end = Math.min((start + pageable.getPageSize()), list.size());
		final Page<Integer> page = new PageImpl<>(list.subList(start, end), pageable, list.size());
		return page;
	}
	
	@PostMapping("/rate")
	public ResponseEntity<Rate> saveRate(@RequestBody Rate rate) {
		return new ResponseEntity<Rate>(rateService.save(rate), HttpStatus.CREATED);
	}
	
	@DeleteMapping("/rate")
	public ResponseEntity<?> deleteRate(@RequestParam(name="movieId") int movieId, @RequestParam(name="userId") int userId) {
		Rate rate = rateService.findById(movieId, userId);
		if(rate == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			rateService.delete(rate);
			return new ResponseEntity<>(HttpStatus.OK);		
		}
	}
	
	@PutMapping("/rate")
	public ResponseEntity<Rate> updateRate(@RequestBody Rate rate) {
		return new ResponseEntity<Rate>(rateService.save(rate), HttpStatus.OK);
	}
}
