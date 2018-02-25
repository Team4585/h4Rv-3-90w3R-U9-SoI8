package org.usfirst.frc.team4585.model;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class HuskyPID {
	private double kp, ki, kd;
	private double maxOut;
	private double oldTime;
	private double oldError;
	private double olderError;
	
	private Timer timer = new Timer();
	
	public HuskyPID(double KP, double KI, double KD, double MaxOut) {
		kp = KP;
		ki = KI;
		kd = KD;
		maxOut = MaxOut;
		
		timer.start();
	}
	
	public double calculate(double inVal, double targVal) {
		double currTime = timer.get();
		
		double dt = currTime - oldTime;
		
		double error = inVal - targVal;
		double errSum = (error * dt) + (oldError * dt) + (olderError * dt); // assumes same dt values
		double errDer = (error - oldError) / dt;
		
		double buffer = 0;
		
		buffer += error * kp;
		buffer += errSum * ki;
		buffer += errDer * kd;
		
		oldTime = currTime;
		
		olderError = oldError;
		oldError = error;
		
		SmartDashboard.putNumber("PID out", buffer);
		
		return buffer;
	}
	
	
}
