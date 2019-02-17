package frc.team691.robot2019.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import frc.team691.robot2019.commands.StickDrive;

public class Drivetrain extends Subsystem {
    private static Drivetrain instance;
    public static synchronized Drivetrain getInstance() {
        if (instance == null) {
            instance = new Drivetrain();
        }
        return instance;
    }

    private WPI_TalonSRX frontLeftTalon;
    private WPI_TalonSRX rearLeftTalon;
    private WPI_TalonSRX frontRightTalon;
    private WPI_TalonSRX rearRightTalon;
    private MecanumDrive mecDrive;

    private Drivetrain() {
        frontLeftTalon = new WPI_TalonSRX(0);
        rearLeftTalon = new WPI_TalonSRX(1);
        frontRightTalon = new WPI_TalonSRX(2);
        rearRightTalon = new WPI_TalonSRX(3);
        mecDrive = new MecanumDrive(frontLeftTalon, rearLeftTalon, frontRightTalon, rearRightTalon);
    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(StickDrive.getInstance());
    }

    public void driveStop() {
        mecDrive.stopMotor();
    }

    public void driveStick(Joystick stick) {
        driveStop();
    }
}
