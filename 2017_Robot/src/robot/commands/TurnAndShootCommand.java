package robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import robot.Robot;
import robot.RobotConst.VisionDistance;
import robot.commands.auto.AutoVisionAlignCommand;
import robot.commands.auto.DriveToEncoderDistanceCommand;
import robot.commands.auto.RotateToHeadingCommand;
import robot.commands.shooter.AutoShootAngleAdjustCommand;
import robot.commands.shooter.AutoShootCommand;
import robot.commands.shooter.AutoShootWindupCommand;


/**
 * This is the auto command that will use the operator input to build the correct command
 */
public class TurnAndShootCommand extends CommandGroup {
	
	private final static double SHOOTER_SPEED = 61.5;

    public TurnAndShootCommand() {
    
    	double currentAngle = Robot.chassisSubsystem.getGyroAngle();

    	double targetAngle = currentAngle + 180;
    	
    	if (targetAngle >= 360) { targetAngle -= 360; }
    	
   
		addSequential(new AutoShootAngleAdjustCommand(11057));
		addSequential(new AutoShootWindupCommand(SHOOTER_SPEED));

    	addSequential(new DriveToEncoderDistanceCommand(300, -.8, 25));
    	addSequential(new RotateToHeadingCommand(targetAngle));
    	addSequential(new AutoVisionAlignCommand(VisionDistance.CLOSE));
    	addSequential(new AutoShootCommand(SHOOTER_SPEED, 11057, 60));
    }
}
