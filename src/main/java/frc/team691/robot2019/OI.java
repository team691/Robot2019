package frc.team691.robot2019;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class OI {
    public static final double STICK_MIN_IN = 0.1;
    // DS USB ports with XboxControllers
    private SendableChooser<Integer>[] xboxTypes =
        new SendableChooser[1];
    private GenericHID[] hids = new GenericHID[DriverStation.kJoystickPorts];

    private OI() {
        for (int i = 0; i < xboxTypes.length; i++) {
            SendableChooser<Integer> sc = new SendableChooser<>();
            sc.setDefaultOption("PS4", DualActionXbox.TYPE_PS4);
            sc.addOption("LOGITECH", DualActionXbox.TYPE_LOGITECH);
            xboxTypes[i] = sc;
            SmartDashboard.putData("port" + i, sc);
        }
        updateSticks();
    }

    public Joystick getStick(int i) {
        if (i < hids.length && i >= xboxTypes.length) {
            return (Joystick) hids[i];
        }
        return null;
    }

    public DualActionXbox getXbox(int i) {
        if (i < xboxTypes.length) {
            return (DualActionXbox) hids[i];
        }
        return null;
    }

    int updateSticks() {
        int res = 0;
        DriverStation ds = DriverStation.getInstance();
        for (int i = 0; i < hids.length; i++) {
            if (ds.getJoystickType(i) != 0) {
                if (hids[i] == null) {
                    if (i < xboxTypes.length) {
                        int t = xboxTypes[i].getSelected();
                        hids[i] = new DualActionXbox(i, t);
                    } else {
                        hids[i] = new Joystick(i);
                    }
                } else if (i < xboxTypes.length) {
                    ((DualActionXbox) hids[i]).type =
                        xboxTypes[i].getSelected();
                }
                res++;
            } else if (hids[i] != null) {
                hids[i] = null;
            }
        }
        return res;
    }

    public static int povToSign(int pov, int plusAngle, int minusAngle) {
        return (pov == plusAngle ? 1 : (pov == minusAngle ? -1 : 0));
    }

    public static double cleanStick(double x) {
        return clean(x, STICK_MIN_IN);
    }

    public static double clean(double x, double min) {
        return (Math.abs(x) < min ? 0 : x);
    }

    public static double limit(double x, int max) {
        return Math.copySign(Math.min(Math.abs(x), max), x);
    }

    private static OI instance;
    public static synchronized OI getInstance() {
        if (instance == null) {
            instance = new OI();
        }
        return instance;
    }

    public static class DualActionXbox extends XboxController {
        public static final int TYPE_LOGITECH   = 0;
        public static final int TYPE_PS4        = 1;
        public static final double[] TYPE_MIN_INS =
            new double[] {0.1, 0};
        public static final int[][] TYPE_AXES   = new int[][] {
            {0, 0},
            {1, 1},
            {2, 2},
            {3, 5}
        };
        public static final int AXIS_LEFT_X     = 0;
        public static final int AXIS_LEFT_Y     = 1;
        public static final int AXIS_RIGHT_X    = 2;
        public static final int AXIS_RIGHT_Y    = 3;
        public static final int[][] TYPE_BUTTONS = new int[][] {
            {1, 1},
            {3, 3},
            {4, 4},
            {2, 2},
            {9, 9},
            {10, 10},
            {14, 14},
            {13, 13}
        };
        public static final int BUTTON_LEFT     = 0;
        public static final int BUTTON_RIGHT    = 1;
        public static final int BUTTON_UP       = 2;
        public static final int BUTTON_DOWN     = 3;
        public static final int BUTTON_LEFT_SP  = 4;
        public static final int BUTTON_RIGHT_SP = 5;
        public static final int BUTTON_UP_SP    = 6;
        public static final int BUTTON_DOWN_SP  = 7;

        public int type;

        public DualActionXbox(int port, int type) {
            super(port);
            this.type = type;
        }

        public double getAxis(int axis) {
            return this.getRawAxis(TYPE_AXES[axis][type]);
        }

        public double getAxisClean(int axis) {
            return clean(getAxis(axis), TYPE_MIN_INS[type]);
        }

        public boolean getButtonPressed(int button) {
            return this.getRawButtonPressed(TYPE_BUTTONS[button][type]);
        }

        public boolean getButton(int button) {
            return this.getRawButton(TYPE_BUTTONS[button][type]);
        }
    }
}
