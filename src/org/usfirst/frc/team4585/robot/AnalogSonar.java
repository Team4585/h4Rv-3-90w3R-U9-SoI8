package org.usfirst.frc.team4585.robot;

import edu.wpi.first.wpilibj.AnalogInput;

public class AnalogSonar{
	private AnalogInput input;
	int sampleBuffer;
	int millimetersPerVolt=976;//constant set by range finder
	double correctionCoeff = 1.05;
	//TODO change correction coefficient in case we go to nationals
	int defaultSampleBuffer=20;

	public AnalogSonar(int AIO_port, int sampleBuffer) {
		input = new AnalogInput(AIO_port);
		this.sampleBuffer = sampleBuffer;

		input.setAverageBits(sampleBuffer);
		input.setOversampleBits(sampleBuffer);

	}

	public AnalogSonar(int AIO_port) {
		input = new AnalogInput(AIO_port);
		this.sampleBuffer = defaultSampleBuffer;

		input.setAverageBits(defaultSampleBuffer);
		input.setOversampleBits(defaultSampleBuffer);

	}

	// default=20, higher numbers are more accurate but have a slower polling
	// speed
	void setSampleBuffer(int buffer) {
		this.sampleBuffer = buffer;
		input.setAverageBits(buffer);
		input.setOversampleBits(buffer);
	}
	

	double getMillimeters() {
		return input.getAverageVoltage() * millimetersPerVolt * correctionCoeff;
	}

	public double getInches() {
		return getMillimeters() * .03937;
	}

	public double getCentimeters() {
		return getMillimeters() * 10;
	}
	public double getVoltage(){
		return input.getAverageVoltage();
	}
	public int getValue(){
		return input.getAverageValue();
	}

}