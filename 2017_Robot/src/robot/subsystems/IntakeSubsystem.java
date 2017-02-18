
package robot.subsystems;

import com.toronto.subsystems.T_Subsystem;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.RobotMap;
import robot.commands.DefaultIntakeCommand;

public class IntakeSubsystem extends T_Subsystem {

	/*
	 * *************************************************************************
	 * *** Hardware declarations
	 * 
	 * Declare all motors and sensors here
	 ******************************************************************************/
	private SpeedController intakeMotor = new VictorSP (RobotMap.INTAKE_PWM_PORT);


	public void initDefaultCommand() {
		setDefaultCommand(new DefaultIntakeCommand());
	}

	public void intake() {
		intakeMotor.set(1);
	}

	public void outtake() { 
		intakeMotor.set(-1);
	}
	
	public void stop(){
		intakeMotor.set(0);
	}

	@Override
	public void updatePeriodic() {
		// Update all SmartDashboard values
		SmartDashboard.putString("Intake Speed", String.valueOf(intakeMotor.get()));
	}

	@Override
	public void robotInit() {
		stop();
	}
}
