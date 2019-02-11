package frc.core.actions;

import frc.core.superclasses.Iinput;
import frc.core.util.SpeedControl;

public class MoveToPointHelper {
	private double targetInches, leftInchesPerSecond, rightInchesPerSecond, leftSpeed, rightSpeed, startVoltage;
	private SpeedControl controlLeft, controlRight;
	private boolean isFinished;

	public MoveToPointHelper(double targetInches, double inchesPerSecond, double offSet) {
		this(targetInches, inchesPerSecond, inchesPerSecond, 0.1, offSet);
	}

	public MoveToPointHelper(double targetInches, double leftInchesPerSecond, double rightInchesPerSecond, double startVoltage, double offSet) {
		this.targetInches = Math.abs(targetInches) - offSet;
		this.targetInches = targetInches > 0 ? this.targetInches : -this.targetInches;

		this.leftInchesPerSecond = leftInchesPerSecond;
		this.rightInchesPerSecond = rightInchesPerSecond;

		this.startVoltage = startVoltage;

		controlLeft  = new SpeedControl();
		controlRight = new SpeedControl();
	}

	public boolean isFinished(double leftDistance, double rightDistance, Iinput input) {

		leftSpeed  = controlLeft.calc(leftInchesPerSecond, Math.abs(leftDistance), "left");
		rightSpeed = controlRight.calc(rightInchesPerSecond, Math.abs(rightDistance), "right");

		if (this.targetInches < 0) {
			leftSpeed = -leftSpeed;
			rightSpeed = -rightSpeed;
		}

		input.run(leftSpeed, rightSpeed);

		isFinished = getCurrentDistance(leftDistance, rightDistance) > Math.abs(targetInches);

		if (isFinished) reset();

		return isFinished;
	}

	public void reset() {
		controlLeft.reset(startVoltage);
		controlRight.reset(startVoltage);
	}

	public double getTarget() {
		return targetInches;
	}

	public double getCurrentDistance(double leftDistance, double rightDistance) {
		return (Math.abs(leftDistance) + Math.abs(rightDistance)) / 2;
	}
}
