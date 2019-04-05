package frc.team691.robot2019.commands;

import edu.wpi.first.wpilibj.command.TimedCommand;
import frc.team691.robot2019.subsystems.DiscElevator;

public class ResetElevate extends TimedCommand {
    private DiscElevator elev = DiscElevator.getInstance();

    public ResetElevate(double time) {
        super(time);
        requires(elev);
    }

    @Override
    protected void initialize() {
        System.out.println("re initialize");
        elev.setHand(DiscElevator.HAND_SHUT);
    }

    @Override
    protected void execute() {
        elev.moveReleaseDir(1);
    }

    @Override
    protected void end() {
        System.out.println("re end");
        elev.moveReleaseDir(0);
    }

    @Override
    protected void interrupted() {
        System.out.println("re interrupted");
        end();
    }
}
