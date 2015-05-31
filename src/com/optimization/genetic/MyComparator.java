package com.optimization.genetic;

import java.util.Comparator;

public class MyComparator implements Comparator<GData> {
	
	@Override
	public int compare(GData o1, GData o2) {
	    if (o1.uygunluk > o2.uygunluk) {
	        return -1;
	    } else if (o1.uygunluk < o2.uygunluk) {
	        return 1;
	    }
	    return 0;
	}

}
