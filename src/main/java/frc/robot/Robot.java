/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.TimedRobot;
import frc.core.util.XboxControl;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.ArmPID;
import frc.robot.subsystems.Camera;
import frc.robot.subsystems.Driver;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Pistons;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

  XboxControl pilot, copilot;
  public static Driver driver;
  public static Arm arm;
  public static Intake intake;
  public static Pistons pistons;
  public static Camera camera;

  @Override
  public void robotInit() {
    driver   = new Driver();
    arm      = new Arm();
    intake   = new Intake();
    pilot    = new XboxControl(0);
    copilot  = new XboxControl(1);
    pistons  = new Pistons();
    camera   = new Camera();

  }

  @Override
  public void robotPeriodic() {
  }

  @Override
  public void autonomousInit() {
  }

  @Override
  public void autonomousPeriodic() {
  }

  double rotation = 0;
  @Override
  public void teleopPeriodic() {

    rotation = pilot.getAxisRightX();

    if (pilot.getButtonL1()) {
        rotation = rotation / 1.4;
        driver.reset();
    }

    driver.arcadeDrive(pilot.getAxisLeftY(), rotation);

    if (copilot.getButtonR1()) {
      intake.setSpeed(copilot.getAxisRightY()*-1);
    } else {
      intake.setSpeed(0);
    }

    if (copilot.getButtonA()) {
      intake.setSpeedRol(0.5);
    } else {
      intake.setSpeedRol(0);
    }

    if (copilot.getButtonB()) {
      pistons.setClimbBack(true);
    }

    driver.print();
  }

  @Override
  public void testPeriodic() {
  }

  public void disabledInit() {
  }

  public void disabledPeriodic() {
    arm.getRPM();
  }

  public void teleopInit() {}
}

