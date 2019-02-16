package frc.team691.robot2019;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team691.robot2019.subsystems.Drivetrain;

public class Robot extends TimedRobot {
    //private Drivetrain drivetrain;
    private OI oi;

    @Override
    public void robotInit() {
        Drivetrain.getInstance();
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
    }

    @Override
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void teleopInit() {
        oi.updateSticks();
        SmartDashboard.putNumber("numSticks", oi.getNumSticks());
    }

    @Override
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }
}