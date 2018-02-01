package org.usfirst.frc.team4585.model.auto;

import java.util.ArrayList;

import org.usfirst.frc.team4585.model.*;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class GhostController implements HuskyClass {
	
	private ArrayList<AutoTask> taskList = new ArrayList<AutoTask>();
	
	private int counter;
	
	private double[] chassisInfo;
	private double[] armInfo;
	private double[] clawInfo;
	private double[] posInfo;
	private double[] teleTargPos;	//experemental
	private double teleTargAngle;
	
	private Chassis chassis;
	private Arm arm;
	private Claw claw;
	private PositionTracker tracker;
	private Joystick joy;
	
	
	public GhostController(Chassis Ch, Arm A, Claw Cl, PositionTracker T, Joystick J) {
		chassis = Ch;
		arm = A;
		claw = Cl;
		tracker = T;
		joy = J;
		
		taskList.add(new AutoTask(TaskType.goTo, new double[] {5, 5}));
		taskList.add(new AutoTask(TaskType.goTo, new double[] {5, 5}));
		taskList.add(new AutoTask(TaskType.goTo, new double[] {5, 5}));
	}
	
	@Override
	public void teleopInit() {
		teleTargPos = new double[] {0, 0};
		teleTargAngle = 0;

	}

	@Override
	public void doTeleop() {
		
		posInfo = tracker.getInfo();
		teleTargAngle += joy.getRawAxis(2) * (((-joy.getRawAxis(3) + 1) / 4) + 0.5);
		
		chassis.giveInfo(new double[] {-joy.getRawAxis(1) * (((-joy.getRawAxis(3) + 1) / 4) + 0.5),
				angleAccel(posInfo[2], teleTargAngle)});
		
		/*  fake mecanum
		targPos[0] += -joy.getRawAxis(0) * (((-joy.getRawAxis(3) + 1) / 4) + 0.5);
		targPos[1] += joy.getRawAxis(1) * (((-joy.getRawAxis(3) + 1) / 4) + 0.5);
		driveTo(targPos);
		*/
	}

	@Override
	public void autoInit() {
		counter = 0;

	}

	@Override
	public void doAuto() {
		
		driveTo(new double[] {5, 0});
		
		
		/*
		switch(taskList.get(counter).getType()){
		
		case goTo:
			if(driveTo(taskList.get(counter).getInfo())) {
				counter++;
			}
			break;
		
		default:
			break;
			
		}
		*/
	}


	@Override
	public double[] getInfo() {
		
		return null;
	}

	@Override
	public void giveInfo(double[] info) {
		// TODO Auto-generated method stub
		
	}
	
	private boolean driveTo(double[] I) {
		double[] buffer = {0, 0};
		double targAngle;
		posInfo = tracker.getInfo();
		
		targAngle = Math.toDegrees(Math.atan2(I[0] - posInfo[0], I[1] - posInfo[1]));
		
		buffer[0] = 0;
		buffer[1] = angleAccel(posInfo[2], targAngle);
		
		chassis.giveInfo(buffer);
		
		SmartDashboard.putNumber("heading", posInfo[2]);
		
		
		return false;
	}
	
	private double angleAccel(double inAngle, double targAngle) {
		double output;
		
		output = (targAngle - inAngle) / 45;
		
		if (output < 0.5 && !(output < 0.1)) {
			output = 0.5;
		}
		else if (output > -0.5 && !(output > -0.1)) {
			output = -0.5;
		}
		else if (Math.abs(output) < 0.05) {
			output = 0;
		}

		
		return output;
	}
	
}
