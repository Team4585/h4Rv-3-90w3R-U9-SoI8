package org.usfirst.frc.team4585.model.auto;

public class AutoTask {
	
	private TaskType type;
	private double[] info;
	
	public AutoTask(TaskType T, double[] I) {
		type = T;
		info = I;
	}
	
	public TaskType getType(){
		return type;
	}
	
	public double[] getInfo() {
		return info;
	}
	
}
