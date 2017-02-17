
package robot.commands;

import javax.xml.bind.annotation.XmlEnumValue;

import com.toronto.oi.T_OiController;
import com.toronto.oi.T_Toggle;

import edu.wpi.first.wpilibj.command.Command;
import robot.Robot;
import robot.RobotConst;
import robot.commands.JoystickCommand.ButtonState;
import robot.subsystems.GearSubsystem.GearState;

/**
 *
 */
public class VisionTrackTestCommand extends Command {

	// ButtonState intakeButtonState = ButtonState.RELEASED;
	// T_Toggle toggle = new T_Toggle(Robot.oi.driver, button, start)

	public VisionTrackTestCommand() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.chassisSubsystem);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {

		double minX = 131;
		double minY = 145;
		double maxX = 192;
		double maxY = 157;
		double distance = 32;

		double currentX = Robot.oi.getVisionTargetCenterX();
		double currentY = Robot.oi.getVisionTargetCenterY();

		// if (Robot.oi.getVisionTrackButton()) {

		// // Means it's centered...

		boolean xCentered = (currentX >= minX && currentX <= maxX);
		boolean yCentered = (currentY >= minY && currentY <= maxY);

		double leftSpeed = 0, rightSpeed = 0;
		System.out.println(xCentered + " " + yCentered);
		if (!xCentered) {
			if (currentX < minX) {
				leftSpeed = -0.15;
				rightSpeed = 0.15;
			} else if (currentX > maxX) {
				leftSpeed = 0.15;
				rightSpeed = -0.15;
			} else {
				leftSpeed = 0;
				rightSpeed = 0;
			}
		}
		Robot.chassisSubsystem.setMotorSpeeds(leftSpeed, rightSpeed);
		// }
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
