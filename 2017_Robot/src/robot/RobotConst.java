package robot;

/**
 * The RobotConst is a set of Robot constants that are determined by measuring
 * the ouput of the robot.
 */
public class RobotConst {

	/** The current Robot number */
	// FIXME: Should be able to get this from the driver station.
	public static int ROBOT = 0;
	
	/** WpiLib compatible inverted indicator {@code boolean true} which can be 
	 * used to make the robot code more readable */
	public static final boolean INVERTED     = true;
	/** WpiLib compatible NOT inverted indicator {@code boolean false} which can be 
	 * used to make the robot code more readable */
	public static final boolean NOT_INVERTED = false;

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
    
 
    /** Ultrasonic Calibration values */
	public static final double ULTRASONIC_VOLTAGE_20IN = 0.183;
	public static final double ULTRASONIC_VOLTAGE_40IN = 0.376;
	public static final double ULTRASONIC_VOLTAGE_80IN = 0.767;
	
	/** Shooter **/
	public static final double SHOOTER_ENCODER_MAX_SPEED = 140;
	
	public static final double DEFAULT_SHOOTER_SPEED = 65;

	public static final double HOPPER_AGITATOR_SPEED = .5;
	
    // This static initializer is used to adjust the constants for the 
    // robot based on which robot is selected.
    static {
    	
    	//****************************
    	// SET THE ROBOT NUMBER HERE
    	//****************************
    	
    	ROBOT = 1311;
    	
    	// Robot 1310 is the production robot
    	if (ROBOT == 1310 || ROBOT == 1311) {
    	    
    		DRIVE_ENCODER_MAX_SPEED = 3300;
    	    DRIVE_ENCODER_COUNTS_PER_IN = 396;
    	    
    	    GYRO_PID_PROPORTIONAL_GAIN = 4.0;
    	    GYRO_PID_INTEGRAL_GAIN = .095;
    	    GYRO_PIVOT_MAX_SPEED = .35; 
    	    
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
