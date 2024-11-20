package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.pathplanner.lib.auto.AutoBuilder;

import cowlib.SwerveModule;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.Odometry;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.networktables.BooleanEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.DriveConstants.ModuleLocations;
import frc.robot.Constants.DriveConstants.SwerveModules;

public class Drivebase extends SubsystemBase {

    private final double DRIVE_REDUCTION = 1.0 / 6.75;
    private final double NEO_FREE_SPEED = 5820.0 / 60.0;
    private final double WHEEL_DIAMETER = 0.1016;
    private final double MAX_VELOCITY = NEO_FREE_SPEED * DRIVE_REDUCTION * WHEEL_DIAMETER * Math.PI;
    private final double MAX_ANGULAR_VELOCITY = MAX_VELOCITY / (ModuleLocations.dist / Math.sqrt(2.0));

    private final double MAX_VOLTAGE = 12;

    private AHRS gyro;

    private SwerveModule frontLeft = new SwerveModule(SwerveModules.frontLeft, MAX_VELOCITY, MAX_VOLTAGE);
    private SwerveModule frontRight = new SwerveModule(SwerveModules.frontRight, MAX_VELOCITY, MAX_VOLTAGE);
    private SwerveModule backLeft = new SwerveModule(SwerveModules.frontLeft, MAX_VELOCITY, MAX_VOLTAGE);
    private SwerveModule backRight = new SwerveModule(SwerveModules.frontRight, MAX_VELOCITY, MAX_VOLTAGE);
    
    private SwerveModule[] modules = new SwerveModule[] { frontLeft, frontRight, backLeft, backRight };

    private SwerveDriveKinematics kinematics = new SwerveDriveKinematics(
        ModuleLocations.frontLeft,
        ModuleLocations.frontRight,
        ModuleLocations.backLeft,
        ModuleLocations.backRight
    );

    private SlewRateLimiter slewRateX = new SlewRateLimiter(DriveConstants.slewRate); // Limits speed of changes in direction
    private SlewRateLimiter slewRateY = new SlewRateLimiter(DriveConstants.slewRate);

    private BooleanEntry fieldOrientedEntry;  // What do?

    // Creates a new drivebase
    public Drivebase(AHRS gyro) {
        var inst = NetworkTableInstance.getDefault();
        var table = inst.getTable("SmartDashboard");
        this.fieldOrientedEntry = table.getBooleanTopic("Field Oriented").getEntry(true);

        this.gyro = gyro;
        odometry = new SwerveDriveOdometry(kinematics, gyro.getRotation2d(), getPositions());

        AutoBuilder.configureHolonomic();
    }

    public Pose2d getPose() {
    return odometry.getPoseMeters();

    public SwerveModulePosition [] getPositions() {
        SwerveModulePosition[] positions = new SwerveModulePosition[modules.length];
        for (int i = 0; i < modules.length; i++) {
            positions[i] = modules[i].getPosition();
        }
    }
  }
}
