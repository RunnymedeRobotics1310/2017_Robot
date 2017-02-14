package robot.oi;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoSelector {

	public enum RobotPosition { CENTER, LEFT, RIGHT };
	public enum BoilerPosition { LEFT, RIGHT };
	public enum ShootMode { GEAR_ONLY, GEAR_SHOOT, GEAR_HOPPER, HOPPER_SHOOT };
	
	SendableChooser<String> robotPosition;
	SendableChooser<String> boilerPosition;
	SendableChooser<String> shootMode;
	
	public AutoSelector() {

		// Robot Position Options
		robotPosition = new SendableChooser<String>();
		robotPosition.addDefault("Left",  "Left");
		robotPosition.addObject("Center", "Center");
		robotPosition.addObject("Right",  "Right");
		
		// Boiler Position Options
		boilerPosition = new SendableChooser<String>();
		boilerPosition.addDefault("Left",  "Left");
		boilerPosition.addObject("Right",  "Right");

		// Boiler Position Options
		shootMode = new SendableChooser<String>();
		shootMode.addDefault("Gear Only",  "Gear");
		shootMode.addObject("Gear and Shoot",  "GearShoot");
		shootMode.addObject("Gear and Hopper",  "GearHopper");
		shootMode.addObject("Hopper and Shoot", "HopperShoot");
	}
	
	public void updateSmartDashboard() {
	    SmartDashboard.putData("Robot Position", robotPosition);
	    SmartDashboard.putData("Boiler Position", boilerPosition);
	    SmartDashboard.putData("Shoot Mode", shootMode);
	}
	
	public RobotPosition getRobotPostion() {
		switch (robotPosition.getSelected()) {
		case "Left":     return RobotPosition.LEFT;
		case "Right":    return RobotPosition.RIGHT;
		case "Center":   return RobotPosition.CENTER;
		default:         return RobotPosition.LEFT;
		}
	}

	public BoilerPosition getBoilerPostion() {
		switch (boilerPosition.getSelected()) {
		case "Left":     return BoilerPosition.LEFT;
		case "Right":    return BoilerPosition.RIGHT;
		default:         return BoilerPosition.LEFT;
		}
	}

	public ShootMode getShootMode() {
		switch (shootMode.getSelected()) {
		case "Gear":      	return ShootMode.GEAR_ONLY;
		case "GearShoot": 	return ShootMode.GEAR_SHOOT;
		case "GearHopper": 	return ShootMode.GEAR_HOPPER;
		case "HopperShoot": return ShootMode.HOPPER_SHOOT;
		default:          	return ShootMode.GEAR_ONLY;
		}
	}

}
