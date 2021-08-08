package com.recommedation.movie.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import org.springframework.hateoas.RepresentationModel;

@Entity
@IdClass(IdUserRate.class)
@Table(name = "rate")
public class Rate extends RepresentationModel<Rate> implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "movie_id")
	private int movieId;
	@Id
	@Column(name = "user_id")
	private int userId;
	
	@Column
	private byte rating;
	
	@Column
	private LocalDate date = LocalDate.now();

	public Rate() {
	}
	
	public Rate(int movieId, int userId, byte rating) {
		super();
		this.movieId = movieId;
		this.userId = userId;
		this.rating = rating;
	}

	public byte getRating() {
		return rating;
	}


	public void setRating(byte rating) {
		if (rating > 0 && rating < 11) {
			this.rating = rating;
		}
	}


	public LocalDate getDate() {
		return date;
	}
	
	public void setDate(LocalDate date) {
		this.date = date;
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
	public String toString() {
		return "Rate[" + userId + ", " + movieId + ", " + rating + ", " + date + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 7;
		int result = movieId + userId;
		result = prime * result + rating;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Rate) {
			Rate u = (Rate) obj;
			return this.movieId == u.movieId && this.userId == u.userId;
		}
		return false;
	}

	public IdUserRate getId() {
		return new IdUserRate(this.movieId, this.userId);
	}
}
