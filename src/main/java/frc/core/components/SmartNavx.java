package frc.core.components;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SPI;

public class SmartNavx {
	private AHRS ahrs;

	public SmartNavx() {
		try {
            ahrs = new AHRS(SPI.Port.kMXP);
        } catch (RuntimeException ex ) {
            DriverStation.reportError("Error instantiating navX MXP:  " + ex.getMessage(), true);
        }
	}

	public double getAngle() {
		return ahrs.getAngle();
	}

	public double getYaw() {
		return ahrs.getYaw();
	}

	public void reset() {
		ahrs.reset();
	}
}
