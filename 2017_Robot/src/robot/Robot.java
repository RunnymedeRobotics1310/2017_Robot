
package robot;

import java.util.ArrayList;
import java.util.List;

import com.toronto.subsystems.T_Subsystem;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.commands.auto.AutonomousCommand;
import robot.oi.OI;
import robot.subsystems.ChassisSubsystem;
import robot.subsystems.ClimbSubsystem;
import robot.subsystems.GearSubsystem;
import robot.subsystems.IntakeSubsystem;
import robot.subsystems.ShooterSubsystem;
import robot.subsystems.LightingSubsystem;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public static List<T_Subsystem> subsystemLs = new ArrayList<T_Subsystem>();
	public static final ChassisSubsystem 	chassisSubsystem = new ChassisSubsystem();
	public static final GearSubsystem    	gearSubsystem    = new GearSubsystem();
	public static final ClimbSubsystem    	climbSubsystem   = new ClimbSubsystem();
	public static final IntakeSubsystem 	intakeSubsystem  = new IntakeSubsystem();
	public static final ShooterSubsystem 	shooterSubsystem = new ShooterSubsystem();
	public static final LightingSubsystem	lightingSubsystem = new LightingSubsystem();
	public static OI oi;
	

	private Command autoCommand;
	
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	
    	subsystemLs.add(chassisSubsystem);
    	subsystemLs.add(gearSubsystem);
    	subsystemLs.add(climbSubsystem);
    	subsystemLs.add(intakeSubsystem);
    	subsystemLs.add(shooterSubsystem);
    	subsystemLs.add(lightingSubsystem);

		oi = new OI();

    	for (T_Subsystem subsystem: subsystemLs) {
    		subsystem.robotInit();
    	}
    	SmartDashboard.putString("Robot Number", String.valueOf(RobotConst.ROBOT));
    	SmartDashboard.putData("Scheduler", Scheduler.getInstance());
    }

	/**
     * This function is called once each time the robot enters Disabled mode.
     * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
     */
    public void disabledInit(){

    }
	
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
		updatePeriodic();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select between different autonomous modes
	 * using the dashboard. The sendable chooser code works with the Java SmartDashboard. If you prefer the LabVIEW
	 * Dashboard, remove all of the chooser code and uncomment the getString code to get the auto name from the text box
	 * below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the chooser code above (like the commented example)
	 * or additional comparisons to the switch structure below with additional strings & commands.
	 */
    public void autonomousInit() {
    	// Turn on the drive pids
    	Robot.oi.setMotorPidToggle(true);
    	chassisSubsystem.enableDrivePids();
    	Robot.chassisSubsystem.setGyroAngle(0);
    	
    	autoCommand = new AutonomousCommand();
    	autoCommand.start();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
        updatePeriodic();
    }

    public void teleopInit() {
    	if (autoCommand != null) {
    		autoCommand.cancel();
    	}
    	// Turn off the drive PIDs
    	Robot.oi.setMotorPidToggle(false);
    	chassisSubsystem.disableDrivePids();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        updatePeriodic();
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }

    private void updatePeriodic() {
    	oi.updatePeriodic();
    	for (T_Subsystem subsystem: subsystemLs) {
    		subsystem.updatePeriodic();
    	}
    }
    
}
