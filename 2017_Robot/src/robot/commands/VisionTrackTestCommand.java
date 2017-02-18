
package robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.Robot;
import robot.commands.auto.RotateToHeadingCommand;

/**
 *
 */
public class VisionTrackTestCommand extends Command {

	double degreeToTurn;
	double currentAvgX;
	double[] currentX;

	public VisionTrackTestCommand() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.chassisSubsystem);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		currentX = Robot.oi.getVisionTargetCenterX();
		if (currentX.length == 2) {
			currentAvgX = (currentX[0] + currentX[1]) / 2;
			degreeToTurn = calculateAngle(currentAvgX);
		}
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {

		if (currentX.length == 2) {
			Scheduler.getInstance().add(new RotateToHeadingCommand(degreeToTurn));
		}
	}

	protected double calculateAngle(double xValue) {
		double angle = -0.1033 * xValue + 16.398;
		
		double currentAngle = Robot.chassisSubsystem.getAngle();
		
	
		// If angle is less than 0, subtract from 360 and add it to the current angle do % 360
		if (angle < 0) {
			angle = 360 - angle;
			return (angle + currentAngle) % 360;
		}
		
		return Math.abs(currentAngle - angle % 360);
	
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		if (currentX.length != 2) {
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
