package org.usfirst.frc.team4585.model;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Arm implements HuskyClass {

	private final int ARM_PORT = 7;
	private final int POT_PORT = 5;
	private double target = 0; 
	private double currentAngle;
	private double angle;

	private Spark arm = new Spark(ARM_PORT);

	private HuskyJoy joy;
	private AnalogPotentiometer pot = new AnalogPotentiometer(POT_PORT, 3600, -2000);
	
	
	public Arm(HuskyJoy J) {
		joy = J;
	}
	
	@Override
	public void teleopInit() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doTeleop() {
		
		SmartDashboard.putNumber("Axis 3:", joy.getRawAxis(3));
		SmartDashboard.putNumber("arm pot", pot.get());
		if (joy.getPOV(0) == 0.0) {
			
			arm.set( - (((-joy.getRawAxis(3) + 1) / 4) + 0.5) );
			//arm.set(.5);
		}
		if (joy.getPOV(0) == 180.0) {
			
			arm.set((((-joy.getRawAxis(3) + 1) / 4) + 0.5));
			//arm.set(-.5);
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
		SmartDashboard.putNumber("pot", pot.get());
		
		currentAngle = pot.get();
		 double output = -(target - currentAngle) / 90;
		 arm.set(output);

	}

	@Override
	public double[] getInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void giveInfo(double[] info) {
		target = info[0];
		// TODO Auto-generated method stub
		
	}

}
