
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
public class ShootAngleAdjustCommand extends Command {
	private static double moveAmount = 0;
	private static double startEncoder = 0;
	private static double adjustSpeed = 0;
	public ShootAngleAdjustCommand(double moveAmount, double adjustSpeed) {
		this.moveAmount = moveAmount;
		this.adjustSpeed = Math.abs(adjustSpeed);
		startEncoder = Robot.shooterSubsystem.getCurrentEncoder();
		// Use requires() here to declare subsystem dependencies
		requires(Robot.shooterSubsystem);
	}

	// Called just before this Command runs the first time
	protected void initialize() {}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		if (moveAmount < 0) {
			Robot.shooterSubsystem.setShooterAdjustSpeed(adjustSpeed);
		}
    	else if (moveAmount > 0){
			Robot.shooterSubsystem.setShooterAdjustSpeed(-adjustSpeed);
    	}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		if(Math.abs(Robot.shooterSubsystem.getCurrentEncoder()) == Math.abs(startEncoder)+Math.abs(moveAmount))	
			return true;
		return false;
	}

	@Override
	protected void end() {
	}

	@Override
	protected void interrupted() {
	}
}
