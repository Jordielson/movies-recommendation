package com.recommedation.movie.recommend;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.recommedation.movie.model.Rate;

public class Knn {
	
	public static List<Integer> findNeighbors(List<Rate> moviesUser, List<Rate> moviesCommom) {
		List<UserDis> userDisList = calculeDistance(moviesCommom, moviesUser);

		Collections.sort(userDisList);
		int k = 5;
		if(userDisList.size() > k) {
			userDisList = userDisList.subList(0, k);
		}

		List<Integer> neighbors = new ArrayList<>();
		for (UserDis dis : userDisList) {
			neighbors.add(dis.getIdUser());
		}
		return neighbors;
	}
 
	private static List<UserDis> calculeDistance(List<Rate> moviesCommom, List<Rate> moviesUser) {
		Map<Integer,UserDis> userDisList = new HashMap<>();

		for (int i = 0; i < moviesCommom.size(); i++) {
			Rate mv = moviesCommom.get(i);
			int userId = mv.getUserId();

			if(! userDisList.containsKey(userId)) {
				userDisList.put(userId, new UserDis(userId));
			}

			Rate movie = moviesUser.stream()
				.filter(rate -> mv.getMovieId() == rate.getMovieId())
				.findAny()
				.orElse(null);
			
			double distances = getDistance(mv, movie);
			userDisList.get(userId).addDistance(distances);
		}
		return new ArrayList<UserDis>(userDisList.values());
	}
	
	public static double getDistance(Rate movie1, Rate movie2) {
		double[] ps1 = { movie1.getRating()};
		double[] ps2 = { movie2.getRating()};
		return calculeEuclidianDistance(ps1, ps2);
	}

	public static double calculeEuclidianDistance(double[] ps1, double[] ps2) {
		if (ps1.length != ps1.length) {
			 throw new RuntimeException("Parameters does not correspond");
		}
		int length = ps1.length;
		double total = 0;
		for (int i = 0; i < length; i++) {
			double sub = ps1[i] - ps2[i];
			total = total + (sub * sub);
		}
		return Math.sqrt(total);
	}

	public static void sort(List<Rate> userDisList) {
		Collections.sort(userDisList, new Comparator<Rate>() {
 
			@Override
			public int compare(Rate o1, Rate o2) {
				double sub = (o1.getUserId() - o2.getUserId());
				if (sub == 0) {
					return 0;
				}
				if (sub > 0) {
					return 1;
				}
				return -1;
			}
		});
	}
}
