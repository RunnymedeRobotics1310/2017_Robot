package robot.oi;

import java.util.ArrayList;
import java.util.List;

import com.toronto.oi.T_Axis;
import com.toronto.oi.T_Button;
import com.toronto.oi.T_Logitech_GameController;
import com.toronto.oi.T_OiController;
import com.toronto.oi.T_OiController.RumbleState;
import com.toronto.oi.T_Stick;
import com.toronto.oi.T_Toggle;
import com.toronto.oi.T_Trigger;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.RobotConst;
import robot.RobotConst.VisionDistance;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 * 
 * Driver Controller (Game Controller) -------------------------------------
 * Joysticks --------- Left: Turn - X Axis Right: Speed - Y Axis
 * 
 * Triggers --------- 
 * Left: Climb Motor (Full Speed) 
 * Right: Climb Motor (Slow/Catch Speed)
 * 
 * Buttons --------- A: B: X: Y: Drive Straight Command
 * 
 * LBumper: High Gear and Rumble 
 * RBumper: Rumble (test) the Driver Controller Start: Back: Toggle
 * (test) LStickPush Toggle Motor Pids RStickPush Toggle Gear State
 * 
 * POV: RotateToAngle
 *
 *
 *
 * Operator Controller (Game Controller) -------------------------------------
 *
 *Buttons 
 *A: Slow shooter motor speed
 *Y: Increase shooter motor speed
 *X: Shooter main motor toggle
 *LBumper: While pressed shooter intake motor runs
 *POV:
 *0: Shooter adjust flap up
 *180: Shooter adjust flap down
 *
 *Back: Turn and Shoot Command
 */
public class OI {

	public AutoSelector autoSelector = new AutoSelector();

	private T_OiController driverController = new T_Logitech_GameController(0);
	
	private T_OiController operatorController = new T_Logitech_GameController(1);

	private T_Toggle motorPidToggle = new T_Toggle(driverController, T_Button.LEFT_STICK, true);

	private T_Toggle intakeToggle = new T_Toggle(driverController, T_Button.RIGHT_BUMPER, false);
	
	private T_Toggle shooterToggle = new T_Toggle(operatorController, T_Button.X, false);
	
	private NetworkTable closeVisionTable = NetworkTable.getTable("GRIP/closeBoilerData");
	

	public class Coordinate {
		public double x, y;
		Coordinate(double x, double y) {
			this.x = x;
			this.y = y;
		}
	}
	
	List<Coordinate> coordinates = new ArrayList<Coordinate>();
	
	/* ************************************************************************
	 * Vision Tracking 
	 **************************************************************************/
	public Coordinate getVisionTarget(VisionDistance visionDistance) {

		double[] xValues = null;
		double[] yValues = null;

		if (visionDistance == VisionDistance.CLOSE) {

			xValues = closeVisionTable.getNumberArray("centerX", new double[0]);
			yValues = closeVisionTable.getNumberArray("centerY", new double[0]);

		}

		coordinates.clear();

		// If the length of x and y are not equal, return -1 so we don't get an
		// array out of bounds exception moving forward
		if (xValues.length != yValues.length) {
			return null;
		}

		for (int i = 0; i < xValues.length; i++) {
			coordinates.add(new Coordinate(xValues[i], yValues[i]));
		}

		if (coordinates.isEmpty()) {
			return null;
		}

		return getCloseVisionTarget();

	}

	private Coordinate getCloseVisionTarget() {

		// Get the coordinate with the lowest y value
		Coordinate lowestYCoordinate = coordinates.get(0);
		int i = 1;
		while (i < coordinates.size()) {
			
			Coordinate coordinate = coordinates.get(i);
			if (coordinate.y < lowestYCoordinate.y) {
				lowestYCoordinate = coordinate;
			}
			i++;
		}

		return lowestYCoordinate;
	}
	
	/* ************************************************************************
	 * Driver Joystick Action / Cancel
	 **************************************************************************/
	public boolean isDriverJoystickAction() {
		return driverController.isJoystickActivated();
	}

	public boolean getCancel() {
		return driverController.getButton(T_Button.BACK);
	}
	
	/* ************************************************************************
	 * Drive Speed and Turn
	 **************************************************************************/
	public boolean getDriverHighGear() {
		return driverController.getButton(T_Button.LEFT_BUMPER);
	}

	public double getSpeed() {
		double speed = driverController.getAxis(T_Stick.LEFT, T_Axis.Y);
		// Square the speed to reduce sensitivity
		// keep the sign
		return speed * Math.abs(speed); 
	}

	public double getTurn() {
		double turn = driverController.getAxis(T_Stick.RIGHT, T_Axis.X);
		// Square the turn to reduce sensitivity
		// keep the sign
		return turn * Math.abs(turn);
	}

	public RumbleState getDriverRumbleState() {
		return driverController.getRumbleState();
	}

	public void setMotorPidToggle(boolean state) {
		motorPidToggle.setToggleState(state);
	}

	public boolean getMotorPidEnabled() {
		return motorPidToggle.getToggleState();
	}

	/* ************************************************************************
	 * Alignment Helpers
	 **************************************************************************/
	public int getRotateToAngle() {
		return driverController.getPov();
	}

	public boolean getVisionTrackButton() {
		return driverController.getButton(T_Button.A);
	}

	public double getY() {
		
		// 
		double[] yValue = closeVisionTable.getNumberArray("centerY", new double[0]);
		if (yValue.length >=1 ) {
			return yValue[0];	
		}
		return -1;
	}
	
	public boolean gearLoadingDistanceButton() {
		return driverController.getButton(T_Button.Y);
	}

	/* ************************************************************************
	 * Rumble the Joysticks
	 **************************************************************************/
	public void setDriverRumble(double rumble) {
		driverController.setRumble(rumble);
	}
	
	public void setOperatorRumble(double rumble) {
		operatorController.setRumble(rumble);
	}
	
	// With timeout
	public void setDriverRumble(double rumble, double timeout) {
		driverController.setRumblePulse(rumble, timeout);
	}
	
	public void setOperatorRumble(double rumble, double timeout) {
		operatorController.setRumblePulse(rumble, timeout);
	}
	

	/* ************************************************************************
	 * Calibration
	 **************************************************************************/
	public boolean getCalibrate() {
		return driverController.getButton(T_Button.START);
	}
	
	/* ************************************************************************
	 * Climber
	 **************************************************************************/
	public boolean getClimbCatch() {
		return operatorController.getPov() == 0;
	}

	public boolean getFastClimb() {
		return operatorController.getButton(T_Trigger.LEFT);
	}

	public double getShooterAngleAdjustmentSpeed(){
		double speed = operatorController.getAxis(T_Stick.RIGHT, T_Axis.Y);
		return speed * Math.abs(speed);

	}
	
	/* ************************************************************************
	 * Gear
	 **************************************************************************/
	/**
	 * This command opens/closes the gear ONLY if the robot is at tower
	 * @return
	 * 
	 * 
	 */
	public boolean getGearCommand() {
		return operatorController.getButton(T_Button.LEFT_BUMPER);
	}
	
	public boolean getGearOverrideCommand() {
		return operatorController.getButton(T_Button.LEFT_STICK);
	}
	
	public boolean getGearFlapCommand() {
		return driverController.getButton(T_Trigger.LEFT);
	}

	/* ************************************************************************
	 * Intake
	 **************************************************************************/
	public boolean getIntakeToggleState() {
		return intakeToggle.getToggleState();
	}

	// Outtake balls
	public boolean getOuttakeCommand() {
		return driverController.getButton(T_Trigger.RIGHT);
	}

	/* ************************************************************************
	 * Shooter
	 ***************************************************************************/
	public void setShooterToggleState(boolean state) {
		shooterToggle.setToggleState(state);
	}

	public boolean isShooterOn() {
		return shooterToggle.getToggleState();
	}

	public boolean getShootButton() {
		return operatorController.getButton(T_Button.RIGHT_BUMPER);
	}

	public boolean getChangeSpeedUp() {
		return operatorController.getButton(T_Button.Y);
	}
	public boolean getChangeSpeedDown() {
		return operatorController.getButton(T_Button.A);
	}
	public boolean getResetShooterAdjustEncoder() {
		return operatorController.getButton(T_Button.B);
	}
	
	public boolean getReverseAgitator() {
		return operatorController.getButton(T_Trigger.RIGHT);
	}

	public boolean getNudgeLeft(){
		return operatorController.getPov() == 270;
	}
		
	public boolean getNudgeRight(){
		return operatorController.getPov() == 90;
	}

	public boolean testDriveBack() {
		return operatorController.getPov() == 0;
	}
	public void updatePeriodic() {

		//Update the joysticks
		driverController.updatePeriodic();
		operatorController.updatePeriodic();
		
		// FIXME: move the Toggle into the base joystick code.
		//        you should be able to get any button as a 
		//        toggle instead of a button.
		//        setToggle(), getToggle();
		
		// Update all toggles
		motorPidToggle.update();
		intakeToggle.update();
		shooterToggle.update();

		// Update all smartdashboard values
		autoSelector.updateSmartDashboard();

		SmartDashboard.putString("Driver Controller", driverController.toString());
		SmartDashboard.putString("Operator Controller", operatorController.toString());
		SmartDashboard.putBoolean("MotorPidToggle", getMotorPidEnabled());
		
		Coordinate target = getVisionTarget(VisionDistance.CLOSE);
		if (target != null) {
			SmartDashboard.putNumber("Vision Target X", target.x);
			SmartDashboard.putNumber("Vision Target Y", target.y);
			
			double distance = RobotConst.SHOOTER_VISION_DISTANCE_SLOPE * target.y + RobotConst.SHOOTER_VISION_DISTANCE_B;
			SmartDashboard.putNumber("Vision Distance", distance);

			
		}

	}



}
