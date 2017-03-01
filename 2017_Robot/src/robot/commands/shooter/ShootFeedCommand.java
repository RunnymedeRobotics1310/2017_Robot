
package robot.commands.shooter;

import edu.wpi.first.wpilibj.command.Command;
import robot.Robot;

/**
 * Feed one ball at a time into the shooter
 */
public class ShootFeedCommand extends Command {
	double timeRunFeed;
	public ShootFeedCommand(double timeRunFeed) {
		this.timeRunFeed = timeRunFeed;
		requires(Robot.shooterSubsystem);
	}

	// Called just before this Command runs the first time
	protected void initialize() {}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		Robot.shooterSubsystem.setFeederSpeed(.5);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		if (!Robot.oi.getShootButton() || timeSinceInitialized() > timeRunFeed ){
			Robot.shooterSubsystem.stopFeeder();
			return true;
		}
		return false;
	}

	@Override
	protected void end() {
	}

	@Override
	protected void interrupted() {
	}
}
