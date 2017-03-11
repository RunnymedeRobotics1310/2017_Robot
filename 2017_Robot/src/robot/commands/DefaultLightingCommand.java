package robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.DriverStation;
import robot.Robot;
/**
 * @author riku
 *
 */
public class DefaultLightingCommand extends Command {

	DriverStation ds;
	
	public DefaultLightingCommand() {
		requires(Robot.lightingSubsystem);
	}
	
	protected void initialize() {
		ds = DriverStation.getInstance();
	}
	
	protected void execute() {
		if (ds.getMatchTime() <= 30) {
			Robot.lightingSubsystem.endGame();
		} else if (Robot.gearSubsystem.gearSensor.atLimit()) {
			Robot.lightingSubsystem.setYellow();
		} else if (ds.getAlliance() == DriverStation.Alliance.Blue) {
			Robot.lightingSubsystem.setBlueAlliance();
		} else if (ds.getAlliance() == DriverStation.Alliance.Red) {
			Robot.lightingSubsystem.setRedAlliance();
		} else if (ds.getAlliance() == DriverStation.Alliance.Invalid) {
			Robot.lightingSubsystem.setTeal();
		}
	}
	
	/* (non-Javadoc)
	 * @see edu.wpi.first.wpilibj.command.Command#isFinished()
	 */
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

}
