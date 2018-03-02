package org.usfirst.frc.team4585.model.auto;

import java.io.IOException;
import java.util.ArrayList;

import org.usfirst.frc.team4585.model.*;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class GhostController implements HuskyClass {
	
	private final int SWITCH_DROP_DISTANCE = 13;
	private final int SCALE_DROP_DISTANCE = 25;
	
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
		firstAutoChooser.addObject("Auto run", "auto_run");
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
		actuator.giveArmAngle(arm.getInfo()[0]);
		
		SmartDashboard.putNumber("sonar inch", posInfo[3]);
		
			//normal drive (slider)
//		chassis.giveInfo(new double[] {-driveJoy.getSliderScaled(1), driveJoy.getSliderScaled(2)});
		
			//non slider drive
		chassis.giveInfo(new double[] {-driveJoy.getRawAxis(1), driveJoy.getRawAxis(2)});
		
		
			//climb
		if (weaponsJoy.getRawButton(1)) {
			double[] actInfo = actuator.getInfo();
			double[] winchInfo = winch.getInfo();
			
			
			//(((-getRawAxis(3) + 1) / 4) + 0.5)
			winch.giveInfo(new double[] {-(((-weaponsJoy.getRawAxis(3) + 1) / 4) + 0.5)});
			actuator.setCliming(true);
			//actuator.giveInfo(winchInfo);
		}
		else if (weaponsJoy.getRawButton(7)) {
			winch.giveInfo(new double[] {(((-weaponsJoy.getRawAxis(3) + 1) / 4) + 0.5)});
		}
		else {
			winch.giveInfo(new double[] {0});
			actuator.setCliming(false);
			actuator.giveArmAngle(arm.getInfo()[0]);
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
		
		double x = tracker.getInfo()[0];
		
		switch (firstAutoChooser.getSelected()) {
		case "sw_in":
			taskList.add(new AutoTask(TaskType.goTo, new double[] {13, 3}));
			taskList.add(new AutoTask(TaskType.setArmDeg, new double[] {45}));
			taskList.add(new AutoTask(TaskType.setArmDist, new double[] {10}));
			if (gameInfo.charAt(0) == 'L') {
				taskList.add(new AutoTask(TaskType.goTo, new double[] {8, 9}));
			} else {
				taskList.add(new AutoTask(TaskType.goTo, new double[] {17, 9}));
			}
			//taskList.add(new AutoTask(TaskType.pointAt, new double[] {0}));
			taskList.add(new AutoTask(TaskType.dropCube, new double[] {0, 0}));
			
			
			break;
		
		case "sw_out":
			taskList.add(new AutoTask(TaskType.goTo, new double[] {x, 17}));
			
			
			if (gameInfo.charAt(0) == 'L') {
				taskList.add(new AutoTask(TaskType.goTo, new double[] {4, 18}));
				taskList.add(new AutoTask(TaskType.setArmDeg, new double[] {45}));
				taskList.add(new AutoTask(TaskType.setArmDist, new double[] {10}));
				taskList.add(new AutoTask(TaskType.goTo, new double[] {5, 13}));
				taskList.add(new AutoTask(TaskType.pointAt, new double[] {90}));
				taskList.add(new AutoTask(TaskType.dropCube, new double[] {90, 0}));
			} else {
				taskList.add(new AutoTask(TaskType.goTo, new double[] {22, 18}));
				taskList.add(new AutoTask(TaskType.setArmDeg, new double[] {45}));
				taskList.add(new AutoTask(TaskType.setArmDist, new double[] {10}));
				taskList.add(new AutoTask(TaskType.goTo, new double[] {21, 13}));
				taskList.add(new AutoTask(TaskType.pointAt, new double[] {-90}));
				taskList.add(new AutoTask(TaskType.dropCube, new double[] {-90, 0}));
			}
			
			break;
		
		case "sc_out":
			if (gameInfo.charAt(1) == 'L') {
				taskList.add(new AutoTask(TaskType.goTo, new double[] {4, 26}));
				taskList.add(new AutoTask(TaskType.pointAt, new double[] {90}));
				taskList.add(new AutoTask(TaskType.dropCube, new double[] {90, 1}));
			} else {
				taskList.add(new AutoTask(TaskType.goTo, new double[] {22, 26}));
				taskList.add(new AutoTask(TaskType.pointAt, new double[] {-90}));
				taskList.add(new AutoTask(TaskType.dropCube, new double[] {-90, 1}));
			}
			
			break;
		
		case "auto_run":
			taskList.add(new AutoTask(TaskType.goTo, new double[] {x, 12}));
			
			break;
			
		default:
			
			break;
		
		}
		
		/*
		taskList.clear();
		taskList.add(new AutoTask(TaskType.pointAt, new double[] {135}));
		taskList.add(new AutoTask(TaskType.pointAt, new double[] {-135}));
		
		*/
		
		
		taskList.add(new AutoTask(TaskType.stop, new double[] {0}));
		counter = 0;

	}

	@Override
	public void doAuto() {
		
//		arm.giveInfo(new double[] {45});
//		dropCube(new double[] {0, 0});
		
		//arm.giveInfo(new double[] {0});
		/*
		actuator.giveArmAngle(arm.getInfo()[0]);
		actuator.giveInfo(new double[] {20});
		*/
		
		/*
		//claw.giveInfo(new double[] {1});
		if (claw.getInfo()[0] != 1) {
			claw.giveInfo(new double[] {1});
		}
		*/
		
		//pointAtCube();
		//goToCube();
		
		//25' 3.5"
		//left -3452.0
		//right -3464.0
		
		//102' 10"
		//right -14032.0
		//left -13976.0
		
		/*		calibrate encoders
		posInfo = tracker.getInfo();
		if ((posInfo[1]-1) < 90) { // 25 3.5
			chassis.giveInfo(new double[] {0.7, angleAccel(posInfo[2], 0)});
			
		}
		else if ((posInfo[1]-1) < 100) {
			chassis.giveInfo(new double[] {0.5, angleAccel(posInfo[2], 0)});
		}
		else {
			chassis.giveInfo(new double[] {0, 0});
		}
		*/
		
		
		//driveTo(new double[] {7, 2});
		//SmartDashboard.putBoolean("at targ?", pointAt(90));
		
		
		actuator.giveArmAngle(arm.getInfo()[0]);
		
		if(counter < taskList.size()) {
			
			SmartDashboard.putNumber("auto item #", counter);
			
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
//				claw.giveInfo(new double[] {1});
//				counter++;
				if (dropCube(taskList.get(counter).getInfo())) {
					counter++;
				}
				
				break;
			
			case setArmDeg:
				arm.giveInfo(taskList.get(counter).getInfo());
				counter++;
				break;
				
			case setArmDist:
				actuator.giveInfo(taskList.get(counter).getInfo());
				counter++;
				break;
				
			case stop:
				chassis.giveInfo(new double[] {0, 0});
				break;
			
			default:
				break;
				
			}
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
	
	private boolean driveTo(double[] I) {
		double[] buffer = {0, 0};
		double targAngle;
		posInfo = tracker.getInfo();
		
		targAngle = Math.toDegrees(Math.atan2(I[0] - posInfo[0], I[1] - posInfo[1]));
		
		SmartDashboard.putNumber("targX", I[0]);
		SmartDashboard.putNumber("targY", I[1]);
		SmartDashboard.putNumber("TargAngle", targAngle);
		
		buffer[0] = ((Math.round(posInfo[0]) == Math.round(I[0])) && (Math.round(posInfo[1]) == Math.round(I[1])) || 
				HuskyMath.gSmallAngDiff(posInfo[2], targAngle) > 30)? 0:0.7;
		buffer[1] = angleAccel(posInfo[2], targAngle);
		
		chassis.giveInfo(buffer);
		
		
		return (Math.round(posInfo[0]) == Math.round(I[0])) && (Math.round(posInfo[1]) == Math.round(I[1]));
	}
	
	
	/*private boolean driveFeet(double feet) {
		double[] buffer = {0, 0};
		
		buffer[0] = ((Math.round(posInfo[0]) == Math.round(I[0])) && (Math.round(posInfo[1]) == Math.round(I[1])))? 0:0.6;
		buffer[1] = angleAccel(posInfo[2], targAngle);
		
		chassis.giveInfo()
		
		return true;
	}*/
	
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
	
	private boolean dropCube(double[] info) {
		posInfo = tracker.getInfo();
		
		double angle = info[0];
		boolean dropped = false;
		
		if (posInfo[2] < angle + 8 && posInfo[2] > angle -8) {
			if (info[1] == 0) {
				if (posInfo[3] > SWITCH_DROP_DISTANCE) {
					chassis.giveInfo(new double[] {0.5, angleAccel(posInfo[2], angle)});
				}
				else {
					chassis.giveInfo(new double[] {0, 0});
					claw.giveInfo(new double[] {0});
					dropped = true;
				}
			}
			else {
				if (posInfo[4] < SCALE_DROP_DISTANCE) {
					chassis.giveInfo(new double[] {0.5, angleAccel(posInfo[2], angle)});
				}
				else {
					chassis.giveInfo(new double[] {0, 0});
					claw.giveInfo(new double[] {0});
					dropped = true;
				}
			}
		}
		else {
			chassis.giveInfo(new double[] {0, angleAccel(posInfo[2], angle)});
		}
		
		
		return dropped;
	}
	
	private double angleAccel(double inAngle, double targAngle) {
		double output;
		
//		/*
		if (inAngle < 0) {
			inAngle = 360 - Math.abs(inAngle);
		}
		if (targAngle < 0) {
			targAngle = 360 - Math.abs(targAngle);
		}
		
		double angleDiff = (targAngle - inAngle);
		double modAngleDiff;
		if (angleDiff >= 0) {
			modAngleDiff = ((angleDiff + 180) % 360) -180;
		}
		else {
			modAngleDiff = -(((-angleDiff + 180) % 360) - 180);
//			modAngleDiff = -(((-modAngleDiff + 180) % 360) - 180);
		}
//		*/
		
//		double modAngleDiff = HuskyMath.gSmallAngDiff(inAngle, targAngle);
		
		SmartDashboard.putNumber("angle diff", modAngleDiff);
		
		output = (modAngleDiff / 45);
		/*
		if (output < 0.3 && !(output < 0.1)) {
			output = 0.3;
		}
		else if (output > -0.3 && !(output > -0.001)) {
			output = -0.3;
		}
		
		else */if (output < 0.5 && !(output < 0.1)) {
			output = 0.5;
		}
		else if (output > -0.5 && !(output > -0.1)) {
			output = -0.5;
		}
		else if (Math.abs(output) < 0.1) {
			output = 0;
		}
		
		if (output > 0.7) {
			output = 0.7;
		}
		else if (output < -0.7) {
			output = -0.7;
		}
		
		return output;
	}
	
	
}
