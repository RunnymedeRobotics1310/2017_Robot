
package robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import robot.Robot;
import robot.subsystems.GearSubsystem.GearState;

/**
 * This command releases the gear
 */
public class GearReleaseCommand extends Command {

    public GearReleaseCommand() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.gearSubsystem);
        requires(Robot.chassisSubsystem);
    }

	// Called just before this Command runs the first time
	protected void initialize() {
//		Robot.gearSubsystem.open();
	}

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    
    	if (Robot.gearSubsystem.getCurrentState() == GearState.CLOSED) {
    		if (Robot.chassisSubsystem.atTower()) {
    			Robot.gearSubsystem.open();
    		}
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
