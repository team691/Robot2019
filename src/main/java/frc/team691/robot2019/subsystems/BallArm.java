package frc.team691.robot2019.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team691.robot2019.commands.StickGrab;

public class BallArm extends Subsystem {
    private static double MOTOR_LOWER_MAX_OUT = 1.0;
    private static double MOTOR_UPPER_MAX_OUT = 0.5;
    private static double MOTOR_HOLD_OUT = 0.2;
    private static double MOTOR_ELEV_MAX_OUT = 0.6;

    // TODO: correct encoder, Victor ports
    private WPI_VictorSPX lowerMotor = new WPI_VictorSPX(1);
    private WPI_VictorSPX upperMotor = new WPI_VictorSPX(3);
    //private CANSparkMax elevMotor = new CANSparkMax(0, MotorType.kBrushless);
    private Encoder lowerEnc = new Encoder(1, 2);
    private Encoder upperEnc = new Encoder(3, 4);
    //private CANEncoder elevEnc = elevMotor.getEncoder();
    private DoubleSolenoid claw = new DoubleSolenoid(2, 3);

    private int encGoal = 0;

    private BallArm() {
        lowerMotor.setInverted(true);
        upperMotor.setInverted(true);
        SmartDashboard.putBoolean("calibrateArm", false);
        SmartDashboard.putNumber("MOTOR_LOWER_MAX_OUT",
            SmartDashboard.getNumber("MOTOR_LOWER_MAX_OUT", MOTOR_LOWER_MAX_OUT));
        SmartDashboard.putNumber("MOTOR_UPPER_MAX_OUT",
            SmartDashboard.getNumber("MOTOR_UPPER_MAX_OUT", MOTOR_UPPER_MAX_OUT));
        SmartDashboard.putNumber("MOTOR_HOLD_OUT",
            SmartDashboard.getNumber("MOTOR_HOLD_OUT", MOTOR_HOLD_OUT));
        SmartDashboard.putNumber("MOTOR_ELEV_MAX_OUT",
            SmartDashboard.getNumber("MOTOR_ELEV_MAX_OUT", MOTOR_ELEV_MAX_OUT));
    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new StickGrab());
    }

    @Override
    public void periodic() {
        if (SmartDashboard.getBoolean("calibrateArm", false)) {
            calibrate();
            SmartDashboard.putBoolean("calibrateArm", false);
        }
        SmartDashboard.putNumber("lowerMotor", lowerMotor.get());
        SmartDashboard.putNumber("upperMotor", upperMotor.get());
        SmartDashboard.putNumber("lowerEnc", lowerEnc.get());
        SmartDashboard.putNumber("upperEnc", upperEnc.get());
        MOTOR_LOWER_MAX_OUT = SmartDashboard.getNumber(
            "MOTOR_LOWER_MAX_OUT", MOTOR_LOWER_MAX_OUT);
        MOTOR_UPPER_MAX_OUT = SmartDashboard.getNumber(
            "MOTOR_UPPER_MAX_OUT", MOTOR_UPPER_MAX_OUT);
        MOTOR_HOLD_OUT = SmartDashboard.getNumber(
            "MOTOR_HOLD_OUT", MOTOR_HOLD_OUT);
        MOTOR_ELEV_MAX_OUT = SmartDashboard.getNumber(
            "MOTOR_ELEV_MAX_OUT", MOTOR_ELEV_MAX_OUT);
    }

    public void calibrate() {
        lowerEnc.reset();
        upperEnc.reset();
        //elevEnc.setPosition(0);
    }

    public void grab() {
        // TODO: determine initial position
        if (claw.get() == Value.kForward) {
            claw.set(Value.kReverse);
        } else {
            claw.set(Value.kForward);
        }
    }

    public void moveStop() {
        moveArm(0, 0);
        moveElev(0);
        claw.set(Value.kOff);
    }

    public void moveArmHold() {
        // TODO: use encoder tracking to hold both
        /*
        moveStop();
        int error = encGoal - lowerEnc.get();
        if (Math.abs(error) > 2) {
            lowerMotor.set(Math.copySign(MOTOR_HOLD_OUT, error));
        }
        */
        moveArm(MOTOR_HOLD_OUT, 0);
    }

    public void moveArmTrack(double lowerPercent, double upperPercent) {
        // TODO: limit movement with both encoders
        moveArm(
            lowerPercent * MOTOR_LOWER_MAX_OUT,
            upperPercent * MOTOR_UPPER_MAX_OUT
        );
        // TODO: track both encoders
        encGoal = lowerEnc.get();
    }

    public void moveElevPercent(double percent) {
        moveElev(percent * MOTOR_ELEV_MAX_OUT);
    }

    public void moveElev(double out) {
        //elevMotor.set(out);
    }

    public void moveArm(double lowerOut, double upperOut) {
        lowerMotor.set(lowerOut);
        upperMotor.set(upperOut);
    }

    private static BallArm instance;
    public static synchronized BallArm getInstance() {
        if (instance == null) {
            instance = new BallArm();
        }
        return instance;
    }
}
