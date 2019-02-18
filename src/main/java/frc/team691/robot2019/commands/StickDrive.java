package frc.team691.robot2019.commands;

import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team691.robot2019.OI;
import frc.team691.robot2019.subsystems.Drivetrain;

public class StickDrive extends Command {
    private static final int XBOX_PORT = 0;
    private static final int BUTTON_RESET       = OI.XBOX_BUTTON_BACK;
    private static final int BUTTON_FIELD_DRIVE = OI.XBOX_BUTTON_START;

    private OI oi           = OI.getInstance();
    private Drivetrain dt   = Drivetrain.getInstance();

    public StickDrive() {
        requires(dt);
    }
    
    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        // TODO: Set field drive
        if (RobotState.isAutonomous()) {
            dt.resetFieldDrive();
        }
    }
    
    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        XboxController xbox = oi.getXbox(XBOX_PORT);
        if (xbox == null) {
            dt.driveStop();
            return;
        }
        dt.driveXbox(xbox);
        //dt.driveStop();
        if (xbox.getRawButtonPressed(BUTTON_RESET)) {
            dt.resetFieldDrive();
        }
        if (xbox.getRawButtonPressed(BUTTON_FIELD_DRIVE)) {
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
        dt.driveStop();
    }
    
    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }
}
