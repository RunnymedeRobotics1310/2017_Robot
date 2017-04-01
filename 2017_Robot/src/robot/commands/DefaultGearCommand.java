
package robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import robot.Robot;
import robot.RobotConst;
import robot.commands.DefaultDriveCommand.ButtonState;
import robot.subsystems.GearSubsystem.GearState;

/**
 * This default gear command is used to release or lock the gear in place
 */
public class DefaultGearCommand extends Command {

	ButtonState gearButtonState = ButtonState.RELEASED;
	boolean hasGear = false;

	public DefaultGearCommand() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.gearSubsystem);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {

    	// Always check for operator cancel
    	if (Robot.oi.getCancel()) {
    		Robot.gearSubsystem.close();
    		return; 
		}

//		// Rumble driver and operator controller for 1 second if there is a gear
		if (!hasGear && Robot.gearSubsystem.getGearSensor().atLimit()) {
			
			Robot.oi.setDriverRumble(1, 1);
			Robot.oi.setOperatorRumble(1, 1);
			hasGear = true;
		}
		
		// If robot doesn't have the gear, make the hasGear false
		if (!Robot.gearSubsystem.getGearSensor().atLimit()) {
			hasGear = false;
		}
		
		// Only open gear if robot is at tower
    	if (Robot.oi.getGearCommand() && Robot.chassisSubsystem.atTower() && Robot.chassisSubsystem.getSpeed() >= 0) {
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
    	
    	// Open / close the flap
    	if (Robot.oi.getGearFlapCommand()) {
    		Robot.gearSubsystem.openFlap();
    	} else {
    		Robot.gearSubsystem.closeFlap();
    	}
    	
    	
		if (       Robot.gearSubsystem.getCurrentState() == GearState.OPEN
				&& Robot.chassisSubsystem.getSpeed() > 0.2 * RobotConst.DRIVE_ENCODER_MAX_SPEED) {
			Robot.gearSubsystem.close();
			return;
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
