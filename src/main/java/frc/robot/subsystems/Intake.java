package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

// import edu.wpi.first.wpilibj.Encoder;

public class Intake {
    // Encoder enc;
    TalonSRX movIntake1, movIntake2;

    public Intake() {
        movIntake1 = new TalonSRX(1);
        movIntake2 = new TalonSRX(2);
        rolIntake  = new TalonSRX();

        // (Wheel Size centimeter * Math.PI) / Encoder Counts Per Revolution
		// enc.setDistancePerPulse(distancePerPulse);
    }

    public void setSpeed(double speed) {
        movIntake1.set(ControlMode.PercentOutput, speed);
        movIntake2.set(ControlMode.PercentOutput, speed);
         rolIntake.set(ControlMode.PercentOutput, speed);
        // SmartDashboard.putNumber("Arm encoder", enc.get());
    }
}