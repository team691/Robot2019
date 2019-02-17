package frc.team691.robot2019.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team691.robot2019.commands.StickGrab;

public class BallArm extends Subsystem {
    // TODO: Add private hardware

    private BallArm() {
    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new StickGrab());
    }

    @Override
    public void periodic() {
    }

    public void driveStop() {
        // TODO: stop motors
    }

    private static BallArm instance;
    public static synchronized BallArm getInstance() {
        if (instance == null) {
            instance = new BallArm();
        }
        return instance;
    }
}
