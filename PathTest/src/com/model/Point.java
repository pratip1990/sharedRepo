package com.model;

import java.util.ArrayList;

public class Point {
	String pointLebel;
	ArrayList <Point> dest;
	
	public Point(String pointLebel) {
		super();
		this.pointLebel = pointLebel;
		this.dest = new ArrayList<Point>();
	}
	
	public String getPointLebel() {
		return pointLebel;
	}
	
	public void setPointLebel(String pointLebel) {
		this.pointLebel = pointLebel;
	}
	
	public ArrayList<Point> getDest() {
		return dest;
	}

	@Override
	public String toString() {
		return "Point [pointLebel=" + pointLebel + ", dest=" + dest + "]";
	}
	

}
