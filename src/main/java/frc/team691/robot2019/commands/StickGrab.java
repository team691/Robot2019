package frc.team691.robot2019.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import frc.team691.robot2019.OI;
import frc.team691.robot2019.subsystems.BallArm;

public class StickGrab extends Command {
    private static final int STICK_PORT = 1;
    private static final int BUTTON_ARM_HOLD = 8;
    private static final int BUTTON_CLAW_GRAB = 9;

    private OI oi       = OI.getInstance();
    private BallArm arm = BallArm.getInstance();

    public StickGrab() {
        requires(arm);
    }

    @Override
    protected void initialize() {
        // TODO: initial claw position
    }

    @Override
    protected void execute() {
        Joystick stick = oi.getStick(STICK_PORT);
        if (stick == null) {
            arm.moveStop();
            return;
        }
        // TODO: control arm movement
        double sy = OI.cleanStick(stick.getY());
        double sx = OI.cleanStick(stick.getX());
        if (sx + sy == 0) {
            arm.moveHold();
        } else {
            arm.moveTrack(sy, sx);
        }
        /*
        if (stick.getRawButton(BUTTON_ARM_HOLD)) {
            arm.moveHold();
        } else {
            arm.moveTrack(
                OI.cleanStick(stick.getY()),
                OI.cleanStick(stick.getX())
            );
        }
        */
        if (stick.getRawButtonPressed(BUTTON_CLAW_GRAB)) {
            arm.grab();
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        arm.moveStop();
    }

    @Override
    protected void interrupted() {
        end();
    }
}
