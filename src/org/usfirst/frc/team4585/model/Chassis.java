package org.usfirst.frc.team4585.model;

import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Chassis implements HuskyClass {
	
	private final int LEFT_ENCODER_PORT_A = 0;
	private final int LEFT_ENCODER_PORT_B = 1;
	private final int RIGHT_DRIVE_PORT = 8;
	private final int LEFT_DRIVE_PORT = 9;
	private final int SONAR_PORT = 0;
	private final double DISTANCE_PER_PULSE = -0.089;
	
	private DifferentialDrive robotDrive 
			= new DifferentialDrive(new Spark(RIGHT_DRIVE_PORT), new Spark(LEFT_DRIVE_PORT));
	
	private AnalogSonar sonar = new AnalogSonar(SONAR_PORT);
	private BuiltInAccelerometer accel;
	private  ADXRS450_Gyro gyro;
	private Encoder encoder = new Encoder(LEFT_ENCODER_PORT_A, LEFT_ENCODER_PORT_B);
	
	private double angle;
	
	
	
	Joystick joy;
	Timer timer;
	
	public Chassis(Joystick J, Timer T) {
		joy = J;
		timer = T;
		accel = new BuiltInAccelerometer();
		gyro = new ADXRS450_Gyro();
		encoder.setDistancePerPulse(DISTANCE_PER_PULSE);
	}
	
	@Override
	public void teleopInit() {
		encoder.reset();
	}
	
	@Override
	public void doTeleop() {
		robotDrive.arcadeDrive(-joy.getRawAxis(1) * (((-joy.getRawAxis(3) + 1) / 4) + 0.5), joy.getRawAxis(2) * (((-joy.getRawAxis(3) + 1) / 4) + 0.5));
		
		SmartDashboard.putNumber("inches:", encoder.getDistance());
		
		SmartDashboard.putNumber("X", accel.getX());
		SmartDashboard.putNumber("Y", accel.getY());
		SmartDashboard.putNumber("Z", accel.getZ());
		SmartDashboard.putNumber("Direction", gyro.getAngle() % 180);
		SmartDashboard.putNumber("Rate:", gyro.getRate());
		
		SmartDashboard.putNumber("joystick axis one:", joy.getRawAxis(1));
		SmartDashboard.putNumber("in: ",sonar.getInches());
	}
	
	@Override
	public void autoInit() {
		gyro.reset();
		angle = 90;
		encoder.reset();
	}
	
	@Override
	public void doAuto() {
		
		
		
		if (timer.get() > 0) {
			robotDrive.arcadeDrive(0, (angle - gyro.getAngle())/10); // drive forwards half speed
		} else {
			robotDrive.stopMotor(); // stop robot
		}
		
		SmartDashboard.putNumber("inches:", encoder.getDistance());
		
		SmartDashboard.putNumber("X", accel.getX());
		SmartDashboard.putNumber("Y", accel.getY());
		SmartDashboard.putNumber("Z", accel.getZ());
		SmartDashboard.putNumber("Direction", gyro.getAngle() % 180);
		SmartDashboard.putNumber("Rate:", gyro.getRate());
		
		SmartDashboard.putNumber("joystick axis one:", joy.getRawAxis(1));
		SmartDashboard.putNumber("in: ",sonar.getInches());
	}
	
}
