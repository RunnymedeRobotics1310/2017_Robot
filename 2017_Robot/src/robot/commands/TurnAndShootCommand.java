package robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import robot.Robot;
import robot.commands.drive.DriveToEncoderDistanceCommand;
import robot.commands.drive.RotateToHeadingCommand;
import robot.commands.shooter.AutoShootAngleAdjustCommand;
import robot.commands.shooter.AutoShootCommand;
import robot.commands.shooter.AutoShootWindupCommand;


/**
 * This is the auto command that will use the operator input to build the correct command
 */
public class TurnAndShootCommand extends CommandGroup {
	
	private final static double SHOOTER_SPEED = 61.5;

    public TurnAndShootCommand() {
    
    	if (Robot.oi.testDriveBack()) {
    		addSequential(new DriveToEncoderDistanceCommand(0, .5, 20));
    	}
   
    	
//    	double currentAngle = Robot.chassisSubsystem.getGyroAngle();
//
//    	double targetAngle = currentAngle + 180;
//    	
//    	if (targetAngle >= 360) { targetAngle -= 360; }
//    	
//   
//    	// Start the windup
//		addSequential(new AutoShootAngleAdjustCommand(11057));
//		addSequential(new AutoShootWindupCommand(SHOOTER_SPEED));
//
//		// Backup, rotate and start tracking for one second
//    	addSequential(new DriveToEncoderDistanceCommand(currentAngle, -.8, 25));
//    	addSequential(new RotateToHeadingCommand(targetAngle));
//    	addSequential(new VisionTrackCommand(1));
//    	
//    	// Start shooting until the driver moves.
//    	addParallel(new AutoShootCommand(SHOOTER_SPEED, 11057, 60));
//    	addSequential(new VisionTrackCommand(60));
    }
}
