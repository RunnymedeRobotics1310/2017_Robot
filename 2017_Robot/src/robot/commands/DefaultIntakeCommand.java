
package robot.commands;

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
		if (Robot.oi.getIntakeToggleState()) {
			Robot.oi.setDriverRumble(0.3);
			Robot.intakeSubsystem.intake();
		} else if (Robot.oi.getOuttakeCommand()) {
			Robot.intakeSubsystem.outtake();
		} else {
			Robot.intakeSubsystem.stop();
			Robot.oi.setDriverRumble(0);
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
