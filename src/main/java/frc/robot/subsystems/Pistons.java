package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;

// import edu.wpi.first.wpilibj.Encoder;

public class Pistons {
    
    Solenoid climbBack, climbFront; 
    DoubleSolenoid intake; 
    Compressor comp;

    public Pistons() {

        comp = new Compressor();
        climbBack = new Solenoid(3);
        climbFront = new Solenoid(4);
        intake = new DoubleSolenoid(0,1);

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
