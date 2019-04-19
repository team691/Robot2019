package frc.team691.robot2019.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team691.robot2019.OI;
import frc.team691.robot2019.subsystems.Belevator;

public class StickBelev extends Command {
    private static final int STICK_PORT = 1; // X3D
    private static final int BUTTON_BOTTOM_DOWN = 3;
    private static final int BUTTON_BOTTOM_UP   = 5;
    private static final int POV_AUTO_UP        = 0;
    private static final int POV_AUTO_DOWN      = 180;

    private OI oi               = OI.getInstance();
    private Belevator belev     = Belevator.getInstance();
    private AutoElevate aeCommand = new AutoElevate();

    private boolean usp, osp;
    private int bud, bdd;

    public StickBelev() {
        SmartDashboard.putNumber("aeDir", 1);
        SmartDashboard.putData(aeCommand);

        requires(belev);
    }

    @Override
    protected void initialize() {
        System.out.println("sb initialize");
    }

    @Override
    protected void execute() {
        aeCommand.setDir((int) SmartDashboard.getNumber("aeDir", 0));

        Joystick stick = oi.getStick(STICK_PORT);
        if (stick == null) {
            belev.moveStop();
            return;
        }

        belev.moveBottomFixed(
            stick.getRawButton(BUTTON_BOTTOM_UP) || bdd > 0,
            stick.getRawButton(BUTTON_BOTTOM_DOWN) || bud > 0,
            true
        );
        boolean us = !belev.getUnderSwitch();
        boolean os = !belev.getOverSwitch();
        if (bud > 0) bud--;
        if (stick.getRawButtonReleased(BUTTON_BOTTOM_UP) ||
            (usp && !us)) {
            bud = Belevator.BOTTOM_STOP_LOOPS;
        }
        if (bdd > 0) bdd--;
        if (stick.getRawButtonReleased(BUTTON_BOTTOM_DOWN) ||
            (osp && !os)) {
            bdd = Belevator.BOTTOM_STOP_LOOPS;
        }
        usp = us;
        osp = os;

        int pov = stick.getPOV(0);
        if (pov == POV_AUTO_UP || pov == POV_AUTO_DOWN) {
            aeCommand.start(pov == POV_AUTO_UP ? 1 : -1);
            return;
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        System.out.println("sb end");
        belev.moveStop();
    }

    @Override
    protected void interrupted() {
        System.out.println("sb interrupted");
        end();
    }
}
