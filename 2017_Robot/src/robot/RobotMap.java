package robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {

	// PWM Ports
	public static final int LEFT_MOTOR_PWM_PORT = 0;
	public static final int RIGHT_MOTOR_PWM_PORT = 1;

	public static final int INTAKE_PWM_PORT = 1;
	public static final int SHOOTER_MOTOR_PWM_PORT = 2;
	public static final int SHOOTER_FEEDER_MOTOR_PWM_PORT = 3;
	public static final int HOPPER_AGITATOR_MOTOR_PORT = 4;
	
	public static final int HOPPER_FLAP_SERVO_PORT = 5;

	// Digital IO Ports
	public static final int LEFT_ENCODER_A_DIO_PORT = 0;
	public static final int LEFT_ENCODER_B_DIO_PORT = 1;

	public static final int RIGHT_ENCODER_A_DIO_PORT = 2;
	public static final int RIGHT_ENCODER_B_DIO_PORT = 3;

	public static final int FRONT_LIMIT_SWITCH_DIO_PORT = 1;

	public static final int FRONT_GEAR_SWITCH_DIO_PORT = 3;

	public static final int SHOOTER_SPEED_ENCODER_DIO_PORT = 2;
	

	// Analog Input Ports
	public static final int GYRO_ANALOG_INPUT_PORT = 0;

	// Relay Ports
	public static final int SPOTLIGHT_RELAY_PORT = 1;

	// Can bus Addresses
	public static final int POWER_DISTRIBUTION_CAN_ADDRESS = 0;
	public static final int LEFT_MOTOR_CAN_ADDRESS = 1;
	public static final int RIGHT_MOTOR_CAN_ADDRESS = 2;
	public static final int CANLIGHT_CAN_ADDRESS = 39;

	public static final int SHOOTER_ANGLE_MOTOR_CAN_ADDRESS = 5;

	// Pneumatic Solenoids
	public static final int SHIFTER_SOLENOID = 0;
	public static final int GEAR_SOLENOID_A = 1;
	public static final int GEAR_SOLENOID_B = 2;
	
	// Pneumatic Solenoids for Gear flap
	public static final int GEAR_FLAP_SOLENOID_A = 5;
	public static final int GEAR_FLAP_SOLENOID_B = 4;

	// Power Distribution Ports
	public static final int AGITATOR_POWER_PORT = 8;

}
