package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;

// import edu.wpi.first.wpilibj.Encoder;

public class Pistons {
    
    Solenoid climbBack, armEnable;
    DoubleSolenoid climbFront, intake; 
    Compressor comp;

    public Pistons() {

        comp       = new Compressor();
        climbBack  = new Solenoid(0);
        climbFront = new DoubleSolenoid(6,7);
        armEnable  = new Solenoid(1);
        intake     = new DoubleSolenoid(2,5);
    }

    public void setArmEnable(boolean activate){
        if(activate == true){
            armEnable.set(true);
        } else {
            armEnable.set(false);
        }
    }

    public void setClimbBack(boolean activate){
        if(activate == true){
            climbBack.set(true);
        }else{
            climbBack.set(false);
        }
    }

    public void setIntake(boolean activate){
        if (activate == true){
            intake.set(DoubleSolenoid.Value.kForward);

        } else {
            intake.set(DoubleSolenoid.Value.kReverse);

        }

    }

    public void setclimbFront(boolean activate){
        if (activate == true){
            climbFront.set(DoubleSolenoid.Value.kForward);

        } else {
            climbFront.set(DoubleSolenoid.Value.kReverse);

        }

    }
}
