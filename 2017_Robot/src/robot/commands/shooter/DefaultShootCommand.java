
package robot.commands.shooter;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import robot.Robot;

/**
 *
 */
public class DefaultShootCommand extends Command {
	

	public DefaultShootCommand() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.shooterSubsystem);
	}

	// Called just before this Command runs the first time
	protected void initialize() {}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		
		// Shooter
    	if(Robot.oi.getShootIntakeTrigger()){
    		Scheduler.getInstance().add(new ShootIntakeCommand());
    	}
    	if(Robot.oi.getShootAngleDownCommand()){
    		Scheduler.getInstance().add(new ShootAngleMoveDownCommand());
    	}
    	if(Robot.oi.getShootAngleUpCommand()){
    		Scheduler.getInstance().add(new ShootAngleMoveUpCommand());
    	}
    	
		if (Robot.oi.getShooterToggleState()) {
    		Robot.shooterSubsystem.startSpin();
    	}
    	else {
    		Robot.shooterSubsystem.shootStop();
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
