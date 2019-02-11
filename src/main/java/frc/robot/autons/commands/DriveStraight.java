package frc.robot.autons.commands;

import frc.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveStraight extends Command {
	private double targetInches, leftSpeed, rightSpeed, leftOutput, rightOutput;
	private long currentTime, lastTime;

	private double kp;

	public DriveStraight(double targetInches, double leftVoltage, double rightVoltage) {
		this.targetInches = targetInches;
		this.leftSpeed    = leftVoltage;
		this.rightSpeed   = rightVoltage;

		System.out.println("DriveStraight Created " + targetInches);
	}

	public DriveStraight(double targetInches, double speed) {
		this(targetInches, speed, speed);
	}

	@Override
	protected void initialize() {
		super.initialize();
		kp = SmartDashboard.getNumber("constant-navx", 0.02D);
		SmartDashboard.putNumber("constant-navx", kp);
		Robot.driver.reset();

		leftOutput  = leftSpeed;
		rightOutput = rightSpeed;
		lastTime    = System.currentTimeMillis();

		System.out.println("[START] DriveStraight " + targetInches + " inches " + Robot.driver.getAngle());
	}

	@Override
	protected boolean isFinished() {
    	currentTime = System.currentTimeMillis() - lastTime;
    	if (currentTime >= 100) {

    		lastTime = System.currentTimeMillis();

    		if (Robot.driver.getAngle() > 0.3)
    			if (targetInches > 0) calcLeftSpeed();
    			else calcRightSpeed();
    		else if (Robot.driver.getAngle() < -0.3)
    			if (targetInches > 0) calcRightSpeed();
    			else calcLeftSpeed();
    	}

    	setSpeed();

		return getCurrentDistance() > Math.abs(targetInches) || isTimedOut();
	}

	@Override
	protected void end() {
		super.end();
		System.out.println("[END] DriveStraight " + targetInches + " (" + getCurrentDistance() + ") 0" + "(" + Robot.driver.getAngle() + ")");
		Robot.driver.reset();
		Robot.driver.setSpeed(0, 0);
		SmartDashboard.putNumber("constant-navx used", kp);
	}

	private double calcSpeed(double sp, double defaultSpeed) {
		return Math.abs(sp - kp);
	}

	private void calcLeftSpeed() {
		leftOutput = calcSpeed(leftOutput, leftSpeed);
	}

	private void calcRightSpeed() {
		rightOutput = calcSpeed(rightOutput, rightSpeed);
	}

	private void setSpeed() {
		if (targetInches > 0)
			Robot.driver.setSpeed(leftOutput, rightOutput);
		else
			Robot.driver.setSpeed(-leftOutput, -rightOutput);
	}

	private double getCurrentDistance() {
		return Math.abs(Robot.driver.getRightDistance());
	}

}
