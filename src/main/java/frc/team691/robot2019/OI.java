package frc.team691.robot2019;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;

public class OI {
    public static final int STICK_TYPE_X3D = 20;
    
    private static OI instance;
    public static synchronized OI getInstance() {
        if (instance == null) {
            instance = new OI();
        }
        return instance;
    }

    private Joystick[] sticks;
    private int[] stickTypes;

    private OI() {
        updateSticks();
    }

    public Joystick getStick(int i) {
        return (i < sticks.length ? sticks[i] : null);
    }

    // Get stick only of given type
    public Joystick getStick(int i, int type) {
        return (getStickType(i) == type ? getStick(i) : null);
    }

    public int getNumSticks() {
        return sticks.length;
    }

    public int getStickType(int i) {
        return (i < sticks.length ? stickTypes[i] : 0);
    }
    
    int updateSticks() {
        int i;
        DriverStation ds = DriverStation.getInstance();
        for (i = 0; ds.getJoystickType(i) != 0 && i < DriverStation.kJoystickPorts; i++);
        if (sticks == null || sticks.length != i) {
            sticks = new Joystick[i];
            stickTypes = new int[i];
            for (i = 0; i < sticks.length; i++) {
                sticks[i] = new Joystick(i);
                stickTypes[i] = ds.getJoystickType(i);
                System.out.format("s%d: %d\n", i, stickTypes[i]);
            }
        }
        return i;
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
