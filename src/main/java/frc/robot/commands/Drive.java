package frc.robot.commands;

import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Drivebase;

public class Drive extends Command {

    private final Drivebase drivebase;
    private final Supplier<double[]> speedXY;
    private final DoubleSupplier rot;

    public Drive(Drivebase drivebase, Supplier<double[]> speedXY, DoubleSupplier rot) {
        this.drivebase = drivebase;
        this.speedXY = speedXY;
        this.rot = rot;

        addRequirements(this.drivebase);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        var xy = speedXY.get();
        var r = rot.getAsDouble();

        drivebase.defaultDrive(xy[0], xy[1], r, true);
    }

    @Override
    public void end(boolean interrupted) {
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
