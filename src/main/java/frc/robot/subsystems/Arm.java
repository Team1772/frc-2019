package frc.robot.subsystems;
import edu.wpi.first.wpilibj.VictorSP;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.VictorSP;

// import edu.wpi.first.wpilibj.Encoder;

public class Arm {
    // Encoder enc;
    TalonSRX movArm1, movArm2;

    public Arm() {
        movArm1 = new TalonSRX(0);
        movArm2 = new TalonSRX(3);

        movArm2.setInverted(true);

        // (Wheel Size centimeter * Math.PI) / Encoder Counts Per Revolution
		// enc.setDistancePerPulse(distancePerPulse);
    }

    public void setSpeed(double speed) {
        movArm1.set(ControlMode.PercentOutput, speed);
        movArm2.set(ControlMode.PercentOutput, speed);
         
        // SmartDashboard.putNumber("Arm encoder", enc.get());
    }
}