package frc.team691.robot2019.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team691.robot2019.commands.StickMelev;

public class Melevator extends Subsystem {
    public static final Value HAND_OPEN    = Value.kReverse;
    public static final Value HAND_SHUT    = Value.kForward;
    public static final int SIDE_STOP_LOOPS       = 2;
    private static final double SIDE_MOTOR_OUT    = 1.0;
    private static final double RELEASE_MOTOR_OUT = 0.5;

    private WPI_VictorSPX sideMotor     = new WPI_VictorSPX(1);
    private WPI_VictorSPX releaseMotor  = new WPI_VictorSPX(3);
    private DoubleSolenoid hand         = new DoubleSolenoid(0, 1);
    
    private Melevator() {
        releaseMotor.setInverted(true);
    }
    
    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new StickMelev());
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("sideMotor", sideMotor.get());
        SmartDashboard.putString("grabber", hand.get().toString());
    }

    public void moveStop() {
        move(0);
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
    
    public void move(double sideOut) {
        sideMotor.set(sideOut);
    }

    public void moveReleaseDir(int dir) {
        releaseMotor.set(dir * RELEASE_MOTOR_OUT);
    }

    // ~Fixed methods return isMoving
    public boolean moveFixed(boolean sideUp, boolean sideDown) {
        return moveMotorFixed(sideMotor, SIDE_MOTOR_OUT, sideUp, sideDown);
    }

    static boolean moveMotorFixed(SpeedController motor,
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

    private static Melevator instance;
    public static synchronized Melevator getInstance() {
        if (instance == null) {
            instance = new Melevator();
        }
        return instance;
    }
}
