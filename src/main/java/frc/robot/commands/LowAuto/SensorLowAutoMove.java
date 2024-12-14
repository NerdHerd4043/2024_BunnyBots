package frc.robot.commands.LowAuto;

import java.util.function.DoubleSupplier;

import com.revrobotics.Rev2mDistanceSensor;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Drivebase;
import frc.robot.subsystems.Manipulator;

public class SensorLowAutoMove extends Command {
    private final Drivebase drivebase;
    private final Manipulator manipulator;
    private double startTime;
    private Rev2mDistanceSensor distSensor;

    public SensorLowAutoMove(Drivebase drivebase, Manipulator manipulator, Rev2mDistanceSensor distSensor) {
        this.drivebase = drivebase;
        this.distSensor = distSensor;
        this.manipulator = manipulator;

        addRequirements(this.drivebase);
    }

    @Override
    public void initialize() {
        this.startTime = Timer.getFPGATimestamp();
    }

    @Override
    public void execute() {
        drivebase.robotOrientedDrive(0.8, 0, 0);

        SmartDashboard.putNumber("Distance", this.distSensor.getRange());
    }

    @Override
    public void end(boolean interrupted) {
        drivebase.robotOrientedDrive(0, 0, 0);
    }

    @Override
    public boolean isFinished() {
        return this.distSensor.getRange() > 3;
    }
}
