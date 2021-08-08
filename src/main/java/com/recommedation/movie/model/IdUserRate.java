package com.recommedation.movie.model;

import java.io.Serializable;

public class IdUserRate implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int movieId;
	private int userId;
	
	public IdUserRate() {
	}
	public IdUserRate(int movieId, int userId) {
		super();
		this.movieId = movieId;
		this.userId = userId;
	}

	public int getMovieId() {
		return movieId;
	}

	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof IdUserRate) {
			IdUserRate u = (IdUserRate) obj;
			return this.movieId == u.movieId && this.userId == u.userId;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		final int prime = 7;
		int result;
		result = prime * movieId;
		result = prime * userId;
		return result;
	}
}
