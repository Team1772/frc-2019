package frc.robot.autons.commands;

import frc.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class TurnToAngle extends Command {
	double leftSpeed, rightSpeed, targetAngle;

	public TurnToAngle(double leftVoltage, double rightVoltage, double angle) {
		leftSpeed   = Math.abs(leftVoltage);
		rightSpeed  = Math.abs(rightVoltage);
		targetAngle = angle;

		if (this.targetAngle > 0)
			this.rightSpeed = -rightSpeed;
		else
			this.leftSpeed = -leftSpeed;
	}

	@Override
	protected boolean isFinished() {

		Robot.driver.setSpeed(leftSpeed, rightSpeed);

		return Math.abs(Robot.driver.getAngle()) >= Math.abs(targetAngle);
	}

	@Override
	protected void initialize() {
		super.initialize();
		Robot.driver.reset();
		System.out.println("[START] TurnToAngle " + this.targetAngle);
	}

	@Override
	protected void end() {
		super.end();
		System.out.println("[END] TurnToAngle " + this.targetAngle + " (" + Robot.driver.getAngle() + ")");
		Robot.driver.setSpeed(0, 0);
		Robot.driver.reset();
	}

}