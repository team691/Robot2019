package frc.team691.robot2019.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import frc.team691.robot2019.OI;
import frc.team691.robot2019.subsystems.DiscElevator;

public class StickElevate extends Command {
    private static final int STICK_PORT = 1;

    private OI oi               = OI.getInstance();
    private DiscElevator elev   = DiscElevator.getInstance();

    public StickElevate() {
        requires(elev);
    }
    
    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
    }
    
    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        Joystick stick = oi.getStick(STICK_PORT);
        if (stick == null) {
            elev.driveStop();
            return;
        }
        // TODO: drive elev
    }
    
    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }
    
    // Called once after isFinished returns true
    @Override
    protected void end() {
        elev.driveStop();
    }
    
    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }
}
