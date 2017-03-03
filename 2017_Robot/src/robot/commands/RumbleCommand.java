
package robot.commands;

import com.toronto.oi.T_OiController;

import edu.wpi.first.wpilibj.command.Command;
import robot.Robot;
import robot.RobotConst;
import robot.commands.DriveCommand.ButtonState;
import robot.subsystems.GearSubsystem.GearState;

/**
 * Rumble the controllers based on the timeout, strength and the controller
 */
public class RumbleCommand extends Command {

	public T_OiController controller;
	public double timeout, strength;

	public RumbleCommand(double strengh, double timeout, T_OiController controller) {
		// Use requires() here to declare subsystem dependencies
		this.strength = strengh;
		this.timeout = timeout;
		this.controller = controller;
	}

	// Called just before this Command runs the first time
	protected void initialize() {

	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		this.controller.setRumble(strength);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		if (timeSinceInitialized() > timeout) {
			this.controller.setRumble(0);
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
