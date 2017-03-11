
package robot.commands.auto;

import edu.wpi.first.wpilibj.command.Command;
import robot.Robot;
import robot.RobotConst;
import robot.RobotConst.VisionDistance;

/**
 * The DriveOnHeading command is the base class for all of the auto commands as
 * it tracks a gyro heading.
 * <p>
 * This command should be extended for other types of auto drive commands.
 */
public class AutoVisionAlignCommand extends Command {

	protected enum Step {
		PAUSE, CALCULATE, ALIGN, DONE
	};

	private final VisionDistance visionDistance;
	protected final double timeout;

	protected Step step = Step.PAUSE;

	private double pauseStartTime;
	private double targetHeading = 0;
	private double calculateStartTime = 0;

	private double TARGET_CENTER_PIXELS_CLOSE = 128;
	private double TARGET_CENTER_PIXELS_FAR = 175;
	
	//y value for close: less than 50 (40 and 20)

	private boolean disableGyroPidWhenAligned = true;

	private boolean firstLoop = true;

	/**
	 * Align to the close or far vision target
	 * <p>
	 * This command finishes immediately if the vision target is not found
	 * <p>
	 * The command ends when the robot is aligned with the target.
	 * 
	 * @param visionDistance
	 *            CLOSE or FAR
	 */
	public AutoVisionAlignCommand(VisionDistance visionDistance) {
		this(visionDistance, 15.0, true);
	}

	/**
	 * Align to the close or far vision target
	 * <p>
	 * This command finishes immediately if the vision target is not found
	 * <p>
	 * The command ends when the robot is aligned with the target.
	 * 
	 * @param visionDistance
	 *            CLOSE or FAR
	 * @param timeout
	 */
	public AutoVisionAlignCommand(VisionDistance visionDistance, double timeout) {
		this(visionDistance, timeout, true);
	}

	/**
	 * Align to the close or far vision target
	 * <p>
	 * This command finishes immediately if the vision target is not found
	 * <p>
	 * The command ends when the robot is aligned with the target.
	 * 
	 * @param visionDistance
	 *            CLOSE or FAR
	 * @param timeout
	 *            before the command ends if alignment is not complete
	 */
	public AutoVisionAlignCommand(VisionDistance visionDistance, double timeout, boolean disableGyroPidWhenAligned) {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.chassisSubsystem);
		this.visionDistance = visionDistance;
		this.timeout = timeout;
		this.disableGyroPidWhenAligned = disableGyroPidWhenAligned;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		step = Step.PAUSE;
		pauseStartTime = timeSinceInitialized();
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {

		switch (step) {

		case PAUSE:

			if (disableGyroPidWhenAligned) {
				disableGyroPid();
			}

			// Wait for .25 seconds for the camera image to stabilize
			// Do nothing until the timer has expired.
			if ((timeSinceInitialized() - pauseStartTime) <= .25) {
				return;
			}

			step = Step.CALCULATE;
			calculateStartTime = timeSinceInitialized();

			return;

		case CALCULATE:
			// Determine the amount of movement required to align with the gyro

			double targetX = Robot.oi.getVisionTargetCenterX(visionDistance);

			if (firstLoop) {
				// Wait 2 seconds for the target on the first loop only
				// If more than 2 seconds, then no target is found.
				if (timeSinceInitialized() - calculateStartTime > 2.0) {
					step = Step.DONE;
					return;
				}

				if (targetX == -1) {
					return;
				}

			} else {

				// If more than .5 seconds, then no target is found.
				if (timeSinceInitialized() - calculateStartTime < 0.5) {
					if (targetX == -1) {
						return;
					}
				}

			}

			// Calculate the angle adjustment
			double adjustAngle = calculateAngle(targetX);

			System.out.println("Target X: " + targetX);
			System.out.println("Robot Angle: " + Robot.chassisSubsystem.getGyroAngle());
			System.out.println("Angle to turn: " + adjustAngle);

			// If we are properly aligned then we are done.
			if (Math.abs(adjustAngle) < 1.6) {
				step = Step.DONE;
				return;
			}

			// The target heading is the current heading plus the adjust angle
			targetHeading = Robot.chassisSubsystem.getGyroAngle() + adjustAngle;

			targetHeading %= 360;

			if (targetHeading > 360) {
				targetHeading -= 360;
			} else if (targetHeading < 0) {
				targetHeading += 360;
			}

			// Enable the PIDs to turn the robot
			enableGyroPid(targetHeading);
			step = Step.ALIGN;
			return;

		case ALIGN:

			// Set the motor speeds based on the PID output.
			// Get the PID output and use it to rotate the robot.
			double gyroPidOutput = Robot.chassisSubsystem.getGyroPidOutput();

			// Limit the PID output to the pivot speed so that the robot does
			// not speed up past the max pivot speed when turning.
			if (Math.abs(gyroPidOutput) > RobotConst.GYRO_PIVOT_MAX_SPEED) {
				gyroPidOutput = Math.signum(gyroPidOutput) * RobotConst.GYRO_PIVOT_MAX_SPEED;
			}

			double leftSpeed = gyroPidOutput;
			double rightSpeed = -gyroPidOutput;

			Robot.chassisSubsystem.setMotorSpeeds(leftSpeed, rightSpeed);

			double angleRate = Robot.chassisSubsystem.getGyroAngleRate();
			double angleError = Robot.chassisSubsystem.getGyroAngleError(targetHeading);

			// Wait for the PID to align the robot and stop when it gets there
			if (Math.abs(angleRate) < 3.0 && Math.abs(angleError) < 1.5) {

				// When at the target, stop the robot and
				// check the vision again by going to the Pause step
				// which will wait for the camera to settle
				if (disableGyroPidWhenAligned) {
					disableGyroPid();
				}

				step = Step.PAUSE;
				pauseStartTime = timeSinceInitialized();
				firstLoop = false;

				return;
			}

			// If not done, then continue waiting for alignment
			return;

		case DONE:
			// Nothing to do here
			return;

		}
	}

	@Override
	protected boolean isFinished() {
		
    	// Always check for operator cancel
    	if (Robot.oi.getCancel()) { return true; }

		// Check for a timeout
		if (timeSinceInitialized() > timeout) {
			return true;
		}

		return step == Step.DONE;
	}

	@Override
	protected void end() {
		if (disableGyroPidWhenAligned) {
			disableGyroPid();
			Robot.chassisSubsystem.setMotorSpeeds(0, 0);
		}
	}

	private void enableGyroPid(double heading) {
		// Enable the Gyro PID
		Robot.chassisSubsystem.setGyroPidSetpoint(heading);
		Robot.chassisSubsystem.enableGyroPid();

	}

	private void disableGyroPid() {
		// Disable the Gyro PID
		Robot.chassisSubsystem.disableGyroPid();
		Robot.chassisSubsystem.setMotorSpeeds(0, 0);
	}

	private double calculateAngle(double xValue) {

		if (xValue == -1) {
			return 0;
		}

		if (visionDistance == VisionDistance.FAR) {
			double error = TARGET_CENTER_PIXELS_FAR - xValue;
			return error * -0.17;
		} else {
			double error = TARGET_CENTER_PIXELS_CLOSE - xValue;
			return error * -0.15;
		}

	}

}
