package com.recommedation.movie.recommend;

public class UserDis implements Comparable<UserDis> {
	private int idUser;
	private double distance = 0;
	private int countMoviesCommom = 0;
	
	public UserDis( int idUser) {
		super();
		this.idUser = idUser;
	}
	public UserDis() {
	}

	public void addDistance(double distance) {
		countMoviesCommom++;
		this.distance += distance;
	}
	public double getDistance() {
		return distance/countMoviesCommom;
	}
	public void setDistancesdouble (double distance) {
		countMoviesCommom = 1;
		this.distance = distance;
	}
	public int getIdUser() {
		return idUser;
	}
	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}
	@Override
	public String toString() {
		return "MovieDis [idUser=" + idUser + ", distance=" + distance + "]";
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof UserDis) {
			return ((UserDis) obj).getIdUser() == this.idUser;
		} else if(obj instanceof Integer) {

		}
		return false;
	}
	@Override
	public int compareTo(UserDis md) {
		double sub = (this.getDistance() - md.getDistance());
		if (sub == 0) {
			return 0;
		}
		if (sub > 0) {
			return 1;
		}
		return -1;
	}
}