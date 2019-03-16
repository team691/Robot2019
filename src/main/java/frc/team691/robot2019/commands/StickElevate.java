package frc.team691.robot2019.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import frc.team691.robot2019.OI;
import frc.team691.robot2019.subsystems.DiscElevator;

public class StickElevate extends Command {
    // X3D stick
    private static final int STICK_PORT = 1;
    private static final int BUTTON_GRAB        = 1;
    private static final int BUTTON_BOTTOM_DOWN = 3;
    private static final int BUTTON_BOTTOM_UP   = 5;
    private static final int BUTTON_SIDE_DOWN   = 4;
    private static final int BUTTON_SIDE_UP     = 6;

    private OI oi               = OI.getInstance();
    private DiscElevator elev   = DiscElevator.getInstance();

    public StickElevate() {
        requires(elev);
    }

    @Override
    protected void initialize() {
        elev.grab();
    }

    @Override
    protected void execute() {
        Joystick stick = oi.getStick(STICK_PORT);
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
