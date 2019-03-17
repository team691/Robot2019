package frc.team691.robot2019;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;

public class OI {
    public static final double STICK_MIN_IN = 0.1;
    public static final int XBOX_BUTTON_X = 1;
    public static final int XBOX_BUTTON_Y = 4;
    public static final int XBOX_BUTTON_A = 2;
    public static final int XBOX_BUTTON_B = 3;
    public static final int XBOX_BUTTON_BACK = 9;
    public static final int XBOX_BUTTON_START = 10;
    public static final int XBOX_AXIS_LEFT_X = 0;
    public static final int XBOX_AXIS_LEFT_Y = 1;
    public static final int XBOX_AXIS_RIGHT_X = 2;
    public static final int XBOX_AXIS_RIGHT_Y = 3;
    public static final double XBOX_MIN_IN = 0.1;
    // DS USB ports with XboxControllers
    private static final boolean[] XBOX_PORTS = new boolean[] {
        true
    };

    private GenericHID[] hids = new GenericHID[DriverStation.kJoystickPorts];

    private OI() {
        updateSticks();
    }

    public Joystick getStick(int i) {
        if (i < hids.length && (i >= XBOX_PORTS.length || !XBOX_PORTS[i])) {
            return (Joystick) hids[i];
        }
        return null;
    }

    public XboxController getXbox(int i) {
        if (i < XBOX_PORTS.length && XBOX_PORTS[i] && i < hids.length) {
            return (XboxController) hids[i];
        }
        return null;
    }
    
    int updateSticks() {
        int res = 0;
        DriverStation ds = DriverStation.getInstance();
        for (int i = 0; i < hids.length; i++) {
            if (ds.getJoystickType(i) != 0) {
                if (hids[i] == null) {
                    hids[i] = (i < XBOX_PORTS.length && XBOX_PORTS[i] ?
                        new XboxController(i) : new Joystick(i));
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

    public static double cleanXbox(double x) {
        return clean(x, XBOX_MIN_IN);
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
    //// CREATING BUTTONS
    // One type of button is a joystick button which is any button on a
    //// joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.
    // Joystick stick = new Joystick(port);
    // Button button = new JoystickButton(stick, buttonNumber);
    
    // There are a few additional built in buttons you can use. Additionally,
    // by subclassing Button you can create custom triggers and bind those to
    // commands the same as any other Button.
    
    //// TRIGGERING COMMANDS WITH BUTTONS
    // Once you have a button, it's trivial to bind it to a button in one of
    // three ways:
    
    // Start the command when the button is pressed and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenPressed(new ExampleCommand());
    
    // Run the command while the button is being held down and interrupt it once
    // the button is released.
    // button.whileHeld(new ExampleCommand());
    
    // Start the command when the button is released and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenReleased(new ExampleCommand());
}
