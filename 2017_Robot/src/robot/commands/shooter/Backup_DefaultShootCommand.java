//
//package robot.commands.shooter;
//
//import edu.wpi.first.wpilibj.command.Command;
//import edu.wpi.first.wpilibj.command.Scheduler;
//import robot.Robot;
//
///**
// *
// */
//public class Backup_DefaultShootCommand extends Command {
//
//
//	public Backup_DefaultShootCommand() {
//		// Use requires() here to declare subsystem dependencies
//		requires(Robot.shooterSubsystem);
//
//	}
//
//	// Called just before this Command runs the first time
//	protected void initialize() {}
//
//	// Called repeatedly when this Command is scheduled to run
//	protected void execute() {
//		double speed = Robot.oi.getShooterAngleAdjustmentSpeed();
//		//		if(Math.abs(speed) > 0.2){
//		//			Robot.shooterSubsystem.runAdjust(speed);
//		//		}
//		// Shooter
//		if(Robot.oi.getResetShooterAdjustEncoder()){
//			Robot.shooterSubsystem.resetShooterAngleAdjustEncoder();
//		}
//		if(Robot.oi.getShootButton()){
//			Scheduler.getInstance().add(new ShootIntakeCommand());
//			if(Robot.shooterSubsystem.isShooterAtSpeed()){
//			Scheduler.getInstance().add(new ShootFeedCommand(0.1));
//			}
//
//		} else {
//			Robot.shooterSubsystem.stopFeeder();
//		}
//		if(Robot.oi.getShootAngleDownCommand()){
//			Scheduler.getInstance().add(new ShootAngleMoveDownCommand());
//		}
//		if(Robot.oi.getShootAngleUpCommand()){
//			Scheduler.getInstance().add(new ShootAngleMoveUpCommand());
//		}
//		if(Robot.oi.getChangeSpeedDown()){
//			Robot.shooterSubsystem.setShooterSpeed(Robot.shooterSubsystem.getShooterSpeedSetpoint() -.001);
//		}
//		if(Robot.oi.getChangeSpeedUp()){
//			Robot.shooterSubsystem.setShooterSpeed(Robot.shooterSubsystem.getShooterSpeedSetpoint() +.001);
//		}
//		if (Robot.oi.isShooterOn()) {
//			double ultrasonicDistance = Robot.chassisSubsystem.ultrasonicSensor.getDistance();
//			Robot.shooterSubsystem.setShooterSpeed(Robot.shooterSubsystem.getShooterSpeedSetpoint());
//		}
//		if(!Robot.oi.isShooterOn()) {
//			Robot.shooterSubsystem.stopShooter();
//		}
//
//	}
//
//	// Make this return true when this Command no longer needs to run execute()
//	protected boolean isFinished() {
//		return false;
//	}
//
//	@Override
//	protected void end() {
//	}
//
//	@Override
//	protected void interrupted() {
//	}
//}
