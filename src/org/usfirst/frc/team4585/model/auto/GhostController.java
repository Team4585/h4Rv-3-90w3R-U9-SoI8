package org.usfirst.frc.team4585.model.auto;

import java.util.ArrayList;

import org.usfirst.frc.team4585.model.*;

public class GhostController implements HuskyClass {
	
	private ArrayList<AutoTask> taskList = new ArrayList<AutoTask>();
	
	
	
	private int counter;
	
	private double[] chassisInfo;
	private double[] armInfo;
	private double[] clawInfo;
	private double[] posInfo;
	
	private Chassis chassis;
	private Arm arm;
	private Claw claw;
	private PositionTracker tracker;
	
	
	public GhostController(Chassis Ch, Arm A, Claw Cl, PositionTracker T) {
		chassis = Ch;
		arm = A;
		claw = Cl;
		tracker = T;
		
		taskList.add(new AutoTask(TaskType.goTo, new double[] {5, 5}));
		taskList.add(new AutoTask(TaskType.goTo, new double[] {5, 5}));
		taskList.add(new AutoTask(TaskType.goTo, new double[] {5, 5}));
	}
	
	@Override
	public void teleopInit() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doTeleop() {
		// TODO Auto-generated method stub

	}

	@Override
	public void autoInit() {
		counter = 0;

	}

	@Override
	public void doAuto() {
		switch(taskList.get(counter).getType()){
		
		case goTo:
			if(driveTo(taskList.get(counter).getInfo())) {
				counter++;
			}
			break;
		
		default:
			break;
			
		}
	}


	@Override
	public double[] getInfo() {
		
		return null;
	}

	@Override
	public void giveInfo(double[] info) {
		// TODO Auto-generated method stub
		
	}
	
	public boolean driveTo(double[] I) {
		double[] buffer = {0, 0};
		double targAngle;
		posInfo = tracker.getInfo();
		
		targAngle = Math.toDegrees(Math.atan2(I[0] - posInfo[0], I[1] - posInfo[1]));
		
		buffer[0] = 0.5;
		buffer[1] = (targAngle - posInfo[2]) / 10;
		
		chassis.giveInfo(buffer);
		return buffer[0] == I[0] && buffer[1] == I[1];
	}

}
