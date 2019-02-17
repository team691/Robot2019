package frc.team691.robot2019.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team691.robot2019.commands.StickElevate;

public class DiscElevator extends Subsystem {
    private static DiscElevator instance;
    public static synchronized DiscElevator getInstance() {
        if (instance == null) {
            instance = new DiscElevator();
        }
        return instance;
    }

    // TODO: Add private hardware

    private DiscElevator() {
    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new StickElevate());
    }

    public void driveStop() {
        // TODO: stop motors
    }
}
