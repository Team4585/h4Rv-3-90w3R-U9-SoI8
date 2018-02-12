package org.usfirst.frc.team4585.model;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Spark;


public class ArmActuator implements HuskyClass {
	
	
	private final int ARMACT_PORT = 0;
	
	private double angle;
	
	private Joystick joy;
	private Spark actuator = new Spark(ARMACT_PORT);
	
	
	public ArmActuator(Joystick J) {
		joy = J;
	}

	@Override
	public void teleopInit() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doTeleop() {
		if (joy.getRawButton(4) == true && joy.getRawButton(6) == false) {
			actuator.set(0.5);
		}
		if (joy.getRawButton(4) == false && joy.getRawButton(6) == true) {
			actuator.set(-0.5);
		}
		if (joy.getRawButton(4) == true && joy.getRawButton(6) == true
     		|| joy.getRawButton(4) == false && joy.getRawButton(6) == false) {
			actuator.set(0);
		}
		// TODO Auto-generated method stub

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
