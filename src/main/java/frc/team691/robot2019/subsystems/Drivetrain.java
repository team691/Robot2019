package frc.team691.robot2019.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team691.robot2019.OI;
import frc.team691.robot2019.commands.StickDrive;

public class Drivetrain extends Subsystem {
    private static final double MOTOR_MIN_OUT = 0.05;
    private static final double K_LOG   = 10;
    private static final double X_MID   = 0.5;
    private static double MOTOR_MAX_OUT = 0.6;
    private static double MEC_Y_MAX_OUT = 0.8;

    private AHRS navx = new AHRS(SPI.Port.kMXP);
    private WPI_TalonSRX frontLeftTalon     = new WPI_TalonSRX(1);
    private WPI_TalonSRX rearLeftTalon      = new WPI_TalonSRX(0);
    private WPI_TalonSRX frontRightTalon    = new WPI_TalonSRX(3);
    private WPI_TalonSRX rearRightTalon     = new WPI_TalonSRX(2);
    private MecanumDrive mecDrive = new MecanumDrive(frontLeftTalon,
        rearLeftTalon, frontRightTalon, rearRightTalon);
    
    private boolean isFieldDrive = false;
    private int swapDir = 1;

    private Drivetrain() {
        SmartDashboard.putNumber("MOTOR_MAX_OUT",
            SmartDashboard.getNumber("MOTOR_MAX_OUT", MOTOR_MAX_OUT));
        SmartDashboard.putNumber("MEC_Y_MAX_OUT",
            SmartDashboard.getNumber("MEC_Y_MAX_OUT", MEC_Y_MAX_OUT));
    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new StickDrive());
    }

    @Override
    public void periodic() {
        SmartDashboard.putString("talon", String.format("%f:%f:%f:%f",
            rearLeftTalon.get(), frontLeftTalon.get(),
            rearRightTalon.get(), frontRightTalon.get()));
        SmartDashboard.putBoolean("isFD", isFieldDrive);
        SmartDashboard.putBoolean("isSwap", swapDir == -1);
        SmartDashboard.putNumber("gyro", navx.getAngle());
        MOTOR_MAX_OUT = SmartDashboard.getNumber("MOTOR_MAX_OUT",
            MOTOR_MAX_OUT);
        MEC_Y_MAX_OUT = SmartDashboard.getNumber("MEC_Y_MAX_OUT",
            MEC_Y_MAX_OUT);
    }

    public void toggleFieldDrive() {
        setFieldDrive(!isFieldDrive);
    }

    public void setFieldDrive(boolean on) {
        isFieldDrive = on;
        // TODO: correct swap behavior in field-drive
        swapDir = 1;
    }

    public void resetFieldDrive() {
        navx.reset();
    }

    public void swapFront() {
        swapDir = -swapDir;
    }

    public void driveStop() {
        mecDrive.stopMotor();
    }

    public void driveLogistic(double yPercent, double xPercent,
        double zPercent) {
        drive(
            swapDir * OI.clean(logisticScale(yPercent, MEC_Y_MAX_OUT),
                MOTOR_MIN_OUT),
            swapDir * OI.clean(logisticScale(xPercent), MOTOR_MIN_OUT),
            OI.clean(logisticScale(zPercent), MOTOR_MIN_OUT)
        );
    }

    public void drive(double yOut, double xOut, double zOut) {
        double gAngle = (isFieldDrive ? navx.getAngle() : 0);
        //SmartDashboard.putString("drive", String.format("%f:%f:%f", yOut, xOut, zOut));
        mecDrive.driveCartesian(yOut, -xOut, -zOut, gAngle);
    }

    private static double logisticScale(double x) {
        return logisticScale(x, MOTOR_MAX_OUT);
    }

    private static double logisticScale(double x, double maxOut) {
        double ax = Math.abs(x);
        double y = maxOut / (1 + Math.exp(K_LOG * (X_MID - ax)));
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
