# Robot2019
Command-based robot code for FRC 2019.

## Controls
### Controller Assumptions
| DS #  | Controller            |
|:-----:|:--------------------- |
| 0     | DualActionXbox        |
| 1     | Logitech Extreme3D    |

### Drivetrain Controls
* Mecanum drive operated by DualActionXbox, with
  two drive modes:
  * FPS mode (default):
    * Strafe with __left-hand stick__.
    * Turn with __right-hand stick__.
  * Racing mode:
    * Drive forward/backward and turn with
      __left-hand stick__.
    * Strafe left/right with __right-hand stick__.
  * Toggle with __pad down-button__
    (state in __isFPS__ on SmartDasboard).
* Robot front swapped with __pad up-button__.
* (Advanced) Robot-oriented drive by default.
  * Toggle between field-oriented with
    __special right-button__.
  * State in __isFD__ on SmartDashboard.
  * Calibrate field-drive gyro with
    __special left-button__.

### Elevator Controls
* Button-operated by Extreme3D.
* At start of Autonomous, grabber opens and
  lowers by release motor for 1 sec.
* Move first stage up/down with buttons __5/3__.
* Move second stage up/down with buttons __6/4__.
* Auto-move first stage to top/bottom
  with POV __up/down__.
* Open and shut grabber with __trigger__.
* Tighten/loosen the release motor with
  buttons __9/11__.
* Automatically close grabber and lift with
  release motor for 1.32 sec with button __12__.

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

| NO Limit Switch   | DIO   |
|:----------------- |:-----:|
| Over              | 0     |
| Under             | 1     |
