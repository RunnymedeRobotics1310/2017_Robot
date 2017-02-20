
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
		requires(Robot.chassisSubsystem);

	}

	// Called just before this Command runs the first time
	protected void initialize() {}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		double speed = Robot.oi.fixAngle();
		//		if(Math.abs(speed) > 0.2){
		//			Robot.shooterSubsystem.runAdjust(speed);
		//		}
		// Shooter
		if(Robot.oi.getResetShootAdjustEncoder()){
			Robot.shooterSubsystem.resetShootAdjustEncoder();
		}
		if(Robot.oi.getShootIntakeTrigger()){
			Scheduler.getInstance().add(new ShootIntakeCommand());
		}
		if(Robot.oi.getShootAngleDownCommand()){
			Scheduler.getInstance().add(new ShootAngleMoveDownCommand());
		}
		if(Robot.oi.getShootAngleUpCommand()){
			Scheduler.getInstance().add(new ShootAngleMoveUpCommand());
		}
		if(Robot.oi.getChangeSpeedDown()){
			Robot.shooterSubsystem.setShootSpeed(Robot.shooterSubsystem.shootSpeedSetpoint -.001);
		}
		if(Robot.oi.getChangeSpeedUp()){
			Robot.shooterSubsystem.setShootSpeed(Robot.shooterSubsystem.shootSpeedSetpoint +.001);
		}
		if (Robot.oi.getShooterToggleState()) {
			double ultrasonicDistance = Robot.chassisSubsystem.ultrasonicSensor.getDistance();
			if(ultrasonicDistance > 0/*between two values*/){
				Robot.shooterSubsystem.setShootSpeed(Robot.shooterSubsystem.shootSpeedSetpoint);
				Scheduler.getInstance().add(new ShootAngleAdjustCommand(5,0.1));

			}
			if(ultrasonicDistance > 1/*between two values*/){
				Robot.shooterSubsystem.setShootSpeed(Robot.shooterSubsystem.shootSpeedSetpoint);
				Scheduler.getInstance().add(new ShootAngleAdjustCommand(45,0.1));

			}
			
			Robot.shooterSubsystem.setShootSpeed(Robot.shooterSubsystem.shootSpeedSetpoint);
		}
		if(!Robot.oi.getShooterToggleState()) {
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
