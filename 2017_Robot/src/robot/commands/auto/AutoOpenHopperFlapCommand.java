package robot.commands.auto;

import edu.wpi.first.wpilibj.command.Command;
import robot.Robot;

public class AutoOpenHopperFlapCommand extends Command {

	public AutoOpenHopperFlapCommand() {
		requires(Robot.shooterSubsystem);
	}
	
	protected void initialize(){
		Robot.shooterSubsystem.openFlap();
	}
	
	protected void execute(){
	}

	@Override
	protected boolean isFinished() {
		return true;
	}
	
	@Override
	protected void end(){
	}
	
	@Override
	protected void interrupted(){
	}
}
