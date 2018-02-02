package org.usfirst.frc.team4585.model;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Chassis extends DifferentialDrive implements HuskyClass {
	
	private static final int RIGHT_DRIVE_PORT = 8;
	private static final int LEFT_DRIVE_PORT = 9;
	private final int SONAR_PORT = 0;
	
	private AnalogSonar sonar = new AnalogSonar(SONAR_PORT);
	
	private double[] info;
	
	
	
	HuskyJoy joy;
	Timer timer;
	
	public Chassis(HuskyJoy J, Timer T) {
		super(new Spark(RIGHT_DRIVE_PORT), new Spark(LEFT_DRIVE_PORT));
		
		joy = J;
		timer = T;
		
	}
	
	
	@Override
	public void teleopInit() {
		
	}
	
	@Override
	public void doTeleop() {
		arcadeDrive(info[0], info[1]);
		
		
		SmartDashboard.putNumber("joystick axis one:", joy.getRawAxis(1));
		SmartDashboard.putNumber("in: ", sonar.getInches());
	}
	
	@Override
	public void autoInit() {
		
	}
	
	@Override
	public void doAuto() {
		arcadeDrive(info[0], info[1]);
		
		
		SmartDashboard.putNumber("joystick axis one:", joy.getRawAxis(1));
		SmartDashboard.putNumber("in: ",sonar.getInches());
	}


	@Override
	public double[] getInfo() {
		// TODO Auto-generated method stub
		return null;
	}
	

	@Override
	public void giveInfo(double[] I) {
		info = I;
		
	}
	
	
	
}
