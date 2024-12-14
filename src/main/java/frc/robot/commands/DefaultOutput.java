// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Manipulator;

public class DefaultOutput extends Command {
  public final Manipulator manipulator;
  public final DoubleSupplier mainMotorSpeed;
  public final DoubleSupplier indexMotorSpeed;

  /** Creates a new DefaultOuttake. */
  public DefaultOutput(Manipulator manipulator, DoubleSupplier mainMotorSpeed, DoubleSupplier indexMotorSpeed) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.manipulator = manipulator;
    this.mainMotorSpeed = mainMotorSpeed;
    this.indexMotorSpeed = indexMotorSpeed;

    addRequirements(this.manipulator);

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    manipulator.runManipulator(0, mainMotorSpeed.getAsDouble(), indexMotorSpeed.getAsDouble());
  }

  @Override
  public void end(boolean interrupted) {
    manipulator.stopManipulator();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
