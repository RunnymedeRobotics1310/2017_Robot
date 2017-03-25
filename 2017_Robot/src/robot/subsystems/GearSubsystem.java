
package robot.subsystems;

import com.toronto.sensors.T_LimitSwitch;
import com.toronto.sensors.T_LimitSwitch.DefaultState;
import com.toronto.subsystems.T_Subsystem;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
	public T_LimitSwitch gearSensor = new T_LimitSwitch(RobotMap.FRONT_GEAR_SWITCH_DIO_PORT, DefaultState.TRUE);
	
	private DoubleSolenoid gearFlapSolenoid = 
			new DoubleSolenoid(RobotMap.GEAR_FLAP_SOLENOID_A, RobotMap.GEAR_FLAP_SOLENOID_B);

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
	
	public void openFlap() {
		gearFlapSolenoid.set(Value.kForward);
	} 
	
	public void closeFlap() {
		gearFlapSolenoid.set(Value.kReverse);
	}
	
	
	public GearState getCurrentState() {
		return gearSolenoid.get() == Value.kForward ? GearState.OPEN : GearState.CLOSED;
	}

	@Override
	public void updatePeriodic() {
		// Update all SmartDashboard values
		SmartDashboard.putString("Gear State", String.valueOf(getCurrentState()));
		SmartDashboard.putBoolean("Gear Sensor", getGearSensor().atLimit());
	}

	@Override
	public void robotInit() {
		close();
	}
}
