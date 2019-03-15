# Robot2019
Command-based robot code for FRC 2019.

## Controls
### Controller Assumptions
+ Driver station port 0: __Logitech XboxController__.
+ Driver station port 1: __Logitech Extreme3D__.

### Drivetrain Controls
+ Mecanum drive operated by XboxController.
+ Strafe with __left-hand stick__.
+ Turn with __right-hand stick__.
+ Robot-oriented drive by default.<br>Toggle between
field-oriented with __START__ button.<br>State
shown in __isFieldDrive__ on SmartDashboard.<br>
Calibrate field-drive gyro with __BACK__ button.

### Elevator Controls
+ Button-operated by Extreme3D.
+ Move spindle up/down with buttons __5/3__.
+ Move assembly up/down with buttons __6/4__.
+ Grab and release with __trigger__.

### Arm Controls
+ Stick- and button-operated by Extreme3D.
+ Move lower part with __Y-axis__.
+ Move upper part with __POV up/down__.
+ Move arm elevator up/down with POV __right/left__.
+ Calibrate arm encoder positions with button
__7__ or __calibrateArm__ in SmartDashboard.

## Hardware
USB Webcam expected.
### Drivetrain
<table>
    <tr><td>TalonSRX</td><td>CAN ID</td></tr>
    <tr><td>Front left</td><td>1</td></tr>
    <tr><td>Rear left</td><td>0</td></tr>
    <tr><td>Front right</td><td>3</td></tr>
    <tr><td>Rear right</td><td>2</td></tr>
</table>

### Elevator
DoubleSolenoid at PCM ports 0, 1.
<table>
    <tr><td>VictorSPX</td><td>CAN ID</td></tr>
    <tr><td>Bottom</td><td>2</td></tr>
    <tr><td>Side</td><td>0</td></tr>
</table>
<br>
<table>
    <tr><td>NC Limit Switch</td><td>DIO</td></tr>
    <tr><td>Under</td><td>5</td></tr>
    <tr><td>Over</td><td>0</td></tr>
</table>

### Arm
+ DoubleSolenoid at PCM ports 2, 3.
+ SparkMax with encoder at CAN ID 0.
<table>
    <tr><td>VictorSPX</td><td>CAN ID</td></tr>
    <tr><td>Lower</td><td>1</td></tr>
    <tr><td>Upper</td><td>3</td></tr>
</table>
<br>
<table>
    <tr><td>Encoder</td><td>DIOs</td></tr>
    <tr><td>Lower</td><td>1, 2</td></tr>
    <tr><td>Upper</td><td>3, 4</td></tr>
</table>
