package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.core.components.*;

public class Driver extends Subsystem {
	// private GearBox leftGearBox, rightGearBox;
	private SmartNavx navx;
	VictorSP leftOne, leftTwo;
	VictorSP rightOne, rightTwo;

    public Driver () {
    	// leftGearBox  = new GearBox(new VictorSP(0), new VictorSP(1), new Encoder(2, 3), 0.02662830636845544737048296408458);
		// rightGearBox = new GearBox(new VictorSP(2), new VictorSP(3), new Encoder(0, 1), 0.0265748358737396733797791830322);
		leftOne  = new VictorSP(2);
		leftTwo  = new VictorSP(3);

		rightOne = new VictorSP(0);
		rightTwo = new VictorSP(1);

		navx = new SmartNavx();
		leftOne.setInverted(true);
		leftTwo.setInverted(true);
	}

	double minR = 0.4D, difR = 0.5D;
	public void arcadeDrive(double sp, double rotation) {
		double mod = minR + difR * Math.pow(1 - Math.abs(sp), 2);
		double r = Math.pow(rotation, 3) * mod;
		leftOne.set(sp - r);
		leftTwo.set(sp - r);

		rightOne.set(sp + r);
		rightTwo.set(sp + r);
	}

	public double getLeftPulses() {
    	return 0;
    }

    public double getRightPulses() {
    	return 0;
    }

    public double getLeftDistance() {
    	return 0;
    }

    public double getRightDistance() {
    	return 0;
    }

    public double getAngle() {
    	return navx.getAngle();
    }

    public boolean reset() {
    	navx.reset();
    	return true;
    }

    public boolean setSpeed(double left, double right) {
		leftOne.set(left);
		leftTwo.set(left);

		rightOne.set(right);
		rightTwo.set(right);

		return true;
	}

	public void print() {
		SmartDashboard.putNumber("Navx angle", navx.getAngle());
		SmartDashboard.putNumber("Navx getYaw", navx.getYaw());
	}

   /* public double getLeftPulses() {
    	return leftGearBox.getPulses();
    }

    public double getRightPulses() {
    	return rightGearBox.getPulses();
    }

    public double getLeftDistance() {
    	return leftGearBox.getDistance();
    }

    public double getRightDistance() {
    	return rightGearBox.getDistance();
    }

    public double getAngle() {
    	return navx.getAngle();
    }

    public boolean reset() {
    	rightGearBox.reset();
    	leftGearBox.reset();
    	navx.reset();
    	return true;
    }

    public boolean setSpeed(double left, double right) {
    	leftGearBox.setSpeed(-left);
		rightGearBox.setSpeed(-right);

		return true;
    }

    double minR = 0.4D, difR = 0.5D;
	public void arcadeDrive(double sp, double rotation) {
		double mod = minR + difR * Math.pow(1 - Math.abs(sp), 2);
		double r = Math.pow(rotation, 3) * mod;
		leftGearBox.setSpeed(sp - r);
		rightGearBox.setSpeed(sp + r);
	}*/

    @Override
    protected void initDefaultCommand() {
    }

}