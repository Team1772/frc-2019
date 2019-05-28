package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class _Arm {
    TalonSRX master, follower;
    // boolean maxLimit, minLimit;

    // // From core/util/SpeedControl.java
    // long currentTime = System.currentTimeMillis(), lastTime =
    // System.currentTimeMillis();
    // double rpm = 0, lastPos = 0;

    public _Arm() {

        // Left
        master = new TalonSRX(0);
        // Right
        follower = new TalonSRX(3);

        follower.configFactoryDefault();
        follower.follow(master);
    }

    /* Config the sensor used for Primary PID and sensor direction */
    // master.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative,
    // 0, 30);
    // // Inverte o encoder
    // master.setSensorPhase(false);

    // // Safety limit
    // // Fica true se passar do valor maximo ou do minimo
    // // maxLimit = master.getSelectedSensorPosition(0) > 50000;
    // // minLimit = master.getSelectedSensorPosition(0) < 0;

    // lastPos = master.getSelectedSensorPosition();
    // }

    // Sempre use essa função para mexer o braco pois ela tem o safety limit
    public void setSpeed(double speed) {
        // Se estiver no limite máximo n deixa o braco se mover
        // if (maxLimit && speed > 0 || minLimit && speed < 0)
        // speed = 0;

        master.set(ControlMode.PercentOutput, speed);
    }

    // From core/util/SpeedControl.java
    // public double getRPM() {
    // currentTime = System.currentTimeMillis() - lastTime;
    // if (currentTime >= 100) {
    // rpm = Math.abs(master.getSelectedSensorPosition() - lastPos);

    // lastTime = System.currentTimeMillis();
    // lastPos = master.getSelectedSensorPosition();
    // System.out.println("RPM: " + rpm);
    // }
    // System.out.println("Position: " + master.getSelectedSensorPosition());
    // return rpm;
    // }
}