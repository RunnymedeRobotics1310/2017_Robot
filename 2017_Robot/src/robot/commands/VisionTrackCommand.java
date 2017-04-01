
package robot.commands;

import robot.Robot;
import robot.RobotConst.VisionDistance;
import robot.commands.auto.AutoVisionAlignCommand;

/**
 *
 */
public class VisionTrackCommand extends AutoVisionAlignCommand {

	public VisionTrackCommand(double timeout) {
		super(VisionDistance.CLOSE, timeout, false);
		// Use requires() here to declare subsystem dependencies
		requires(Robot.chassisSubsystem);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		
    	// Always check for operator cancel
    	if (Robot.oi.getCancel()) { 
    		return true;
		}

		// This command ends when the driver moves a joystick
		if (Robot.oi.isDriverJoystickAction()) { return true; }
		
		// Check for a timeout
		if (timeSinceInitialized() > timeout) {
			return true;
		}

		// This command does not end when the vision alignment is complete, it 
		// ends if a driver joystick is pressed.
		if (step == Step.DONE) { 
			step = Step.CALCULATE; 
		}
		
		return false;
	}

	@Override
	protected void end() {
		if (Robot.oi.isDriverJoystickAction()) {
			Robot.chassisSubsystem.disableGyroPid();
		}
	}

	@Override
	protected void interrupted() {
	}
}
