package robot;

/**
 * The RobotConst is a set of Robot constants that are determined by measuring
 * the ouput of the robot.
 */
public class RobotConst {

	/** The current Robot number */
	// FIXME: Should be able to get this from the driver station.
	public static int ROBOT = 1310;
	
	/** WpiLib compatible inverted indicator {@code boolean true} which can be 
	 * used to make the robot code more readable */
	public static final boolean INVERTED     = true;
	/** WpiLib compatible NOT inverted indicator {@code boolean false} which can be 
	 * used to make the robot code more readable */
	public static final boolean NOT_INVERTED = false;

	public enum Direction { FORWARD, BACKWARDS };

	/** 
	 * Drive Max Speed is the maximum encoder feedback rate that is received from the 
	 * drive encoders with an unloaded robot (robot on blocks).  This represents the 
	 * theoretical maximum speed of the robot.
	 */
    public static double DRIVE_ENCODER_MAX_SPEED = 1800;
    
    /** The measured encoder counts per in */

   
    public static double DRIVE_ENCODER_COUNTS_PER_IN = 3570.0d/200.0d;

    
    /** Gyro PID proportional gain */

    public static final double GYRO_PROPORTIONAL_GAIN = 2.0;

    public static double GYRO_PID_PROPORTIONAL_GAIN = 5.0;

    /** Gyro PID integral gain */

    public static final double GYRO_INTEGRAL_GAIN = .05;

    public static double GYRO_PID_INTEGRAL_GAIN = .1;
    
    /** Gyro in-place pivot speed used when under gyro control */
    public static double GYRO_PIVOT_MAX_SPEED = .65; 
    
    /** Drive PID proportional gain */
    public static double DRIVE_PID_PROPORTIONAL_GAIN = 1.0;
    /** Drive PID integral gain */
    public static double DRIVE_PID_INTEGRAL_GAIN = 0.0;
    
    /** Gyro Sensitivity for calibration of the rotational rate of the gyro */
    public static double GYRO_SENSITIVITY = .00172;
    
    public static double GYRO_COARSE_ADJUSTMENT_CUTOFF = 15.0d;
    
 
    /** Ultrasonic Calibration values */
	public static final double ULTRASONIC_VOLTAGE_20IN = 0.183;
	public static final double ULTRASONIC_VOLTAGE_40IN = 0.376;
	public static final double ULTRASONIC_VOLTAGE_80IN = 0.767;
	
	/** Shooter **/
	public static final double SHOOTER_ENCODER_MAX_SPEED = 140;
	
	public static final double DEFAULT_SHOOTER_SPEED = 60.8;

	public static final double HOPPER_AGITATOR_SPEED = .7;
	
	// Vision Tracking 
	public enum VisionDistance { CLOSE };
	
	public final static double SHOOTER_SPEED_CLOSE = 60.8;
	
	public final static int SHOOTER_ANGLE_ENCODER_COUNT_CLOSE = 13748;
	
	public final static int SHOOTER_ANGLE_ENCODER_UPPER_LIMIT = 25000;
	public final static int SHOOTER_ANGLE_ENCODER_LOWER_LIMIT = -25000;
	
	/*
	 * Calculate the slope used to translate the Y pixels from the 
	 * camera contour detection (height on the screen) to a
	 * distance measurement (using a y=mx+b type equation).

	 * shooterDistance = slope * yPixels + b
	 *
	 * In order to calibrate this equal
	 */
	// 50 and 130 orig
	public final static int SHOOTER_VISION_YPIXELS_74  = 50;  // Y pixel value at 74"
	public final static int SHOOTER_VISION_YPIXELS_133 = 130; // Y pixel value at 133"
	
	/** Slope = rise/run = change in distance / change in Y pixel value */
	public final static double SHOOTER_VISION_DISTANCE_SLOPE = 
			(133.0-74.0) / (double) (SHOOTER_VISION_YPIXELS_133 - SHOOTER_VISION_YPIXELS_74);

	/** b (Offset) = distance_133 - slope * yPixels_133 */
	public final static double SHOOTER_VISION_DISTANCE_B = 
			133.0 - (SHOOTER_VISION_DISTANCE_SLOPE * SHOOTER_VISION_YPIXELS_133);
	
    // This static initializer is used to adjust the constants for the 
    // robot based on which robot is selected.
    static {
    	
    	//****************************
    	// SET THE ROBOT NUMBER HERE
    	//****************************
    	
    	ROBOT = 1310;
    	
    	// Robot 1310 is the production robot
    	if (ROBOT == 1310 || ROBOT == 1311) {
    	    
    		DRIVE_ENCODER_MAX_SPEED = 3300;
    	    DRIVE_ENCODER_COUNTS_PER_IN = 396;
    	    
    	    GYRO_PID_PROPORTIONAL_GAIN = 4.8;
    	    GYRO_PID_INTEGRAL_GAIN = .3;
    	    GYRO_PIVOT_MAX_SPEED = .5; 
    	    
    	    DRIVE_PID_PROPORTIONAL_GAIN = 0.3;
    	    DRIVE_PID_INTEGRAL_GAIN = 0.0;
    	    
    	    GYRO_SENSITIVITY = .00161;
    	}

    	// Robot 1320 has smaller 3" wheels
    	if (ROBOT == 1320) {
    	    
    		DRIVE_ENCODER_MAX_SPEED = 1800;
    	    DRIVE_ENCODER_COUNTS_PER_IN = 3570.0d/200.0d;
    	    
    	    GYRO_PID_PROPORTIONAL_GAIN = 6.0;
    	    GYRO_PID_INTEGRAL_GAIN = .1;
    	    GYRO_PIVOT_MAX_SPEED = .7; 
    	    
    	    DRIVE_PID_PROPORTIONAL_GAIN = 1.0;
    	    DRIVE_PID_INTEGRAL_GAIN = 0.0;
    	    
    	    GYRO_SENSITIVITY = .00172;
    	    
    	}
    	
    	
    	if (ROBOT == 1307){
    		DRIVE_ENCODER_MAX_SPEED = 900;
    	    DRIVE_ENCODER_COUNTS_PER_IN = 73.18;
    	    
    	    GYRO_PID_PROPORTIONAL_GAIN = 3.0;
    	    GYRO_PID_INTEGRAL_GAIN = .15;
    	    GYRO_PIVOT_MAX_SPEED = .35; 
    	    
    	    DRIVE_PID_PROPORTIONAL_GAIN = 1.0;
    	    DRIVE_PID_INTEGRAL_GAIN = 0.0;
    	    
    	    GYRO_SENSITIVITY = .00161;
    	    
    	}
    	
    	// Robot 1321 has larger 6" wheels
    	if (ROBOT == 1321) {
    	    
    		DRIVE_ENCODER_MAX_SPEED = 900;
    	    DRIVE_ENCODER_COUNTS_PER_IN = 73.18;
    	    
    	    GYRO_PID_PROPORTIONAL_GAIN = 3.0;
    	    GYRO_PID_INTEGRAL_GAIN = .15;
    	    GYRO_PIVOT_MAX_SPEED = .35; 
    	    
    	    DRIVE_PID_PROPORTIONAL_GAIN = 1.0;
    	    DRIVE_PID_INTEGRAL_GAIN = 0.0;
    	    
    	    GYRO_SENSITIVITY = .00161;
    	    
    	}
    
    }
}
