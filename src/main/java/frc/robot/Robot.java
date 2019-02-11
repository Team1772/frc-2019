/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import frc.core.util.XboxControl;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Driver;

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

  @Override
  public void robotInit() {
    driver   = new Driver();
    arm      = new Arm();
    pilot    = new XboxControl(0);
    copilot  = new XboxControl(1);
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


    if (copilot.getButtonL1()) {
        arm.setSpeed(copilot.getAxisLeftY());
    } else {
        arm.setSpeed(0);
    }

    driver.print();
  }

  @Override
  public void testPeriodic() {
  }
}
