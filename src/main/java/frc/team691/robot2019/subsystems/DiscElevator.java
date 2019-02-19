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

    // TODO: correct switch ports
    private DigitalInput overSwitch     = new DigitalInput(6);
    private DigitalInput underSwitch    = new DigitalInput(5);
    private WPI_VictorSPX bottomMotor   = new WPI_VictorSPX(2);
    private WPI_VictorSPX sideMotor     = new WPI_VictorSPX(0);
    private DoubleSolenoid grabber      = new DoubleSolenoid(0, 1);
    
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
        SmartDashboard.putString("grabber", grabber.get().toString());
        BOTTOM_MOTOR_OUT = SmartDashboard.getNumber(
            "BOTTOM_MOTOR_OUT", BOTTOM_MOTOR_OUT);
        SIDE_MOTOR_OUT   = SmartDashboard.getNumber(
            "SIDE_MOTOR_OUT", SIDE_MOTOR_OUT);
    }
    
    public void moveStop() {
        move(0, 0);
        grabber.set(Value.kOff);
    }

    public void grab() {
        if (grabber.get() == Value.kForward) {
            grabber.set(Value.kReverse);
        } else {
            grabber.set(Value.kForward);
        }
    }
    
    public void move(double bottomOut, double sideOut) {
        bottomMotor.set(bottomOut);
        sideMotor.set(sideOut);
    }

    public void moveFixed(boolean bottomUp, boolean bottomDown,
        boolean sideUp, boolean sideDown) {
        moveMotorFixed(bottomMotor, BOTTOM_MOTOR_OUT,
            bottomUp    && overSwitch.get(),
            bottomDown  && underSwitch.get());
        moveMotorFixed(sideMotor, SIDE_MOTOR_OUT,
            sideUp, sideDown);
    }

    private static void moveMotorFixed(SpeedController motor,
        double out, boolean up, boolean down) {
        double mout = 0;
        if (up) {
            mout = out;
        } else if (down) {
            mout = -out;
        }
        motor.set(mout);
    }

    private static DiscElevator instance;
    public static synchronized DiscElevator getInstance() {
        if (instance == null) {
            instance = new DiscElevator();
        }
        return instance;
    }
}
