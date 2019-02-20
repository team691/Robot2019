package frc.team691.robot2019.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team691.robot2019.commands.StickGrab;

public class BallArm extends Subsystem {
    private static double MOTOR_LOWER_MAX_OUT = 1.0;
    private static double MOTOR_UPPER_MAX_OUT = 1.0;
    private static double MOTOR_HOLD_OUT = 0.2;

    // TODO: correct encoder ports
    private Encoder lowerEnc = new Encoder(1, 2);
    private Encoder upperEnc = new Encoder(3, 4);
    private WPI_VictorSPX lowerMotor = new WPI_VictorSPX(1);
    private WPI_VictorSPX upperMotor = new WPI_VictorSPX(3);
    private DoubleSolenoid claw = new DoubleSolenoid(2, 3);

    private int encGoal = 0;

    private BallArm() {
        lowerMotor.setInverted(true);
        upperMotor.setInverted(true);
        SmartDashboard.putNumber("lowerMotorMax",
            SmartDashboard.getNumber("lowerMotorMax", MOTOR_LOWER_MAX_OUT));
        SmartDashboard.putNumber("upperMotorMax",
            SmartDashboard.getNumber("upperMotorMax", MOTOR_UPPER_MAX_OUT));
        SmartDashboard.putNumber("motorHoldOut",
            SmartDashboard.getNumber("motorHoldOut", MOTOR_HOLD_OUT));
    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new StickGrab());
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("lowerMotor", lowerMotor.get());
        SmartDashboard.putNumber("upperMotor", upperMotor.get());
        SmartDashboard.putNumber("lowerEnc", lowerEnc.get());
        SmartDashboard.putNumber("upperEnc", upperEnc.get());
        MOTOR_LOWER_MAX_OUT = SmartDashboard.getNumber(
            "lowerMotorMax", MOTOR_LOWER_MAX_OUT);
        MOTOR_UPPER_MAX_OUT = SmartDashboard.getNumber(
            "upperMotorMax", MOTOR_UPPER_MAX_OUT);
        MOTOR_HOLD_OUT = SmartDashboard.getNumber(
            "motorHoldOut", MOTOR_HOLD_OUT);
    }

    public void grab() {
        if (claw.get() == Value.kForward) {
            claw.set(Value.kReverse);
        } else {
            claw.set(Value.kForward);
        }
    }

    public void moveStop() {
        move(0, 0);
        claw.set(Value.kOff);
    }

    public void moveHold() {
        // TODO: use encoder tracking to hold both
        /*
        moveStop();
        int error = encGoal - lowerEnc.get();
        if (Math.abs(error) > 2) {
            lowerMotor.set(Math.copySign(MOTOR_HOLD_OUT, error));
        }
        */
        move(MOTOR_HOLD_OUT, 0);
    }

    public void moveTrack(double lowerPercent, double upperPercent) {
        // TODO: limit movement with both encoders
        move(
            lowerPercent * MOTOR_LOWER_MAX_OUT,
            upperPercent * MOTOR_UPPER_MAX_OUT
        );
        // TODO: track both encoders
        encGoal = lowerEnc.get();
    }

    public void move(double lowerOut, double upperOut) {
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
