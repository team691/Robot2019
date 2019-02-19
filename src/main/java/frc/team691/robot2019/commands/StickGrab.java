package frc.team691.robot2019.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import frc.team691.robot2019.OI;
import frc.team691.robot2019.subsystems.BallArm;

public class StickGrab extends Command {
    private static final int STICK_PORT = 1;

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
        if (stick.getRawButton(8)) {
            arm.moveHold();
        } else {
            arm.moveTrack(
                OI.cleanStick(stick.getY()),
                OI.cleanStick(stick.getX())
            );
        }
        if (stick.getRawButtonPressed(9)) {
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
