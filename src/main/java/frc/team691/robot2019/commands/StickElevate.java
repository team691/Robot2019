package frc.team691.robot2019.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import frc.team691.robot2019.OI;
import frc.team691.robot2019.subsystems.DiscElevator;

public class StickElevate extends Command {
    private static final int STICK_PORT = 1;
    private static final int BUTTON_BOTTOM_DOWN = 2;
    private static final int BUTTON_BOTTOM_UP   = 3;
    private static final int BUTTON_SIDE_DOWN   = 4;
    private static final int BUTTON_SIDE_UP     = 5;

    private OI oi               = OI.getInstance();
    private DiscElevator elev   = DiscElevator.getInstance();

    public StickElevate() {
        requires(elev);
    }
    
    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        // TODO: initial grabber position
    }
    
    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        Joystick stick = oi.getStick(STICK_PORT);
        if (stick == null) {
            elev.moveStop();
            return;
        }
        //elev.drive(0, 0);
        if (stick.getTriggerPressed()) {
            elev.grab();
        }
        elev.moveFixed(stick.getRawButton(BUTTON_BOTTOM_UP),
            stick.getRawButton(BUTTON_BOTTOM_DOWN),
            stick.getRawButton(BUTTON_SIDE_UP),
            stick.getRawButton(BUTTON_SIDE_DOWN));
        /*
        if (stick.getRawButton(BUTTON_BOTTOM_DOWN)) {
            elev.moveDirBottom(-1);
        } else if (stick.getRawButton(BUTTON_BOTTOM_UP)) {
            elev.moveDirBottom(1);
        } else {
            elev.moveDirBottom(0);
        }
        if (stick.getRawButton(BUTTON_SIDE_DOWN)) {
            elev.moveDirSide(-1);
        } else if (stick.getRawButton(BUTTON_SIDE_UP)) {
            elev.moveDirSide(1);
        } else {
            elev.moveDirSide(0);
        }
        */
    }
    
    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }
    
    // Called once after isFinished returns true
    @Override
    protected void end() {
        elev.moveStop();
    }
    
    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }
}
