
package robot.commands.auto;

import edu.wpi.first.wpilibj.command.Command;
import robot.Robot;
import robot.subsystems.GearSubsystem.GearState;

/**
 * This command releases the gear
 */
public class DropGearCommand extends Command {

	private enum Step { OPEN, BACK, CLOSE, FORWARD, LEAVE, DONE; };
	
	Step step = Step.OPEN;
	
	long startTime;
	
    public DropGearCommand() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.gearSubsystem);
        requires(Robot.chassisSubsystem);
    }

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.chassisSubsystem.resetEncoders();
		startTime = System.currentTimeMillis();
	}

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

    	switch (step) {
    	case OPEN:
    		Robot.gearSubsystem.open();
    		if (System.currentTimeMillis() - startTime > 100) {
    			step = Step.BACK;
    			Robot.chassisSubsystem.resetEncoders();
    		}
    		return;
    	case BACK:
    		Robot.chassisSubsystem.setMotorSpeeds(-.4, -.4);
    		if (Robot.chassisSubsystem.getEncoderDistanceInches() < -3) {
    			Robot.chassisSubsystem.setMotorSpeeds(0, 0);
    			step = Step.CLOSE;
    			startTime = System.currentTimeMillis();
    		}
    		return;
    	case CLOSE:
    		Robot.gearSubsystem.close();
    		if (System.currentTimeMillis() - startTime > 100) {
    			step = Step.FORWARD;
    			Robot.chassisSubsystem.resetEncoders();
    			startTime = System.currentTimeMillis();
    		}
    		return;
    	case FORWARD:
    		Robot.chassisSubsystem.setMotorSpeeds(.4, .4);
    		if (   Robot.chassisSubsystem.getEncoderDistanceInches() > 12
    			|| System.currentTimeMillis() - startTime > 500) {
    			Robot.chassisSubsystem.setMotorSpeeds(0, 0);
    			step = Step.LEAVE;
    			startTime = System.currentTimeMillis();
    		}
    		return;
    	case LEAVE:
    		Robot.chassisSubsystem.setMotorSpeeds(-.5, -.5);
    		if (System.currentTimeMillis() - startTime > 1000) {
    			step = Step.DONE;
    		}
    		return;
    	default:
    		step = Step.DONE;
    	}
    	
    }

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		
    	// Always check for operator cancel
    	if (Robot.oi.getCancel()) { return true; }

    	if (Robot.oi.isDriverJoystickAction()) { return true; }
    	
		return step == Step.DONE;
	}

	@Override
	protected void end() {
	}

	@Override
	protected void interrupted() {
	}
}
