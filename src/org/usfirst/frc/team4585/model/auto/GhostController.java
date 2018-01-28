package org.usfirst.frc.team4585.model.auto;

import java.util.ArrayList;

import org.usfirst.frc.team4585.model.*;

public class GhostController implements HuskyClass {
	
	private ArrayList<AutoTask> taskList = new ArrayList<AutoTask>();
	
	
	
	private int counter;
	
	private double chassisInfo;
	private double armInfo;
	private double clawInfo;
	
	private Chassis chassis;
	private Arm arm;
	private Claw claw;
	
	
	public GhostController(Chassis Ch, Arm A, Claw Cl) {
		chassis = Ch;
		arm = A;
		claw = Cl;
		
		taskList.add(new AutoTask(TaskType.drive, new double[] {0.5, 0}));
		taskList.add(new AutoTask(TaskType.drive, new double[] {0, 0.5}));
		taskList.add(new AutoTask(TaskType.drive, new double[] {0.5, 0}));
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
		
		case drive:
			doDrive(taskList.get(counter).getInfo());
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
	
	public void doDrive(double[] I) {
		chassis.giveInfo(I);
	}

}
