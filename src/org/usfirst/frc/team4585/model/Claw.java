package org.usfirst.frc.team4585.model;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Claw implements HuskyClass {
	private final int CLAW_PORT = 6;
	
	private Spark claw = new Spark(CLAW_PORT);
	
	private HuskyJoy joy;
	
	
	public Claw(HuskyJoy J) {
		joy = J;
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
