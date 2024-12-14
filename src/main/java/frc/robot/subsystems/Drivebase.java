package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;

import cowlib.SwerveModule;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.networktables.BooleanEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.DriveConstants.ModuleLocations;
import frc.robot.Constants.DriveConstants.SwerveModules;

public class Drivebase extends SubsystemBase {

    private final double DRIVE_REDUCTION = 1.0 / 6.12; // Where to find drive reduction (for swerve):
                                                       // (https://www.swervedrivespecialties.com/products/mk4i-swerve-module)
    private final double NEO_FREE_SPEED = 5820.0 / 60.0; // Get from website, divide by 60 since the number that the
                                                         // website gives is in rotations per minute and we want
                                                         // rotations per second.
    private final double WHEEL_DIAMETER = 0.1016; // Measured by hand
    private final double MAX_VELOCITY = NEO_FREE_SPEED * DRIVE_REDUCTION * WHEEL_DIAMETER * Math.PI; // Max velocity =
    // Fastest the motor can spin
    // (free speed)
    // times the gear
    // ratio times
    // the
    // circumference
    // of
    // the wheel
    // (diameter
    // (twice
    // the radius)
    // times pi).
    private final double MAX_ANGULAR_VELOCITY = MAX_VELOCITY / (ModuleLocations.robotRaduius); // Max velocity applied
                                                                                               // tangentially (in a
                                                                                               // direction that causes
                                                                                               // the robot to spin)
                                                                                               // divided by the radius
                                                                                               // of the robot (center
                                                                                               // to wheel) is equal to
                                                                                               // the max angular
                                                                                               // velocity.
                                                                                               // Rearrangement of basic
                                                                                               // physics formula.

    private final double MAX_VOLTAGE = 12; // This is the battery voltage.

    private AHRS gyro;

    private SwerveModule frontLeft = new SwerveModule(SwerveModules.frontLeft, MAX_VELOCITY, MAX_VOLTAGE);
    private SwerveModule frontRight = new SwerveModule(SwerveModules.frontRight, MAX_VELOCITY, MAX_VOLTAGE);
    private SwerveModule backLeft = new SwerveModule(SwerveModules.backLeft, MAX_VELOCITY, MAX_VOLTAGE);
    private SwerveModule backRight = new SwerveModule(SwerveModules.backRight, MAX_VELOCITY, MAX_VOLTAGE);

    private SwerveModule[] modules = new SwerveModule[] { frontLeft, frontRight, backLeft, backRight };

    private SwerveDriveKinematics kinematics = new SwerveDriveKinematics(
            ModuleLocations.frontLeft,
            ModuleLocations.frontRight,
            ModuleLocations.backLeft,
            ModuleLocations.backRight);

    private SwerveDriveOdometry odometry;

    private Field2d field = new Field2d();

    private SlewRateLimiter slewRateX = new SlewRateLimiter(DriveConstants.slewRate); // Limits speed of changes in
                                                                                      // direction
    private SlewRateLimiter slewRateY = new SlewRateLimiter(DriveConstants.slewRate);

    private SendableChooser<Double> driveSpeedChooser = new SendableChooser<>();

    private BooleanEntry fieldOrientedEntry; // What do?

    // Creates a new drivebase
    public Drivebase(AHRS gyro) {
        var inst = NetworkTableInstance.getDefault();
        var table = inst.getTable("SmartDashboard");
        this.fieldOrientedEntry = table.getBooleanTopic("Field Oriented").getEntry(true);

        this.driveSpeedChooser = new SendableChooser<>();

        this.driveSpeedChooser.setDefaultOption("Full Speed", 1.0);
        this.driveSpeedChooser.addOption("Three-Quarter Speed", 0.75);
        this.driveSpeedChooser.addOption("Half Speed", 0.5);
        this.driveSpeedChooser.addOption("Quarter Speed", 0.25);
        this.driveSpeedChooser.addOption("No Speed", 0.0);

        SmartDashboard.putData(this.driveSpeedChooser);

        this.gyro = gyro;
        odometry = new SwerveDriveOdometry(kinematics, gyro.getRotation2d(), getModulePositions());
    }

    public double getFieldAngle() {
        return -gyro.getYaw();
    }

    public void fieldOrientedDrive(double speedX, double speedY, double rot) {
        ChassisSpeeds speeds = ChassisSpeeds.fromFieldRelativeSpeeds(speedX, speedY, rot,
                Rotation2d.fromDegrees(getFieldAngle()));
        this.drive(speeds);
    }

    public void robotOrientedDrive(double speedX, double speedY, double rot) {
        ChassisSpeeds speeds = new ChassisSpeeds(speedX, speedY, rot);
        this.drive(speeds);
    }

    public void defaultDrive(double speedX, double speedY, double rot, boolean slew) {
        if (slew) {
            speedX = slewRateX.calculate(speedX);
            speedY = slewRateY.calculate(speedY);
        }
        if (this.fieldOrientedEntry.get(true)) {
            fieldOrientedDrive(speedX, speedY, rot);
        } else {
            robotOrientedDrive(speedX, speedY, rot);
        }
    }

    private void drive(ChassisSpeeds speeds) {
        SwerveModuleState[] moduleStates = kinematics.toSwerveModuleStates(speeds);

        SwerveDriveKinematics.desaturateWheelSpeeds(moduleStates, MAX_VELOCITY * getRobotSpeedRatio());

        this.frontLeft.drive(moduleStates[0]);
        this.frontRight.drive(moduleStates[1]);
        this.backLeft.drive(moduleStates[2]);
        this.backRight.drive(moduleStates[3]);
    }

    public double getMaxVelocity() {
        return MAX_VELOCITY;
    }

    public double getMaxAngleVelocity() {
        return MAX_ANGULAR_VELOCITY;
    }

    public Pose2d getRobotPose() {
        return odometry.getPoseMeters();
    }

    public SwerveModulePosition[] getModulePositions() {
        SwerveModulePosition[] positions = new SwerveModulePosition[modules.length];
        for (int i = 0; i < modules.length; i++) {
            positions[i] = modules[i].getPosition();
        }
        return positions;
    }

    public double getRobotSpeedRatio() {
        return this.driveSpeedChooser.getSelected();
    }

    public void periodic() {
        var positions = getModulePositions();

        odometry.update(gyro.getRotation2d(), positions);
        var pose = getRobotPose();

        // Everything below (within the function) is unnecessary for running the robot
        var translation = pose.getTranslation();
        var x = translation.getX();
        var y = translation.getY();
        var rotation = pose.getRotation().getDegrees();
        SmartDashboard.putNumber("x", x);
        SmartDashboard.putNumber("y", y);
        SmartDashboard.putNumber("rot", rotation);
        field.setRobotPose(getRobotPose());

        SmartDashboard.putNumber("Speed Ratio", getRobotSpeedRatio());
    }
}
