package frc.robot.commands.LowAuto;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Manipulator;

public class AutoOutput extends Command {
    private final Manipulator manipulator;
    private double startTime;

    public AutoOutput(Manipulator manipulator) {
        this.manipulator = manipulator;

        addRequirements(this.manipulator);
    }

    @Override
    public void initialize() {
        this.startTime = Timer.getFPGATimestamp();
    }

    @Override
    public void execute() {
        manipulator.runManipulator(0, 0.8, -0.1);
    }

    @Override
    public void end(boolean interrupted) {
        manipulator.stopManipulator();
    }

    @Override
    public boolean isFinished() {
        return Timer.getFPGATimestamp() >= this.startTime + 8;
    }
}
