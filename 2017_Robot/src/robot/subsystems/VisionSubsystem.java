package robot.subsystems;

import com.toronto.subsystems.T_Subsystem;

import edu.wpi.first.wpilibj.CameraServer;

/**
 *
 */
public class VisionSubsystem extends T_Subsystem {

    public void initDefaultCommand() {
    	// there is no default command for the vision subsystem
    }

	@Override
	public void robotInit() {
		/** 
		 * Automatically start a camera to send to the SmartDashboard 
		 */
		CameraServer.getInstance().startAutomaticCapture("USBCam0", 0);
	}

	@Override
	public void updatePeriodic() {
		// There is no periodic update for the vision server.
	}
}

