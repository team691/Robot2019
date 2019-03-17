package frc.team691.robot2019.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team691.robot2019.subsystems.DiscElevator;

public class AutoElevate extends Command {
    private DiscElevator elev = DiscElevator.getInstance();

    private int dir = 0;

    public AutoElevate() {
        requires(elev);
    }

    public void start(int dir) {
        setDir(dir);
        start();
    }

    public void setDir(int dir) {
        this.dir = dir;
    }

    @Override
    protected void initialize() {
        System.out.println("ae initialize");
    }

    @Override
    protected void execute() {
    }

    @Override
    protected boolean isFinished() {
        boolean done = elev.moveBottomAuto(dir);
        SmartDashboard.putBoolean("aeDone", done);
        return done;
    }

    @Override
    protected void end() {
        System.out.println("ae end");
        elev.moveStop();
    }

    @Override
    protected void interrupted() {
        System.out.println("ae interrupted");
        end();
    }
}
