
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
import robot.commands.shooter.DefaultShootCommand;

public class ShooterSubsystem extends T_Subsystem {

	/*
	 * *************************************************************************
	 * *** Hardware declarations
	 * 
	 * Declare all motors and sensors here
	 ******************************************************************************/
	private SpeedController shooterMotor = new VictorSP (RobotMap.SHOOTER_MOTOR_PORT);
	private SpeedController shooterIntakeMotor = new VictorSP (RobotMap.SHOOTER_INTAKE_MOTOR_PORT);
	private SpeedController shooterAdjustMotor = new CANTalon(RobotMap.SHOOTER_ADJUST_CAN_ADDRESS);
    private T_Encoder shooterAdjustEncoder = new T_SrxEncoder((CANTalon) shooterAdjustMotor);
	private T_CounterEncoder shooterSpeedEncoder = new T_CounterEncoder(2);
	public double shootSpeedSetpoint = .5;
	private SpeedController hopperAdjitatorMotor = new VictorSP (4);
	

	// PID Controller
	T_MotorSpeedPidController shooterController = 
			new T_MotorSpeedPidController(1, 0, 
					shooterSpeedEncoder, RobotConst.MAX_SHOOTER_SPEED);

	double prevShooterSpeed = 0.0;
	
	public double getShootSpeed(){
		double shooterSpeed = shooterSpeedEncoder.getRate();
		// Throw bad data
		if (Math.abs(shooterSpeed) > 160) {
			return prevShooterSpeed;
		}
		prevShooterSpeed = shooterSpeed;
		return prevShooterSpeed;
	}
	public void setShootSpeed(double speed){
		if (!shooterController.isEnabled()) {
			shooterController.enable();
		}
		shootSpeedSetpoint = speed;
		shooterController.setSetpoint(shootSpeedSetpoint);
	}
	public void runAdjust(double speed){
		shooterAdjustMotor.set(speed);
	}
	
	public void initDefaultCommand() {
		setDefaultCommand(new DefaultShootCommand());
	}

	public void startSpin(){
		shooterMotor.set(-1);
	}
	
	public void shootStop(){
		shooterMotor.set(0);
	}
	
	public void intake(){
		shooterIntakeMotor.set(1);
		hopperAdjitatorMotor.set(-0.5);
		
	}
	public void intakeStop(){
		shooterIntakeMotor.set(0);
		hopperAdjitatorMotor.set(0);
	}
	public double getCurrentEncoder(){
		return shooterAdjustEncoder.get();
	}
	public void setShooterAdjustSpeed(double speed){
		shooterAdjustMotor.set(speed);
	}
	
	@Override
	public void updatePeriodic() {
		
		// Calculate all PIDs (only once per loop)
		shooterController.calculatePidOutput();

		// set motor
		if (shooterController.isEnabled()) {
			shooterMotor.set(shooterController.get());
		}
				
				
		// Update all SmartDashboard values
		SmartDashboard.putString("Shooter Motor Speed", String.valueOf(Math.abs(shooterMotor.get())));
		SmartDashboard.putString("Shooter adjust encoder", String.valueOf(shooterAdjustEncoder.get()));
		SmartDashboard.putNumber("Current Shooter Speed", getShootSpeed());
		SmartDashboard.putNumber("Shooter setpoint", shootSpeedSetpoint);
		SmartDashboard.putData("Shooter PIDs", shooterController);
	}

	@Override
	public void robotInit() {
		shooterMotor.setInverted(true);
		shootStop();
		intakeStop();
	}
}
