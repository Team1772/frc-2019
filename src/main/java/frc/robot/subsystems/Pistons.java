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
        climbBack = new Solenoid(0);
        climbFront = new Solenoid(0);
        intake = new DoubleSolenoid(0,1);

    }

    public void setclimbBack(boolean activate){
        climbBack.set(activate);
    }

    public void setIntake(boolean activate){
        if (activate == true){
            intake.set(DoubleSolenoid.Value.kForward);

        } else {
            intake.set(DoubleSolenoid.Value.kReverse);

        }

    }  
    

    

}
