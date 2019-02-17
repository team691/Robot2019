package frc.team691.robot2019.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team691.robot2019.commands.StickDrive;

public class Drivetrain extends Subsystem {
    private static double MOTOR_MIN_OUT = 0.0;
    private static double MOTOR_MAX_OUT = 0.8;
    private static double K_LOG         = 10;
    private static double X_MID         = 0.5;

    private ADXRS450_Gyro gyro              = new ADXRS450_Gyro();
    private WPI_TalonSRX frontLeftTalon     = new WPI_TalonSRX(1);
    private WPI_TalonSRX rearLeftTalon      = new WPI_TalonSRX(0);
    private WPI_TalonSRX frontRightTalon    = new WPI_TalonSRX(3);
    private WPI_TalonSRX rearRightTalon     = new WPI_TalonSRX(2);
    private MecanumDrive mecDrive = new MecanumDrive(frontLeftTalon,
    rearLeftTalon, frontRightTalon, rearRightTalon);
    
    private boolean isFieldDrive = false;

    private Drivetrain() {
        SmartDashboard.putNumber("MOTOR_MIN_OUT",   SmartDashboard.getNumber("MOTOR_MIN_OUT", MOTOR_MIN_OUT));
        SmartDashboard.putNumber("MOTOR_MAX_OUT",   SmartDashboard.getNumber("MOTOR_MAX_OUT", MOTOR_MAX_OUT));
        SmartDashboard.putNumber("K_LOG",           SmartDashboard.getNumber("K_LOG", K_LOG));
        SmartDashboard.putNumber("X_MID",           SmartDashboard.getNumber("X_MID", X_MID));
    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new StickDrive());
    }

    @Override
    public void periodic() {
        SmartDashboard.putBoolean("isFieldDrive", isFieldDrive);
        SmartDashboard.putNumber("gyro", gyro.getAngle());
        MOTOR_MIN_OUT   = SmartDashboard.getNumber("MOTOR_MIN_OUT", MOTOR_MIN_OUT);
        MOTOR_MAX_OUT   = SmartDashboard.getNumber("MOTOR_MAX_OUT", MOTOR_MAX_OUT);
        K_LOG           = SmartDashboard.getNumber("K_LOG", K_LOG);
        X_MID           = SmartDashboard.getNumber("X_MID", X_MID);
    }

    public void toggleFieldDrive() {
        isFieldDrive = !isFieldDrive;
    }

    public void resetFieldDrive() {
        gyro.reset();
    }

    public void driveStop() {
        mecDrive.stopMotor();
    }

    public void driveXbox(XboxController xbox) {
        double yOut = xbox.getX(Hand.kLeft);
        double xOut = xbox.getY(Hand.kLeft);
        double zOut = xbox.getX(Hand.kRight);
        driveLogistic(yOut, xOut, zOut);
    }

    // Requires stick type X3D
    public void driveStick(Joystick stick) {
        double yOut = stick.getX();
        double xOut = stick.getY();
        double zOut = stick.getZ();
        driveLogistic(yOut, xOut, zOut);
    }

    public void driveLogistic(double yOut, double xOut, double zOut) {
        yOut = logisticScale(yOut);
        xOut = logisticScale(xOut);
        zOut = logisticScale(zOut);
        drive(yOut, xOut, zOut);
    }

    public void drive(double yOut, double xOut, double zOut) {
        double gAngle = (isFieldDrive ? gyro.getAngle() : 0);
        mecDrive.driveCartesian(yOut, xOut, zOut, gAngle);
    }

    private double logisticScale(double x) {
        double ax = Math.abs(x);
        double y = (MOTOR_MAX_OUT - MOTOR_MIN_OUT) /
            (1 + Math.exp(K_LOG * (X_MID - ax))) + MOTOR_MIN_OUT;
        return Math.copySign(y, x);
    }
    
    private static Drivetrain instance;
    public static synchronized Drivetrain getInstance() {
        if (instance == null) {
            instance = new Drivetrain();
        }
        return instance;
    }
}
