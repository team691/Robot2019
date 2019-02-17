package frc.team691.robot2019.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.command.Command;
import frc.team691.robot2019.OI;
import frc.team691.robot2019.subsystems.Drivetrain;

public class StickDrive extends Command {
    private static final int STICK_PORT = 0;
    private static final int STICK_TYPE = OI.STICK_TYPE_X3D;
    private static final int BUTTON_RESET_GYRO  = 11;
    private static final int BUTTON_FIELD_DRIVE = 12;

    private OI oi           = OI.getInstance();
    private Drivetrain dt   = Drivetrain.getInstance();

    public StickDrive() {
        //System.out.println("sdrive construct");
        requires(dt);
    }
    
    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        // TODO: when reset gyro?
        // dt.resetGyro();
        System.out.format("sdrive init: %b\n", RobotState.isAutonomous());
    }
    
    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        Joystick stick = oi.getStick(STICK_PORT, STICK_TYPE);
        if (stick == null) {
            dt.driveStop();
            return;
        }
        dt.driveStick(stick);
        if (stick.getRawButtonPressed(BUTTON_RESET_GYRO)) {
            dt.resetGyro();
        }
        if (stick.getRawButtonPressed(BUTTON_FIELD_DRIVE)) {
            dt.toggleFieldDrive();
        }
    }
    
    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }
    
    // Called once after isFinished returns true
    @Override
    protected void end() {
    }
    
    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
    }
}
