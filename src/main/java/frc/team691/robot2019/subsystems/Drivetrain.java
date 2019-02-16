package frc.team691.robot2019.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team691.robot2019.commands.StickDrive;

public class Drivetrain extends Subsystem {
    private static double MOTOR_MIN_OUT = 0.1;
    private static double MOTOR_MAX_OUT = 0.8;
    private static double K_LOG = 10;
    private static double X_MID = 0.5;

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
        frontLeftTalon = new WPI_TalonSRX(1);
        rearLeftTalon = new WPI_TalonSRX(0);
        frontRightTalon = new WPI_TalonSRX(3);
        rearRightTalon = new WPI_TalonSRX(2);
        mecDrive = new MecanumDrive(frontLeftTalon, rearLeftTalon,
            frontRightTalon, rearRightTalon);

        SmartDashboard.putNumber("MOTOR_MIN_OUT",   SmartDashboard.getNumber("MOTOR_MIN_OUT", MOTOR_MIN_OUT));
        SmartDashboard.putNumber("MOTOR_MAX_OUT",   SmartDashboard.getNumber("MOTOR_MAX_OUT", MOTOR_MAX_OUT));
        SmartDashboard.putNumber("K_LOG",           SmartDashboard.getNumber("K_LOG", K_LOG));
        SmartDashboard.putNumber("X_MID",           SmartDashboard.getNumber("X_MID", X_MID));
    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(StickDrive.getInstance());
    }

    @Override
    public void periodic() {
        MOTOR_MIN_OUT = SmartDashboard.getNumber("MOTOR_MIN_OUT", MOTOR_MIN_OUT);
        MOTOR_MAX_OUT = SmartDashboard.getNumber("MOTOR_MAX_OUT", MOTOR_MAX_OUT);
        K_LOG =         SmartDashboard.getNumber("K_LOG", K_LOG);
        X_MID =         SmartDashboard.getNumber("X_MID", X_MID);
    }

    public void driveStop() {
        mecDrive.stopMotor();
    }

    public void driveStick(Joystick stick) {
        mecDrive.driveCartesian(stick.getX(), -stick.getY(), 0);
        //mecDrive.driveCartesian(logisticScale(lostick.getX()),
        //    logisticScale(-stick.getY()), 0);
    }

    private double logisticScale(double x) {
        double ax = Math.abs(x);
        double y = (MOTOR_MAX_OUT - MOTOR_MIN_OUT) /
            (1 + Math.exp(K_LOG * (X_MID - ax))) + MOTOR_MIN_OUT;
        return Math.copySign(y, x);
    }
}
