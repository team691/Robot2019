# Robot2019
Command-based robot code for FRC 2019.

## Controls
### Controller Assumptions
| DS #  | Controller                |
|:-----:|:------------------------- |
| 0     | Logitech XboxController   |
| 1     | Logitech Extreme3D        |

### Drivetrain Controls
* Mecanum drive operated by XboxController, with
  two drive modes:
  * FPS mode (default):
    * Strafe with __left-hand stick__.
    * Turn with __right-hand stick__.
  * Racing mode:
    * Drive forward/backward and turn with
      __left-hand stick__.
    * Strafe left/right with __right-hand stick__.
  * Toggle with __B-button__
    (state in __isFPS__ on SmartDasboard).
* Robot front swapped with __Y-button__.
* (Advanced) Robot-oriented drive by default.
  * Toggle between field-oriented with __START__
    button.
  * State in __isFD__ on SmartDashboard.
  * Calibrate field-drive gyro with __BACK__ button.

### Elevator Controls
* Button-operated by Extreme3D.
* Move first stage up/down with buttons __5/3__.
* Move second stage up/down with buttons __6/4__.
* Open and shut grabber with __trigger__.
* Tighten/loosen the release motor with
  buttons __9/11__.

### Arm Controls (DEFUNCT)
* Stick- and button-operated by Extreme3D.
* Move lower part with __Y-axis__.
* Move upper part with __POV up/down__.
* Move arm elevator up/down with __POV right/left__.
* Calibrate arm encoder positions with button
  __7__ or __calibrateArm__ in SmartDashboard.

## Hardware
USB Webcam expected.
### Drivetrain
| TalonSRX      | CAN ID    |
|:------------- |:---------:|
| Front left    | 1         |
| Front right   | 3         |
| Rear left     | 0         |
| Rear right    | 2         |

### Elevator
DoubleSolenoid at PCM ports 0, 1.

| VictorSPX | CAN ID    |
|:--------- |:---------:|
| Side      | 1         |
| Bottom    | 2         |
| Release   | 3         |

| NC Limit Switch   | DIO   |
|:----------------- |:-----:|
| Over              | 0     |
| Under             | 5     |

### Arm (DISCONNECTED)
* DoubleSolenoid at PCM ports 2, 3.
* SparkMax with encoder at CAN ID 1.
* Arm motors unknown.

| Encoder   | DIOs  |
|:--------- |:-----:|
| Lower     | 1, 2  |
| Upper     | 3, 4  |
