
package robot.commands.shooter;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.Robot;
import robot.RobotConst;
import robot.RobotConst.VisionDistance;
import robot.oi.OI.Coordinate;

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
		Coordinate target = Robot.oi.getVisionTarget(VisionDistance.CLOSE);
		if (target != null) {
			
			// Determine the distance based on the yPixel value of the vision target.
			// The closer the robot is to the target, the higher the target will appear
			// in the image, and the lower the yPixel value (since the top of the screen
			// is row zero)
			
			double distance = RobotConst.SHOOTER_VISION_DISTANCE_SLOPE * target.y + RobotConst.SHOOTER_VISION_DISTANCE_B;
			
			// Once the distance is known, set the shooter speed based on the distance.
			double shooterSpeed = 0.2 * distance + 42.2;
			
			Robot.shooterSubsystem.setShooterSpeed(shooterSpeed);
			
			System.out.println("Auto Vision Shooter Speed: " + shooterSpeed);
			System.out.println("Auto Vision Distance: " + distance);
			
//				Robot.shooterSubsystem.setShooterSpeed(Robot.oi.getY() * 0.11 + 54.8);
			// old formula before march 29 tuesday
//				Robot.shooterSubsystem.setShooterSpeed(Robot.oi.getY() * 0.11 + 55.8);

		}
		else{
			Robot.shooterSubsystem.setShooterSpeed(shooterSpeed);
		}
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
			Robot.shooterSubsystem.startFeeder();
		} else {
			Robot.shooterSubsystem.stopFeeder();
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		
		// If the driver presses the shoot button, then this command ends
		if (Robot.oi.getShootButton()) { return true; }
		
		// Timeout
		return timeSinceInitialized() > timeout;
	}

	@Override
	protected void end() {
		
		// If the user is not holding the shoot button, then
		// shut down the shooter.
		if (!Robot.oi.getShootButton()) {
			Robot.shooterSubsystem.stopShooter();
			Robot.shooterSubsystem.stopFeeder();
			Robot.shooterSubsystem.stopAgitator();
			Robot.shooterSubsystem.closeShooterAngleAdjuster();
		}
	}

	@Override
	protected void interrupted() {
		end();
	}
}
