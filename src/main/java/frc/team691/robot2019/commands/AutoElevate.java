package frc.team691.robot2019.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team691.robot2019.subsystems.Belevator;

public class AutoElevate extends Command {
    private Belevator belev = Belevator.getInstance();

    private int dir = 0;
    private int cd = 0;
    private boolean isStarted = false;
    private boolean hasTouched = false;
    private boolean done = false;

    public AutoElevate() {
        requires(belev);
    }

    @Override
    public void start() {
        System.out.println("ae start");
        if (!isStarted) {
            isStarted = true;
            super.start();
        }
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
        if (dir == 0) cancel();
        cd = 0;
        hasTouched = false;
        done = false;
    }

    @Override
    protected void execute() {
        if (!hasTouched && belev.moveBottomAuto(dir)) {
        //boolean touch = !(dir > 0 ? elev.getOverSwitch() :
        //    elev.getUnderSwitch());
        //if (!hasTouched && touch) {
            hasTouched = true;
            cd = Belevator.BOTTOM_STOP_LOOPS;
        }
        if (cd > 0) {
            System.out.println(cd);
            belev.moveBottomAuto(-dir);
            cd--;
        } else if (hasTouched) {
            belev.moveStop();
            done = true;
        }
    }

    @Override
    protected boolean isFinished() {
        SmartDashboard.putBoolean("aeDone", done);
        return done;
    }

    @Override
    protected void end() {
        System.out.println("ae end");
        isStarted = false;
        belev.moveStop();
    }

    @Override
    protected void interrupted() {
        System.out.println("ae interrupted");
        end();
    }
}
