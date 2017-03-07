
package robot.commands.drive;

import robot.Robot;

/**
 * This command is used to drive to a certain heading at a certain speed with perfect accuracy.
 */
public class DriveToEncoderDistanceCommand extends DriveOnHeadingCommand {

	private double encoderDistanceInches;
	private boolean coastAtEnd = false;
	
    public DriveToEncoderDistanceCommand(double heading, double speed, double encoderDistanceInches) {
    	this(heading, speed, encoderDistanceInches, false);
    }

    public DriveToEncoderDistanceCommand(double heading, double speed, double encoderDistanceInches, boolean coastAtEnd) {
    	super(heading, speed);
    	this.encoderDistanceInches = encoderDistanceInches - 7.3;  // allow 2 inches overshoot for stopping.
    	this.coastAtEnd = coastAtEnd;
    }

    @Override
    protected void initialize() {
    	super.initialize();
    	Robot.chassisSubsystem.resetEncoders();
    }
    
	@Override
	protected boolean isFinished() {

		if (Math.abs(Robot.chassisSubsystem.getEncoderDistanceInches()) > Math.abs(this.encoderDistanceInches)) {
			if (!coastAtEnd) {
				Robot.chassisSubsystem.setMotorSpeeds(0, 0);
			}
			return true;
			
		}
		return false;
	}

}
