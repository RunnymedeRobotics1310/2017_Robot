
package robot.subsystems;

import com.toronto.subsystems.T_Subsystem;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.commands.DefaultClimbCommand;

public class ClimbSubsystem extends T_Subsystem {

	
	/*
	 * *************************************************************************
	 * *** Hardware declarations
	 * 
	 * Declare all motors and sensors here
	 ******************************************************************************/
	private SpeedController climbMotor = new VictorSP (0);

	public void initDefaultCommand() {
		setDefaultCommand(new DefaultClimbCommand());
	}
	
	public void catchRope() { 
		climbMotor.set(0.15);
	}

	public void climb() {
		climbMotor.set(1);
	}

	public void stop(){
		climbMotor.set(0);
	}

	@Override
	public void updatePeriodic() {
		// Update all SmartDashboard values
		SmartDashboard.putNumber("Climb Motor Speed:", climbMotor.get());
	}

	@Override
	public void robotInit() {
		climbMotor.setInverted(true);
		stop();
	}
}
