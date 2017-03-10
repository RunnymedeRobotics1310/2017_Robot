package robot.commands.drive;

import robot.Robot;
import robot.RobotConst.Direction;

public class DriveToUltrasonicDistanceCommand extends DriveOnHeadingCommand {

	private double setpointDistance;
	private Direction direction;
	
    public DriveToUltrasonicDistanceCommand(double heading, double speed, double setpointDistance) {
    	
    	super(heading, speed);
    	this.setpointDistance = setpointDistance;
    	direction = Direction.FORWARD;
    }

    @Override
    protected void initialize() {
    	
    	super.initialize();
    	
    	// Where am I now?
    	double currentDistance = Robot.chassisSubsystem.ultrasonicSensor.getDistance();
    	
    	// Check whether to go forward or backward to get to the desired distance
    	if (currentDistance >= setpointDistance)
    	{
    		System.out.println("BACKWARDS");
    		direction = Direction.BACKWARDS;
    	}
    	else{
    		System.out.println("FORWARD");
    		direction=Direction.FORWARD;
    	}
    		
    	// Set the direction
    	super.setDirection(direction);   	    
    	
    }
    
	@Override
	protected boolean isFinished() {
		
    	// Always check for operator cancel
    	if (Robot.oi.getCancel()) { return true; }

		// Stop if you are there (beware of direction)
		double currentDistance = Robot.chassisSubsystem.ultrasonicSensor.getDistance();
		if (direction==Direction.BACKWARDS)
		{
			if (currentDistance<=setpointDistance)
			{
				System.out.println("Finished");
				return true;
			}
			else
			{
				return false;
			}
		}
		else if (direction==Direction.FORWARD)
		{
			if (currentDistance>=setpointDistance)
			{
				System.out.println("Finished");
				return true;
			}
			else
			{
				return false;
			}
					
		}
		return false;	
		
	}		
	
	@Override
	public void end() {
		// put the stopping code here
		Robot.chassisSubsystem.setMotorSpeeds(0, 0);
	}

}


