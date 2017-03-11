package robot.commands.drive;

import com.toronto.sensors.T_UltrasonicSensor;

import robot.Robot;
import robot.RobotConst.Direction;

public class DriveToUltrasonicDistanceCommand extends DriveOnHeadingCommand {

	private static final double STOPPING_DISTANCE = 1.0; // Stopping distance in inches

	private final double setpointDistance;
	private final T_UltrasonicSensor ultrasonicSensor;
	private final boolean isFrontMounted;

	private Direction direction;

    public DriveToUltrasonicDistanceCommand(double heading, double speed, double setpointDistance,
    		T_UltrasonicSensor ultrasonicSensor) {
    	// the default is that the ultrasonic is mounted on the front of the robot.
    	this(heading, speed, setpointDistance, ultrasonicSensor, true);
    }

    public DriveToUltrasonicDistanceCommand(double heading, double speed, double setpointDistance, 
    		T_UltrasonicSensor ultrasonicSensor, boolean isFrontMounted) {
    	
    	super(heading, speed);
    	this.setpointDistance = setpointDistance;
    	this.ultrasonicSensor = ultrasonicSensor;
    	this.isFrontMounted   = isFrontMounted;

    	//FIXME: Calculate the stopping distance based on the speed.

    }

    @Override
    protected void initialize() {
    	
    	super.initialize();
    	
    	// Where am I now?
    	double currentDistance = ultrasonicSensor.getDistance();
    	
    	// Check whether to go forward or backward to get to the desired distance depending on
    	// whether the ultrasonic is in the front or rear of the robot
    	if (isFrontMounted) {
    		
	    	if (currentDistance >= setpointDistance) {
	    		
	    		direction=Direction.FORWARD;
	    		
	    	} else {

	    		direction = Direction.BACKWARDS;
	    	
	    	}
	    	
    	} else {
    		
    		// Sensor is rear mounted

    		if (currentDistance <= setpointDistance) {
	    		
	    		direction=Direction.FORWARD;
		    	
		    	} else  {
		    	
	    		direction = Direction.BACKWARDS;
	    	}
    	}
    		
    	System.out.println("Drive " + direction + " from distance "  
    			+ currentDistance + " to " + setpointDistance);

    	// Set the direction
    	super.setDirection(direction);
    	
    }
    
	@Override
	protected boolean isFinished() {
		
    	// Always check for operator cancel
    	if (Robot.oi.getCancel()) { return true; }

		// Stop if you are there
		double currentDistance = ultrasonicSensor.getDistance();

		if (isFrontMounted) {
			
			switch (direction) {
			
			case FORWARD:
	
		    	if (currentDistance <= setpointDistance + STOPPING_DISTANCE) {
					System.out.println("Finished distance " + currentDistance);
		    		return true;
		    	}

		    	break;
			
			case BACKWARDS:
				
				if (currentDistance >= setpointDistance - STOPPING_DISTANCE) {
					System.out.println("Finished distance " + currentDistance);
					return true;
				} 
				break;
		
			default:
				System.out.println("Unknown direction " + direction);
				break;
			}
			
		} else {
		
			// The sensor is rear mounted
			switch (direction) {
			
			case FORWARD:
	
		    	if (currentDistance >= setpointDistance - STOPPING_DISTANCE) {
					System.out.println("Finished distance " + currentDistance);
		    		return true;
		    	}

		    	break;
			
			case BACKWARDS:
				
				if (currentDistance <= setpointDistance + STOPPING_DISTANCE) {
					System.out.println("Finished distance " + currentDistance);
					return true;
				} 
					
			default:
				System.out.println("Unknown direction " + direction);
				break;
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


