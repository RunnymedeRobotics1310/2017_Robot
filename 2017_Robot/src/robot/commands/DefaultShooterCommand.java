
package robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.Robot;
import robot.RobotConst;
import robot.RobotConst.VisionDistance;
import robot.oi.OI.Coordinate;

/**
 * Control the Shooter from the Joystick
 */
public class DefaultShooterCommand extends Command {

	private enum ShooterState { ON, OFF };
	
	private ShooterState shooterState = ShooterState.OFF;
	
	private double autoAlignStartTime = -1; 
	
	public DefaultShooterCommand() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.shooterSubsystem);
	}

	// Called just before this Command runs the first time
	protected void initialize() {}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		
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
		// Autostart the shooter and set the speed based on the distance for 5 seconds 
    	// after the auto align button was pressed.
		if (Robot.oi.getVisionTrackButton()) {
			autoAlignStartTime = System.currentTimeMillis();
    		Robot.oi.setShooterToggleState(true);
		}
		
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
				shooterState = ShooterState.ON;
			}

			// Auto adjust the shooter speed for 5 seconds after autoalign was last released.
			if (System.currentTimeMillis() - autoAlignStartTime < 5000) {

				Coordinate target = Robot.oi.getVisionTarget(VisionDistance.CLOSE);
				if (target != null) {
					
					// Determine the distance based on the yPixel value of the vision target.
					// The closer the robot is to the target, the higher the target will appear
					// in the image, and the lower the yPixel value (since the top of the screen
					// is row zero)
					
					double distance = RobotConst.SHOOTER_VISION_DISTANCE_SLOPE * target.y + RobotConst.SHOOTER_VISION_DISTANCE_B;
					
					// Once the distance is known, set the shooter speed based on the distance.
					double shooterSpeed = 0.2 * distance + 42.2;
					
					Robot.shooterSubsystem.setShooterSpeed(shooterSpeed);
					
					SmartDashboard.putNumber("Vision Shooter Speed Setpoint" , shooterSpeed);
					SmartDashboard.putNumber("Vision Old Shooter Speed Setpoint" , target.y * 0.11 + 56.4);
					
	//				Robot.shooterSubsystem.setShooterSpeed(Robot.oi.getY() * 0.11 + 54.8);
					// old formula before march 29 tuesday
	//				Robot.shooterSubsystem.setShooterSpeed(Robot.oi.getY() * 0.11 + 55.8);
	
				}
			}
			else {
				
				// Allow for manual adjustment of the shooter speed when not in auto align
				
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
			
			}

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
		if (Robot.oi.isShooterOn() && Robot.oi.getShootButton()) {
			Robot.shooterSubsystem.startAgitator();
			// Reverse agitator if they pressed the reverse agitator button
		} else if (Robot.oi.getReverseAgitator()) {
			Robot.shooterSubsystem.reverseAgitator();
		} else {
			// Stop agitator
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
