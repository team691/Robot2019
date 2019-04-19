package frc.team691.robot2019.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team691.robot2019.OI;
import frc.team691.robot2019.subsystems.Melevator;

public class StickMelev extends Command {
    private static final int STICK_PORT = 1; // X3D
    private static final int BUTTON_EMODE       = 10;
    private static final int BUTTON_RELEASE_FW  = 9;
    private static final int BUTTON_RELEASE_BW  = 11;
    private static final int BUTTON_GRAB        = 1;
    private static final int BUTTON_SIDE_DOWN   = 4;
    private static final int BUTTON_SIDE_UP     = 6;
    private static final int BUTTON_RESET_GRAB  = 12;
    private static final double RELEASE_TIME_SEC = 1.2;

    private OI oi               = OI.getInstance();
    private Melevator elev   = Melevator.getInstance();
    private ResetElevate resetCommand =
        new ResetElevate(RELEASE_TIME_SEC + 0.12);

    private boolean needAutoInit = true;
    private int rd, sdd;

    public StickMelev() {
        SmartDashboard.putBoolean("isElev",
            SmartDashboard.getBoolean("isElev", true));
        requires(elev);
    }

    @Override
    protected void initialize() {
        System.out.println("sm initialize");
        if (RobotState.isAutonomous() && needAutoInit) {
            rd = (int) (RELEASE_TIME_SEC / 0.02);
            elev.setHand(Melevator.HAND_OPEN);
        }
        needAutoInit = RobotState.isOperatorControl();
    }

    @Override
    protected void execute() {
        elev.moveReleaseDir(rd > 0 ? -1 : 0);
        if (rd > 0) rd--;

        Joystick stick = oi.getStick(STICK_PORT);
        //if (stick == null || !SmartDashboard.getBoolean("isElev", true)) {
        if (stick == null) {
            elev.move(0);
            return;
        }

        elev.moveFixed(
            stick.getRawButton(BUTTON_SIDE_UP) || sdd > 0,
            stick.getRawButton(BUTTON_SIDE_DOWN)
        );
        if (sdd > 0) sdd--;
        if (stick.getRawButtonReleased(BUTTON_SIDE_DOWN)) {
            sdd = Melevator.SIDE_STOP_LOOPS;
        }

        if (stick.getRawButtonPressed(BUTTON_GRAB)) {
            elev.grab();
        }

        if (stick.getRawButton(BUTTON_RELEASE_FW)) {
            elev.moveReleaseDir(1);
        } else if (stick.getRawButton(BUTTON_RELEASE_BW)) {
            elev.moveReleaseDir(-1);
        }

        if (stick.getRawButtonReleased(BUTTON_RESET_GRAB)) {
            resetCommand.start();
        }

        if (stick.getRawButtonPressed(BUTTON_EMODE)) {
            SmartDashboard.putBoolean("isElev",
                !SmartDashboard.getBoolean("isElev", false));
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        System.out.println("sm end");
        elev.moveStop();
    }

    @Override
    protected void interrupted() {
        System.out.println("sm interrupted");
        end();
    }
}
