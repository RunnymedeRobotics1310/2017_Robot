
package robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import robot.Robot;
import robot.commands.JoystickCommand.ButtonState;
import robot.subsystems.GearSubsystem.GearState;

/**
 *
 */
public class DefaultGearCommand extends Command {

	ButtonState gearButtonState = ButtonState.RELEASED;

	public DefaultGearCommand() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.gearSubsystem);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
//		Robot.gearSubsystem.close();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		
    	if (Robot.oi.getGearToggleState()) {
    		Robot.gearSubsystem.open();
    	} else {
    		Robot.gearSubsystem.close();
    	}
    	
		if (       Robot.gearSubsystem.getCurrentState() == GearState.OPEN
				&& Robot.chassisSubsystem.getSpeed() > 0.2) {
			Robot.oi.setGearButton(false);
			return;
		}

	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return timeSinceInitialized() > 1.0;
	}

	@Override
	protected void end() {
	}

	@Override
	protected void interrupted() {
	}
}
