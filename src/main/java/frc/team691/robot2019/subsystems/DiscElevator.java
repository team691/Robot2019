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
    private static double BOTTOM_MOTOR_OUT  = 1.0;
    private static double SIDE_MOTOR_OUT    = 1.0;
    private static final Value HAND_OPEN    = Value.kReverse;
    private static final Value HAND_SHUT    = Value.kForward;

    // TODO: correct switch ports
    private DigitalInput overSwitch     = new DigitalInput(0);
    private DigitalInput underSwitch    = new DigitalInput(5);
    private WPI_VictorSPX bottomMotor   = new WPI_VictorSPX(2);
    private WPI_VictorSPX sideMotor     = new WPI_VictorSPX(0);
    private DoubleSolenoid hand         = new DoubleSolenoid(0, 1);
    
    private DiscElevator() {
        bottomMotor.setInverted(true);
        SmartDashboard.putNumber("BOTTOM_MOTOR_OUT",
            SmartDashboard.getNumber("BOTTOM_MOTOR_OUT", BOTTOM_MOTOR_OUT));
        SmartDashboard.putNumber("SIDE_MOTOR_OUT",
            SmartDashboard.getNumber("SIDE_MOTOR_OUT", SIDE_MOTOR_OUT));
    }
    
    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new StickElevate());
    }

    @Override
    public void periodic() {
        SmartDashboard.putBoolean("overSwitch", overSwitch.get());
        SmartDashboard.putBoolean("underSwitch", underSwitch.get());
        SmartDashboard.putString("grabber", hand.get().toString());
        BOTTOM_MOTOR_OUT = SmartDashboard.getNumber(
            "BOTTOM_MOTOR_OUT", BOTTOM_MOTOR_OUT);
        SIDE_MOTOR_OUT   = SmartDashboard.getNumber(
            "SIDE_MOTOR_OUT", SIDE_MOTOR_OUT);
    }
    
    public void moveStop() {
        move(0, 0);
        hand.set(Value.kOff);
    }

    public void grab() {
        if (hand.get() == HAND_OPEN) {
            hand.set(HAND_SHUT);
        } else {
            hand.set(HAND_OPEN);
        }
    }
    
    public void move(double bottomOut, double sideOut) {
        bottomMotor.set(bottomOut);
        sideMotor.set(sideOut);
    }

    // Returns isTouched
    public boolean moveBottomAuto(int dir) {
        boolean up = dir > 0;
        boolean down = dir < 0;
        return !moveBottomFixed(up, down);
    }
    
    // ~Fixed methods return isMoving
    public boolean moveFixed(boolean bottomUp, boolean bottomDown,
        boolean sideUp, boolean sideDown) {
        return moveBottomFixed(bottomUp, bottomDown)
            && moveMotorFixed(sideMotor, SIDE_MOTOR_OUT,
            sideUp, sideDown);
    }

    public boolean moveBottomFixed(boolean up, boolean down) {
        return moveMotorFixed(bottomMotor, BOTTOM_MOTOR_OUT,
            up    && overSwitch.get(),
            down  && underSwitch.get());
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
        return mout != 0;
    }

    private static DiscElevator instance;
    public static synchronized DiscElevator getInstance() {
        if (instance == null) {
            instance = new DiscElevator();
        }
        return instance;
    }
}
