package frc.robot.subsystems;

import cowlib.SwerveModule;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveConstants.ModuleLocations;
import frc.robot.Constants.DriveConstants.SwerveModules;

public class Drivebase extends SubsystemBase{

    private final double DRIVE_REDUCTION = 1.0 / 6.75;
    private final double NEO_FREE_SPEED = 5820.0 / 60.0;
    private final double WHEEL_DIAMETER = 0.1016;
    private final double MAX_VELOCITY = NEO_FREE_SPEED * DRIVE_REDUCTION * WHEEL_DIAMETER * Math.PI;
    private final double MAX_ANGULAR_VELOCITY = MAX_VELOCITY / (ModuleLocations.dist / Math.sqrt(2.0));

    private final double MAX_VOLTAGE = 12;

    private SwerveModule frontLeft = new SwerveModule(SwerveModules.frontLeft, MAX_VELOCITY, MAX_VOLTAGE);
    private SwerveModule frontRight = new SwerveModule(SwerveModules.frontRight, MAX_ANGULAR_VELOCITY, MAX_VOLTAGE);

    
}
