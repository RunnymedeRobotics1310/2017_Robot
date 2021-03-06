
package robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.Robot;
import robot.commands.drive.DriveToUltrasonicDistanceCommand;
import robot.commands.drive.RotateToHeadingCommand;
import robot.commands.shooter.AutoShootAngleAdjustCommand;
import robot.oi.AutoSelector.BoilerPosition;

/**
 * Drive command handles all commands related to driving Such as resetting
 * encoders and driving straight
 */
public class DefaultDriveCommand extends Command {

	enum ButtonState {
		PRESSED, RELEASED
	};

	ButtonState driveStraightState = ButtonState.RELEASED;
	ButtonState povState = ButtonState.RELEASED;
	ButtonState calibrateState = ButtonState.RELEASED;
	ButtonState nudgeState = ButtonState.RELEASED;

	BoilerPosition boilerPosition;

	public DefaultDriveCommand() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.chassisSubsystem);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		boilerPosition = Robot.oi.autoSelector.getBoilerPostion();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {

		// Always check for operator cancel
		if (Robot.oi.getCancel()) {
			Robot.oi.setDriverRumble(0);
			Robot.chassisSubsystem.disableDrivePids();
			Robot.chassisSubsystem.setMotorSpeeds(0, 0);
			return;
		}

		switch (povState) {
		case RELEASED:
			double angle = Robot.oi.getRotateToAngle();
			if (angle >= 0) {
				Scheduler.getInstance().add(new RotateToHeadingCommand(angle));
				povState = ButtonState.PRESSED;
				return;
			}
			break;
		case PRESSED:
			if (Robot.oi.getRotateToAngle() < 0) {
				povState = ButtonState.RELEASED;
			}
			break;
		}

		if (Robot.oi.getDriverHighGear()) {
			Robot.chassisSubsystem.setHighGear();
			Robot.oi.setDriverRumble(0.8);
		} else {
			Robot.chassisSubsystem.setLowGear();
			Robot.oi.setDriverRumble(0);
		}

		switch (calibrateState) {
		case RELEASED:
			if (Robot.oi.getCalibrate()) {
				Robot.chassisSubsystem.resetEncoders();
				Robot.chassisSubsystem.calibrateGyro();
				calibrateState = ButtonState.PRESSED;
			}
			break;
		case PRESSED:
			if (!Robot.oi.getCalibrate()) {
				calibrateState = ButtonState.RELEASED;
			}
		}

		switch (nudgeState) {
		case RELEASED:
			if (Robot.oi.getNudgeLeft()) {
				Scheduler.getInstance().add(new RotateToHeadingCommand(Robot.chassisSubsystem.getGyroAngle() - 6));
				nudgeState = ButtonState.PRESSED;
				return;
			} else if (Robot.oi.getNudgeRight()) {
				Scheduler.getInstance().add(new RotateToHeadingCommand(Robot.chassisSubsystem.getGyroAngle() + 6));
				nudgeState = ButtonState.PRESSED;
				return;
			}
			break;
		case PRESSED:
			if (!Robot.oi.getNudgeLeft() && !Robot.oi.getNudgeRight()) {
				nudgeState = ButtonState.RELEASED;
			}
			break;

		}

		// Set the robot to the gear load distance
		// Use the current robot angle as the drive angle
		if (Robot.oi.gearLoadingDistanceButton()) {
			Scheduler.getInstance().add(new DriveToUltrasonicDistanceCommand(Robot.chassisSubsystem.getGyroAngle(), .3,
					16.0, Robot.chassisSubsystem.getUltrasonicSensor()));
		}

		// Turn on or off the PIDs
		if (Robot.oi.getMotorPidEnabled()) {
			Robot.chassisSubsystem.enableDrivePids();
		} else {
			Robot.chassisSubsystem.disableDrivePids();
		}

		double speed = Robot.oi.getSpeed();
		if (Math.abs(speed) <= .02) {
			speed = 0;
		}

		double turn = Robot.oi.getTurn();
		if (Math.abs(turn) <= .02) {
			turn = 0;
		}

		double leftSpeed = 0.0;
		double rightSpeed = 0.0;

		// If the robot is not moving forward or backwards and there is a
		if (speed == 0.0) {
			leftSpeed = turn;
			rightSpeed = -turn;
		} else {
			if (speed > 0) {
				if (turn == 0) {
					leftSpeed = speed;
					rightSpeed = speed;
				} else if (turn < 0) {
					rightSpeed = speed;
					leftSpeed = (1.0 + turn) * speed;
				} else if (turn > 0) {
					leftSpeed = speed;
					rightSpeed = (1.0 - turn) * speed;
				}
			}
			if (speed < 0) {
				if (turn == 0) {
					leftSpeed = speed;
					rightSpeed = speed;
				} else if (turn < 0) {
					rightSpeed = (1.0 + turn) * speed;
					leftSpeed = speed;
				} else if (turn > 0) {
					leftSpeed = (1.0 - turn) * speed;
					rightSpeed = speed;
				}
			}
		}

		/*
		 * NOTE: Once the vision tracking is started, none of the other buttons above
		 *       will work until the vision tracking ends.
		 *       The only ways to end vision tracking are to wait for the timeout
		 *       or move the robot using the joystick
		 *       See: VisionTrackCommand

		 * NOTE: The shooter speed adjustment based on the vision tracking is 
		 *       moved to the DefaultShooterCommand      
		 */
		if (Robot.oi.getVisionTrackButton()) {
			
			Scheduler.getInstance().add(new AutoShootAngleAdjustCommand(13748));
			Scheduler.getInstance().add(new VisionTrackCommand(5));
			
		}

		if (Robot.oi.testDriveBack()) {
			Scheduler.getInstance().add(new AutoShootAngleAdjustCommand(13748));
		}
		

		SmartDashboard.putNumber("Robot Speed", (leftSpeed + rightSpeed) / 2);

		Robot.chassisSubsystem.setMotorSpeeds(leftSpeed, rightSpeed);
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
