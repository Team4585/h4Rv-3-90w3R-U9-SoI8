package org.usfirst.frc.team4585.model;

public class Accelerometer {
	
	public Accelerometer(){
		Accelerometer accel;
		double accelerationX;
		double accelerationY;
		double accelerationZ;

		AccelerometerSample()
		{
			accel = new ADXL345_I2C(I2C.Port.kOnboard, Accelerometer.Range.k4G);
		}

		public void operatorControl() {
			while(isOperatorControl() && isEnabled())
			{
				accelerationX = accel.getX();
				accelerationY = accel.getY();
				accelerationZ = accel.getZ();
			}
		}
	}
}


/*
public class AccelerometerSample extends SampleRobot {
	Accelerometer accel;
	double accelerationX;
	double accelerationY;
	double accelerationZ;

	AccelerometerSample()
	{
		accel = new ADXL345_I2C(I2C.Port.kOnboard, Accelerometer.Range.k4G);
	}

	public void operatorControl() {
		while(isOperatorControl() && isEnabled())
		{
			accelerationX = accel.getX();
			accelerationY = accel.getY();
			accelerationZ = accel.getZ();
		}
	}
}*/