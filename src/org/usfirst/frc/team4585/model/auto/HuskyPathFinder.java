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
	
	String mapFilePath;
	
	
	public HuskyPathFinder(String mapFilePath) {
		this.mapFilePath = mapFilePath;
		
		loadMap();
		
	}
	
	public void setEndPoints(double x0, double y0, double x1, double y1) {
		this.x0 = (int) Math.round(x0);
		this.y0 = (int) Math.round(y0);
		this.x1 = (int) Math.round(x1);
		this.y1 = (int) Math.round(y1);
	}
	
	public ArrayDeque<Vertex> calculatePath() throws Exception{
		
		loadMap();
		
		int[] start = {y0, x0};
		int[] goal = {y1, x1};
		bestroute = route(start, goal, Options.ASTAR, Options.DIAGONAL_HEURISTIC, true);
		if (bestroute == null) {
			throw new Exception("Route creation failed!");
		}
		
//		bestroute = clearLinePoints(bestroute);
//		bestroute = clearClosePoints(bestroute);
		
		bestroute.remove();
		
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
	
	public ArrayDeque<Vertex> clearClosePoints(ArrayDeque<Vertex> points) {
		Vertex[] pointsA = points.toArray(new Vertex[0]);
		ArrayDeque<Vertex> output = new ArrayDeque<Vertex>();
		output.add(points.getFirst());
		
		
		
		for (int i = 1; i < pointsA.length - 1; i++) {	//skip the first and last element
			if ((distance(pointsA[i].getX(), pointsA[i].getY(), pointsA[i-1].getX(), pointsA[i-1].getY()) > 1.42d)
					&& (distance(pointsA[i].getX(), pointsA[i].getY(), pointsA[i+1].getX(), pointsA[i+1].getY()) > 1.42d)){
				output.add(pointsA[i]);
			}
		}
		
		output.add(points.getLast());
		
		return output;
	}
	
	public double distance(double x0, double y0, double x1, double y1) {
		return Math.sqrt(Math.pow(x1 - x0, 2) + Math.pow(y1 - y0, 2));
	}
	
	public void printPath() {
		Vertex[][] vMap = getVertexMatrix();
		for (int i = 0; i < vMap.length; i++) {
			for (int j = 0; j < vMap[i].length; j++) {
				if (bestroute.contains(vMap[i][j])) {
					vMap[i][j].setKey('O');
				}
			}
		}
		
		for (int i = 0; i < vMap.length; i++) {
			for (int j = 0; j < vMap[i].length; j++) {
				System.out.print(vMap[i][j].getKey());
			}
			System.out.println();
		}
		
		
	}
	
	private boolean loadMap() {
		
		/*
//		File dotMapFile = new File("./maps/testMap.map");
		File dotMapFile = new File(mapFilePath);
		
		
		try {
			loadCharMatrix(dotMapToCharMatrix(dotMapFile));
			System.out.println("Loaded map!");
		} catch (Exception e) {
			e.printStackTrace();
		}
//		*/
		
		/*
		final char c = '.';
		final char b = 'T';
		char[][] charMatrix = {
				{c, c, c, c, c, c, c, c, c, c},
				{c, c, c, c, c, c, c, c, c, c},
				{c, c, c, c, c, c, c, c, c, c},
				{c, c, c, c, c, c, c, c, c, c},
				{c, c, c, c, c, c, c, c, c, c},
				{c, c, c, c, c, c, c, c, c, c},
				{c, c, c, c, c, c, c, c, c, c},
				{c, c, c, c, c, c, c, c, c, c},
				{c, c, c, c, c, c, c, c, c, c},
				{c, c, c, c, c, c, c, c, c, c},
				
		};
		
		try {
			loadCharMatrix(charMatrix);
		} catch (Exception e) {
			e.printStackTrace();
		}
//		*/
		
		String map = "TTTTTTTTTTTTTTTTTTTTTTTTTTT\r\n" + 
				 	 "TT.......................TT\r\n" + 
				 	 "T.........................T\r\n" + 
				 	 "T.........TTTTTTT.........T\r\n" + 
				 	 "T.........................T\r\n" + 
				 	 "T.........TTTTTTT.........T\r\n" + 
				 	 "T.........................T\r\n" + 
				 	 "T.........................T\r\n" + 
				 	 "T..........TTTTT..........T\r\n" + 
				 	 "T..........T...T..........T\r\n" + 
				 	 "T..........T...T..........T\r\n" + 
				 	 "T......TTTTTTTTTTTTT......T\r\n" + 
				 	 "T......T...........T......T\r\n" + 
				 	 "T......T...........T......T\r\n" + 
				 	 "T......T...........T......T\r\n" + 
				 	 "T......TTTTTTTTTTTTT......T\r\n" + 
				 	 "T.........................T\r\n" + 
				 	 "T.........................T\r\n" + 
				 	 "T.........................T\r\n" + 
				 	 "T.........................T\r\n" + 
				 	 "T.........................T\r\n" + 
				 	 "T.........................T\r\n" + 
				 	 "T.........................T\r\n" + 
				 	 "T.........................T\r\n" + 
				 	 "T.........................T\r\n" + 
				 	 "T.........................T";
		String mapArr[] = map.split("\r\n");
		char[][] charMatrix = new char[29][27];
		for(int i = 0; i < mapArr.length; i++) {
			charMatrix[i] = mapArr[i].toCharArray();
		}
			
		try {
			loadCharMatrix(charMatrix);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return true;
	}
	
}









