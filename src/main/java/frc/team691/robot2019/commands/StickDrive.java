package frc.team691.robot2019.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team691.robot2019.OI;
import frc.team691.robot2019.subsystems.Drivetrain;

public class StickDrive extends Command {
    private static final int STICK_PORT = 0;

    private static StickDrive instance;

    public static synchronized StickDrive getInstance() {
        if (instance == null) {
            instance = new StickDrive();
        }
        return instance;
    }

    OI oi;
    Drivetrain dt;

    private StickDrive() {
        this.oi = OI.getInstance();
        this.dt = Drivetrain.getInstance();
        requires(dt);
    }
    
    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
    }
    
    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        if (oi.getNumSticks() > STICK_PORT) {
            dt.driveStick(oi.getStick(STICK_PORT));
        } else {
            dt.driveStop();
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
