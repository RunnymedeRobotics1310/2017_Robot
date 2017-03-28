
package robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import robot.Robot;
import robot.RobotConst;

/**
 * Control the Shooter from the Joystick
 */
public class DefaultShooterCommand extends Command {

	private enum ShooterState { ON, OFF };
	
	private ShooterState shooterState = ShooterState.OFF;
	
	public DefaultShooterCommand() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.shooterSubsystem);
	}

	// Called just before this Command runs the first time
	protected void initialize() {}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		
		if (Robot.oi.getVisionTrackButton()) {
			shooterState = ShooterState.ON;
		}
		
    	// Always check for operator cancel
    	if (Robot.oi.getCancel()) {
    		Robot.oi.setShooterToggleState(false);
			Robot.shooterSubsystem.stopShooter();
			Robot.shooterSubsystem.closeShooterAngleAdjuster();
			shooterState = ShooterState.OFF;
    		return; 
		}

		//*******************************************
		// Shooter Speed Control 
		//*******************************************
		if(!Robot.oi.isShooterOn()) {
			
			if (shooterState == ShooterState.ON) {
				Robot.shooterSubsystem.stopShooter();
				Robot.shooterSubsystem.closeShooterAngleAdjuster();
			}
			shooterState = ShooterState.OFF;
			
		} else {
			
			// If the shooter was previously off, then start
			// the shooter at the last setpoint.
			if (shooterState == ShooterState.OFF) {
				Robot.shooterSubsystem.startShooter();
				Robot.shooterSubsystem.openShooterAngleAdjuster();
			}
			
			// Adjust the speed up or down based on user input
			// Only do this when the shooter is running.
			double shooterSpeedSetpoint = 
					Robot.shooterSubsystem.getShooterSpeedSetpoint();
			
			// Adjust the speed down until a minimum of zero
			if(Robot.oi.getChangeSpeedDown()) {
				shooterSpeedSetpoint = 
						Math.max(0.0, shooterSpeedSetpoint - .05);
				Robot.shooterSubsystem.setShooterSpeed(shooterSpeedSetpoint);
			}
			
			// Adjust the speed up until the max shooter speed
			if(Robot.oi.getChangeSpeedUp()) {
				shooterSpeedSetpoint = 
						Math.min(RobotConst.SHOOTER_ENCODER_MAX_SPEED, shooterSpeedSetpoint + .05);
				Robot.shooterSubsystem.setShooterSpeed(shooterSpeedSetpoint);
			}
			
			shooterState = ShooterState.ON;
		}
		
		//*******************************************
		// Update the shooter angle 
		//*******************************************
		if (shooterState == ShooterState.OFF) {

			// Reset the shooter angle encoder
			if(Robot.oi.getResetShooterAdjustEncoder()){
				Robot.shooterSubsystem.resetShooterAngleAdjustEncoder();
			}
		}
		
		double speed = Robot.oi.getShooterAngleAdjustmentSpeed();
		if(Math.abs(speed) > 0.2){
			Robot.shooterSubsystem.setShooterAngleAdjustSpeed(speed);
		} else {
			Robot.shooterSubsystem.setShooterAngleAdjustSpeed(0);
		}
		
		//*******************************************
		// Take a shot - Feeder
		//*******************************************
		// Shooter must be up to speed to feed balls
		if (       Robot.oi.isShooterOn() 
				&& Robot.oi.getShootButton()
//				&& Robot.shooterSubsystem.isShooterAtSpeed()
				) 
		{
			Robot.shooterSubsystem.startFeeder();
		} else {
			Robot.shooterSubsystem.stopFeeder();
		}
		
		//*******************************************
		// Agitator
		//*******************************************
		if (       Robot.oi.isShooterOn() 
				&& Robot.oi.getShootButton()) {
			Robot.shooterSubsystem.startAgitator();
		} else {
			Robot.shooterSubsystem.stopAgitator();
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
