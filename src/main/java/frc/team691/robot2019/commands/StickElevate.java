package frc.team691.robot2019.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team691.robot2019.OI;
import frc.team691.robot2019.subsystems.DiscElevator;

public class StickElevate extends Command {
    private static final int STICK_PORT = 1; // X3D
    private static final int BUTTON_EMODE       = 2;
    private static final int BUTTON_GRAB        = 1;
    private static final int BUTTON_BOTTOM_DOWN = 3;
    private static final int BUTTON_BOTTOM_UP   = 5;
    private static final int BUTTON_SIDE_DOWN   = 4;
    private static final int BUTTON_SIDE_UP     = 6;

    private OI oi               = OI.getInstance();
    private DiscElevator elev   = DiscElevator.getInstance();

    public StickElevate() {
        SmartDashboard.putBoolean("isElev",
            SmartDashboard.getBoolean("isElev", true));
        requires(elev);
    }

    @Override
    protected void initialize() {
        elev.grab();
    }

    @Override
    protected void execute() {
        Joystick stick = oi.getStick(STICK_PORT);
        //if (stick == null || !SmartDashboard.getBoolean("isElev", true)) {
        if (stick == null) {
            elev.moveStop();
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
        elev.moveStop();
    }

    @Override
    protected void interrupted() {
        end();
    }
}
