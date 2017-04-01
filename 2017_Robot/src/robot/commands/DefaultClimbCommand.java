
package robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import robot.Robot;
import robot.commands.DefaultDriveCommand.ButtonState;

/**
 * Climb command is used for climbing the rope
 */
public class DefaultClimbCommand extends Command {

	ButtonState climbButtonState = ButtonState.RELEASED;

	public DefaultClimbCommand() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.climbSubsystem);
	}

	// Called just before this Command runs the first time
	protected void initialize() {

	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		
    	// Always check for operator cancel
    	if (Robot.oi.getCancel()) { 
    		Robot.climbSubsystem.stop();
    		return;
		}

    	if (Robot.oi.getClimbCatch()) {
    		Robot.climbSubsystem.catchRope();
    	} 
    	else if (Robot.oi.getFastClimb()){
    		Robot.climbSubsystem.climb();
    	}
    	else {
    		Robot.climbSubsystem.stop();
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
