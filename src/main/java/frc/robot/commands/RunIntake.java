package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Intake;

public class RunIntake extends Command {
    public final Intake intake;
    public final double subIntakeMotorSpeed;
    public final double mainIntakeMotorSpeed;
    public final double indexMotorSpeed;

    public RunIntake(Intake intake, double subIntakeMotorSpeed, double mainIntakeMotorSpeed, double indexMotorSpeed) {
        this.intake = intake;
        this.subIntakeMotorSpeed = subIntakeMotorSpeed;
        this.mainIntakeMotorSpeed = mainIntakeMotorSpeed;
        this.indexMotorSpeed = indexMotorSpeed;

        addRequirements(this.intake);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        intake.runIntake(subIntakeMotorSpeed, mainIntakeMotorSpeed, indexMotorSpeed);
    }

    @Override
    public void end(boolean interrupted) {
        intake.stopIntake();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}