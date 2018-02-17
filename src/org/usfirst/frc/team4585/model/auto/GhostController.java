package org.usfirst.frc.team4585.model.auto;

import java.io.IOException;
import java.util.ArrayList;

import org.usfirst.frc.team4585.model.*;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class GhostController implements HuskyClass {
	
	private ArrayList<AutoTask> taskList = new ArrayList<AutoTask>();
	
	private int counter;
	
	private double[] chassisInfo;
	
	private double[] clawInfo;
	private double[] posInfo;
	private double[] teleTargPos;	//experemental
	private double teleTargAngle;
	
	private Chassis chassis;
	private Arm arm;
	private ArmActuator actuator;
	private Winch winch;
	private Claw claw;
	private PositionTracker tracker;
	private HuskyJoy driveJoy;
	private HuskyJoy weaponsJoy;
	
	private SendableChooser<String> firstAutoChooser = new SendableChooser<>();
	private HuskyPID anglePID = new HuskyPID(1/90, 0, 0, 0);
	private VisionCom visCom = new VisionCom();
	
	
	
	public GhostController(Chassis Ch, Arm A, Claw Cl, ArmActuator AA, Winch W, PositionTracker T,  HuskyJoy DJ, HuskyJoy WJ) {
		chassis = Ch;
		arm = A;
		
		actuator = AA;
		winch = W;
		
		claw = Cl;
		tracker = T;
		driveJoy = DJ;
		weaponsJoy = WJ;
		
		
	}
	
	public void dashInit() {
		firstAutoChooser.addDefault("Switch inside", "sw_in");
		firstAutoChooser.addObject("Switch outside", "sw_out");
		firstAutoChooser.addObject("Scale outside", "sc_out");
		SmartDashboard.putData("Robot Auto Destenation", firstAutoChooser);
		
	}
	
	@Override
	public void teleopInit() {
		teleTargPos = new double[] {0, 0};
		teleTargAngle = 0;
		
		

	}

	@Override
	public void doTeleop() {
		
		posInfo = tracker.getInfo();
		
		SmartDashboard.putNumber("sonar inch", posInfo[3]);
		
			//normal drive
		chassis.giveInfo(new double[] {-driveJoy.getSliderScaled(1), driveJoy.getSliderScaled(2)});
		
			//climb
		
		if (weaponsJoy.getRawButton(1)) {
			double[] actInfo = actuator.getInfo();
			double[] winchInfo = winch.getInfo();
			
			actuator.giveInfo(winchInfo);
		}
		else {
			actuator.giveInfo(new double[] {0});
		}
		
		
		
		
		/*	//angle accel turn
		teleTargAngle += joy.getSliderScaled(2) * 5;
		
		SmartDashboard.putNumber("joy", joy.getSliderScaled(2));
		
		chassis.giveInfo(new double[] {-joy.getSliderScaled(1),
				angleAccel(posInfo[2], teleTargAngle)});
		*/
		
		
		//fake mecanum
		/*
		teleTargPos[0] += (joy.getSliderScaled(0) / 10);
		teleTargPos[1] += (-joy.getSliderScaled(1) / 10);
		driveTo(teleTargPos);
		*/
		
		
		
	}

	@Override
	public void autoInit() {
		String gameInfo = DriverStation.getInstance().getGameSpecificMessage();
		taskList.clear();
		
		switch (firstAutoChooser.getSelected()) {
		case "sw_in":
			if (gameInfo.charAt(0) == 'L') {
				taskList.add(new AutoTask(TaskType.goTo, new double[] {8, 9}));
			} else {
				taskList.add(new AutoTask(TaskType.goTo, new double[] {17, 9}));
			}
			taskList.add(new AutoTask(TaskType.pointAt, new double[] {0}));
			
			break;
		
		case "sw_out":
			if (gameInfo.charAt(0) == 'L') {
				taskList.add(new AutoTask(TaskType.goTo, new double[] {6, 13}));
				taskList.add(new AutoTask(TaskType.pointAt, new double[] {90}));
			} else {
				taskList.add(new AutoTask(TaskType.goTo, new double[] {21, 13}));
				taskList.add(new AutoTask(TaskType.pointAt, new double[] {-90}));
			}
			
			break;
		
		case "sc_out":
			if (gameInfo.charAt(1) == 'L') {
				taskList.add(new AutoTask(TaskType.goTo, new double[] {4, 26}));
				taskList.add(new AutoTask(TaskType.pointAt, new double[] {90}));
			} else {
				taskList.add(new AutoTask(TaskType.goTo, new double[] {22, 26}));
				taskList.add(new AutoTask(TaskType.pointAt, new double[] {-90}));
			}
			
			break;
		
		default:
			
			break;
		
		}
		
		taskList.clear();
		taskList.add(new AutoTask(TaskType.goTo, new double[] {7, 5}));
		taskList.add(new AutoTask(TaskType.pointAt, new double[] {90}));
		taskList.add(new AutoTask(TaskType.getCube, new double[] {-90}));
		taskList.add(new AutoTask(TaskType.goTo, new double[] {7, 0}));
		
		taskList.add(new AutoTask(TaskType.stop, new double[] {0}));
		
		/*
		taskList.add(new AutoTask(TaskType.goTo, new double[] {-10, 12}));
		//taskList.add(new AutoTask(TaskType.goTo, new double[] {4, 4}));
		//taskList.add(new AutoTask(TaskType.goTo, new double[] {4, 0}));
		//taskList.add(new AutoTask(TaskType.goTo, new double[] {0, 0}));
		taskList.add(new AutoTask(TaskType.pointAt, new double[] {90}));
		taskList.add(new AutoTask(TaskType.goTo, new double[] {0, 0}));
		taskList.add(new AutoTask(TaskType.pointAt, new double[] {0}));
		
		taskList.add(new AutoTask(TaskType.stop, new double[] {0}));
		
		
		taskList.add(new AutoTask(TaskType.goTo, new double[] {6, 13}));
		taskList.add(new AutoTask(TaskType.pointAt, new double[] {90}));
		taskList.add(new AutoTask(TaskType.stop, new double[] {0}));
		*/
		counter = 0;

	}

	@Override
	public void doAuto() {
		
		//arm.giveInfo(new double[] {45});
		
		//pointAtCube();
		//goToCube();
		
		//25' 3.5"
		//left -3452.0
		//right -3464.0
		
		//102' 10"
		//right -14032.0
		//left -13976.0
		
		/*
		posInfo = tracker.getInfo();
		if ((posInfo[1]-1) < 90) { // 25 3.5 
			chassis.giveInfo(new double[] {0.7, angleAccel(posInfo[2], 0)});
			
		} else if ((posInfo[1]-1) < 100) {
			chassis.giveInfo(new double[] {0.5, angleAccel(posInfo[2], 0)});
		} else {
			chassis.giveInfo(new double[] {0, 0});
		}
		*/
		
		
		//driveTo(new double[] {7, 2});
		//SmartDashboard.putBoolean("at targ?", pointAt(90));
		
		/*
		if(counter < taskList.size()) {
			switch(taskList.get(counter).getType()){
			
			case goTo:
				if(driveTo(taskList.get(counter).getInfo())) {
					counter++;
				}
				break;
			
			case pointAt:
				if(pointAt(taskList.get(counter).getInfo()[0])) {
					counter++;
				}
				break;
				
			case getCube:
				if(goToCube()) {
					counter++;
				}
				break;
				
			case dropCube:
				pointAt(taskList.get(counter).getInfo()[0]);
				counter++;
				break;
			
			case setArmDeg:
				arm.giveInfo(taskList.get(counter).getInfo());
				counter++;
				break;
				
			case setArmDist:
				actuator.giveInfo(taskList.get(counter).getInfo());
				break;
				
			case stop:
				chassis.giveInfo(new double[] {0, 0});
				break;
			
			default:
				break;
				
			}
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
		//targAngle = 90;
		
		SmartDashboard.putNumber("targX", I[0] - posInfo[0]);
		SmartDashboard.putNumber("targY", I[1] - posInfo[1]);
		SmartDashboard.putNumber("TargAngle", targAngle);
		
		//buffer[0] = 0.6;
		buffer[0] = ((Math.round(posInfo[0]) == Math.round(I[0])) && (Math.round(posInfo[1]) == Math.round(I[1])))? 0:0.6;
		buffer[1] = angleAccel(posInfo[2], targAngle);
		
		chassis.giveInfo(buffer);
		
		SmartDashboard.putNumber("heading", posInfo[2]);
		
		
		return (Math.round(posInfo[0]) == Math.round(I[0])) && (Math.round(posInfo[1]) == Math.round(I[1]));
	}
	
	private boolean pointAt(double targAngle) {
		double[] buffer = {0, 0};
		posInfo = tracker.getInfo();
		
		buffer[1] = angleAccel(posInfo[2], targAngle);
		//buffer[1] = anglePID.calculate(posInfo[2], targAngle);
		
		chassis.giveInfo(buffer);
		
		return (posInfo[2] < targAngle + 5) && (posInfo[2] > targAngle - 5);
	}
	
	private boolean pointAtCube() {
		posInfo = tracker.getInfo();
		
		double angle = visCom.getAngleToCube();
		
		if (Math.round(angle) != 0) {
			pointAt(angle + posInfo[2]);
		}
		else {
			chassis.giveInfo(new double[] {0, 0.5});
		}
		return true;
	}
	
	private boolean goToCube() {
		boolean atCube = false;
		
		posInfo = tracker.getInfo();
		
		SmartDashboard.putNumber("sonar inch", posInfo[3]);
		
		double angle = visCom.getAngleToCube();
		
		if (angle != 0) {
			if (posInfo[3] > 15) {
				chassis.giveInfo(new double[] {0.55, angleAccel(posInfo[2], angle + posInfo[2])});
			}
			else {
				if (angle + posInfo[2] < posInfo[2] + 5 && angle + posInfo[2] > posInfo[2] - 5) {
					chassis.giveInfo(new double[] {0, 0});
					atCube = true;
				}
				else {
					chassis.giveInfo(new double[] {0, angleAccel(posInfo[2], angle + posInfo[2])});
					
				}
				
			}
		}
		else {
			chassis.giveInfo(new double[] {0, 0.5});
		}
			
		
		
		return atCube;
		
	}
	
	private boolean dropCube() {
		return true;
	}
	
	private double angleAccel(double inAngle, double targAngle) {
		double output;
		
		output = (targAngle - inAngle) / 90;
		/*
		if (output < 0.3 && !(output < 0.1)) {
			output = 0.3;
		}
		else if (output > -0.3 && !(output > -0.001)) {
			output = -0.3;
		}
		
		else */if (output < 0.4 && !(output < 0.1)) {
			output = 0.4;
		}
		else if (output > -0.4 && !(output > -0.001)) {
			output = -0.4;
		}
		else if (Math.abs(output) < 0.001) {
			output = 0;
		}
		
		if (output > 0.77) {
			output = 0.77;
		}
		else if (output < -0.77) {
			output = -0.77;
		}
		
		return output;
	}
	
	
}
