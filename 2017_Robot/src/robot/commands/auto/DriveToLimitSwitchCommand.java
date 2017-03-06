
package robot.commands.auto;

import com.toronto.sensors.T_LimitSwitch;

import robot.Robot;

/**
 * Drive to a limit switch using the distance as a safety stop
 */
public class DriveToLimitSwitchCommand extends DriveToEncoderDistanceCommand {

	private T_LimitSwitch limitSwitch;
	
	private boolean coastAtEnd = false;
	
    public DriveToLimitSwitchCommand(double heading, double speed, T_LimitSwitch limitSwitch, double maxEncoderDistanceInches) {
    	this(heading, speed, limitSwitch, maxEncoderDistanceInches, false);
    }

    public DriveToLimitSwitchCommand(double heading, double speed, T_LimitSwitch limitSwitch, double maxEncoderDistanceInches, 
    		boolean coastAtEnd) {
    	super(heading, speed, maxEncoderDistanceInches);
    	this.limitSwitch = limitSwitch;
    	this.coastAtEnd  = coastAtEnd;
    }

    @Override
    protected void initialize() {
    	super.initialize();
    }
    
	@Override
	protected boolean isFinished() {
		
		// If at the limit, this command is finished
		if (limitSwitch.atLimit()) {
			if (!coastAtEnd) {
				Robot.chassisSubsystem.setMotorSpeeds(0, 0);
			}
			return true; 
		}

		// Check for the max distance
		return super.isFinished();
	}

}
