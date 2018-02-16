package org.usfirst.frc.team4585.model;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Timer;

public class Lifters implements HuskyClass {
	
	private final int JOY = 1;
	private final int SECONDS = 105;
	
	private final int LIFTERS_PORT = 1;
	private Servo lifters = new Servo(LIFTERS_PORT);
	
	private Joystick joy;
	private Timer timer;
	
	
	public Lifters(Joystick J, Timer T) {
		joy = J;
		timer = T;
	}

	@Override
	public void teleopInit() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doTeleop() {
		if (timer.get() >= SECONDS && joy.getRawButton(JOY) == true) {
			lifters.setAngle(0);
		}
		// TODO Auto-generated method stub

	}

	@Override
	public void autoInit() {		
		    lifters.setAngle(30);
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
