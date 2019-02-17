package frc.team691.robot2019.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team691.robot2019.OI;
import frc.team691.robot2019.subsystems.BallArm;

public class StickGrab extends Command {
    private OI oi       = OI.getInstance();
    private BallArm arm = BallArm.getInstance();

    public StickGrab() {
        requires(arm);
    }
    
    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
    }
    
    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
    }
    
    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }
    
    // Called once after isFinished returns true
    @Override
    protected void end() {
        arm.driveStop();
    }
    
    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }
}
