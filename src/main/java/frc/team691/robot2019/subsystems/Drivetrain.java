package frc.team691.robot2019.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Drivetrain extends Subsystem {
    WPI_TalonSRX leftTalon;
    WPI_TalonSRX leftTalonSlave;
    WPI_TalonSRX rightTalon;
    WPI_TalonSRX rightTalonSlave;

    public Drivetrain() {
        leftTalon = new WPI_TalonSRX(0);
        leftTalonSlave = new WPI_TalonSRX(1);
        rightTalon = new WPI_TalonSRX(2);
        leftTalonSlave = new WPI_TalonSRX(3);
    }

    @Override
    public void initDefaultCommand() {
        // Test...
    }
}
