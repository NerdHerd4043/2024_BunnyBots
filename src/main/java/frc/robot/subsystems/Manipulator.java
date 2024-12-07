package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ManipulatorConstants;

public class Manipulator extends SubsystemBase {
    private CANSparkMax subManipulatorMotor = new CANSparkMax(ManipulatorConstants.subMotorID, MotorType.kBrushless);
    private CANSparkMax mainManipulatorMotor = new CANSparkMax(ManipulatorConstants.mainMotorID, MotorType.kBrushless);
    private CANSparkMax indexMotor = new CANSparkMax(ManipulatorConstants.indexMotorID, MotorType.kBrushless);

    public Manipulator() {
        subManipulatorMotor.restoreFactoryDefaults();
        mainManipulatorMotor.restoreFactoryDefaults();
        indexMotor.restoreFactoryDefaults();

        subManipulatorMotor.setIdleMode(IdleMode.kCoast);
        mainManipulatorMotor.setIdleMode(IdleMode.kBrake);
        indexMotor.setIdleMode(IdleMode.kBrake);

        subManipulatorMotor.setSmartCurrentLimit(ManipulatorConstants.manipulatorMotorCurrentLimit);
        mainManipulatorMotor.setSmartCurrentLimit(ManipulatorConstants.manipulatorMotorCurrentLimit);
        indexMotor.setSmartCurrentLimit(ManipulatorConstants.manipulatorMotorCurrentLimit);
    }

    public void runManipulator(double subManipulatorMotorSpeed, double mainManipulatorMotorSpeed,
            double indexMotorSpeed) {
        subManipulatorMotor.set(subManipulatorMotorSpeed);
        mainManipulatorMotor.set(mainManipulatorMotorSpeed);
        indexMotor.set(indexMotorSpeed);
    }

    public void stopManipulator() {
        subManipulatorMotor.stopMotor();
        mainManipulatorMotor.stopMotor();
        indexMotor.stopMotor();
    }

    @Override
    public void periodic() {

    }
}
