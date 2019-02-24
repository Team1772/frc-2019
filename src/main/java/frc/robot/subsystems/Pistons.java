package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;

// import edu.wpi.first.wpilibj.Encoder;

public class Pistons {
    
    Solenoid climbBack, climbFront, armEnable; 
    DoubleSolenoid intake; 
    Compressor comp;

    public Pistons() {

        comp       = new Compressor();
        climbBack  = new Solenoid(3);
        climbFront = new Solenoid(0);
        armEnable  = new Solenoid(2);
        intake     = new DoubleSolenoid(4,1);

        setArmEnable(true);

    }

    public void setArmEnable(boolean activate){
        if(activate == true){
            armEnable.set(true);
        }
    }

    public void setClimbBack(boolean activate){
        if (activate == true){
            climbBack.set(true);
        }

    }

    public void setClimbFront(boolean activate){
        if(activate == true){
            climbFront.set(true);
        }

    }

    public void setIntake(boolean activate){
        if (activate == true){
            intake.set(DoubleSolenoid.Value.kForward);

        } else {
            intake.set(DoubleSolenoid.Value.kReverse);

        }

    }
}
