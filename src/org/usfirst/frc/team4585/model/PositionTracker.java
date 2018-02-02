package org.usfirst.frc.team4585.model;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PositionTracker implements HuskyClass {
	
	private final int LEFT_ENCODER_PORT_A = 0;
	private final int LEFT_ENCODER_PORT_B = 1;
	private final int RIGHT_ENCODER_PORT_A = 2;
	private final int RIGHT_ENCODER_PORT_B = 3;
	private final double LEFT_DISTANCE_PER_PULSE = -(6 * Math.PI) / 2562; //feet
	private final double RIGHT_DISTANCE_PER_PULSE = -(6 * Math.PI) / 2562; //feet
	
	private Encoder leftEncoder = new Encoder(LEFT_ENCODER_PORT_A, LEFT_ENCODER_PORT_B);
	private Encoder rightEncoder = new Encoder(RIGHT_ENCODER_PORT_A, RIGHT_ENCODER_PORT_B);
	
	private BuiltInAccelerometer accel;
	private ADXRS450_Gyro gyro;
	private Timer timer;
	private double oldTime;
	private double dt;
	
	private double encoderXPos;
	private double encoderYPos;
	private double accelXPos;
	private double accelYPos;
	
	private double encoderVelocity;
	
	private int[][] fieldMap = {{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0,},
								   {0},
								   {0},
								   {0},
								   {0}};
	
	public PositionTracker(Timer T) {
		accel = new BuiltInAccelerometer();
		gyro = new ADXRS450_Gyro();
		timer = T;
		
		leftEncoder.setDistancePerPulse(LEFT_DISTANCE_PER_PULSE);
		rightEncoder.setDistancePerPulse(RIGHT_DISTANCE_PER_PULSE);
	}
	
	@Override
	public void teleopInit() {
		encoderXPos = 0;
		encoderYPos = 0;
		
		accelXPos = 0;
		accelYPos = 0;
		
		//accelVelocity = 0;
		gyro.reset();
		
		rightEncoder.reset();
		leftEncoder.reset();
	}

	@Override
	public void doTeleop() {
		double accelXW;
		double accelYW;
		double accelXLoc;
		double accelYLoc;
		double sinTheta;
		double cosTheta;
		double velX;
		double velY;
		
		dt = timer.get() - oldTime;
		
		//accelXLoc = accel.getX() /* * 32.175197*/;
		//accelYLoc = accel.getY() /* * 32.175197*/;
		
		accelXLoc = 0.1;
		accelYLoc = 0;
		
		SmartDashboard.putNumber("accel X", accelXLoc);
		SmartDashboard.putNumber("accel Y", accelYLoc);
		
		sinTheta = Math.sin(Math.toRadians(gyro.getAngle()));
		cosTheta = Math.cos(Math.toRadians(gyro.getAngle()));
		
		SmartDashboard.putNumber("sin", sinTheta);
		SmartDashboard.putNumber("cos", cosTheta);
		
		SmartDashboard.putNumber("Heading", gyro.getAngle());
		
		accelXW = (cosTheta * accelXLoc) - (sinTheta * accelYLoc);
		accelYW = (sinTheta * accelXLoc) + (cosTheta * accelYLoc);
		
		SmartDashboard.putNumber("accelXW", accelXW);
		SmartDashboard.putNumber("accelYW", accelYW);
		
		velX = accelXW * dt;
		velY = accelYW * dt;
		
		SmartDashboard.putNumber("velX", velX);
		SmartDashboard.putNumber("velY", velY);
		
		accelXPos += velX * dt;
		accelYPos += velY * dt;
		
		encoderVelocity = (leftEncoder.getRate() + rightEncoder.getRate()) / 2;
		
		encoderXPos += Math.cos(Math.toRadians(gyro.getAngle())) * encoderVelocity * dt;
		encoderYPos += Math.sin(Math.toRadians(gyro.getAngle())) * encoderVelocity * dt;
		

		SmartDashboard.putNumber("encoder X pos", encoderXPos);
		SmartDashboard.putNumber("encoder Y pos", encoderYPos);
		
		SmartDashboard.putNumber("accel X pos", accelXPos);
		SmartDashboard.putNumber("accel Y pos", accelYPos);
		
		SmartDashboard.putNumber("meters:", (rightEncoder.getDistance() + leftEncoder.getDistance()) / 2);
		SmartDashboard.putNumber("right:", rightEncoder.getDistance());
		SmartDashboard.putNumber("left:", leftEncoder.getDistance());
		//SmartDashboard.putNumber("accelVelocity:", accelXVelocity);
		
		SmartDashboard.putNumber("dt", dt);
		
		SmartDashboard.putNumber("leftEncoder", leftEncoder.get());
		SmartDashboard.putNumber("rightEncoder", rightEncoder.get());
		
		
		oldTime = timer.get();
		/*	
		if (leftEncoder.getRate() > maxVelocity) {
				maxVelocity = leftEncoder.getRate();
			}
			if (gyro.getRate() > maxTurnVelocity) {
				maxTurnVelocity = gyro.getRate();
			}
			if (accel.getX() > maxAccel) {
				maxAccel = accel.getX();
			}
			if (accel.getX() < minAccel) {
				minAccel = accel.getX();
			}
			
			SmartDashboard.putNumber("X", accel.getX());
			SmartDashboard.putNumber("Y", accel.getY());
			SmartDashboard.putNumber("Z", accel.getZ());
			SmartDashboard.putNumber("Direction", gyro.getAngle() % 180);
			SmartDashboard.putNumber("Rate:", gyro.getRate());
		}
		*/

	}

	@Override
	public void autoInit() {
		encoderXPos = 0;
		encoderYPos = 0;
		
		accelXPos = 0;
		accelYPos = 0;
		
		//accelVelocity = 0;
		gyro.reset();
		
		rightEncoder.reset();
		leftEncoder.reset();
	}

	@Override
	public void doAuto() {
		double accelXW;
		double accelYW;
		double accelXLoc;
		double accelYLoc;
		double sinTheta;
		double cosTheta;
		double velX;
		double velY;
		
		
		dt = timer.get() - oldTime;
		
		accelXLoc = accel.getX() * 32.175197;
		accelYLoc = accel.getY() * 32.175197;
		
		sinTheta = Math.sin(Math.toRadians(gyro.getAngle()));
		cosTheta = Math.cos(Math.toRadians(gyro.getAngle()));
		
		accelXW = (cosTheta * accelXLoc) - (sinTheta * accelYLoc);
		accelYW = (sinTheta * accelXLoc) + (cosTheta * accelYLoc);
		
		velX = accelXW * dt;
		velY = accelYW * dt;
		
		accelXPos += velX * dt;
		accelYPos += velY * dt;
		
		encoderVelocity = (leftEncoder.getRate() + rightEncoder.getRate()) / 2;
		
		encoderXPos += Math.cos(Math.toRadians(gyro.getAngle())) * encoderVelocity * dt;
		encoderYPos += Math.sin(Math.toRadians(gyro.getAngle())) * encoderVelocity * dt;
		

		
		SmartDashboard.putNumber("encoder X pos", encoderXPos);
		SmartDashboard.putNumber("encoder Y pos", encoderYPos);
		
		SmartDashboard.putNumber("accel X pos", accelXPos);
		SmartDashboard.putNumber("accel Y pos", accelYPos);
		
		SmartDashboard.putNumber("meters:", (rightEncoder.getDistance() + leftEncoder.getDistance()) / 2);
		SmartDashboard.putNumber("right:", rightEncoder.getDistance());
		SmartDashboard.putNumber("left:", leftEncoder.getDistance());
		//SmartDashboard.putNumber("accelVelocity:", accelXVelocity);
		
		SmartDashboard.putNumber("dt", dt);
		
		SmartDashboard.putNumber("leftEncoder", leftEncoder.get());
		SmartDashboard.putNumber("rightEncoder", rightEncoder.get());
		
		
		oldTime = timer.get();
	}

	@Override
	public double[] getInfo() {
		return new double[] {encoderXPos, encoderYPos, gyro.getAngle()};
	}

	@Override
	public void giveInfo(double[] info) {
		// TODO Auto-generated method stub

	}

}
