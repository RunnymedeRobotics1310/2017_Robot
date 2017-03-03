
package robot.commands.auto;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.Robot;
import robot.RobotConst;

/**
 *  The DriveOnHeading command is the base class for all of the 
 *  auto commands as it tracks a gyro heading.
 *  <p>
 *  This command should be extended for other types of auto drive commands.
 */
public class RotateToHeadingCommand extends Command {

	private enum Step { COARSE, FINE };
	
	protected double heading;
	
	private double angleError;
	
	private double angleRate;
	
	private Step step = Step.COARSE;
	
	/**
	 * Drive on the specified heading at the specified speed.
	 * <p>
	 * This Command is used as the base for all Auto commands and 
	 * should be extended to stop using the isFinished method.
	 * @param heading to drive in degrees (0 to 360)
	 */
    public RotateToHeadingCommand(double heading) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.chassisSubsystem);
        this.heading  = heading;
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
    	
    	// Coarse adjustment is used if the error is > 30 degrees.  This will
    	// cause the robot to pivot before continuing on the selected direction.
    	step = Step.COARSE;
    	
    	double angleError = Robot.chassisSubsystem.getGyroAngleError(heading);
    	
    	if (Math.abs(angleError) < 25.0d) { 
    		step = Step.FINE;
    		enableGyroPid();
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {

    	double leftSpeed  = 0d;
    	double rightSpeed = 0d;
    	
    	angleError = Robot.chassisSubsystem.getGyroAngleError(heading);
    	
    	angleRate = Robot.chassisSubsystem.getGyroAngleRate();
    	
    	SmartDashboard.putNumber("heading", heading);
    	SmartDashboard.putNumber("angleError", angleError);
    	switch (step) {
    	
    	case COARSE:
    		
        	// In the coarse step, the robot pivots in place turning one wheel forward
        	// and the other wheel back until the robot angle is within the range
    		// to use the PID controller.  The PID controller is not used outside of this
    		// range because the idea is to have the robot travelling in the direction
    		// of the heading, so we do not want to move forward until the direction
    		// is approximately correct.
    		
    		// Turn off the pid control for the gyro.
    		Robot.chassisSubsystem.disableGyroPid();
    		
        	// Pivot based on the error direction
        	if (angleError > 0d) {
        		
        		// If the angle error is positive, then turn clockwise to close the error
        		leftSpeed  =   RobotConst.GYRO_PIVOT_MAX_SPEED;
        		rightSpeed = - RobotConst.GYRO_PIVOT_MAX_SPEED;
        		
        	} else {
        		
        		// If the angle error is negative, then turn counter clockwise to close the error
        		leftSpeed  = - RobotConst.GYRO_PIVOT_MAX_SPEED;
        		rightSpeed =   RobotConst.GYRO_PIVOT_MAX_SPEED;

        	}

        	if (Math.abs(angleError) < 30.0d) { 
        		step = Step.FINE;
        		enableGyroPid();
        	}

        	break;
			
    	case FINE:

    		// Get the PID output and use it to rotate the robot.
    		double gyroPidOutput = Robot.chassisSubsystem.getGyroPidOutput();
    		
    		// Limit the PID output to the pivot speed so that the robot does 
    		// not speed up past the max pivot speed when turning.
    		if (Math.abs(gyroPidOutput) > RobotConst.GYRO_PIVOT_MAX_SPEED) {
    			gyroPidOutput = Math.signum(gyroPidOutput) * RobotConst.GYRO_PIVOT_MAX_SPEED;
    		}
    		
    		leftSpeed  =  gyroPidOutput;
    		rightSpeed = -gyroPidOutput;
    		
    	}
    	
    	
    	Robot.chassisSubsystem.setMotorSpeeds(leftSpeed, rightSpeed);
    }

    
    private void enableGyroPid() {
    	
		// Enable the Gyro PID
		Robot.chassisSubsystem.enableGyroPid();
		Robot.chassisSubsystem.setGyroPidSetpoint(heading);
		
    }
    
    @Override
    public boolean isFinished() {
    	if(Math.abs(angleRate)<3 && Math.abs(angleError)<1.5){
    		return true;
    	}
    	return Robot.oi.getCancel();
    }
}
