package org.usfirst.frc.team4585.model;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Chassis {
	
	private final int RIGHT_DRIVE_PORT = 8;
	private final int LEFT_DRIVE_PORT = 9;
	private final int SONAR_PORT = 0;
	
	private DifferentialDrive robotDrive 
			= new DifferentialDrive(new Spark(RIGHT_DRIVE_PORT), new Spark(LEFT_DRIVE_PORT));
	private AnalogSonar sonar = new AnalogSonar(SONAR_PORT);
	
	Joystick joy;
	
	public Chassis(Joystick J) {
		joy = J;
		SmartDashboard.putNumber("YO", 2);
	}
	
	public void doTeleop() {
		robotDrive.arcadeDrive(stick.getRawAxis(1), stick.getRawAxis(2));
	}
	
	public void doAuto() {
		
	}
	
}
