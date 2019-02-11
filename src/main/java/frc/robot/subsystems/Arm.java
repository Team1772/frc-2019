package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

// import edu.wpi.first.wpilibj.Encoder;

public class Arm {
    // Encoder enc;
    TalonSRX left, right;

    public Arm() {
        left = new TalonSRX(0);
        right = new TalonSRX(3);


        // (Wheel Size centimeter * Math.PI) / Encoder Counts Per Revolution
		// enc.setDistancePerPulse(distancePerPulse);
    }

    public void setSpeed(double speed) {
        left.set(ControlMode.PercentOutput, speed);
        right.set(ControlMode.PercentOutput, speed);
        // SmartDashboard.putNumber("Arm encoder", enc.get());
    }
}