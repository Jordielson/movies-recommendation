package com.recommedation.movie.recommend;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.recommedation.movie.model.Rate;

public class Knn {
	private List<MovieDis> movieDisList;
	
	public List<Integer> recommned(List<Rate> sample, List<Rate> moviesUser) {
		movieDisList = new ArrayList<MovieDis>();
		for (int i = 0; i < moviesUser.size(); i++) {
			calculeDistance(sample, moviesUser.get(i));
		}
		sort(movieDisList);
		List<Integer> movieDisListUnique = new ArrayList<Integer>();
		for (int i = 0; i < movieDisList.size(); i++) {
			MovieDis movieDis = movieDisList.get(i);
			if(! movieDisListUnique.contains(movieDis.getIdMovie())) {
				movieDisListUnique.add(movieDis.getIdMovie());
			}
		}
		for (int i = 0; i < moviesUser.size(); i++) {
			Integer id = moviesUser.get(i).getMovieId();
			if(movieDisListUnique.contains(id)) {
				movieDisListUnique.remove(id);
			}
		}
		return movieDisListUnique;
	}
 
	private void calculeDistance(List<Rate> sample, Rate movie) {
		int length = sample.size();

		for (int i = 0; i < length; i++) {
			Rate mv = sample.get(i);
			double distances = getDistance(mv, movie);
			MovieDis movieDis = new MovieDis(mv.getMovieId(), mv.getUserId(), mv.getRating(), distances);
			movieDisList.add(movieDis);
		}
	}
	
	public void sort(List<MovieDis> movieDisList) {
		Collections.sort(movieDisList, new Comparator<MovieDis>() {
			 
			@Override
			public int compare(MovieDis o1, MovieDis o2) {
				double sub = (o1.getDistance() - o2.getDistance());
				if (sub == 0) {
					return 0;
				}
				if (sub > 0) {
					return 1;
				}
				return -1;
			}
		});
		
		int k=5;
		movieDisList=movieDisList.subList(0,k);
	}
	
	public double getDistance(Rate movie1, Rate movie2) {
		double[] ps1 = { movie1.getRating(), movie1.getRating()};
		double[] ps2 = { movie2.getRating(), movie2.getRating()};
		return getDistance(ps1, ps2);
	}
 
	public double getDistance(double[] ps1, double[] ps2) {
		if (ps1.length != ps1.length) {
			 throw new RuntimeException("The number of attributes does not correspond");
		}
		int length = ps1.length;
		double total = 0;
		for (int i = 0; i < length; i++) {
			double sub = ps1[i] - ps2[i];
			total = total + (sub * sub);
		}
		return Math.sqrt(total);
	}
 
}
