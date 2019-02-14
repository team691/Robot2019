package frc.team691.robot2019;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team691.robot2019.commands.StickDrive;
import frc.team691.robot2019.subsystems.Drivetrain;

public class Robot extends TimedRobot {
    private OI oi;
    private Drivetrain drivetrain;

    private Command sdrive;
    
    @Override
    public void robotInit() {
        oi = new OI();
        drivetrain = new Drivetrain();

        sdrive = new StickDrive(drivetrain, oi);
        drivetrain.setDefaultCommand(sdrive);
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
    }
    
    @Override
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }
}
