package robot.subsystems;

import com.toronto.subsystems.T_Subsystem;
import com.mindsensors.CANLight;

import robot.RobotMap;
import robot.commands.LightingCommand;

/**
 * @author riku
 *
 */
public class LightingSubsystem extends T_Subsystem {
	
	CANLight frameLights;
	/* (non-Javadoc)
	 * @see com.toronto.subsystems.T_Subsystem#robotInit()
	 */
	public void initDefaultCommand() {
		setDefaultCommand(new LightingCommand());
	}
	
	public void robotInit() {
		// TODO Auto-generated method stub
		frameLights = new CANLight(RobotMap.CANLIGHT_CAN_ADDRESS);
	}
	
	public void setBlueAlliance() {
		frameLights.showRGB(0, 0, 255);
	}
	
	public void setRedAlliance() {
		frameLights.showRGB(255, 0, 0);
	}
	
	public void setGreen() {
		frameLights.showRGB(0, 255, 0);
	}
	
	public void setTeal() {
		frameLights.showRGB(0, 221, 192);
	}
	
	public void setYellow() {
		frameLights.showRGB(255, 255, 0);
	}
	/* (non-Javadoc)
	 * @see com.toronto.subsystems.T_Subsystem#updatePeriodic()
	 */
	@Override
	public void updatePeriodic() {
		// TODO Auto-generated method stub
		
	}

}
