package frc.team691.robot2019.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team691.robot2019.subsystems.DiscElevator;

public class AutoElevate extends Command {
    private DiscElevator elev = DiscElevator.getInstance();

    private int dir = 0;
    private int cd = 0;
    private boolean hasTouched = false;

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
        cd = 0;
        hasTouched = false;
    }

    @Override
    protected void execute() {
        //boolean touch = elev.moveBottomAuto(dir);
        boolean touch = (dir > 0 ? elev.getOverSwitch() :
            elev.getUnderSwitch());
        if (!hasTouched && touch) {
            hasTouched = true;
            cd = DiscElevator.BOTTOM_STOP_LOOPS;
        }
        if (cd > 0) {
            System.out.println(cd);
            //elev.moveBottomAuto(-dir);
            cd--;
        }
    }

    @Override
    protected boolean isFinished() {
        //boolean done = elev.moveBottomAuto(dir);
        boolean done = hasTouched && cd == 0;
        SmartDashboard.putBoolean("aeDone", done);
        return done;
    }

    @Override
    protected void end() {
        System.out.println("ae end");
        elev.move(0, 0);
    }

    @Override
    protected void interrupted() {
        System.out.println("ae interrupted");
        end();
    }
}
