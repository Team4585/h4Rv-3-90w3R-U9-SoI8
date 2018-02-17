package org.usfirst.frc.team4585.model;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Spark;


public class ArmActuator implements HuskyClass {
	
	private final int POT_PORT = 1;
	private final int ARMACT_PORT = 6;
	
	private final double ACT_SPEED = 0.1;
	
	private AnalogPotentiometer pot = new AnalogPotentiometer(POT_PORT, 20.0d * Math.PI * 1.5d);
	
	private double targPos;
	
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
		
		if (joy.getRawButton(4) && !joy.getRawButton(6)) {
			targPos += ACT_SPEED;
		}
		else if (!joy.getRawButton(4) && joy.getRawButton(6)) {
			targPos -= ACT_SPEED;
		}
		actuator.set((targPos - pot.get()) / 1);
		
		/*
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
		*/

	}

	@Override
	public void autoInit() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doAuto() {
		actuator.set(targPos - pot.get());	//pid it

	}
	
	
	@Override
	public double[] getInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void giveInfo(double[] info) {
		targPos = info[0];

	}

}
