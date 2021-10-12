package com.recommedation.movie.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PageController {
	@GetMapping("/movies-recommendation/home")
	public String home() {
        return "home/home";
    }
	@GetMapping("/movies-recommendation/movie")
    public String movie(@RequestParam(name="movie_id") int movieId) {
        return "movie/movie";
    }
    @GetMapping("/movies-recommendation/popular")
    public String movies() {
        return "movies/popular";
    }
    @GetMapping("/movies-recommendation/recommends")
    public String recommend() {
        return "recommends/recommend";
    }
    @GetMapping("/movies-recommendation/genre")
    public String genre(@RequestParam(name="genre_id") int genreId) {
        return "genres/genre";
    }
    @GetMapping("/movies-recommendation/search")
    public String search() {
        return "search/search";
    }
    @GetMapping("/movies-recommendation/register")
    public String register() {
        return "register/register";
    }
    @GetMapping("/movies-recommendation/login")
    public String login() {
        return "login/login";
    }
}