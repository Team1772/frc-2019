package frc.core.components;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;

public class GearBox {
	private SpeedController m1, m2;
    private Encoder enc;

	public GearBox(SpeedController motor1, SpeedController motor2, Encoder encoder, boolean isMotor1Inverted, boolean isMotor2Inverted, double distancePerPulse) {
		m1 = motor1;
		m2 = motor2;
		enc  = encoder;

		motor1.setInverted(isMotor1Inverted);
		if (motor2 != null) motor2.setInverted(isMotor2Inverted);
		configureEncoder(distancePerPulse);
	}

	public GearBox(SpeedController motor1, SpeedController motor2, Encoder encoder, double distancePerPulse) {
		this(motor1, motor2, encoder, false, false, distancePerPulse);
	}

	public GearBox(SpeedController motor1, Encoder encoder, boolean isMotorInverted, double distancePerPulse) {
		this(motor1, null, encoder, isMotorInverted, false, distancePerPulse);
	}

	public GearBox(SpeedController motor1, Encoder encoder, double distancePerPulse) {
		this(motor1, null, encoder, false, false, distancePerPulse);
	}

	private void configureEncoder(double distancePerPulse) {
		// (Wheel Size centimeter * Math.PI) / Encoder Counts Per Revolution
		enc.setDistancePerPulse(distancePerPulse);

    	// Set PIDs type
    	enc.setPIDSourceType(edu.wpi.first.wpilibj.PIDSourceType.kDisplacement);
	}

	public void setEncoderInverted(boolean reverseDirection) {
		enc.setReverseDirection(reverseDirection);
	}

	public void setSpeed(double speed) {
		m1.set(speed);
		if (m2 != null) m2.set(speed);
	}

    public void reset() {
    	enc.reset();
    }

    public double getPulses() {
    	return enc.get();
    }
    public double getDistance() {
    	return enc.getDistance();
    }
}
