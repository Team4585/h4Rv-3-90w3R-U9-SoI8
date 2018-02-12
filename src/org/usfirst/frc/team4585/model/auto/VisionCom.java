package org.usfirst.frc.team4585.model.auto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import edu.wpi.cscore.MjpegServer;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class VisionCom {
	
	private final String hostName = "10.45.85.96";
	private final int portNumber = 4585;
	
	private final int CAMERA_ANGLE = 90;
	
	private UsbCamera source;
	private MjpegServer server;
	
	private double cubeAngle = 0;
	
	public void beginCamera() {
		source = CameraServer.getInstance().startAutomaticCapture();
		source.setResolution(480, 320);
		
		server = CameraServer.getInstance().addServer("VisionCam", 5800);
		server.setSource(source);
		
	}
	
	public void doStuff() {
		
		try {
			SmartDashboard.putString("cube distance", get(Requests.NEAREST_CUBE_DISTANCE));
			
			double width = Integer.parseInt(get(Requests.WIDTH));
			String cubeXY = get(Requests.NEAREST_CUBE);
			double cubeX = Integer.parseInt(cubeXY.substring(0, cubeXY.indexOf(",")));
			double cubeAngle = ((cubeX * CAMERA_ANGLE) / width) - (CAMERA_ANGLE / 2);
			
			SmartDashboard.putNumber("angle", cubeAngle);
			SmartDashboard.putNumber("cubeX", cubeX);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public double getAngleToCube() {
		
		try {
			SmartDashboard.putString("cube distance", get(Requests.NEAREST_CUBE_DISTANCE));
			
			double width = Integer.parseInt(get(Requests.WIDTH));
			String cubeXY = get(Requests.NEAREST_CUBE);
			double cubeX = Integer.parseInt(cubeXY.substring(0, cubeXY.indexOf(",")));
			cubeAngle = ((cubeX * CAMERA_ANGLE) / width) - (CAMERA_ANGLE / 2);
			
			SmartDashboard.putNumber("angle", cubeAngle);
			SmartDashboard.putNumber("cubeX", cubeX);
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return cubeAngle;
	}
	
	public String get(int message) throws IOException {
		//connect
		Socket socket = new Socket(hostName, portNumber);
	    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
	    BufferedReader in = new BufferedReader(
	        new InputStreamReader(socket.getInputStream()));
	    
	    //send and receive
	    out.println(message);
	    //System.out.println("Server: " + in.readLine());
	    String output = in.readLine();
	    
	    socket.close();
	    
	    return output;
	}
	
}
