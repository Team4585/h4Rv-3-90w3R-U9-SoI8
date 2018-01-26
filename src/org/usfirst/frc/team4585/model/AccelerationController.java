package org.usfirst.frc.team4585.model;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AccelerationController {
	private double jerk;
	private double maxVelocity;
	private double acceleration;
	
	
	public AccelerationController(double vel, double A, double J) {
		maxVelocity = vel;
		acceleration = A;
		jerk = J;
	}
	
	public double calculateVelocity(double currVelocity, double distanceLeft, double time) {
		double buffer;
		double a = acceleration;
		double D = distanceLeft;
		double j = jerk;
		double i = currVelocity;
		
		double M_V = maxVelocity;
		double L_V = 0.1;
		
		//f = (2 a d j)/(3^(1/3) (sqrt(3) sqrt(27 d^4 j^12 - 8 a^3 d^3 j^12) + 9 d^2 j^6)^(1/3))
		// + (sqrt(3) sqrt(27 d^4 j^12 - 8 a^3 d^3 j^12) + 9 d^2 j^6)^(1/3) / (3^(2/3) j^3)
		// + s
		/*
		buffer = (2 * a * d * j) / (Math.pow(3, (1 / 3)) * Math.pow(Math.sqrt(3) * Math.sqrt((27 * Math.pow(d, 4) * Math.pow(j, 12)) - (8 * Math.pow(a, 3) * Math.pow(d, 3) * Math.pow(j, 12))) + (9 * Math.pow(d, 2) * Math.pow(j, 6)), 1 / 3));
		buffer += Math.pow(Math.sqrt(3) * Math.sqrt((27 * Math.pow(d, 4) * Math.pow(j, 12)) - (8 * Math.pow(a, 3) * Math.pow(d, 3) * Math.pow(j, 12))) + (9 * Math.pow(d, 2) * Math.pow(j, 6)), 1 / 3) / (Math.pow(3, 2 / 3) * Math.pow(j, 3));
		buffer += i;
		*/
		
		double v = Math.sqrt(- Math.pow(D / 1.71596, 2) * Math.log(L_V / M_V));
		
		
		buffer = Math.pow(M_V * Math.E, -(Math.pow(time - v, 2) - Math.pow(D / 1.71596, 2)));
		
		SmartDashboard.putNumber("Suposed Vel", buffer);
		return buffer;
		
		
	}
	
	public double getMapedVelocity(double V, double D, double T) {
		return map(calculateVelocity(V, D, T), 0.1, 1, 0.4, 1);
	} //no
	
	public void setMaxJerk(double val) {
		jerk = val;
	}
	
	public void setMaxVelocity(double val) {
		maxVelocity = val;
	}
	
	double map(double x, double in_min, double in_max, double out_min, double out_max) {
	  return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
	}
	

}

