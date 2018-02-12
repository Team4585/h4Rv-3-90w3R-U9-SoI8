package org.usfirst.frc.team4585.model.auto;

import org.usfirst.frc.team4585.model.AnalogSonar;
import org.usfirst.frc.team4585.model.HuskyClass;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PositionTracker implements HuskyClass {
	
	private final int LEFT_ENCODER_PORT_A = 0;
	private final int LEFT_ENCODER_PORT_B = 1;
	private final int RIGHT_ENCODER_PORT_A = 2;
	private final int RIGHT_ENCODER_PORT_B = 3;
	/*
	private final double LEFT_DISTANCE_PER_PULSE = -(6 * Math.PI) / 2562; //feet
	private final double RIGHT_DISTANCE_PER_PULSE = -(6 * Math.PI) / 2562; //feet
	*/
	private final double LEFT_DISTANCE_PER_PULSE = (617.0d / 6.0d) / 13976.0d;
	private final double RIGHT_DISTANCE_PER_PULSE = (617.0d / 6.0d) / 14032.0d;
	
	private final double ACCEL_FACTOR = 32.175197; //32.175197
	private final double ACCEL_DEAD = 0.1;
	private final double VEL_DEAD = 0.1 * ACCEL_FACTOR;
	
	private Encoder leftEncoder = new Encoder(LEFT_ENCODER_PORT_A, LEFT_ENCODER_PORT_B);
	private Encoder rightEncoder = new Encoder(RIGHT_ENCODER_PORT_A, RIGHT_ENCODER_PORT_B);
	
	private AnalogSonar frontSonar = new AnalogSonar(1);
	
	private BuiltInAccelerometer accel;
	private ADXRS450_Gyro gyro;
	private Timer timer;
	private double oldTime;
	private double dt;
	
	private double encoderXPos;
	private double encoderYPos;
	private double accelXPos;
	private double accelYPos;
	private double modAngle;
	private double encoderVelocity;
	
	private double accelXLoc = 0;
	private double accelYLoc = 0;
	private double velX2 = 0;
	private double velY2 = 0;
	
	private SendableChooser<Integer> stationChooser = new SendableChooser<>();
	
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
	
	public void dashInit() {
		stationChooser.addDefault("Left", 0);
		stationChooser.addObject("Middle", 1);
		stationChooser.addObject("Right", 2);
		SmartDashboard.putData("Robot Driver Station", stationChooser);
	}
	
	@Override
	public void teleopInit() {
		encoderXPos = 0;
		encoderYPos = 0;
		
		accelXPos = 0;
		accelYPos = 0;
		
		velX2 = 0;
		velY2 = 0;
		
		//accelVelocity = 0;
		gyro.reset();
		
		rightEncoder.reset();
		leftEncoder.reset();
	}

	@Override
	public void doTeleop() {
		double accelXW;
		double accelYW;
		double sinTheta;
		double cosTheta;
		
		
		dt = timer.get() - oldTime;
		
		accelXLoc = accel.getX() /* * 32.175197*/;
		if(Math.abs(accelXLoc) < ACCEL_DEAD) {
			accelXLoc = 0.0;
		}
		accelXLoc *= ACCEL_FACTOR;
		
		//accelXLoc = 0.1;
		
		accelYLoc = accel.getY() /* * 32.175197*/;
		if(Math.abs(accelYLoc) < ACCEL_DEAD) {
			accelYLoc = 0.0;
		}
		accelYLoc *= ACCEL_FACTOR;
		//accelYLoc = 0;
		
		modAngle = ((gyro.getAngle() + 180) % 360) -180;
		//modAngle = 30;
		
		SmartDashboard.putNumber("accel X", accelXLoc);
		SmartDashboard.putNumber("accel Y", accelYLoc);
		
		sinTheta = Math.sin(Math.toRadians(modAngle));
		cosTheta = Math.cos(Math.toRadians(modAngle));
		
		SmartDashboard.putNumber("sin", sinTheta);
		SmartDashboard.putNumber("cos", cosTheta);
		
		SmartDashboard.putNumber("Heading", gyro.getAngle());
		SmartDashboard.putNumber("modAngle", modAngle);
		
		accelXW = (cosTheta * accelXLoc) - (sinTheta * accelYLoc);
		accelYW = (sinTheta * accelXLoc) + (cosTheta * accelYLoc);
		
		SmartDashboard.putNumber("accelXW", accelXW);
		SmartDashboard.putNumber("accelYW", accelYW);
		
		velX2 += accelXW * dt;
		SmartDashboard.putNumber("velX2", velX2);
		if (accelXW == 0.0 && Math.abs(velX2) < VEL_DEAD) {
			velX2 = 0;
		}
		
		velY2 += accelYW * dt;
		SmartDashboard.putNumber("velY2", velY2);
		if (accelYW == 0.0 && Math.abs(velY2) < VEL_DEAD) {
			velY2 = 0;
		}
		
		
		SmartDashboard.putNumber("velY2", velY2);
		
		accelXPos += velX2 * dt;
		accelYPos += velY2 * dt;
		
		encoderVelocity = (leftEncoder.getRate() + rightEncoder.getRate()) / 2;
		
		SmartDashboard.putNumber("R encoder dist", rightEncoder.getDistance());
		SmartDashboard.putNumber("L encoder dist", leftEncoder.getDistance());
		
		encoderXPos += Math.sin(Math.toRadians(modAngle)) * encoderVelocity * dt;
		encoderYPos += Math.cos(Math.toRadians(modAngle)) * encoderVelocity * dt;
		

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
		
		encoderYPos = 1;
		
		switch (stationChooser.getSelected()){
		
		case 0:
			encoderXPos = 7;
			break;
			
		case 1:
			encoderXPos = 13;
			break;
			
		case 2:
			encoderXPos = 23;
			break;
			
		default:
			encoderXPos = 7;
			break;
		
		}
		
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
		
		//accelXLoc = accel.getX() /* * 32.175197*/;
		//accelYLoc = accel.getY() /* * 32.175197*/;
		
		accelXLoc = 0.1;
		accelYLoc = 0;
		modAngle = ((gyro.getAngle() + 180) % 360) -180;
		
		SmartDashboard.putNumber("accel X", accelXLoc);
		SmartDashboard.putNumber("accel Y", accelYLoc);
		
		sinTheta = Math.sin(Math.toRadians(modAngle));
		cosTheta = Math.cos(Math.toRadians(modAngle));
		
		SmartDashboard.putNumber("sin", sinTheta);
		SmartDashboard.putNumber("cos", cosTheta);
		
		SmartDashboard.putNumber("Heading", gyro.getAngle());
		SmartDashboard.putNumber("modAngle", modAngle);
		
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
		
		SmartDashboard.putNumber("Left pulse", leftEncoder.get());
		SmartDashboard.putNumber("Right pulse", rightEncoder.get());
		
		encoderVelocity = (leftEncoder.getRate() + rightEncoder.getRate()) / 2;
		
		encoderXPos += Math.sin(Math.toRadians(modAngle)) * encoderVelocity * dt;
		encoderYPos += Math.cos(Math.toRadians(modAngle)) * encoderVelocity * dt;
		

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
		return new double[] {encoderXPos, encoderYPos, modAngle, frontSonar.getInches()};
	}

	@Override
	public void giveInfo(double[] info) {
		// TODO Auto-generated method stub

	}

}
