package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;


public class Arm {
    TalonSRX left, right;
    boolean maxLimit;

    public Arm() {

        left = new TalonSRX(0);
        right = new TalonSRX(3);

        right.setInverted(true);

        right.follow(left);


		/* Config the sensor used for Primary PID and sensor direction */
        left.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 30);
        // Inverte o encoder
        left.setSensorPhase(false);

        // Safety limit
		// Fica true se passar do valor maximo ou do minimo
		maxLimit = left.getSelectedSensorPosition(0) > 50000;
    }

    // Sempre use essa função para mexer o braco pois ela tem o safety limit
    public void setSpeed(double speed) {
        // Se estiver no limite máximo n deixa o braco se mover
        if (maxLimit)
            speed = 0;

        left.set(ControlMode.PercentOutput, speed);
        right.set(ControlMode.PercentOutput, speed);
    }

    // From core/util/SpeedControl.java
    private long currentTime = System.currentTimeMillis(), lastTime = System.currentTimeMillis();
    private double rpm = 0, lastPos = 0;
    public double getRPM() {
        currentTime = System.currentTimeMillis() - lastTime;
    	if (currentTime >= 100) {
            rpm = left.getSelectedSensorPosition() - lastPos;

            lastTime = System.currentTimeMillis();
            lastPos = left.getSelectedSensorPosition();
    	}
    	return rpm;
    }
}