package org.usfirst.frc.team4585.model;

public class HuskyMath {
	
	public static double gMod360(double in) {
		double out = in;
		if (in > 360) {
			out = in;
		}
		else if (in >= 360) {
			out = in - 360;
		}
		else if (in < 0) {
			out = in + 360;
		}
		
		return out;
	}
	
	public static double gSmallAngDiff(double a, double b) {
		double out = gMod360(b - a);
		if (out >= 180) {
			out = out - 360;
		}
		
		return out;
	}
	
}
