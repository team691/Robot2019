package frc.team691.robot2019.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team691.robot2019.OI;
import frc.team691.robot2019.commands.StickDrive;

public class Drivetrain extends Subsystem {
    private static final double XBOX_MIN_IN = 0.1;
    private static final double MOTOR_MIN_OUT = 0.05;
    private static final double K_LOG = 10;
    private static final double X_MID = 0.5;
    private static double MOTOR_MAX_OUT = 0.6;
    private static double MEC_Y_MAX_OUT = 0.8;

    private AHRS navx = new AHRS(SPI.Port.kMXP);
    private WPI_TalonSRX frontLeftTalon = new WPI_TalonSRX(1);
    private WPI_TalonSRX rearLeftTalon = new WPI_TalonSRX(0);
    private WPI_TalonSRX frontRightTalon = new WPI_TalonSRX(3);
    private WPI_TalonSRX rearRightTalon = new WPI_TalonSRX(2);
    private MecanumDrive mecDrive = new MecanumDrive(frontLeftTalon,
        rearLeftTalon, frontRightTalon, rearRightTalon);
    
    private boolean isFieldDrive = false;

    private Drivetrain() {
        SmartDashboard.putNumber("MOTOR_MAX_OUT",   SmartDashboard.getNumber("MOTOR_MAX_OUT", MOTOR_MAX_OUT));
        SmartDashboard.putNumber("MEC_Y_MAX_OUT",   SmartDashboard.getNumber("MEC_Y_MAX_OUT", MEC_Y_MAX_OUT));
    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new StickDrive());
    }

    @Override
    public void periodic() {
        SmartDashboard.putString("talon", String.format("%f:%f:%f:%f",
            rearLeftTalon.get(), frontLeftTalon.get(), rearRightTalon.get(),
            frontRightTalon.get()));
        SmartDashboard.putBoolean("isFieldDrive", isFieldDrive);
        SmartDashboard.putNumber("gyro", navx.getAngle());
        MOTOR_MAX_OUT   = SmartDashboard.getNumber("MOTOR_MAX_OUT", MOTOR_MAX_OUT);
        MEC_Y_MAX_OUT   = SmartDashboard.getNumber("MEC_Y_MAX_OUT", MEC_Y_MAX_OUT);
    }

    public void toggleFieldDrive() {
        isFieldDrive = !isFieldDrive;
    }

    public void resetFieldDrive() {
        navx.reset();
    }

    public void driveStop() {
        mecDrive.stopMotor();
    }

    public void driveXbox(XboxController xbox) {
        double yOut = clean(xbox.getRawAxis(OI.XBOX_AXIS_LEFT_X),  XBOX_MIN_IN);
        double xOut = clean(-xbox.getRawAxis(OI.XBOX_AXIS_LEFT_Y), XBOX_MIN_IN);
        double zOut = clean(xbox.getRawAxis(OI.XBOX_AXIS_RIGHT_X), XBOX_MIN_IN);
        //SmartDashboard.putString("xbox", String.format("%f:%f:%f", yOut, xOut, zOut));
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
        yOut = clean(logisticScale(yOut, MEC_Y_MAX_OUT), MOTOR_MIN_OUT);
        xOut = clean(logisticScale(xOut), MOTOR_MIN_OUT);
        zOut = clean(logisticScale(zOut), MOTOR_MIN_OUT);
        drive(yOut, xOut, zOut);
    }

    public void drive(double yOut, double xOut, double zOut) {
        double gAngle = (isFieldDrive ? navx.getAngle() : 0);
        //SmartDashboard.putString("drive", String.format("%f:%f:%f", yOut, xOut, zOut));
        mecDrive.driveCartesian(-yOut, -xOut, -zOut, gAngle);
    }

    private static double logisticScale(double x) {
        return logisticScale(x, MOTOR_MAX_OUT);
    }

    private static double logisticScale(double x, double maxOut) {
        double ax = Math.abs(x);
        double y = maxOut / (1 + Math.exp(K_LOG * (X_MID - ax)));
        return Math.copySign(y, x);
    }

    private static double limit(double x) {
        return Math.copySign(Math.min(Math.abs(x), MOTOR_MAX_OUT), x);
    }

    private static double clean(double x, double min) {
        return (Math.abs(x) < min ? 0 : x);
    }

    private static Drivetrain instance;
    public static synchronized Drivetrain getInstance() {
        if (instance == null) {
            instance = new Drivetrain();
        }
        return instance;
    }
}
