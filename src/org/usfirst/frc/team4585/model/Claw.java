package org.usfirst.frc.team4585.model;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//.33
//.34

public class Claw implements HuskyClass {
	private final int CLAW_PORT = 5;
	private final double MAX_AMPS = 3;
	private final int MOVE_TIME = 2;
	
	private Spark claw = new Spark(CLAW_PORT);
	private PowerDistributionPanel powReg = new PowerDistributionPanel();
	private HuskyJoy joy;
	private Timer timer = new Timer();
	
	private double ampOffSet = 0;
	private boolean targState = false; // open
	private boolean oldState = true; // closed
	
	public Claw(HuskyJoy J) {
		joy = J;
		timer.reset();
		timer.start();
	}
	
	@Override
	public void teleopInit() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doTeleop() {
		
		SmartDashboard.putNumber("Axis 3:", joy.getRawAxis(3));
		
		if (joy.getRawButton(5) == true && joy.getRawButton(3) == false) {
			claw.set(0.5);
		}
		if (joy.getRawButton(5) == false && joy.getRawButton(3) == true) {
			claw.set(-0.5);
		}
		if (joy.getRawButton(5) == false && joy.getRawButton(3) == false) {
			claw.set(0);
		}
		if (joy.getRawButton(5) == true && joy.getRawButton(3) == true) {
			claw.set(0);
		}
	}

	@Override
	public void autoInit() {
		targState = true;

	}

	@Override
	public void doAuto() {
		if (targState != oldState) {
			timer.reset();
			timer.start();
			oldState = targState;
		}
		if (targState) {
			claw.set(0.3);
		}
		else {
			claw.set(-0.3);
		}

	}
	
	private double addAmpLimit(double input) {
		double amps = powReg.getCurrent(6);
		if (amps > MAX_AMPS) {
			ampOffSet += 0.1;
		}
		else {
			ampOffSet -= 0.1;
		}
		if (ampOffSet < 0){
			ampOffSet = 0;
		}
		SmartDashboard.putNumber("claw amps", amps);
		SmartDashboard.putNumber("claw offset", ampOffSet);
		return input - ampOffSet;
	}

	@Override
	public double[] getInfo() {
		
		return new double[] {(timer.get() > MOVE_TIME) ? 1:0};
	}

	@Override
	public void giveInfo(double[] info) {
		targState = (info[0] == 1.0) ? true:false;
		
	}

}
