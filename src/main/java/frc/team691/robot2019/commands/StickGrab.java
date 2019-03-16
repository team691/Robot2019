package frc.team691.robot2019.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team691.robot2019.OI;
import frc.team691.robot2019.subsystems.BallArm;

public class StickGrab extends Command {
    private static final int STICK_PORT = 1; // X3D
    //private static final int BUTTON_ARM_HOLD  = 8;
    private static final int BUTTON_GRAB        = 11;
    private static final int BUTTON_CALIBRATE   = 7;
    private static final int POV_UPPER_UP   = 0;
    private static final int POV_UPPER_DOWN = 180;
    private static final int POV_ELEV_UP    = 90;
    private static final int POV_ELEV_DOWN  = 270;

    private OI oi       = OI.getInstance();
    private BallArm arm = BallArm.getInstance();

    public StickGrab() {
        requires(arm);
    }

    @Override
    protected void initialize() {
        //arm.grab();
    }

    @Override
    protected void execute() {
        Joystick stick = oi.getStick(STICK_PORT);
        //if (stick == null || SmartDashboard.getBoolean("isElev", false)) {
        if (stick == null) {
            arm.moveStop();
            return;
        }
        // TODO: control arm movement
        int pov = stick.getPOV(0);
        SmartDashboard.putNumber("pov", pov);
        /*
        double lowerp = OI.cleanStick(stick.getY());
        //double upperp = OI.cleanStick(stick.getX());
        double upperp = OI.povToSign(
            pov, POV_UPPER_UP, POV_UPPER_DOWN);
        double elevp = OI.povToSign(
            pov, POV_ELEV_UP, POV_ELEV_DOWN);
        if (upperp + lowerp == 0) {
            arm.moveArmHold();
        } else {
            arm.moveArmTrack(lowerp, upperp);
        }
        arm.moveElevPercent(elevp);
        */
        arm.moveArm(0, 0);
        //arm.moveElevPercent(OI.cleanStick(-stick.getY()));
        arm.moveElev(0);

        if (stick.getRawButtonPressed(BUTTON_GRAB)) {
            arm.grab();
        }
        if (stick.getRawButtonPressed(BUTTON_CALIBRATE)) {
            arm.calibrate();
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
