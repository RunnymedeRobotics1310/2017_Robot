package robot.commands.auto;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoPauseCommand extends Command {

	private final double pauseTime;
	
    public AutoPauseCommand(double pauseTime) {
    	this.pauseTime = pauseTime;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return timeSinceInitialized() >= pauseTime;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
