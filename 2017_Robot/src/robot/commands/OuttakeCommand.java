
package robot.commands;

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
public class OuttakeCommand extends Command {


	public OuttakeCommand() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.intakeSubsystem);
	}

	// Called just before this Command runs the first time
	protected void initialize() {}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		if (Robot.oi.getOuttakeToggleState()) {
    		Robot.intakeSubsystem.outtake();
    	}
    	else {
    		Robot.intakeSubsystem.stop();
    	}
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
