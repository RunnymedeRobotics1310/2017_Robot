package robot.commands.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;
import robot.Robot;
import robot.RobotConst;
import robot.RobotConst.VisionDistance;
import robot.commands.drive.DriveToEncoderDistanceCommand;
import robot.commands.drive.DriveToLimitSwitchCommand;
import robot.commands.drive.RotateToHeadingCommand;
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
	        		addSequential(new AutoShootWindupCommand(RobotConst.SHOOTER_SPEED_CLOSE));
	        		
	        		addSequential(new RotateToHeadingCommand(113));
	        		addSequential(new DriveToEncoderDistanceCommand(113, .8, 46));
	        		addSequential(new AutoVisionAlignCommand(VisionDistance.CLOSE));
	        		addSequential(new AutoShootCommand(RobotConst.SHOOTER_SPEED_CLOSE, RobotConst.SHOOTER_ANGLE_ENCODER_COUNT_CLOSE, 60));
        		}
        	}
        	
        	if (robotPosition == RobotPosition.LEFT) {
        
        		// Start the shooter motors
    			addSequential(new AutoShootAngleAdjustCommand(RobotConst.SHOOTER_ANGLE_ENCODER_COUNT_FAR));
    			
        		// Do Gear
        		addSequential(new DriveToEncoderDistanceCommand(0, .8, 89));
        		addSequential(new RotateToHeadingCommand(60));
        		addSequential(new DriveToEncoderDistanceCommand(60, .8, 15, true));
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
        			

	        		addSequential(new AutoShootWindupCommand(RobotConst.SHOOTER_SPEED_FAR));
	        		
	        		// Rotate to a heading
	        		addSequential(new RotateToHeadingCommand(116));
         			
	        		addSequential(new AutoVisionAlignCommand(VisionDistance.FAR));
	        		
	        		// Shoot
	        		addSequential(new AutoShootCommand(RobotConst.SHOOTER_SPEED_FAR, RobotConst.SHOOTER_ANGLE_ENCODER_COUNT_FAR, 60));
        		}
        		
        		
        	}
        	if (robotPosition == RobotPosition.RIGHT) {
        		
        		// Start the shooter motors
    			addSequential(new AutoShootAngleAdjustCommand(RobotConst.SHOOTER_ANGLE_ENCODER_COUNT_CLOSE));
    			
        		// Do Gear
        		addSequential(new DriveToEncoderDistanceCommand(0, .8, 84));
        		addSequential(new RotateToHeadingCommand(300));
        		addSequential(new DriveToEncoderDistanceCommand(300, .8, 20, true));
        		addSequential(new DriveToLimitSwitchCommand(300, .6, Robot.chassisSubsystem.getTowerSensor(), 36));
        		addSequential(new GearReleaseCommand());
        		addSequential(new DriveToEncoderDistanceCommand(300, -.8, 26));
        		
        		// If gear only we just turn straight to neutral zone and go straight
        		if (shootMode == ShootMode.GEAR_ONLY) {
        			addSequential(new RotateToHeadingCommand(0));
        			addSequential(new DriveToEncoderDistanceCommand(0, .8, 36));
        		}
        		
        		// If gear and shoot we shoot
        		if (shootMode == ShootMode.GEAR_SHOOT) {
        			

	        		addSequential(new AutoShootWindupCommand(RobotConst.SHOOTER_SPEED_CLOSE));
	        		
	        		// Rotate to a heading
	        		addSequential(new RotateToHeadingCommand(124));
         			
	        		addSequential(new AutoVisionAlignCommand(VisionDistance.CLOSE));
	        		
	        		// Shoot
	        		addSequential(new AutoShootCommand(RobotConst.SHOOTER_SPEED_CLOSE, RobotConst.SHOOTER_ANGLE_ENCODER_COUNT_CLOSE, 60));
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
	        		addSequential(new AutoShootWindupCommand(RobotConst.SHOOTER_SPEED_CLOSE));
	        		
	        		addSequential(new RotateToHeadingCommand(237));
	        		addSequential(new DriveToEncoderDistanceCommand(237, .8, 44));
	        		addSequential(new AutoVisionAlignCommand(VisionDistance.CLOSE));
	        		addSequential(new AutoShootCommand(RobotConst.SHOOTER_SPEED_CLOSE, RobotConst.SHOOTER_ANGLE_ENCODER_COUNT_CLOSE, 60));
        		}
        	}
        	
        	
        	if (robotPosition == RobotPosition.LEFT) {
        	
        		// Start the shooter motors
    			addSequential(new AutoShootAngleAdjustCommand(RobotConst.SHOOTER_ANGLE_ENCODER_COUNT_CLOSE));
    			
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
        			

	        		addSequential(new AutoShootWindupCommand(RobotConst.SHOOTER_SPEED_CLOSE));
	        		
	        		// Rotate to a heading
	        		addSequential(new RotateToHeadingCommand(229));
         			
	        		addSequential(new AutoVisionAlignCommand(VisionDistance.CLOSE));
	        		
	        		// Shoot
	        		addSequential(new AutoShootCommand(RobotConst.SHOOTER_SPEED_CLOSE, RobotConst.SHOOTER_ANGLE_ENCODER_COUNT_CLOSE, 60));
        		}
        		
        	}
        	if (robotPosition == RobotPosition.RIGHT) {
        		
        		// Start shooter motors
    			addSequential(new AutoShootAngleAdjustCommand(RobotConst.SHOOTER_ANGLE_ENCODER_COUNT_FAR));
        		
        		
        		// Do Gear
        		addSequential(new DriveToEncoderDistanceCommand(0, .8, 9));
        		addSequential(new RotateToHeadingCommand(301));
    			addSequential(new DriveToLimitSwitchCommand(301, .6, Robot.chassisSubsystem.getTowerSensor(), 28));
    			addSequential(new GearReleaseCommand());
    			addSequential(new DriveToEncoderDistanceCommand(295, -.8, 25));
    			
    			// Since you won't be able to shoot from the right side, just drive back and straight
    			if (shootMode == ShootMode.GEAR_ONLY) {
        			addSequential(new RotateToHeadingCommand(0));
        			addSequential(new DriveToEncoderDistanceCommand(0, .8, 30));
        		}
    			
    			
    			// If gear and shoot we shoot
        		if (shootMode == ShootMode.GEAR_SHOOT) {
        			

	        		addSequential(new AutoShootWindupCommand(RobotConst.SHOOTER_SPEED_FAR));
	        		
	        		// Rotate to a heading
	        		addSequential(new RotateToHeadingCommand(239));
         			
	        		addSequential(new AutoVisionAlignCommand(VisionDistance.FAR));
	        		
	        		// Shoot
	        		addSequential(new AutoShootCommand(RobotConst.SHOOTER_SPEED_FAR, RobotConst.SHOOTER_ANGLE_ENCODER_COUNT_FAR, 60));
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
