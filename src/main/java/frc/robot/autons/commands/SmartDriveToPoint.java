package frc.robot.autons.commands;

import frc.core.actions.MoveToPointHelper;
import frc.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class SmartDriveToPoint extends Command {
	private MoveToPointHelper moveToPointHelper;

	public SmartDriveToPoint(double targetInches, double leftInchesPerSecond, double rightInchesPerSecond, double startVoltage, double offSet) {
		moveToPointHelper = new MoveToPointHelper(targetInches, leftInchesPerSecond, rightInchesPerSecond, startVoltage, offSet);
	}
	public SmartDriveToPoint(double targetInches, double leftInchesPerSecond, double rightInchesPerSecond) {
		this(targetInches, leftInchesPerSecond, rightInchesPerSecond, 0.1, 1);
	}
	public SmartDriveToPoint(double targetInches, double leftInchesPerSecond, double rightInchesPerSecond, double startVoltage) {
		this(targetInches, leftInchesPerSecond, rightInchesPerSecond, startVoltage, 1);
	}

	@Override
	protected boolean isFinished() {
		return moveToPointHelper.isFinished(Robot.driver.getLeftDistance(), Robot.driver.getRightDistance(), (leftSpeed, rightSpeed) -> {
			Robot.driver.setSpeed(leftSpeed, rightSpeed);
		});
	}

	@Override
	protected void initialize() {
		super.initialize();
		moveToPointHelper.reset();
		System.out.println("[START] SmartDriveToPoint " + moveToPointHelper.getTarget());
	}

	@Override
	protected void end() {
		super.end();
		double currentDistance = moveToPointHelper.getCurrentDistance(Robot.driver.getLeftDistance(), Robot.driver.getRightDistance());
		System.out.println("[END] SmartDriveToPoint " + moveToPointHelper.getTarget() + " (" + currentDistance + ")");

		Robot.driver.setSpeed(0, 0);
		Robot.driver.reset();
	}

}
