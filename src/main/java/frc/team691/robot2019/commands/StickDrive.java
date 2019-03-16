package frc.team691.robot2019.commands;

import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team691.robot2019.OI;
import frc.team691.robot2019.subsystems.Drivetrain;

public class StickDrive extends Command {
    private static final int XBOX_PORT = 0;
    private static final int BUTTON_RESET_FD  = OI.XBOX_BUTTON_BACK;
    private static final int BUTTON_TOGGLE_FD = OI.XBOX_BUTTON_START;
    private static final int BUTTON_SWAP = OI.XBOX_BUTTON_Y;
    private static final int BUTTON_FPS_MODE = OI.XBOX_BUTTON_B;

    private OI oi           = OI.getInstance();
    private Drivetrain dt   = Drivetrain.getInstance();

    private boolean isFpsMode = true;

    public StickDrive() {
        SmartDashboard.putBoolean("fpsMode",
            SmartDashboard.getBoolean("fpsMode", isFpsMode));
        SmartDashboard.putBoolean("switchFpsMode", false);
        requires(dt);
    }

    @Override
    protected void initialize() {
        isFpsMode = SmartDashboard.getBoolean("fpsMode", isFpsMode);
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
        double y, x, z;
        if (isFpsMode) {
            y = xbox.getRawAxis(OI.XBOX_AXIS_LEFT_X);
            z = xbox.getRawAxis(OI.XBOX_AXIS_RIGHT_X);
        } else {
            y = xbox.getRawAxis(OI.XBOX_AXIS_RIGHT_X);
            z = xbox.getRawAxis(OI.XBOX_AXIS_LEFT_X);
        }
        x = -xbox.getRawAxis(OI.XBOX_AXIS_LEFT_Y);
        dt.driveLogistic(
            OI.cleanXbox(y),
            OI.cleanXbox(x),
            OI.cleanXbox(z)
        );

        if (SmartDashboard.getBoolean("switchFpsMode", false)) {
            toggleFpsMode();
            SmartDashboard.putBoolean("switchFpsMode", false);
        }
        if (xbox.getRawButtonPressed(BUTTON_FPS_MODE)) {
            toggleFpsMode();
        }
        
        if (xbox.getRawButtonPressed(BUTTON_RESET_FD)) {
            dt.resetFieldDrive();
        }
        if (xbox.getRawButtonPressed(BUTTON_TOGGLE_FD)) {
            dt.toggleFieldDrive();
        }
        if (xbox.getRawButtonPressed(BUTTON_SWAP)) {
            dt.swapFront();
        }
    }

    private void toggleFpsMode() {
        isFpsMode = !isFpsMode;
        SmartDashboard.putBoolean("fpsMode", isFpsMode);
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
