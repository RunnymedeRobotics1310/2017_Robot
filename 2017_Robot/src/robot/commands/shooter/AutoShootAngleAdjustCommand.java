
package robot.commands.shooter;

import edu.wpi.first.wpilibj.command.Command;
import robot.Robot;

public class AutoShootAngleAdjustCommand extends Command {	

	private final int    shooterAngleAdjustSetpoint;

	public AutoShootAngleAdjustCommand(int shooterAngleAdjustSetpoint) {
		requires(Robot.shooterSubsystem);
		this.shooterAngleAdjustSetpoint = shooterAngleAdjustSetpoint;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.shooterSubsystem.setShooterAngleAdjustSetpoint(shooterAngleAdjustSetpoint);
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
