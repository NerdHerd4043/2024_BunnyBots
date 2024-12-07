package frc.robot;

import cowlib.SwerveModuleConfig;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.util.Units;

public class Constants {
    public static final class DriveConstants {
        public static final double deadband = 0.08;
        public static final int currentLimit = 40;
        public static final double slewRate = 20; // lower number for higher center of mass

        public static final class SwervePID {
            public static final double p = 0.12; // Big P = Current error
            public static final double i = 0; // Cumulative error
            public static final double d = 0.0015; // Error's rate of change
        }

        public static final class SwerveModules { // TODO Match IDs
            public static final SwerveModuleConfig frontRight = new SwerveModuleConfig(1, 11, 21, true);
            public static final SwerveModuleConfig frontLeft = new SwerveModuleConfig(2, 12, 22, false);
            public static final SwerveModuleConfig backLeft = new SwerveModuleConfig(3, 13, 23, false);
            public static final SwerveModuleConfig backRight = new SwerveModuleConfig(4, 14, 24, true);
        }

        public static final class ModuleLocations {
            public static final double moduleLocationWidth = Units.inchesToMeters(13.25);
            public static final double moduleLocationLength = Units.inchesToMeters(11.5);
            public static final double robotRaduius = Math
                    .sqrt(Math.pow(moduleLocationLength, 2) + Math.pow(moduleLocationWidth, 2));
            public static final Translation2d frontLeft = new Translation2d(moduleLocationWidth, moduleLocationLength);
            public static final Translation2d frontRight = new Translation2d(moduleLocationWidth,
                    -moduleLocationLength);
            public static final Translation2d backLeft = new Translation2d(-moduleLocationWidth, moduleLocationLength);
            public static final Translation2d backRight = new Translation2d(-moduleLocationWidth,
                    -moduleLocationLength);
        }
    }

}
