package org.usfirst.frc.team4585.model;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Winch implements HuskyClass {
	
	private final int WINCH_PORT = 4;
	private final int POT_PORT = 3;

	private Joystick joy;
	private Spark winch = new Spark(WINCH_PORT);
	
	private AnalogPotentiometer pot = new AnalogPotentiometer(POT_PORT, 20.0d * Math.PI * 0.77d);
	
	private double speed;
	private double position;
	
	
	
	public Winch(Joystick J) {
		joy = J;
	}
	

	@Override
	public void teleopInit() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doTeleop() {
		winch.set(speed);
	}

	@Override
	public void autoInit() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doAuto() {
		

	}

	@Override
	public double[] getInfo() {
		// TODO Auto-generated method stub
		return new double[] {pot.get()};
	}

	@Override
	public void giveInfo(double[] info) {
		speed = info[0];

	}

}
