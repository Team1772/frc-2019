package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;

// import edu.wpi.first.wpilibj.Encoder;

public class Pistons {

    DoubleSolenoid armLeft, armRight, intake, climbFrontLeft, climbFrontRight, climbBackLeft, climbBackRight; 
    Compressor comp;

    public Pistons() {

        comp = new Compressor();
        // armLeft = new DoubleSolenoid(0,1);
        // armRight = new DoubleSolenoid(0,1);
        // intake = new DoubleSolenoid(0,1);
        // climbFrontLeft = new DoubleSolenoid(0,1);
        // climbFrontRight = new DoubleSolenoid(0,1);
        // climbBackLeft = new DoubleSolenoid(0,1);
        // climbBackRight = new DoubleSolenoid(0,1);

    }

    public void setClimbFront(boolean activate){
        if (activate == true){
            climbFrontRight.set(DoubleSolenoid.Value.kForward);
            climbFrontLeft.set(DoubleSolenoid.Value.kForward);
        } else {
            climbFrontRight.set(DoubleSolenoid.Value.kReverse);
            climbFrontLeft.set(DoubleSolenoid.Value.kReverse);
        
        }
    }

    public void setClimbBack(boolean activate){
        if (activate == true){
            climbBackRight.set(DoubleSolenoid.Value.kForward);
            climbBackLeft.set(DoubleSolenoid.Value.kForward);
        } else {
            climbBackRight.set(DoubleSolenoid.Value.kReverse);
            climbBackLeft.set(DoubleSolenoid.Value.kReverse);
        
        }
    }

    public void setArm(boolean activate){

        if (activate == true){
            armLeft.set(DoubleSolenoid.Value.kForward);
            armRight.set(DoubleSolenoid.Value.kForward);
        } else {
            armLeft.set(DoubleSolenoid.Value.kReverse);
            armRight.set(DoubleSolenoid.Value.kReverse);

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
