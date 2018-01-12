/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4585.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	private DifferentialDrive robotDrive
			= new DifferentialDrive(new Spark(8), new Spark(9));
	private Joystick stick = new Joystick(0);
	private Timer timer = new Timer();
	
	private AnalogSonar sonar = new AnalogSonar(0);
	
	private SmartDashboard dash = new SmartDashboard();
	
	

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		
	}

	/**
	 * This function is run once each time the robot enters autonomous mode.
	 */
	@Override
	public void autonomousInit() {
		timer.reset();
		timer.start();
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		/*
		// Drive for 2 seconds
		if (timer.get() < 2.0) {
			robotDrive.arcadeDrive(0.5, 0.0); // drive forwards half speed
		} else {
			robotDrive.stopMotor(); // stop robot
		}*/
		
		dash.putNumber("cm: ",sonar.getCentimeters());
		dash.putNumber("mm: ",sonar.getMillimeters());
		dash.putNumber("in: ",sonar.getInches());
		dash.putNumber("volts: ",sonar.getVoltage());
		dash.putNumber("value: ",sonar.getValue());
	}

	/**
	 * This function is called once each time the robot enters teleoperated mode.
	 */
	@Override
	public void teleopInit() {
	}

	/**
	 * This function is called periodically during teleoperated mode.
	 */
	@Override
	public void teleopPeriodic() {
		robotDrive.arcadeDrive(-stick.getRawAxis(1), stick.getRawAxis(2));
		
		dash.putNumber("cm: ",sonar.getCentimeters());
		dash.putNumber("mm: ",sonar.getMillimeters());
		dash.putNumber("in: ",sonar.getInches());
		dash.putNumber("volts: ",sonar.getVoltage());
		dash.putNumber("value: ",sonar.getValue());
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}
