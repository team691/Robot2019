package frc.team691.robot2019.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team691.robot2019.commands.StickBelev;

public class Belevator extends Subsystem {
    public static final int BOTTOM_STOP_LOOPS     = 2;
    private static final double BOTTOM_MOTOR_OUT  = 1.0;

    private WPI_VictorSPX bottomMotor   = new WPI_VictorSPX(2);
    private DigitalInput overSwitch     = new DigitalInput(0);
    private DigitalInput underSwitch    = new DigitalInput(1);

    public Belevator() {
        bottomMotor.setInverted(true);
    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new StickBelev());
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("bottomMotor", bottomMotor.get());
        SmartDashboard.putBoolean("overSwitch", overSwitch.get());
        SmartDashboard.putBoolean("underSwitch", underSwitch.get());
    }

    public boolean getUnderSwitch() {
        return underSwitch.get();
    }

    public boolean getOverSwitch() {
        return overSwitch.get();
    }

    public void move(double bottomOut) {
        bottomMotor.set(bottomOut);
    }

    public void moveStop() {
        move(0);
    }

    // Returns isTouched
    public boolean moveBottomAuto(int dir) {
        return !moveBottomFixed(dir > 0, dir < 0, false);
    }

    // ~Fixed methods return isMoving
    public boolean moveBottomFixed(boolean up, boolean down,
        boolean normalize) {
        boolean os = overSwitch.get();
        boolean us = underSwitch.get();
        boolean u = up    && os;
        boolean d = down  && us;
        if (normalize) {
            u = !us || u;
            d = !os || d;
        }
        return Melevator.moveMotorFixed(
            bottomMotor, BOTTOM_MOTOR_OUT, u, d);
    }

    private static Belevator instance;
    public static synchronized Belevator getInstance() {
        if (instance == null) {
            instance = new Belevator();
        }
        return instance;
    }
}
