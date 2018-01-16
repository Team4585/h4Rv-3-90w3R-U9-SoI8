package org.usfirst.frc.team4585.model;


public class AccelerationController {
	private double jerk;
	private double maxVelocity;
	private double acceleration;
	
	public AccelerationController(double vel, double A, double J) {
		maxVelocity = vel;
		acceleration = A;
		jerk = J;
	}
	
	public double calculateVelocity(double currVelocity, double distanceLeft) {
		double buffer;
		double a = acceleration;
		double d = distanceLeft;
		double j = jerk;
		double i = currVelocity;
		
		//f = (2 a d j)/(3^(1/3) (sqrt(3) sqrt(27 d^4 j^12 - 8 a^3 d^3 j^12) + 9 d^2 j^6)^(1/3))
		// + (sqrt(3) sqrt(27 d^4 j^12 - 8 a^3 d^3 j^12) + 9 d^2 j^6)^(1/3) / (3^(2/3) j^3)
		// + s
		
		buffer = (2 * a * d * j) / (Math.pow(3, (1 / 3)) * Math.pow(Math.sqrt(3) * Math.sqrt((27 * Math.pow(d, 4) * Math.pow(j, 12)) - (8 * Math.pow(a, 3) * Math.pow(d, 3) * Math.pow(j, 12))) + (9 * Math.pow(d, 2) * Math.pow(j, 6)), 1 / 3));
		buffer += Math.pow(Math.sqrt(3) * Math.sqrt((27 * Math.pow(d, 4) * Math.pow(j, 12)) - (8 * Math.pow(a, 3) * Math.pow(d, 3) * Math.pow(j, 12))) + (9 * Math.pow(d, 2) * Math.pow(j, 6)), 1 / 3) / (Math.pow(3, 2 / 3) * Math.pow(j, 3));
		buffer += i;
		
		return buffer;
	}
	
	public double getMapedVelocity(double V, double D) {
		return calculateVelocity(V, D);
	}
	
	public void setMaxJerk(double val) {
		jerk = val;
	}
	
	public void setMaxVelocity(double val) {
		maxVelocity = val;
	}

}

