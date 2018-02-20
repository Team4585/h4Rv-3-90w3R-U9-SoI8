package org.usfirst.frc.team4585.model;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class ArmActuator implements HuskyClass {
	
	private final double MAX_AMPS = 2;
	private final int POT_PORT = 1;
	private final int ARMACT_PORT = 6;
	
	private final double ACT_SPEED = 0.10;
	
	private AnalogPotentiometer pot = new AnalogPotentiometer(POT_PORT, 20.0d * Math.PI * 1.5d);
	private PowerDistributionPanel powReg = new PowerDistributionPanel();
	
	private double targPos;
	private double ampOffSet = 0;
	
	private Joystick joy;
	private Spark actuator = new Spark(ARMACT_PORT);
	
	
	public ArmActuator(Joystick J) {
		
		joy = J;
	}

	@Override
	public void teleopInit() {
		targPos = pot.get();
		// TODO Auto-generated method stub

	}

	@Override
	public void doTeleop() {
		
		
		
		
//		/*
		if (joy.getRawButton(4) && !joy.getRawButton(6)) {
			targPos += ACT_SPEED;
		}
		else if (!joy.getRawButton(4) && joy.getRawButton(6)) {
			targPos -= ACT_SPEED;
		}
		
		if (joy.getRawButtonReleased(4) || joy.getRawButtonReleased(6)) {
			//targPos = pot.get();
		}
		
		
		actuator.set(addAmpLimit((pot.get() - targPos) / 2));
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
		return input - ampOffSet;
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
