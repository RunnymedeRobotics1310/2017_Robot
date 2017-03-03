
package robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import robot.Robot;
import robot.commands.auto.RotateToHeadingCommand;

/**
 *
 */
public class VisionTrackCommand extends Command {

	double degreeToTurn;
	double currentX;
	double distanceFromTower;
	double ultrasonicDistance, currentUltrasonicDistance;

	public VisionTrackCommand() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.chassisSubsystem);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		
//		ultrasonicDistance = Robot.chassisSubsystem.getUltrasonicDistance();
//		
//		currentX = Robot.oi.getVisionTargetCenterX();
//		
//		if (currentX > 0) {
//			degreeToTurn = calculateAngle(currentX); 
////			degreeToTurn += 3;
//		}
		
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		if (currentX > 0) {
			Scheduler.getInstance().add(new RotateToHeadingCommand(degreeToTurn));
		}
	}

	/**
	 * Calculate the angle at which the robot should turn to 
	 * so that the midpoint of the reflective tapes are exactly in center
	 * @param xValue
	 * @return
	 */
	protected double calculateAngle(double xValue) {
		// Equation to get the angle at which we have to be in the center
//		double angle = -0.1033 * xValue + 16.398;
		
		//TODO: equation to get the angle at which we have to be in the
		double angle = -0.144 * xValue + 360.3;
		
		double currentAngle = Robot.chassisSubsystem.getGyroAngle();
	
		// If angle is less than 0, subtract from 360 and add it to the current angle do % 360
		if (angle < 0) {
			angle = 360 - angle;
			return (angle + currentAngle) % 360;
		}
		
		return Math.abs(currentAngle - angle % 360);
	
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		if (currentX > 0) {
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
