
package robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import robot.Robot;
import robot.RobotConst.VisionDistance;

/**
 * THIS COMMAND IS FOR TESTING PURPOSE.
 * WHAT IT DOES: auto receive pixel at degree until robot is out of frame and use that to come up with an equation
 */
public class TestVisionGetDataCommand extends Command {

	public double startingAngle, startingPixel, currentAngle, currentPixel;

	public TestVisionGetDataCommand() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.chassisSubsystem);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		startingAngle = Robot.chassisSubsystem.getGyroAngle();
		startingPixel = Robot.oi.getVisionTargetCenterX(VisionDistance.CLOSE);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		
		
		currentAngle = Robot.chassisSubsystem.getGyroAngle();
		currentPixel = Robot.oi.getVisionTargetCenterX(VisionDistance.CLOSE);
		
		if (timeSinceInitialized() % 1 == 0) {
			System.out.println("Current Angle: " + currentAngle);
			System.out.println("Current Pixel: " + currentPixel);
		}
		
		// Go right
		Robot.chassisSubsystem.setMotorSpeeds(0.3, -0.3);
		
		// Go left
//		Robot.chassisSubsystem.setMotorSpeeds(-0.3, 0.3);
		
	
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return timeSinceInitialized() > 15;
	
	}

	@Override
	protected void end() {
	}

	@Override
	protected void interrupted() {
	}
}
