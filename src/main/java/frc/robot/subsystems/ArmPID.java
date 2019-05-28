package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ArmPID extends PIDSubsystem {
    TalonSRX left, right;
    double targetAngle = 0;

    public ArmPID() {
        // PID HERE
        // Set P, I, D in this order
        super("ArmSubsystem", 0, 0, 0);

        // Hardware
        left = new TalonSRX(0);
        right = new TalonSRX(3);
        right.setInverted(true);
    }

    public void setSpeed(double speed) {
        left.set(ControlMode.PercentOutput, speed);
        right.set(ControlMode.PercentOutput, speed);
    }

    /**
     *
     * HERE START PID
     *
     * EXAMPLE FROM:
     * https://github.com/frc1334/DeepSpace2019/blob/535e31b0e5dc3b4784712ad677682548762e0a22/DeepSpace2019/src/main/java/frc/robot/subsystems/ArmSubsystem.java
     *
     */

    // This method sets the destination angle/set point
    public void setDestAngle(double dAngle) {
        this.targetAngle = dAngle;
    }

    @Override
    protected double returnPIDInput() {
        return right.getSelectedSensorPosition(0);
    }

    @Override
    protected void usePIDOutput(double output) {
        // Error term (destination angle - output, output is the current angle)
        double error = targetAngle - output;

        SmartDashboard.putNumber("PID OUTPUT", output);
        SmartDashboard.putNumber("PID ERROR", error);
    }

    @Override
    protected void initDefaultCommand() {
        right.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
        right.setSelectedSensorPosition(0);
        right.configMotionAcceleration(60);
        right.configMotionCruiseVelocity(200);
    }

    // Ignore this part.. is only for prints
    /**
     * @param units CTRE mag encoder sensor units
     * @return degrees rounded to tenths.
     */
    String ToDeg(int units) {
        double deg = units * 360.0 / 4096.0;

        /* truncate to 0.1 res */
        deg *= 10;
        deg = (int) deg;
        deg /= 10;

        return "" + deg;
    }

    /**
     * Get the selected sensor register and print it
     */
    public void print() {
        /**
         * Quadrature is selected for soft-lim/closed-loop/etc. initQuadrature() will
         * initialize quad to become absolute by using PWD
         */
        int selSenPos = right.getSelectedSensorPosition(0);
        int pulseWidthWithoutOverflows = right.getSensorCollection().getPulseWidthPosition() & 0xFFF;

        /**
         * Display how we've adjusted PWM to produce a QUAD signal that is absolute and
         * continuous. Show in sensor units and in rotation degrees.
         */
        System.out.print("pulseWidPos:" + pulseWidthWithoutOverflows + "   =>    " + "selSenPos:" + selSenPos);
        System.out.print("      ");
        System.out.print(
                "pulseWidDeg:" + ToDeg(pulseWidthWithoutOverflows) + "   =>    " + "selSenDeg:" + ToDeg(selSenPos));
        System.out.println();
    }
}