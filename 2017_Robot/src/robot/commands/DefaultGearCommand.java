
package robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import robot.Robot;
import robot.RobotConst;
import robot.commands.DriveCommand.ButtonState;
import robot.subsystems.GearSubsystem.GearState;

/**
 * This default gear command is used to release or lock the gear in place
 */
public class DefaultGearCommand extends Command {

	ButtonState gearButtonState = ButtonState.RELEASED;

	public DefaultGearCommand() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.gearSubsystem);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		
		// Only open gear if robot is at tower
    	if (Robot.oi.getGearCommand() && Robot.chassisSubsystem.atTower()) {
    		Robot.gearSubsystem.open();
    	}
    	
    	//..and close it if the user no longer presses the button
    	if (!Robot.oi.getGearCommand() && !Robot.oi.getGearOverrideCommand()) {
    		Robot.gearSubsystem.close();
    	}
    	
    	// User can easily open/close gear using another button for emergency purposes, so this just lets that happen
    	if (Robot.oi.getGearOverrideCommand()) {
    		Robot.gearSubsystem.open();
    	}
    	
    	
		if (       Robot.gearSubsystem.getCurrentState() == GearState.OPEN
				&& Robot.chassisSubsystem.getSpeed() > 0.2 * RobotConst.DRIVE_ENCODER_MAX_SPEED) {
			Robot.gearSubsystem.close();
			return;
		}

	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return timeSinceInitialized() > 1.0;
	}

	@Override
	protected void end() {
	}

	@Override
	protected void interrupted() {
	}
}
