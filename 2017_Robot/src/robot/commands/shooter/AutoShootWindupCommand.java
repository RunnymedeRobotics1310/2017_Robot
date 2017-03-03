
package robot.commands.shooter;

import edu.wpi.first.wpilibj.command.Command;
import robot.Robot;

public class AutoShootWindupCommand extends Command {	

	private final double shooterSpeedSetpoint;

	public AutoShootWindupCommand(double shooterSpeedSetpoint) {
		requires(Robot.shooterSubsystem);
		this.shooterSpeedSetpoint = shooterSpeedSetpoint;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.shooterSubsystem.setShooterSpeed(shooterSpeedSetpoint);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return true;
	}

	@Override
	protected void end() {
	}

	@Override
	protected void interrupted() {
	}
}
