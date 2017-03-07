
package robot.commands.shooter;

import edu.wpi.first.wpilibj.command.Command;
import robot.Robot;

/**
 * Control the Shooter from the Joystick
 */
public class AutoShootCommand extends Command {

	private final double shooterSpeed;
	private final int    shooterAngleAdjustSetpoint;
	private final double timeout;
	
	public AutoShootCommand(double shooterSpeed, int shooterAngleAdjustSetpoint, double timeout) {
		requires(Robot.shooterSubsystem);
		this.shooterSpeed               = shooterSpeed;
		this.shooterAngleAdjustSetpoint = shooterAngleAdjustSetpoint;
		this.timeout                    = timeout;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.shooterSubsystem.setShooterSpeed(shooterSpeed);
		Robot.shooterSubsystem.setShooterAngleAdjustSetpoint(shooterAngleAdjustSetpoint);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {

		// Wait for the angle to be adjusted in order to shoot
		if (!Robot.shooterSubsystem.atShooterAngleAdjustSetpoint()) {
			Robot.shooterSubsystem.stopFeeder();
			Robot.shooterSubsystem.stopAgitator();
			return;
		}
		
		//*******************************************
		// Take a shot - Feeder
		//*******************************************
		// Shooter must be up to speed to feed balls
		if (Robot.shooterSubsystem.isShooterAtSpeed()) {
			Robot.shooterSubsystem.startAgitator();
			Robot.shooterSubsystem.setFeederSpeed(.4);
		} else {
			Robot.shooterSubsystem.stopFeeder();
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return timeSinceInitialized() > timeout;
	}

	@Override
	protected void end() {
		Robot.shooterSubsystem.stopShooter();
		Robot.shooterSubsystem.stopFeeder();
		Robot.shooterSubsystem.stopAgitator();
	}

	@Override
	protected void interrupted() {
		Robot.shooterSubsystem.stopShooter();
		Robot.shooterSubsystem.stopFeeder();
		Robot.shooterSubsystem.stopAgitator();
	}
}
