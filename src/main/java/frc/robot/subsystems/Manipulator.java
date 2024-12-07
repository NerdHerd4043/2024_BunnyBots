package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.IntakeConstants;

public class Manipulator extends SubsystemBase {
    private CANSparkMax subIntakeMotor = new CANSparkMax(IntakeConstants.subMotorID, MotorType.kBrushless);
    private CANSparkMax mainIntaketMotor = new CANSparkMax(IntakeConstants.mainMotorID, MotorType.kBrushless);
    private CANSparkMax indexMotor = new CANSparkMax(IntakeConstants.indexMotorID, MotorType.kBrushless);

    public Manipulator() {
        subIntakeMotor.restoreFactoryDefaults();
        mainIntaketMotor.restoreFactoryDefaults();
        indexMotor.restoreFactoryDefaults();

        subIntakeMotor.setIdleMode(IdleMode.kCoast);
        mainIntaketMotor.setIdleMode(IdleMode.kBrake);
        indexMotor.setIdleMode(IdleMode.kBrake);

        subIntakeMotor.setSmartCurrentLimit(IntakeConstants.manipulatorMotorCurrentLimit);
        mainIntaketMotor.setSmartCurrentLimit(IntakeConstants.manipulatorMotorCurrentLimit);
        indexMotor.setSmartCurrentLimit(IntakeConstants.manipulatorMotorCurrentLimit);
    }

    public void runIntake(double subIntakeMotorSpeed, double mainIntakeMotorSpeed, double indexMotorSpeed) {
        subIntakeMotor.set(subIntakeMotorSpeed);
        mainIntaketMotor.set(mainIntakeMotorSpeed);
        indexMotor.set(indexMotorSpeed);
    }

    public void stopIntake() {
        subIntakeMotor.stopMotor();
        mainIntaketMotor.stopMotor();
        indexMotor.stopMotor();
    }

    @Override
    public void periodic() {

    }
}
