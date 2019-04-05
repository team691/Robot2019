package frc.team691.robot2019.commands;

import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team691.robot2019.OI;
import frc.team691.robot2019.OI.DualActionXbox;
import frc.team691.robot2019.subsystems.Drivetrain;

public class StickDrive extends Command {
    private static final int XBOX_PORT = 0;
    private static final int BUTTON_RESET_FD    = DualActionXbox.BUTTON_LEFT_SP;
    private static final int BUTTON_TOGGLE_FD   = DualActionXbox.BUTTON_RIGHT_SP;
    private static final int BUTTON_SWAP        = DualActionXbox.BUTTON_UP;
    private static final int BUTTON_FPS_MODE    = DualActionXbox.BUTTON_DOWN;

    private OI oi           = OI.getInstance();
    private Drivetrain dt   = Drivetrain.getInstance();

    private boolean isFpsMode;

    public StickDrive() {
        isFpsMode = SmartDashboard.getBoolean("isFPS", true);
        SmartDashboard.putBoolean("isFPS", isFpsMode);
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
        DualActionXbox xbox = oi.getXbox(XBOX_PORT);
        if (xbox == null) {
            dt.driveStop();
            return;
        }

        double y, x, z;
        if (isFpsMode) {
            y = xbox.getAxisClean(DualActionXbox.AXIS_LEFT_X);
            z = xbox.getAxisClean(DualActionXbox.AXIS_RIGHT_X);
        } else {
            y = xbox.getAxisClean(DualActionXbox.AXIS_RIGHT_X);
            z = xbox.getAxisClean(DualActionXbox.AXIS_LEFT_X);
        }
        x = -xbox.getAxisClean(DualActionXbox.AXIS_LEFT_Y);

        //TODO: Turbo/slow mode
        dt.driveLogistic(y, x, z);

        if (xbox.getButtonPressed(BUTTON_FPS_MODE)) {
            toggleFpsMode();
        }
        if (xbox.getButtonPressed(BUTTON_SWAP)) {
            dt.swapFront();
        }

        if (xbox.getButtonPressed(BUTTON_RESET_FD)) {
            dt.resetFieldDrive();
        }
        if (xbox.getButtonPressed(BUTTON_TOGGLE_FD)) {
            dt.toggleFieldDrive();
        }
    }

    private void toggleFpsMode() {
        isFpsMode = !isFpsMode;
        SmartDashboard.putBoolean("isFPS", isFpsMode);
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
