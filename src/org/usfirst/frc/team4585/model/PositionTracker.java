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
	
	private double xPos;
	private double yPos;
	private double accelXW;
	private double accelYW;
	private double velX;
	private double velY;
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
		xPos = 0;
		yPos = 0;
		
		//accelVelocity = 0;
		gyro.reset();
		
		rightEncoder.reset();
		leftEncoder.reset();
	}

	@Override
	public void doTeleop() {
		dt = timer.get() - oldTime;
		//accelXVelocity += accel.getY() * dt * 32.175197; // gs to feet per second^2
		//accelYVelocity += accel.getX() * dt * 32.175197; // backwards on purpose
		
		accelXW = Math.cos(Math.toRadians(gyro.getAngle())) * accel.getX() * 32.175197;
		accelYW = Math.sin(Math.toRadians(gyro.getAngle())) * accel.getY() * 32.175197;
		
		velX = accelXW * dt;
		velY = accelYW * dt;
		
		encoderVelocity = (leftEncoder.getRate() + rightEncoder.getRate()) / 2;
		
		xPos += Math.cos(Math.toRadians(gyro.getAngle())) * encoderVelocity * dt;
		yPos += Math.sin(Math.toRadians(gyro.getAngle())) * encoderVelocity * dt;
		
		//robot_position_x += cos(robot_heading) * robot_velocity * dt;
		//robot_position_y += sin(robot_heading) * robot_velocity * dt;
		
		SmartDashboard.putNumber("X pos", xPos);
		SmartDashboard.putNumber("Y pos", yPos);
		
		SmartDashboard.putNumber("meters:", (rightEncoder.getDistance() + leftEncoder.getDistance()) / 2);
		SmartDashboard.putNumber("right:", rightEncoder.getDistance());
		SmartDashboard.putNumber("left:", leftEncoder.getDistance());
		//SmartDashboard.putNumber("accelVelocity:", accelXVelocity);
		
		SmartDashboard.putNumber("accel X", accel.getX());
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
		xPos = 0;
		yPos = 0;
		
		//accelVelocity = 0;
		gyro.reset();
		
		rightEncoder.reset();
		leftEncoder.reset();
	}

	@Override
	public void doAuto() {
		dt = timer.get() - oldTime;
		
		//accelVelocity += accel.getX() * dt * 32.175197; // gs to feet per second^2
		
		encoderVelocity = (leftEncoder.getRate() + rightEncoder.getRate()) / 2;
		
		xPos += Math.cos(Math.toRadians(gyro.getAngle())) * encoderVelocity * dt;
		yPos += Math.sin(Math.toRadians(gyro.getAngle())) * encoderVelocity * dt;
		
		//robot_position_x += cos(robot_heading) * robot_velocity * dt;
		//robot_position_y += sin(robot_heading) * robot_velocity * dt;
		
		SmartDashboard.putNumber("X pos", xPos);
		SmartDashboard.putNumber("Y pos", yPos);
		
		SmartDashboard.putNumber("meters:", (rightEncoder.getDistance() + leftEncoder.getDistance()) / 2);
		SmartDashboard.putNumber("right:", rightEncoder.getDistance());
		SmartDashboard.putNumber("left:", leftEncoder.getDistance());
		//SmartDashboard.putNumber("accelVelocity:", accelVelocity);
		
		SmartDashboard.putNumber("accel X", accel.getX());
		SmartDashboard.putNumber("dt", dt);
		
		SmartDashboard.putNumber("leftEncoder", leftEncoder.get());
		SmartDashboard.putNumber("rightEncoder", rightEncoder.get());
		
		
		oldTime = timer.get();
	}

	@Override
	public double[] getInfo() {
		return new double[] {xPos, yPos, gyro.getAngle()};
	}

	@Override
	public void giveInfo(double[] info) {
		// TODO Auto-generated method stub

	}

}
