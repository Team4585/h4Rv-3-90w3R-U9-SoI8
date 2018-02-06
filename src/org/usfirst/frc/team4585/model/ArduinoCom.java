package org.usfirst.frc.team4585.model;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.DriverStation;

public class ArduinoCom implements HuskyClass {

	private DigitalOutput colorPin = new DigitalOutput(14);
	private DigitalOutput clawPin = new DigitalOutput(15);
	
	private Claw claw;
	
	public ArduinoCom(Claw C) {
		claw = C;
	}
	
	@Override
	public void teleopInit() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doTeleop() {
		

	}

	@Override
	public void autoInit() {
		//colorPin.set(DriverStation.getInstance().getAlliance() == DriverStation.Alliance.Blue);
		colorPin.set(DriverStation.getInstance().getGameSpecificMessage().charAt(3) == 'B');

	}

	@Override
	public void doAuto() {
		clawPin.set(false);

	}

	@Override
	public double[] getInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void giveInfo(double[] info) {
		// TODO Auto-generated method stub

	}

}
