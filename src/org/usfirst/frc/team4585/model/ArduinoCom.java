package org.usfirst.frc.team4585.model;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ArduinoCom {

	private DigitalOutput colorPin = new DigitalOutput(8);
	private DigitalOutput clawPin = new DigitalOutput(11);
	
	private Claw claw;
	
	public ArduinoCom(Claw C) {
		claw = C;
	}

	
	public void setPins() {
		colorPin.set(DriverStation.getInstance().getAlliance() == DriverStation.Alliance.Blue);
		//colorPin.set(false);
		//colorPin.set(DriverStation.getInstance().getGameSpecificMessage().charAt(2) == 'B');
		SmartDashboard.putBoolean("colorPin", colorPin.get());
		clawPin.set(false);
	}

}
