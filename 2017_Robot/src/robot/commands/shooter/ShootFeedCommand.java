
package robot.commands.shooter;

import java.util.function.DoubleToLongFunction;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import robot.Robot;

/**
 *
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
		Robot.shooterSubsystem.feedIntake();
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		if (!Robot.oi.getShootIntakeTrigger() || timeSinceInitialized() > timeRunFeed ){
			Robot.shooterSubsystem.feedStop();
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
