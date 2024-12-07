// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.ManipulatorConstants;
import frc.robot.commands.Drive;
import frc.robot.commands.RunManipulator;
import frc.robot.subsystems.Drivebase;
import frc.robot.subsystems.Manipulator;

public class RobotContainer {
  private final AHRS gyro = new AHRS();
  
  private final Drivebase drivebase = new Drivebase(gyro);

  private final Manipulator manipulator = new Manipulator();

  private static XboxController driveStick = new XboxController(0);
  private static CommandXboxController c_driveStick = new CommandXboxController(0);

  public RobotContainer() {

    drivebase.setDefaultCommand(
        new Drive(
            drivebase,
            () -> getScaledXY(),
            () -> scaleRotationAxis(driveStick.getRightX())));

    configureBindings();
  }

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }

  private double[] getXY() {
    double[] xy = new double[2];
    xy[1] = deadband(driveStick.getLeftX(), DriveConstants.deadband);
    xy[0] = deadband(driveStick.getLeftY(), DriveConstants.deadband);
    return xy;
  }

  private double[] getScaledXY() {
    double[] xy = getXY();

    // Convert to Polar coordinates
    double r = Math.sqrt(xy[0] * xy[0] + xy[1] * xy[1]);
    double theta = Math.atan2(xy[1], xy[0]);

    // Square radius and scale by max velocity
    r = r * r * drivebase.getMaxVelocity();

    // Convert to Cartesian coordinates
    xy[0] = r * Math.cos(theta);
    xy[1] = r * Math.sin(theta);

    return xy;
  }

  private double scaleRotationAxis(double input) {
    return deadband(squared(input), DriveConstants.deadband) * drivebase.getMaxAngleVelocity() * 0.6;
  }

  private double squared(double input) {
    return Math.copySign(input * input, input);
  }

  private double deadband(double input, double deadband) {
    if (Math.abs(input) < deadband) {
      return 0;
    } else {
      return input;
    }
  }

  private void configureBindings() {
    // Gyro Reset
    c_driveStick.povUp().onTrue(Commands.runOnce(gyro::reset));

    // Intake
    c_driveStick.rightTrigger().whileTrue(
        new RunManipulator(
            manipulator, ManipulatorConstants.subMotorSpeed, ManipulatorConstants.mainMotorSpeed,
            ManipulatorConstants.indexMotorSpeed));

    // Output
    c_driveStick.a().whileTrue(
        new RunManipulator(
            manipulator, 0, ManipulatorConstants.mainMotorSpeed, -ManipulatorConstants.indexMotorSpeed));
  }

}
