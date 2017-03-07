package robot.commands.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;
import robot.Robot;
import robot.RobotConst.VisionDistance;
import robot.commands.GearReleaseCommand;
import robot.commands.shooter.AutoShootAngleAdjustCommand;
import robot.commands.shooter.AutoShootCommand;
import robot.commands.shooter.AutoShootWindupCommand;
import robot.oi.AutoSelector.BoilerPosition;
import robot.oi.AutoSelector.RobotPosition;
import robot.oi.AutoSelector.ShootMode;


/**
 * This is the auto command that will use the operator input to build the correct command
 */
public class AutonomousCommand extends CommandGroup {
	
	private final static double SHOOTER_SPEED = 61.5;

    public AutonomousCommand() {
    	
    	RobotPosition  robotPosition  = Robot.oi.autoSelector.getRobotPostion();
    	BoilerPosition boilerPosition = Robot.oi.autoSelector.getBoilerPostion();
    	ShootMode      shootMode      = Robot.oi.autoSelector.getShootMode();
    	
    	System.out.println("Robot Position " + robotPosition);
    	System.out.println("Boiler Position " + boilerPosition);
    	System.out.println("Shoot Mode " + shootMode);
    	
    	if (boilerPosition == BoilerPosition.RIGHT) {

        	if (robotPosition == RobotPosition.CENTER) {
        		
        		// Do Gear
           		addSequential(new DriveToLimitSwitchCommand(0, .6, Robot.chassisSubsystem.getTowerSensor(), 84));
        		addSequential(new GearReleaseCommand());
        		addSequential(new DriveToEncoderDistanceCommand(0, -.8, 24));
        		
        		if (shootMode == ShootMode.GEAR_SHOOT) {
        			addSequential(new AutoShootAngleAdjustCommand(11057));
	        		addSequential(new AutoShootWindupCommand(SHOOTER_SPEED));
	        		
	        		addSequential(new RotateToHeadingCommand(113));
	        		addSequential(new DriveToEncoderDistanceCommand(113, .8, 42));
	        		addSequential(new AutoVisionAlignCommand(VisionDistance.CLOSE));
	        		addSequential(new AutoShootCommand(SHOOTER_SPEED, 11057, 60));
        		}
        	}
        	
        	if (robotPosition == RobotPosition.LEFT) {
        		// Do Gear
        		addSequential(new DriveToEncoderDistanceCommand(0, .8, 100));
        		addSequential(new RotateToHeadingCommand(59));
    			addSequential(new DriveToLimitSwitchCommand(59, .6, Robot.chassisSubsystem.getTowerSensor(), 28));
    			addSequential(new GearReleaseCommand());
    			addSequential(new DriveToEncoderDistanceCommand(51, -.8, 25));
    			
    			// Since you won't be able to shoot from the right side, just drive back and straight
    			if (shootMode == ShootMode.GEAR_ONLY || shootMode == ShootMode.GEAR_SHOOT) {
        			addSequential(new RotateToHeadingCommand(0));
        			addSequential(new DriveToEncoderDistanceCommand(0, .8, 30));
        		}
        		
        		
        	}
        	if (robotPosition == RobotPosition.RIGHT) {
        		
        		// Do Gear
        		addSequential(new DriveToEncoderDistanceCommand(0, .8, 92));
        		addSequential(new RotateToHeadingCommand(300));
        		addSequential(new DriveToLimitSwitchCommand(300, .6, Robot.chassisSubsystem.getTowerSensor(), 32));
        		addSequential(new GearReleaseCommand());
        		addSequential(new DriveToEncoderDistanceCommand(300, -.8, 25));
        		
        		if (shootMode == ShootMode.GEAR_ONLY) {
        			addSequential(new RotateToHeadingCommand(0));
        			addSequential(new DriveToEncoderDistanceCommand(0, .8, 30));
        		}
        		
        		if (shootMode == ShootMode.GEAR_SHOOT) {
	        		addSequential(new RotateToHeadingCommand(145));
	        		addSequential(new DriveToEncoderDistanceCommand(145, .8, 60));
        		}
    			
    		
        	}
			
    	}
    	
    	if (boilerPosition == BoilerPosition.LEFT) {

        	if (robotPosition == RobotPosition.CENTER) {
        		
        		// Do Gear
           		addSequential(new DriveToLimitSwitchCommand(0, .6, Robot.chassisSubsystem.getTowerSensor(), 80));
        		addSequential(new GearReleaseCommand());
        		addSequential(new DriveToEncoderDistanceCommand(0, -.8, 24));
        		
        		if (shootMode == ShootMode.GEAR_SHOOT) {
        			addSequential(new AutoShootAngleAdjustCommand(11057));
	        		addSequential(new AutoShootWindupCommand(SHOOTER_SPEED));
	        		
	        		addSequential(new RotateToHeadingCommand(237));
	        		addSequential(new DriveToEncoderDistanceCommand(237, .8, 40));
	        		addSequential(new AutoVisionAlignCommand(VisionDistance.CLOSE));
	        		addSequential(new AutoShootCommand(SHOOTER_SPEED, 11057, 60));
        		}
        	}
        	
        	
        	if (robotPosition == RobotPosition.LEFT) {
        	
        		// Start the shooter motors
    			addSequential(new AutoShootAngleAdjustCommand(8782));
    			
        		// Do Gear
        		addSequential(new DriveToEncoderDistanceCommand(0, .8, 87));
        		addSequential(new RotateToHeadingCommand(60));
        		addSequential(new DriveToEncoderDistanceCommand(60, .8, 18, true));
        		addSequential(new DriveToLimitSwitchCommand(60, .6, Robot.chassisSubsystem.getTowerSensor(), 38));
        		addSequential(new GearReleaseCommand());
        		addSequential(new DriveToEncoderDistanceCommand(60, -.8, 28));
        		
        		// If gear only we just turn straight to neutral zone and go straight
        		if (shootMode == ShootMode.GEAR_ONLY) {
        			addSequential(new RotateToHeadingCommand(0));
        			addSequential(new DriveToEncoderDistanceCommand(0, .8, 36));
        		}
        		
        		// If gear and shoot we shoot
        		if (shootMode == ShootMode.GEAR_SHOOT) {
        			

	        		addSequential(new AutoShootWindupCommand(SHOOTER_SPEED));
	        		
	        		// Rotate to a heading
	        		addSequential(new RotateToHeadingCommand(229));
         			
	        		addSequential(new AutoVisionAlignCommand(VisionDistance.CLOSE));
	        		
	        		// Shoot
	        		addSequential(new AutoShootCommand(SHOOTER_SPEED, 11057, 60));
        		}
        		
        	}
        	if (robotPosition == RobotPosition.RIGHT) {
        		
        		// Do Gear
        		addSequential(new DriveToEncoderDistanceCommand(0, .8, 94));
        		addSequential(new RotateToHeadingCommand(301));
    			addSequential(new DriveToLimitSwitchCommand(301, .6, Robot.chassisSubsystem.getTowerSensor(), 28));
    			addSequential(new GearReleaseCommand());
    			addSequential(new DriveToEncoderDistanceCommand(301, -.8, 25));
    			
    			// Since you won't be able to shoot from the right side, just drive back and straight
    			if (shootMode == ShootMode.GEAR_ONLY || shootMode == ShootMode.GEAR_SHOOT) {
        			addSequential(new RotateToHeadingCommand(0));
        			addSequential(new DriveToEncoderDistanceCommand(0, .8, 30));
        		}
    			
    		
        	}
    	}
    	
    
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    }
}
