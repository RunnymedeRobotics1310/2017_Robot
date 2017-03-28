
package robot.commands;

import com.toronto.oi.T_OiController.RumbleState;

import edu.wpi.first.wpilibj.command.Command;
import robot.Robot;

/**
 * Intake command used to intake balls
 */
public class DefaultIntakeCommand extends Command {

	public DefaultIntakeCommand() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.intakeSubsystem);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {

		// Always check for operator cancel
    	if (Robot.oi.getCancel()) {
    		Robot.intakeSubsystem.stop();
			Robot.oi.setDriverRumble(0);
    		return; 
		}
    	
    	if(Robot.oi.getGearFlapCommand()){
    		if (Robot.oi.getDriverRumbleState() == RumbleState.OFF) {
				Robot.oi.setDriverRumble(1);
			}
			Robot.intakeSubsystem.intake();
    	} else {
    		if (Robot.oi.getDriverRumbleState() == RumbleState.ON) {
				Robot.oi.setDriverRumble(0);
			}
			Robot.intakeSubsystem.stop();
    	}
		
		if (Robot.oi.getIntakeToggleState()) {
			
			// Do not override pulses
			if (Robot.oi.getDriverRumbleState() == RumbleState.OFF) {
				Robot.oi.setDriverRumble(1);
			}
			Robot.intakeSubsystem.intake();
			
		} else if (Robot.oi.getOuttakeCommand()) {
		
			Robot.intakeSubsystem.outtake();
		
		} else {

			// Do not override pulses
			if (Robot.oi.getDriverRumbleState() == RumbleState.ON) {
				Robot.oi.setDriverRumble(0);
			}
			Robot.intakeSubsystem.stop();
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
	}

	@Override
	protected void interrupted() {
	}
}
