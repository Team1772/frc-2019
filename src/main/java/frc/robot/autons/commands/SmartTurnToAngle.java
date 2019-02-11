package frc.robot.autons.commands;

import frc.core.util.SpeedControl;
import frc.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class SmartTurnToAngle extends Command {
	private double targetAngle, leftSpeed, rightSpeed, leftInchesPerSecond, rightInchesPerSecond, startVoltage;
	private SpeedControl controlLeft, controlRight;

	public SmartTurnToAngle(double angle, double leftInchesPerSecond, double rightInchesPerSecond, double startVoltage, double offSet) {
		this.targetAngle = Math.abs(angle) - offSet;
		this.targetAngle = angle > 0 ? this.targetAngle : -this.targetAngle;

		this.leftInchesPerSecond = leftInchesPerSecond;
		this.rightInchesPerSecond = rightInchesPerSecond;
		this.startVoltage = startVoltage;

		controlLeft  = new SpeedControl();
		controlRight = new SpeedControl();
	}

	public SmartTurnToAngle(double angle, double leftInchesPerSecond, double rightInchesPerSecond, double startVoltage) {
		this(angle, leftInchesPerSecond, rightInchesPerSecond, startVoltage, 1);
	}

	public SmartTurnToAngle(double angle, double leftInchesPerSecond, double rightInchesPerSecond) {
		this(angle, leftInchesPerSecond, rightInchesPerSecond, 0.1, 1);
	}

	public SmartTurnToAngle(double angle, double inchesPerSecond) {
		this(angle, inchesPerSecond, inchesPerSecond, 0.1, 1);
	}

	@Override
	protected boolean isFinished() {
		leftSpeed  = controlLeft.calc(this.leftInchesPerSecond, Robot.driver.getLeftDistance());
		rightSpeed = controlRight.calc(this.rightInchesPerSecond, Robot.driver.getRightDistance());

		if (this.targetAngle > 0)
			rightSpeed = -rightSpeed;
		else
			leftSpeed = -leftSpeed;

		Robot.driver.setSpeed(leftSpeed, rightSpeed);

		return Math.abs(Robot.driver.getAngle()) >= Math.abs(targetAngle);
	}

	private void reset() {
		controlLeft.reset(this.startVoltage);
		controlRight.reset(this.startVoltage);
	}

	@Override
	protected void initialize() {
		super.initialize();
		reset();
		System.out.println("[START] SmartTurnToAngle " + this.targetAngle);
	}

	@Override
	protected void end() {
		super.end();
		System.out.println("[END] SmartTurnToAngle " + this.targetAngle + " (" + Robot.driver.getAngle() + ")");

		Robot.driver.setSpeed(0, 0);
		Robot.driver.reset();
	}

}
