package robot.subsystems;

import com.toronto.subsystems.T_Subsystem;
import com.mindsensors.CANLight;

import robot.RobotMap;
import robot.commands.DefaultLightingCommand;

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
		setDefaultCommand(new DefaultLightingCommand());
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
	
	public void endGame() {
		frameLights.writeRegister(0, 0.5, 0, 221, 192);
		frameLights.writeRegister(1, 0.3, 0, 0, 0);
		frameLights.writeRegister(2, 0.5, 250, 0, 255);
		frameLights.writeRegister(3, 0.3, 0, 0, 0);
		frameLights.writeRegister(4, 0.5, 255, 225, 49);
		frameLights.writeRegister(5, 0.3, 0, 0, 0);
	}
	/* (non-Javadoc)
	 * @see com.toronto.subsystems.T_Subsystem#updatePeriodic()
	 */
	@Override
	public void updatePeriodic() {
		// TODO Auto-generated method stub
		
	}

}
