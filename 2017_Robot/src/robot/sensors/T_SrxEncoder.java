package robot.sensors;

import com.ctre.CANTalon;
import com.toronto.sensors.T_Encoder;

public class T_SrxEncoder extends T_Encoder {

	private final CANTalon canTalon;
	
	private int offset;

	public T_SrxEncoder(CANTalon canTalon) {
		this(canTalon, false);
	}

	public T_SrxEncoder(CANTalon canTalon, boolean inverted) {
		super(inverted);
		this.canTalon = canTalon;
		reset();
	}

	/**
	 * Subtract the encoder position with the offset and return the position as negative if it's inverted and positive if not
	 */
	@Override
	public int get() {
		int position = (int)canTalon.getPosition() - this.offset;
		return super.inverted ? -position : position;
	}

	@Override
	public double getRate() {
		return super.inverted ? -canTalon.getEncVelocity() : canTalon.getEncVelocity();
	}

	/**
	 * Subtract the encoder position from the offset because the
	 * setEncPosition() does not work.
	 */
	@Override
	public void reset() {
		this.offset = (int)canTalon.getPosition();
	}

}
