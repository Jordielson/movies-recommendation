package com.recommedation.movie.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PageController {
	@GetMapping("/movies/home")
	public String home() {
        return "home/home";
    }
	@GetMapping("/movies/movie")
    public String movie(@RequestParam(name="movie_id") int movieId) {
        return "movie/movie";
    }
    @GetMapping("/movies/popular")
    public String movies() {
        return "movies/popular";
    }
    @GetMapping("/movies/recommends")
    public String recommend() {
        return "recommends/recommend";
    }
    @GetMapping("/movies/genre")
    public String genre(@RequestParam(name="genre_id") int genreId) {
        return "genres/genre";
    }
    @GetMapping("/movies/search")
    public String search() {
        return "search/search";
    }
    @GetMapping("/movies/register")
    public String register() {
        return "register/register";
    }
    @GetMapping("/movies/login")
    public String login() {
        return "login/login";
    }
}