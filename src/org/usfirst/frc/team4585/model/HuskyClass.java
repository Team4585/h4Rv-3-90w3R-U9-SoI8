package org.usfirst.frc.team4585.model;

public interface HuskyClass {
	
	public void teleopInit();
	
	public void doTeleop();
	
	public void autoInit();
	
	public void doAuto();
	
	public double[] getInfo();
	
	public void giveInfo(double[] info);
}
