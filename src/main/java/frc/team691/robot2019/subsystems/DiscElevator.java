package frc.team691.robot2019.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team691.robot2019.commands.StickElevate;

public class DiscElevator extends Subsystem {
    private static double BOTTOM_MOTOR_MAX_OUT  = 0.8;
    private static double SIDE_MOTOR_MAX_OUT    = 0.8;

    private WPI_VictorSPX bottomMotor   = new WPI_VictorSPX(0);
    private WPI_VictorSPX sideMotor     = new WPI_VictorSPX(1);
    private DoubleSolenoid grabber  = new DoubleSolenoid(0, 1);
    
    private DiscElevator() {
        SmartDashboard.putNumber("BOTTOM_MOTOR_MAX_OUT",
            SmartDashboard.getNumber("BOTTOM_MOTOR_MAX_OUT", BOTTOM_MOTOR_MAX_OUT));
        SmartDashboard.putNumber("SIDE_MOTOR_MAX_OUT",
            SmartDashboard.getNumber("SIDE_MOTOR_MAX_OUT", SIDE_MOTOR_MAX_OUT));
    }
    
    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new StickElevate());
    }

    @Override
    public void periodic() {
        BOTTOM_MOTOR_MAX_OUT = SmartDashboard.getNumber(
            "BOTTOM_MOTOR_MAX_OUT", BOTTOM_MOTOR_MAX_OUT);
        SIDE_MOTOR_MAX_OUT   = SmartDashboard.getNumber(
            "SIDE_MOTOR_MAX_OUT", SIDE_MOTOR_MAX_OUT);
    }
    
    public void driveStop() {
        bottomMotor.stopMotor();
        sideMotor.stopMotor();
        grabber.set(DoubleSolenoid.Value.kOff);
    }

    private static DiscElevator instance;
    public static synchronized DiscElevator getInstance() {
        if (instance == null) {
            instance = new DiscElevator();
        }
        return instance;
    }
}
