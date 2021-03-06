package frc.team691.robot2019;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.team691.robot2019.subsystems.*;

public class Robot extends TimedRobot {
    private OI oi;
    private UsbCamera webcam;

    @Override
    public void robotInit() {
        // Construct subsystems in use
        Drivetrain.getInstance();
        Melevator.getInstance();
        Belevator.getInstance();

        oi = OI.getInstance();

        webcam = CameraServer.getInstance().startAutomaticCapture();
        //webcam.setFPS(30);
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
        oi.updateHids();
    }

    @Override
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void teleopInit() {
        oi.updateHids();
    }

    @Override
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }
}
