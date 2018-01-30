package org.usfirst.frc.team4585.model;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Winch implements HuskyClass {
	
	private final int WINCH_PORT = 0;

	
	private Joystick joy;
	private Spark winch = new Spark(WINCH_PORT);
	
	
	public Winch(Joystick J) {
		joy = J;
	}
	

	@Override
	public void teleopInit() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doTeleop() {
		if (joy.getRawButton(1) == true) {
			winch.setSpeed(0.5);
		}
		if (joy.getRawButton(1) == false) {
			winch.setSpeed(0);
		}
	}

	@Override
	public void autoInit() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doAuto() {
		// TODO Auto-generated method stub

	}

	@Override
	public double[] getInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void giveInfo(double[] info) {
		// TODO Auto-generated method stub

	}

}
