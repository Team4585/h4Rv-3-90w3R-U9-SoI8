package org.usfirst.frc.team4585.model;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class ArmActuator implements HuskyClass {
	
	private final double MAX_AMPS = 2;
	private final double MAX_EXTEND = 10;
	private final int POT_PORT = 1;
	private final int ARMACT_PORT = 6;
	
	private final double ACT_SPEED = 0.10;
	
	private AnalogPotentiometer pot = new AnalogPotentiometer(POT_PORT, -20.0d * Math.PI * 1.5d, 62.8);
	private PowerDistributionPanel powReg = new PowerDistributionPanel();
	
	private double targPos;
	private double winchPos;
	private double ampOffSet = 0;
	private double armAngle;
	private boolean climbing = false;
	private boolean moving;
	
	
	private Joystick joy;
	private Spark actuator = new Spark(ARMACT_PORT);
	
	
	public ArmActuator(Joystick J) {
		
		joy = J;
	}

	@Override
	public void teleopInit() {
		targPos = pot.get();
		
		climbing = false;
		moving = true;

	}

	@Override
	public void doTeleop() {
//		/*
		/*if (climbing) {
			actuator.set(-0.2);
		}
		else */
		if (joy.getRawButton(6) && !joy.getRawButton(4)) {
			targPos += ACT_SPEED;
			moving = true;
			actuator.set(0.5);
		}
		else if (!joy.getRawButton(6) && joy.getRawButton(4)) {
			targPos -= ACT_SPEED;
			moving = true;
			actuator.set(-0.5);
		}
		else if (moving) {
			targPos = pot.get();
			moving = false;
		}
		else {
			//actuator.set(0);
			actuator.set((distanceLimit(targPos) - pot.get()) / 5);
		}
		
		//actuator.set((pot.get() - distanceLimit(targPos)) / 2);
		
		
		
		SmartDashboard.putNumber("Targ Extend", targPos);
//		*/
		SmartDashboard.putNumber("extend pot", pot.get());
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
		//actuator.set(targPos - pot.get());	//pid it
		actuator.set((pot.get() - distanceLimit(targPos)) / 2);

	}
	
	private double distanceLimit(double targDist) {
		double limitDist = targDist;
		if (targDist * Math.cos(Math.toRadians(armAngle)) >= MAX_EXTEND) {
			limitDist = 15 / Math.cos(Math.toRadians(armAngle));
		}
		
		return limitDist;
	}
	
	private double addAmpLimit(double input) {
		
		double amps = powReg.getCurrent(11);
		if (amps > MAX_AMPS) {
			ampOffSet += 0.1;
		}
		else {
			ampOffSet -= 0.1;
		}
		if (ampOffSet < 0){
			ampOffSet = 0;
		}
		SmartDashboard.putNumber("actuator amps", amps);
		return input - ampOffSet;
	}
	
	public boolean setCliming(boolean state) {
		climbing = state;
		return climbing;
	}
	
	public void giveArmAngle(double AA) {
		armAngle = AA;
	}
	
	@Override
	public double[] getInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void giveInfo(double[] info) {
		winchPos = info[0];

	}

}
