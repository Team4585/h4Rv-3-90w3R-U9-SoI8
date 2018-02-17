
package org.usfirst.frc.team4585.robot;

import java.io.IOException;

import org.usfirst.frc.team4585.model.*;
import org.usfirst.frc.team4585.model.auto.*;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;



/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */

public class Robot extends IterativeRobot {
	
	private final int DRIVE_PORT = 0;
	private final int WEAPONS_PORT = 1;
	
	
	private Timer timer = new Timer();
	
	private HuskyJoy driveJoy = new HuskyJoy(DRIVE_PORT);
	private HuskyJoy weaponsJoy = driveJoy;
	//private HuskyJoy weaponsJoy = new HuskyJoy(WEAPONS_PORT);
	
	private Chassis chassis = new Chassis(driveJoy, timer);
	private Arm arm = new Arm(weaponsJoy);
	private Claw claw = new Claw(weaponsJoy);
	private PositionTracker tracker = new PositionTracker(timer);

	private Winch winch = new Winch(weaponsJoy);
	private ArmActuator actuator = new ArmActuator(weaponsJoy);
	
	private Lifters lifters = new Lifters(driveJoy, timer);
	

	private GhostController marcus = new GhostController(chassis, arm, claw, actuator, winch, tracker, driveJoy, weaponsJoy);
  
	private ArduinoCom arduino = new ArduinoCom(claw);
	private VisionCom visCom = new VisionCom();
	
	private double oldTime;
  
	

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		visCom.beginCamera();
		
		tracker.dashInit();
		marcus.dashInit();
		
		arduino.setPins();
		
		
		//DriverStation.getInstance().getGameSpecificMessage();
		
	}

	/**
	 * This function is run once each time the robot enters autonomous mode.
	 */
	@Override
	public void autonomousInit() {
		marcus.autoInit();
		chassis.autoInit();
		arm.autoInit();
		claw.autoInit();
		tracker.autoInit();
		actuator.autoInit();
		lifters.autoInit();

		
		timer.reset();
		timer.start();
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		marcus.doAuto();
		
		chassis.doAuto();
		arm.doAuto();
		claw.doAuto();
		tracker.doAuto();
		actuator.doAuto();
		
		arduino.setPins();
		
	}

	/**
	 * This function is called once each time the robot enters teleoperated mode.
	 */
	@Override
	public void teleopInit() {
		marcus.teleopInit();
		chassis.teleopInit();
		arm.teleopInit();
		claw.teleopInit();
		tracker.teleopInit();
		actuator.teleopInit();
		
		timer.reset();
		timer.start();
		
		oldTime = timer.get();

	}

	/**
	 * This function is called periodically during teleoperated mode.
	 */
	@Override
	public void teleopPeriodic() {
		marcus.doTeleop();

		chassis.doTeleop();	
		arm.doTeleop();
		claw.doTeleop();
		tracker.doTeleop();
		actuator.doTeleop();
		
		arduino.setPins();
		/*
		if (timer.get() - oldTime > 1) {
			visCom.doStuff();
			oldTime = timer.get();
		}*/
		
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
		arduino.setPins();
		
		if (timer.get() - oldTime > 1) {
			visCom.doStuff();
			oldTime = timer.get();
		}
	}
	
	
	@Override
	public void disabledPeriodic() {
		arduino.setPins();
	}
}
