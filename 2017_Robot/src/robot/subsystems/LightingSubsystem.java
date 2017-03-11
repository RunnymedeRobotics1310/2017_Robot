package robot.subsystems;

import com.toronto.subsystems.T_Subsystem;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Direction;
import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.mindsensors.CANLight;

import robot.RobotMap;
import robot.commands.DefaultLightingCommand;

/**
 * @author riku
 *
 */
public class LightingSubsystem extends T_Subsystem {
	
	private CANLight frameLights;
	private Relay spotlightRelay = new Relay(RobotMap.SPOTLIGHT_RELAY_PORT);

	private String color = "Default";
	
	/* (non-Javadoc)
	 * @see com.toronto.subsystems.T_Subsystem#robotInit()
	 */
	public void initDefaultCommand() {
		setDefaultCommand(new DefaultLightingCommand());
	}
	
	public void robotInit() {
		// TODO Auto-generated method stub
		frameLights = new CANLight(RobotMap.CANLIGHT_CAN_ADDRESS);
		spotlightRelay.setDirection(Direction.kForward);
		setSpotlight(false);
	}
	
	public void setBlueAlliance() {
		frameLights.showRGB(0, 0, 255);
		color = "Blue";
	}
	
	public void setRedAlliance() {
		frameLights.showRGB(255, 0, 0);
		color = "Red";
	}
	
	public void setGreen() {
		frameLights.showRGB(0, 255, 0);
		color = "Green";
	}
	
	public void setTeal() {
		frameLights.showRGB(0, 221, 192);
		color = "Teal";
	}
	
	public void setYellow() {
		frameLights.showRGB(255, 255, 0);
		color = "Yellow";
	}
	
	public void setSpotlight(boolean on) {
		if (on) {
			spotlightRelay.set(Value.kOn);
		} else {
			spotlightRelay.set(Value.kOff);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.toronto.subsystems.T_Subsystem#updatePeriodic()
	 */
	@Override
	public void updatePeriodic() {

		SmartDashboard.putString("Framelights", color);
		SmartDashboard.putString("Spotlight", (spotlightRelay.get() == Value.kOn ? "On":"Off"));
	}

}
