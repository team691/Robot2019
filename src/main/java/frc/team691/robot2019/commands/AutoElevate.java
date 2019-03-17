package frc.team691.robot2019.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team691.robot2019.subsystems.DiscElevator;

public class AutoElevate extends Command {
    private DiscElevator elev = DiscElevator.getInstance();

    private int dir = 0;

    public AutoElevate() {
        requires(elev);
    }

    public void start(int dir) {
        this.dir = dir;
        start();
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
    }

    @Override
    protected boolean isFinished() {
        return elev.moveBottomAuto(dir);
    }

    @Override
    protected void end() {
        elev.moveStop();
    }

    @Override
    protected void interrupted() {
        end();
    }
}
