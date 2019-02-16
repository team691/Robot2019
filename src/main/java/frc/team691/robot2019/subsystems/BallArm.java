package frc.team691.robot2019.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team691.robot2019.commands.StickArm;

public class BallArm extends Subsystem {
    private static double MOTOR_LOWER_MAX_OUT = 1;
    private static double MOTOR_UPPER_MAX_OUT = 1;
    private static double MOTOR_HOLD_OUT = 0.2;

    private static BallArm instance;

    public static synchronized BallArm getInstance() {
        if (instance == null) {
            instance = new BallArm();
        }
        return instance;
    }

    private Spark lowerMotor = new Spark(1);
    private Spark upperMotor = new Spark(0);
    private Encoder lowerEnc = new Encoder(8, 9);
    private Encoder upperEnc = new Encoder(6, 7);

    private int encGoal = 0;

    private BallArm() {
        lowerMotor.setInverted(true);
        upperMotor.setInverted(true);
        SmartDashboard.putNumber("lowerMotorMax", SmartDashboard.getNumber("lowerMotorMax", 1));
        SmartDashboard.putNumber("upperMotorMax", SmartDashboard.getNumber("upperMotorMax", 1));
        SmartDashboard.putNumber("motorHoldOut", SmartDashboard.getNumber("motorHoldOut", 0.2));
    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new StickArm());
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("lowerMotor", lowerMotor.get());
        SmartDashboard.putNumber("upperMotor", upperMotor.get());
        SmartDashboard.putNumber("lowerEnc", lowerEnc.get());
        SmartDashboard.putNumber("upperEnc", upperEnc.get());
    }

    public void driveStop() {
        lowerMotor.stopMotor();
        upperMotor.stopMotor();
        MOTOR_LOWER_MAX_OUT = SmartDashboard.getNumber("lowerMotorMax", 1);
        MOTOR_UPPER_MAX_OUT = SmartDashboard.getNumber("upperMotorMax", 1);
        MOTOR_HOLD_OUT = SmartDashboard.getNumber("motorHoldOut", 0.2);
    }

    public void driveHold() {
        driveStop();
        int error = encGoal - lowerEnc.get();
        if (Math.abs(error) > 2) {
            lowerMotor.set(Math.copySign(MOTOR_HOLD_OUT, error));
        }
    }

    public void driveStick(Joystick stick) {
        lowerMotor.set(stick.getY() * MOTOR_LOWER_MAX_OUT);
        upperMotor.set(stick.getX() * MOTOR_UPPER_MAX_OUT);
        encGoal = lowerEnc.get();
    }
}
