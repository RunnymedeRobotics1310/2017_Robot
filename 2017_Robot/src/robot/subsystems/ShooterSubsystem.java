
package robot.subsystems;

import com.ctre.CANTalon;
import com.toronto.sensors.T_Encoder;
import com.toronto.subsystems.T_Subsystem;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.RobotMap;
import robot.commands.DefaultShootCommand;

public class ShooterSubsystem extends T_Subsystem {

	/*
	 * *************************************************************************
	 * *** Hardware declarations
	 * 
	 * Declare all motors and sensors here
	 ******************************************************************************/
	private SpeedController shooterMotor = new VictorSP (2);
	private SpeedController shooterIntakeMotor = new VictorSP (3);
	private SpeedController shooterAdjustMotor = new CANTalon(5);
    private T_Encoder shooterAdjustEncoder = new T_SrxEncoder((CANTalon) shooterAdjustMotor);
	
    //private VictorSP shooterAdjustEncoder  = new VictorSP(0);

	public void initDefaultCommand() {
		setDefaultCommand(new DefaultShootCommand());
	}

	public void startSpin(){
		shooterMotor.set(-1);
	}
	public void shootSpeed(double speed){
		shooterMotor.set(speed);
	}
	public void shootStop(){
		shooterMotor.set(0);
	}
	
	public void intake(){
		shooterIntakeMotor.set(1);
		
	}
	public void intakeStop(){
		shooterIntakeMotor.set(0);
	}
	public double getCurrentEncoder(){
		return shooterAdjustEncoder.get();
	}
	public void setShooterAdjustSpeed(double speed){
		shooterAdjustMotor.set(speed);
	}
	
	@Override
	public void updatePeriodic() {
		// Update all SmartDashboard values
		SmartDashboard.putString("Shooter Speed", String.valueOf(shooterMotor.get()));
	}

	@Override
	public void robotInit() {
		shootStop();
		intakeStop();
	}
}
