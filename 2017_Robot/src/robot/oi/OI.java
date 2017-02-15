package robot.oi;

import com.toronto.oi.T_Axis;
import com.toronto.oi.T_Button;
import com.toronto.oi.T_Logitech_GameController;
import com.toronto.oi.T_OiController;
import com.toronto.oi.T_Stick;
import com.toronto.oi.T_Toggle;
import com.toronto.oi.T_Trigger;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 * 
 * Operator Controller (Game Controller)
 * -------------------------------------
 * Joysticks
 * ---------
 * Left:        Turn - X Axis
 * Right:       Speed - Y Axis
 * 
 * Triggers
 * ---------
 * Left:		Climb Motor (Full Speed)
 * Right:		Climb Motor (Catch Speed)
 * 
 * Buttons
 * ---------
 * A:
 * B:
 * X:
 * Y:			Drive Straight Command
 * 
 * LBumper:
 * RBumper: 	Rumble (test) the Driver Controller
 * Start:		
 * Back:		Toggle (test)
 * LStickPush   Toggle Motor Pids
 * RStickPush   Toggle Gear State
 * 
 * POV:       	RotateToAngle
 *
 */
public class OI {
	
	public AutoSelector autoSelector = new AutoSelector();

	private T_OiController driverController = new T_Logitech_GameController(0);
	
	private T_Toggle driverTestToggle = new T_Toggle(driverController, T_Button.BACK, false);
	
	private T_Toggle motorPidToggle = new T_Toggle(driverController, T_Button.LEFT_STICK, true);
	
	private T_Toggle gearToggle = new T_Toggle(driverController, T_Button.RIGHT_STICK, false);

	public boolean getDriverRumbleStart() {
		return driverController.getButton(T_Button.RIGHT_BUMPER);
	}
	
	public double getSpeed() {
		return driverController.getAxis(T_Stick.RIGHT, T_Axis.Y);
	}
	
	public boolean getCLimbCatch() {
		return driverController.getButton(T_Trigger.RIGHT);
	}
	
	public boolean getFastCLimb() {
		return driverController.getButton(T_Trigger.LEFT);
	}
	
	public boolean getStartDriveStraightCommand() {
		return driverController.getButton(T_Button.Y);
	}
	
	public boolean getDriverToggle() {
		return driverTestToggle.getToggleState();
	}
	
	public boolean getMotorPidEnabled() {
		return motorPidToggle.getToggleState();
	}
	
	public int getRotateToAngle() {
		return driverController.getPov();
	}
	
	public double getTurn() {
		return driverController.getAxis(T_Stick.LEFT, T_Axis.X);
	}

	public boolean isDriverAction() {
		return driverController.isControllerActivated();
	}

	public void setDriverRumble(double rumble) {
		driverController.setRumble(rumble);
	}

	public boolean getCancel() {
		return driverController.getButton(T_Button.BACK);
	}
	
	public boolean getStartGearCommand() {
		return driverController.getButton(T_Button.B);
	}
	
	public boolean getCalibrate() {
		return driverController.getButton(T_Button.START);
	}
	
	public boolean getGearToggleState() {
		return gearToggle.getToggleState();
	}
	
	public void updatePeriodic() {
		
		// Update all toggles
		driverTestToggle.update();
		motorPidToggle.update();
		gearToggle.update();
		
		// Update all smartdashboard values
		autoSelector.updateSmartDashboard();
		
		SmartDashboard.putString("Driver Controller", 
				driverController.toString());
		SmartDashboard.putBoolean("Toggle", getDriverToggle());
		SmartDashboard.putBoolean("MotorPidToggle", getMotorPidEnabled());
	}

	public void setGearButton(boolean b) {
		gearToggle.setToggleState(b);
	}
}

