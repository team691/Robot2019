package frc.team691.robot2019.commands;

import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.command.Command;
import frc.team691.robot2019.OI;
import frc.team691.robot2019.subsystems.Drivetrain;

public class StickDrive extends Command {
    private static final int XBOX_PORT = 0;
    private static final int BUTTON_RESET_FD  = OI.XBOX_BUTTON_BACK;
    private static final int BUTTON_TOGGLE_FD = OI.XBOX_BUTTON_START;

    private OI oi           = OI.getInstance();
    private Drivetrain dt   = Drivetrain.getInstance();

    public StickDrive() {
        requires(dt);
    }

    @Override
    protected void initialize() {
        // TODO: Set field drive?
        if (RobotState.isAutonomous()) {
            //dt.setFieldDrive(false);
            dt.resetFieldDrive();
        } else {
            //dt.setFieldDrive(true);
        }
    }

    @Override
    protected void execute() {
        XboxController xbox = oi.getXbox(XBOX_PORT);
        if (xbox == null) {
            dt.driveStop();
            return;
        }
        dt.driveLogistic(
            OI.cleanXbox(xbox.getRawAxis(OI.XBOX_AXIS_LEFT_X)),
            OI.cleanXbox(-xbox.getRawAxis(OI.XBOX_AXIS_LEFT_Y)),
            OI.cleanXbox(xbox.getRawAxis(OI.XBOX_AXIS_RIGHT_X))
        );
        if (xbox.getRawButtonPressed(BUTTON_RESET_FD)) {
            dt.resetFieldDrive();
        }
        if (xbox.getRawButtonPressed(BUTTON_TOGGLE_FD)) {
            dt.toggleFieldDrive();
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        dt.driveStop();
    }

    @Override
    protected void interrupted() {
        end();
    }
}
