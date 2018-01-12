/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4585.robot;

import org.usfirst.frc.team4585.model.AnalogSonar;
import org.usfirst.frc.team4585.model.Chassis;

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
	
	private final int JOYSTICK_PORT = 0;
	
	
	//private Timer timer = new Timer();
	private Joystick joy = new Joystick(JOYSTICK_PORT);
	private Chassis chassis = new Chassis(joy);
	

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
		//timer.reset();
		//timer.start();
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
		
		SmartDashboard.putNumber("cm: ",sonar.getCentimeters());
		SmartDashboard.putNumber("mm: ",sonar.getMillimeters());
		SmartDashboard.putNumber("in: ",sonar.getInches());
		SmartDashboard.putNumber("volts: ",sonar.getVoltage());
		SmartDashboard.putNumber("value: ",sonar.getValue());
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
		chassis.doTeleop();
		
		SmartDashboard.putNumber("cm: ",sonar.getCentimeters());
		SmartDashboard.putNumber("mm: ",sonar.getMillimeters());
		SmartDashboard.putNumber("in: ",sonar.getInches());
		SmartDashboard.putNumber("volts: ",sonar.getVoltage());
		SmartDashboard.putNumber("value: ",sonar.getValue());
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}
