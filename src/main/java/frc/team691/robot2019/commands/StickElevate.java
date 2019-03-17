package frc.team691.robot2019.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team691.robot2019.OI;
import frc.team691.robot2019.subsystems.DiscElevator;

public class StickElevate extends Command {
    private static final int STICK_PORT = 1; // X3D
    private static final int BUTTON_EMODE       = 10;
    private static final int BUTTON_RELEASE_FW  = 9;
    private static final int BUTTON_RELEASE_BW  = 11;
    private static final int BUTTON_GRAB        = 1;
    private static final int BUTTON_BOTTOM_DOWN = 3;
    private static final int BUTTON_BOTTOM_UP   = 5;
    private static final int BUTTON_SIDE_DOWN   = 4;
    private static final int BUTTON_SIDE_UP     = 6;
    private static final int POV_AUTO_UP        = 0;
    private static final int POV_AUTO_DOWN      = 180;

    private OI oi               = OI.getInstance();
    private DiscElevator elev   = DiscElevator.getInstance();
    private AutoElevate aeCommand = new AutoElevate();

    private int rd = 100;
    //private boolean povPressed = false;

    public StickElevate() {
        SmartDashboard.putData(aeCommand);
        SmartDashboard.putNumber("aeDir", 1);
        SmartDashboard.putBoolean("isElev",
            SmartDashboard.getBoolean("isElev", true));
        requires(elev);
    }

    @Override
    protected void initialize() {
        if (RobotState.isAutonomous()) {
            rd = 0;
        }
        elev.setHand(DiscElevator.HAND_OPEN);
    }

    @Override
    protected void execute() {
        elev.moveReleaseDir(rd++ < 25 ? -1 : 0);

        Joystick stick = oi.getStick(STICK_PORT);
        //if (stick == null || !SmartDashboard.getBoolean("isElev", true)) {
        if (stick == null) {
            elev.moveStop();
            return;
        }

        /*
        int pov = stick.getPOV(0);
        boolean povp = (pov == POV_AUTO_UP || pov == POV_AUTO_DOWN);
        if (!povp && povPressed) {
            povPressed = false;
            aeCommand.start(pov == POV_AUTO_UP ? 1 : -1);
            return;
        }
        povPressed = povp;
        */
        if (stick.getRawButtonReleased(2)) {
            aeCommand.start((int) SmartDashboard.getNumber("aeDir", 1));
            return;
        }

        elev.moveFixed(
            stick.getRawButton(BUTTON_BOTTOM_UP),
            stick.getRawButton(BUTTON_BOTTOM_DOWN),
            stick.getRawButton(BUTTON_SIDE_UP),
            stick.getRawButton(BUTTON_SIDE_DOWN)
        );
        if (stick.getRawButtonPressed(BUTTON_GRAB)) {
            elev.grab();
        }

        if (stick.getRawButton(BUTTON_RELEASE_FW)) {
            elev.moveReleaseDir(1);
        } else if (stick.getRawButton(BUTTON_RELEASE_BW)) {
            elev.moveReleaseDir(-1);
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
        System.out.println("se end");
        elev.moveStop();
        elev.setHand(DiscElevator.HAND_SHUT);
    }

    @Override
    protected void interrupted() {
        System.out.println("se interrupted");
        end();
    }
}
