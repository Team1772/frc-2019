package frc.core.components;

import edu.wpi.first.wpilibj.Solenoid;

public class SmartSolenoid {
	private Solenoid solIn, solOut;
	
	public SmartSolenoid(int channelIn, int channelOut) {
		solIn = new Solenoid(channelIn);
		solOut = new Solenoid(channelOut);
	}
	
	public void set(boolean push) {
		solIn.set(push);
		solOut.set(!push);
	}

}
