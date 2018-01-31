package org.usfirst.frc.team4585.model;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ArmExtender implements HuskyClass {
	
	private final int EXTENDER_PORT = 4;
	private Servo actuator = new Servo(EXTENDER_PORT);
	
	private Joystick joy;
	private double position = 1;
	
	public ArmExtender(Joystick J) {
		joy = J;
	}
	
	

	@Override
	public void teleopInit() {

	}

	@Override
	public void doTeleop() {
		//buttons : 6 and 4
		if (joy.getRawButton(6) == true && joy.getRawButton(4) == false) {		
			    position += 0.002;
		    }
		if (joy.getRawButton(6) == false && joy.getRawButton(4) == true) {
				position -= 0.002;
		}
		if (position > 0.8) {
			position = 0.8;
		} else if (position < 0.2) {
			position = 0.2;
		}
		actuator.set(position);
		SmartDashboard.putNumber("position: ", position);
	}
	
	@Override
	public void autoInit() {
		actuator.set(position);
		// TODO Auto-generated method stub
	}

	@Override
	public void doAuto() {
		actuator.set(position);	
		}
		// TODO Auto-generated method stub */



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
