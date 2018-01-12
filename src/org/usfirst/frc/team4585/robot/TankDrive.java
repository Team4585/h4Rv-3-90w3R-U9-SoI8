/*

package org.usfirst.frc.team4585.robot;


import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;

public class TankDrive {
	private RobotDrive chassis;
	private Extreme3DPro _joy;
	public  ADXRS450_Gyro _gyro;
	SmartDashboard dash = new SmartDashboard();
	//DigitalInput _FlagSensor = new DigitalInput(7);
	
	private final double ROTATIONCONSTANT = 1;
	private final double SCALEX = 1;
	private final double SCALEY = 2;
	private final double DEADX = .05;
	private final double DEADY = .05;

	public TankDrive(int MOTORLEFT, int MOTORRIGHT, Extreme3DPro joyparam) {
		chassis = new RobotDrive(MOTORLEFT, MOTORRIGHT);
		chassis.setExpiration(.1);
		_joy = joyparam;
		_gyro = new ADXRS450_Gyro();
		
	}

	public void arcadeDrive() { // x is rotation, y is forward/back
		double magX = -_joy.getZ();
		double magY = -_joy.getY();
		//dash.putDouble("Slider", _joy.getAxis(3));
		
		magX = applyDeadzone(magX, DEADX);
		magY = applyDeadzone(magY, DEADY);

		magX = applyScale(magX, SCALEX);
		magY = applyScale(magY, SCALEY);
 
		chassis.arcadeDrive(magY * (_joy.getAxis(3) + 1), magX - (_joy.getAxis(3)) + 1);
	}

	private double applyDeadzone(double input, double deadzone) {
		if (Math.abs(input) >= deadzone) {
			return input;
		} else {
			return 0;
		}
	}


	private double applyScale(double input, double power) {
		return Math.copySign(Math.pow(input, power), input);

	}
	
	public void Spin4While(){
		chassis.arcadeDrive(0, 1);
	}
	
	public void MoveSlowly(){
		double junk = ((_gyro.getAngle() % 90) / 4);
		chassis.arcadeDrive(0.6, junk);
		dash.putDouble("Junk", junk);
		dash.putDouble("GyroAngle", _gyro.getAngle());
	}
	
	public void MoveBackSlowly(	){
		double junk = ((_gyro.getAngle() % 90) / 4);
		chassis.arcadeDrive(-0.6, junk);
	}
	
	public void Reset(){
		_gyro.reset();
		//_gyro.calibrate();
	}
	
	public void doNothing(){
		dash.putDouble("GyroAngle", _gyro.getAngle());
	}

}

*/