package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Manipulator;

public class RunManipulator extends Command {
    public final Manipulator manipulator;
    public final double subMotorSpeed;
    public final double mainMotorSpeed;
    public final double indexMotorSpeed;
    public final boolean beamBreak;

    public RunManipulator(Manipulator manipulator, double subMotorSpeed, double mainMotorSpeed,
            double indexMotorSpeed, Boolean beamBreak) {
        this.manipulator = manipulator;
        this.subMotorSpeed = subMotorSpeed;
        this.mainMotorSpeed = mainMotorSpeed;
        this.beamBreak = beamBreak;

        if (beamBreak = true) {
            this.indexMotorSpeed = indexMotorSpeed;
        } else {
            this.indexMotorSpeed = 0;
        }

        addRequirements(this.manipulator);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        manipulator.runManipulator(subMotorSpeed, mainMotorSpeed, indexMotorSpeed);
    }

    @Override
    public void end(boolean interrupted) {
        manipulator.stopManipulator();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
