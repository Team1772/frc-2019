package frc.robot.autons.commands;

import frc.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ResetDrive extends Command {
	private double forceStopVoltageLeft, forceStopVoltageRight;

	public ResetDrive(double forceStopVoltageLeft, double forceStopVoltageRight) {
		this.forceStopVoltageLeft  = forceStopVoltageLeft;
		this.forceStopVoltageRight = forceStopVoltageRight;
	}
	public ResetDrive(double forceStopVoltage) {
		this.forceStopVoltageLeft  = forceStopVoltage;
		this.forceStopVoltageRight = forceStopVoltage;
	}
	public ResetDrive() {
		this(0, 0);
	}

	@Override
	protected boolean isFinished() {
		Robot.driver.setSpeed(forceStopVoltageLeft, forceStopVoltageRight);
		return isTimedOut();
	}

	@Override
	protected void end() {
		super.end();
		Robot.driver.setSpeed(0, 0);
		Robot.driver.reset();
	}

}
