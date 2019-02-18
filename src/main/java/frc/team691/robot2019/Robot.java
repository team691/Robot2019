package frc.team691.robot2019;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.team691.robot2019.subsystems.*;

public class Robot extends TimedRobot {
    private OI oi;

    @Override
    public void robotInit() {
        Drivetrain.getInstance();
        DiscElevator.getInstance();
        oi = OI.getInstance();
    }

    @Override
    public void robotPeriodic() {
    }

    @Override
    public void disabledInit() {
    }

    @Override
    public void disabledPeriodic() {
        Scheduler.getInstance().run();
        
    }

    @Override
    public void autonomousInit() {
        oi.updateSticks();
    }

    @Override
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void teleopInit() {
        oi.updateSticks();
    }

    @Override
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }
}
