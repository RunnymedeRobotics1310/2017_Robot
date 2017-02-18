
package robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import robot.Robot;


public class ShootAngleMoveDownCommand extends Command {
	
	public ShootAngleMoveDownCommand() {
		requires(Robot.shooterSubsystem);
	}

	// Called just before this Command runs the first time
	protected void initialize() {}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		
		Robot.shooterSubsystem.setShooterAdjustSpeed(-.5);
		
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		if (!Robot.oi.getShootAngleDownCommand()){
			Robot.shooterSubsystem.setShooterAdjustSpeed(0);
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
