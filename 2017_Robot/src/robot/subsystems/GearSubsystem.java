
package robot.subsystems;

import com.toronto.sensors.T_LimitSwitch;
import com.toronto.sensors.T_LimitSwitch.DefaultState;
import com.toronto.subsystems.T_Subsystem;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.Robot;
import robot.RobotMap;
import robot.commands.DefaultGearCommand;

public class GearSubsystem extends T_Subsystem {

	public enum GearState { OPEN, CLOSED };
	/*
	 * *************************************************************************
	 * *** Hardware declarations
	 * 
	 * Declare all motors and sensors here
	 ******************************************************************************/
	private DoubleSolenoid gearSolenoid = 
			new DoubleSolenoid(RobotMap.GEAR_SOLENOID_A, RobotMap.GEAR_SOLENOID_B);
	public T_LimitSwitch gearSensor = new T_LimitSwitch(RobotMap.FRONT_GEAR_SWITCH_DIO_PORT, DefaultState.FALSE);

	public void initDefaultCommand() {
		setDefaultCommand(new DefaultGearCommand());
	}
	
	public void open() { 
		gearSolenoid.set(Value.kForward);
	}

	public void close() {
		gearSolenoid.set(Value.kReverse);
	}

	public T_LimitSwitch getGearSensor() {
		return gearSensor;
	}
	
	public void rumbleControllers() {
		if (getGearSensor().atLimit()) {
			Robot.oi.setDriverRumble(1);
			Robot.oi.setOperatorRumble(1);
		} else {
			Robot.oi.setDriverRumble(0);
			Robot.oi.setOperatorRumble(0);
		}
	}
	
	public GearState getCurrentState() {
		return gearSolenoid.get() == Value.kForward ? GearState.OPEN : GearState.CLOSED;
	}

	@Override
	public void updatePeriodic() {
		// Update all SmartDashboard values
		SmartDashboard.putString("Gear State", String.valueOf(getCurrentState()));
	}

	@Override
	public void robotInit() {
		close();
	}
}
