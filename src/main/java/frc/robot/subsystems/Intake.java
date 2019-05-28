package frc.robot.subsystems;

import edu.wpi.first.wpilibj.VictorSP;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.VictorSP;

// import edu.wpi.first.wpilibj.Encoder;

public class Intake {
    // Encoder enc;
    TalonSRX movIntake1, movIntake2;
    VictorSP rolIntake;

    public Intake() {
        movIntake1 = new TalonSRX(1);
        movIntake2 = new TalonSRX(2);
        rolIntake = new VictorSP(4);

        // (Wheel Size centimeter * Math.PI) / Encoder Counts Per Revolution
        // enc.setDistancePerPulse(distancePerPulse);
    }

    public void setSpeed(double speed) {
        movIntake1.set(ControlMode.PercentOutput, speed);
        movIntake2.set(ControlMode.PercentOutput, speed);

        // SmartDashboard.putNumber("Arm encoder", enc.get());
    }

    public void setSpeedRol(double speed) {
        rolIntake.set(speed);
    }

    public void setSpeedRolBack(double speed) {
        rolIntake.set((speed) * -1);
    }

}
