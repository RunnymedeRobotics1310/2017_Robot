
package robot.commands.drive;

import com.toronto.sensors.T_LimitSwitch;

import robot.Robot;

/**
 * Drive to a limit switch using the distance as a safety stop
 */
public class DriveToLimitSwitchCommand extends DriveToEncoderDistanceCommand {

	private T_LimitSwitch limitSwitch;
	
    public DriveToLimitSwitchCommand(double heading, double speed, T_LimitSwitch limitSwitch, double maxEncoderDistanceInches) {
    	super(heading, speed, maxEncoderDistanceInches);
    	this.limitSwitch = limitSwitch;
    }

    @Override
    protected void initialize() {
    	super.initialize();
    }
    
	@Override
	protected boolean isFinished() {
		
    	// Always check for operator cancel
    	if (Robot.oi.getCancel()) { return true; }

		// If at the limit, this command is finished
		if (limitSwitch.atLimit()) {
			Robot.chassisSubsystem.setMotorSpeeds(0, 0);
			return true; 
		}

		// Check for the max distance
		return super.isFinished();
	}

}
