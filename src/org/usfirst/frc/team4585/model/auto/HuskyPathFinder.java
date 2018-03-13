package org.usfirst.frc.team4585.model.auto;

import java.io.File;
import java.util.ArrayDeque;

import GridNav.GridNav;
import GridNav.Vertex;
import GridNav.Options;

public class HuskyPathFinder extends GridNav{
	
	private int x0;
	private int y0;
	private int x1;
	private int y1;
	
	ArrayDeque<Vertex> bestroute;
	
	
	public HuskyPathFinder(String mapFilePath) {
		
//		File dotMapFile = new File("./maps/testMap.map");
		File dotMapFile = new File(mapFilePath);
		
		try {
			loadCharMatrix(dotMapToCharMatrix(dotMapFile));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setPoints(double x0, double y0, double x1, double y1) {
		this.x0 = (int) Math.round(x0);
		this.y0 = (int) Math.round(y0);
		this.x1 = (int) Math.round(x1);
		this.y1 = (int) Math.round(y1);
	}
	
	public ArrayDeque<Vertex> calculatePath() {
		int[] start = {y0, x0};
		int[] goal = {y1, x1};
		bestroute = route(start, goal, Options.ASTAR, Options.DIAGONAL_HEURISTIC, true);
		
		bestroute = clearLinePoints(bestroute);
		
		return bestroute;
	}
	
	public ArrayDeque<Vertex> getPathList() {
		return bestroute;
	}
	
	public ArrayDeque<Vertex> clearLinePoints(ArrayDeque<Vertex> points) {
		Vertex[] pointsA = points.toArray(new Vertex[0]);
		ArrayDeque<Vertex> output = new ArrayDeque<Vertex>();
		output.add(points.getFirst());
		
		for (int i = 1; i < pointsA.length - 1; i++) {	//skip the first and last element
			
			double xDiff = (Math.abs(pointsA[i + 1].getX() - pointsA[i - 1].getX()) / 2.0);
			double yDiff = (Math.abs(pointsA[i + 1].getY() - pointsA[i - 1].getY()) / 2.0);
			
			if (!(( (int) xDiff == xDiff) &&
					( (int) yDiff == yDiff))) {
				output.add(pointsA[i]);
			}
		}
		
		output.add(points.getLast());
		
		return output;
	}
	
}









