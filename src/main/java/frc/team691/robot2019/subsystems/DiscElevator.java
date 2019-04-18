package frc.team691.robot2019.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team691.robot2019.commands.StickElevate;

public class DiscElevator extends Subsystem {
    private static final double BOTTOM_MOTOR_OUT  = 1.0;
    private static final double SIDE_MOTOR_OUT    = 1.0;
    private static final double RELEASE_MOTOR_OUT = 0.5;
    public static final int SIDE_STOP_LOOPS       = 2;
    public static final int BOTTOM_STOP_LOOPS     = 2;
    public static final Value HAND_OPEN    = Value.kReverse;
    public static final Value HAND_SHUT    = Value.kForward;

    private DigitalInput overSwitch     = new DigitalInput(0);
    private DigitalInput underSwitch    = new DigitalInput(1);
    private WPI_VictorSPX sideMotor     = new WPI_VictorSPX(1);
    private WPI_VictorSPX bottomMotor   = new WPI_VictorSPX(2);
    private WPI_VictorSPX releaseMotor  = new WPI_VictorSPX(3);
    private DoubleSolenoid hand         = new DoubleSolenoid(0, 1);
    
    private DiscElevator() {
        bottomMotor.setInverted(true);
        releaseMotor.setInverted(true);
    }
    
    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new StickElevate());
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("bottomMotor", bottomMotor.get());
        SmartDashboard.putNumber("sideMotor", sideMotor.get());
        SmartDashboard.putBoolean("overSwitch", overSwitch.get());
        SmartDashboard.putBoolean("underSwitch", underSwitch.get());
        SmartDashboard.putString("grabber", hand.get().toString());
    }

    public boolean getUnderSwitch() {
        return underSwitch.get();
    }

    public boolean getOverSwitch() {
        return overSwitch.get();
    }
    
    public void moveStop() {
        move(0, 0);
        moveReleaseDir(0);
        hand.set(Value.kOff);
    }

    public void grab() {
        if (hand.get() == HAND_OPEN) {
            setHand(HAND_SHUT);
        } else {
            setHand(HAND_OPEN);
        }
    }

    public void setHand(Value v) {
        hand.set(v);
    }
    
    public void move(double bottomOut, double sideOut) {
        bottomMotor.set(bottomOut);
        sideMotor.set(sideOut);
    }

    public void moveReleaseDir(int dir) {
        releaseMotor.set(dir * RELEASE_MOTOR_OUT);
    }

    // Returns isTouched
    public boolean moveBottomAuto(int dir) {
        return !moveBottomFixed(dir > 0, dir < 0, false);
    }
    
    // ~Fixed methods return isMoving
    public boolean moveFixed(boolean bottomUp, boolean bottomDown,
        boolean sideUp, boolean sideDown, boolean normalize) {
        boolean sideMoving = moveMotorFixed(sideMotor, SIDE_MOTOR_OUT,
            sideUp, sideDown);
        return moveBottomFixed(bottomUp, bottomDown, normalize) && sideMoving;
    }

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
        return moveMotorFixed(bottomMotor, BOTTOM_MOTOR_OUT, u, d);
    }

    private static boolean moveMotorFixed(SpeedController motor,
        double out, boolean up, boolean down) {
        double mout = 0;
        if (up) {
            mout = out;
        } else if (down) {
            mout = -out;
        }
        motor.set(mout);
        return up || down;
    }

    private static DiscElevator instance;
    public static synchronized DiscElevator getInstance() {
        if (instance == null) {
            instance = new DiscElevator();
        }
        return instance;
    }
}
