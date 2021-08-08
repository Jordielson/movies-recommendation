package com.recommedation.movie.recommend;

public class MovieDis {
	private int idMovie;
	private int idUser;
	private int rate;
	private double distance;
	
	public MovieDis(int idMovie, int idUser, int rate, double distance) {
		super();
		this.idMovie = idMovie;
		this.idUser = idUser;
		this.rate = rate;
		this.distance = distance;
	}
	public MovieDis() {
	}
	public double getDistance() {
		return distance;
	}
	public void setDistancesdouble (double distance) {
		this.distance = distance;
	}
	public int getIdMovie() {
		return idMovie;
	}
	public void setIdMovie(int idMovie) {
		this.idMovie = idMovie;
	}
	public int getIdUser() {
		return idUser;
	}
	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}
	public int getRate() {
		return rate;
	}
	public void setRate(int rate) {
		this.rate = rate;
	}
	@Override
	public String toString() {
		return "MovieDis [idUser=" + idUser + ", idMovie=" + idMovie + ", distance=" + distance + ", rate=" + rate + "]";
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof MovieDis) {
			return ((MovieDis) obj).getIdMovie() == this.idMovie;
		}
		return false;
	}
}