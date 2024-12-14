package frc.robot.commands.LowAuto;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Drivebase;

public class LowAutoMove extends Command {
    private final Drivebase drivebase;
    private double startTime;

    public LowAutoMove(Drivebase drivebase) {
        this.drivebase = drivebase;
        addRequirements(this.drivebase);
    }

    @Override
    public void initialize() {
        this.startTime = Timer.getFPGATimestamp();
    }

    @Override
    public void execute() {
        drivebase.robotOrientedDrive(0.8, 0, 0);
    }

    @Override
    public void end(boolean interrupted) {
        drivebase.robotOrientedDrive(0, 0, 0);
    }

    @Override
    public boolean isFinished() {
        return Timer.getFPGATimestamp() >= this.startTime + 8;
    }
}

