
package robot.commands.auto;

import robot.Robot;

/**
 *
 */
public class DriveToEncoderDistanceCommand extends DriveOnHeadingCommand {

	private double encoderDistanceInches;
	
    public DriveToEncoderDistanceCommand(double heading, double speed, double encoderDistanceInches) {
    	super(heading, speed);
    	this.encoderDistanceInches = encoderDistanceInches - 7.3;  // allow 2 inches overshoot for stopping.
    }

    @Override
    protected void initialize() {
    	super.initialize();
    	Robot.chassisSubsystem.resetEncoders();
    }
    
	@Override
	protected boolean isFinished() {
//		if (this.setSpeed >= 0) {
//			if (Robot.chassisSubsystem.getEncoderDistanceInches() > this.encoderDistanceInches) {
//				Robot.chassisSubsystem.setMotorSpeeds(0, 0);
//				return true;
//			}
//		} else {
//			if (Robot.chassisSubsystem.getEncoderDistanceInches() < this.encoderDistanceInches) {
//				Robot.chassisSubsystem.setMotorSpeeds(0, 0);
//				return true;
//			}
//		}
		if(Math.abs(Robot.chassisSubsystem.getEncoderDistanceInches()) > Math.abs(this.encoderDistanceInches)){
			Robot.chassisSubsystem.setMotorSpeeds(0, 0);
			System.out.println("Chassis subsystem Encoder distance inches:" + Math.abs(Robot.chassisSubsystem.getEncoderDistanceInches()));
			System.out.println(" Encoder distance inches:" + Math.abs(this.encoderDistanceInches));
//			System.out.println("ending drive to encoder");
			return true;
			
		}
		return false;
	}

}
