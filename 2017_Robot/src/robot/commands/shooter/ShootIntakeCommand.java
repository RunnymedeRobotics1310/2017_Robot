
package robot.commands.shooter;

import edu.wpi.first.wpilibj.command.Command;
import robot.Robot;

/**
 *
 */
public class ShootIntakeCommand extends Command {
	
	public ShootIntakeCommand() {
		requires(Robot.shooterSubsystem);
	}

	// Called just before this Command runs the first time
	protected void initialize() {}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		
		Robot.shooterSubsystem.intake();
		
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		if (!Robot.oi.getShootIntakeTrigger()){
			Robot.shooterSubsystem.intakeStop();
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
