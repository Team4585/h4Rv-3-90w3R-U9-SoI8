package org.usfirst.frc.team4585.model;

import edu.wpi.first.wpilibj.Joystick;

public class HuskyJoy extends Joystick {

	public HuskyJoy(int port) {
		super(port);
		// TODO Auto-generated constructor stub
	}
	
	
	public double getSliderScaled(int axis) {
		double val;
		val = getRawAxis(axis);
		
		if (Math.abs(val) < 0.18) { //deadzone
			val = 0;
		}
		
		return val * (((-getRawAxis(3) + 1) / 4) + 0.5);	//scale to .5 to 1;
		
	}
	
	
}
