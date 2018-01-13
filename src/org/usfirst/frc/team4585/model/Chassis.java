package org.usfirst.frc.team4585.model;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Chassis {
	
	private final int RIGHT_DRIVE_PORT = 8;
	private final int LEFT_DRIVE_PORT = 9;
	private final int SONAR_PORT = 0;
	
	private DifferentialDrive robotDrive 
			= new DifferentialDrive(new Spark(RIGHT_DRIVE_PORT), new Spark(LEFT_DRIVE_PORT));
	private AnalogSonar sonar = new AnalogSonar(SONAR_PORT);
	private
	
	Joystick joy;
	Timer timer;
	
	public Chassis(Joystick J, Timer T) {
		joy = J;
		timer = T;
		
	}
	
	public void doTeleop() {
		robotDrive.arcadeDrive(-joy.getRawAxis(1), joy.getRawAxis(2));
		
		SmartDashboard.putNumber("joystick axis one:", joy.getRawAxis(1));
		SmartDashboard.putNumber("cm: ",sonar.getCentimeters());
		SmartDashboard.putNumber("mm: ",sonar.getMillimeters());
		SmartDashboard.putNumber("in: ",sonar.getInches());
		SmartDashboard.putNumber("volts: ",sonar.getVoltage());
		SmartDashboard.putNumber("value: ",sonar.getValue());
	}
	
	public void doAuto() {
		
		// Drive for 2 seconds
		if (timer.get() < 2.0) {
			robotDrive.arcadeDrive(0.5, 0.0); // drive forwards half speed
		} else {
			robotDrive.stopMotor(); // stop robot
		}
		
		SmartDashboard.putNumber("cm: ",sonar.getCentimeters());
		SmartDashboard.putNumber("mm: ",sonar.getMillimeters());
		SmartDashboard.putNumber("in: ",sonar.getInches());
		SmartDashboard.putNumber("volts: ",sonar.getVoltage());
		SmartDashboard.putNumber("value: ",sonar.getValue());
	}
	
}
