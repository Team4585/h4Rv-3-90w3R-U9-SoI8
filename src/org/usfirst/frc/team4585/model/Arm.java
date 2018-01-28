package org.usfirst.frc.team4585.model;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Arm implements HuskyClass {
	private final int CLAW_PORT = 6;
	
	private Spark arm = new Spark(CLAW_PORT);
	
	private Joystick joy;
	
	
	public Arm(Joystick J) {
		joy = J;
	}
	
	@Override
	public void teleopInit() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doTeleop() {
		
		SmartDashboard.putNumber("Axis 3:", joy.getRawAxis(3));
		
		if (joy.getPOV(0) == 0.0) {
			//claw.set(((-joy.getRawAxis(3) + 1) / 4) + 0.5);
			
			arm.set(((-joy.getRawAxis(3) + 1) / 4) + 0.5);
		}
		if (joy.getPOV(0) == 180.0) {
			
			arm.set( - (((-joy.getRawAxis(3) + 1) / 4) + 0.5));
		}
		if (joy.getPOV(0) == -1.0) {
			arm.set(0);
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
