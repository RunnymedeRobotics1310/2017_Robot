
package robot.subsystems;

import com.ctre.CANTalon;
import com.toronto.pid.T_MotorSpeedPidController;
import com.toronto.sensors.T_CounterEncoder;
import com.toronto.sensors.T_Encoder;
import com.toronto.subsystems.T_Subsystem;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.RobotConst;
import robot.RobotMap;
import robot.commands.DefaultShooterCommand;
import robot.sensors.T_SrxEncoder;

public class ShooterSubsystem extends T_Subsystem {

	/*
	 * *************************************************************************
	 * Hardware declarations
	 * 
	 * Declare all motors and sensors here
	 ******************************************************************************/
	private SpeedController shooterMotor         = new VictorSP(RobotMap.SHOOTER_MOTOR_PWM_PORT);
	private SpeedController shooterFeederMotor   = new VictorSP(RobotMap.SHOOTER_FEEDER_MOTOR_PWM_PORT);
	private SpeedController shooterAngleMotor    = new CANTalon(RobotMap.SHOOTER_ANGLE_MOTOR_CAN_ADDRESS);
	private SpeedController hopperAgitatorMotor  = new VictorSP(RobotMap.HOPPER_AGITATOR_MOTOR_PORT);
	
	private T_Encoder       shooterAngleEncoder  = new T_SrxEncoder((CANTalon) shooterAngleMotor);
	private T_Encoder       shooterSpeedEncoder  = 
			new T_CounterEncoder(RobotMap.SHOOTER_SPEED_ENCODER_DIO_PORT, RobotConst.SHOOTER_ENCODER_MAX_SPEED);
	
	public double shooterSpeedSetpoint = RobotConst.DEFAULT_SHOOTER_SPEED;

	// FIXME: Determine the encoder counts from max flap to min flap
	public double shooterAdjustMaxEncoderCount = 2000;

	// PID Controller
	private T_MotorSpeedPidController shooterController = 
			new T_MotorSpeedPidController(10, 0, shooterSpeedEncoder, RobotConst.SHOOTER_ENCODER_MAX_SPEED);
	
	private final int SHOOTER_ANGLE_ADJUST_TOLERANCE = 25;
	
	private int shooterAngleAdjustSetpoint;
	
	private int shooterAngleAdjustPidSetpoint;
	private boolean shooterAnglePidEnabled = false;

	//***********************************************
	//  Initialize the Subsystem 
	//***********************************************
	
	@Override
	public void robotInit() {
		shooterMotor.setInverted(true);
		stopShooter();
		stopFeeder();
		stopAgitator();
	}

	public void initDefaultCommand() {
		setDefaultCommand(new DefaultShooterCommand());
	}

	//***********************************************
	//  Shooter Speed Methods 
	//***********************************************
	
	public double getShooterSpeed() {
		return shooterSpeedEncoder.getRate();
	}

	/**
	 * Set the shooter speed to the given speed.  
	 * <p>
	 * If the shooter
	 * speed is set to zero, the PIDS are disabled and the motor
	 * speed is set to zero.  In this case, the previous setpoint
	 * is retained. 
	 * @param speed the speed in encoder counts per seconds
	 */
	public void setShooterSpeed(double speed) {
		
		if (speed == 0) {
			if (shooterController.isEnabled()) {
				shooterController.disable();
				shooterMotor.set(0);
			}
		} else {
			if (!shooterController.isEnabled()) {
				shooterController.enable();
			}
			shooterSpeedSetpoint = speed;
			shooterController.setSetpoint(speed/RobotConst.SHOOTER_ENCODER_MAX_SPEED);
		}
	}

	/**
	 * Get the shooter speed setpoint as last set on the set command.
	 * <p>
	 * This setpoint is not reset when the shooter is stopped.
	 * @return double shooterSpeedSetpoint
	 */
	public double getShooterSpeedSetpoint() {
		return shooterSpeedSetpoint;
	}
	
	public boolean isShooterAtSpeed(){
		double speed = shooterSpeedEncoder.getRate();
		if (   speed >= shooterSpeedSetpoint*0.985
			&& speed <= shooterSpeedSetpoint*1.015) {
			return true;
		}
		return false;
	}
	
	public void startShooter() {
		shooterController.enable();
	}
	
	public void stopShooter() {
		shooterController.disable();
		shooterMotor.set(0);
	}
	
	//***********************************************
	//  Feeder Methods 
	//***********************************************
	
	public void startFeeder(){
		shooterFeederMotor.set(.6);
	}
	
	public void stopFeeder(){
		shooterFeederMotor.set(0);
	}
	
	//***********************************************
	//  Shooter Angle Adjustment Methods 
	//***********************************************
	
	public void closeShooterAngleAdjuster() {
		shooterAnglePidEnabled = true;
		this.shooterAngleAdjustPidSetpoint = 0;
	}
	
	public void openShooterAngleAdjuster() {
		shooterAnglePidEnabled = true;
		this.shooterAngleAdjustPidSetpoint = shooterAngleAdjustSetpoint;
	}
	
	public void setShooterAngleAdjustSpeed(double speed) {

		if (Math.abs(speed) > 0) {
			shooterAnglePidEnabled = false;
			shooterAngleMotor.set(speed);
		}
		
		// If the encoder is at the limit, then stop turning.
		if (Math.abs(speed) > 0 && shooterAngleEncoder.get() >= RobotConst.SHOOTER_ANGLE_ENCODER_UPPER_LIMIT) {
			speed = 0;
		}
		if (Math.abs(speed) < 0 && shooterAngleEncoder.get() <= RobotConst.SHOOTER_ANGLE_ENCODER_LOWER_LIMIT) {
			speed = 0;
		}
		
		// When the user stops moving the angle, then set this
		// as the new setpoint
		if (speed == 0 && !shooterAnglePidEnabled) {
			shooterAngleAdjustSetpoint = shooterAngleEncoder.get();
			shooterAngleAdjustPidSetpoint = shooterAngleAdjustSetpoint;
			shooterAnglePidEnabled = true;
		}
	}

	public void resetShooterAngleAdjustEncoder() {
		shooterAngleEncoder.reset();
		shooterAngleAdjustSetpoint = 0;
		shooterAngleAdjustPidSetpoint = shooterAngleAdjustSetpoint;
	}

	public void setShooterAngleAdjustSetpoint(int setpoint) {
		
		// Update the Pid value
		shooterAnglePidEnabled = true;

		// Do not save the setpoint if the value is zero so that
		// the user can go back to the previous value
		if (setpoint != 0) {
			this.shooterAngleAdjustSetpoint = setpoint;
		}
		shooterAngleAdjustPidSetpoint = shooterAngleAdjustSetpoint;

	}
	
	public boolean atShooterAngleAdjustSetpoint() {
		if (shooterAnglePidEnabled) {
			return Math.abs(getShooterAngleAdjustError()) < SHOOTER_ANGLE_ADJUST_TOLERANCE;
		} else {
			return true;
		}
	}
	
	private int getShooterAngleAdjustEncoder() {
		return shooterAngleEncoder.get();
	}

	private int getShooterAngleAdjustError() {
		if (shooterAnglePidEnabled) { 
			return shooterAngleAdjustPidSetpoint - getShooterAngleAdjustEncoder();
		} else {
			return 0;
		}
	}
	
	//***********************************************
	//  Hopper Agitator Methods 
	//***********************************************
	public void startAgitator() {
		hopperAgitatorMotor.set(RobotConst.HOPPER_AGITATOR_SPEED);
	}
	
	public void stopAgitator() {
		hopperAgitatorMotor.set(0);
	}
	
	//***********************************************
	//  Update Dashboard 
	//***********************************************
	@Override
	public void updatePeriodic() {

		// Calculate all PIDs (only once per loop)
		shooterController.calculatePidOutput();

		// set motor
		if (shooterController.isEnabled()) {
			 shooterMotor.set(shooterController.get());
		}

		// Adjust the shooter angle to the setpoint
		if (shooterAnglePidEnabled) {
			if (!atShooterAngleAdjustSetpoint()) {
				
				if (Math.abs(getShooterAngleAdjustError()) > 200) {
					
					if (getShooterAngleAdjustError() > 0) {
						shooterAngleMotor.set(.7);
					}
					else {
						shooterAngleMotor.set(-.7);
					}
					
				} else {
					if (getShooterAngleAdjustError() > 0) {
						shooterAngleMotor.set(0.1);
					}
					else {
						shooterAngleMotor.set(-0.1);
					}
				}
			}
			else {
				shooterAngleMotor.set(0);
			}
		}

		// Update all SmartDashboard values
		SmartDashboard.putString("Shooter Motor Speed", String.valueOf(Math.abs(shooterMotor.get())));
		SmartDashboard.putNumber("Current Shooter Speed", getShooterSpeed());
		SmartDashboard.putNumber("Shooter Speed Setpoint", shooterSpeedSetpoint);
		SmartDashboard.putData("Shooter PIDs", shooterController);
		SmartDashboard.putBoolean("Shooter At Speed", isShooterAtSpeed());

		SmartDashboard.putString("Shooter Angle Adjust Encoder", String.valueOf(shooterAngleEncoder.get()));
		SmartDashboard.putNumber("Shooter Angle Adjust Setppoint", shooterAngleAdjustSetpoint);
		SmartDashboard.putBoolean("Shooter Angle PIDs Enabled", shooterAnglePidEnabled);
	}


}
