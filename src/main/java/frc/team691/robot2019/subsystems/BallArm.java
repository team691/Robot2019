package frc.team691.robot2019.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team691.robot2019.commands.StickArm;

public class BallArm extends Subsystem {
    private static final double MOTOR_HOLD_OUT = 0.2;

    private static BallArm instance;

    public static synchronized BallArm getInstance() {
        if (instance == null) {
            instance = new BallArm();
        }
        return instance;
    }

    private Spark lowerMotor = new Spark(0);
    private Spark upperMotor = new Spark(1);
    private Encoder lowerEnc = new Encoder(8, 9);
    private Encoder upperEnc;

    private int encGoal = 0;

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new StickArm());
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("lowerMotor", lowerMotor.get());
        SmartDashboard.putNumber("upperMotor", upperMotor.get());
        SmartDashboard.putNumber("lowerEnc", lowerEnc.get());
    }

    public void driveStop() {
        lowerMotor.stopMotor();
        upperMotor.stopMotor();
    }

    public void driveHold() {
        driveStop();
        int error = encGoal - lowerEnc.get();
        if (Math.abs(error) > 4) {
            lowerMotor.set(Math.copySign(MOTOR_HOLD_OUT, error));
        }
    }

    public void driveStick(Joystick stick) {
        lowerMotor.set(stick.getY());
        upperMotor.set(0);
        encGoal = lowerEnc.get();
    }
}
