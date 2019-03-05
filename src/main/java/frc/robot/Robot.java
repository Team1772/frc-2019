/**
 * Description:
 * The PositionClosedLoop example demonstrates the Position closed-loop servo.
 * Tested with Logitech F350 USB Gamepad inserted into Driver Station
 * 
 * Be sure to select the correct feedback sensor using configSelectedFeedbackSensor() below.
 * Use Percent Output Mode (Holding A and using Left Joystick) to confirm talon is driving 
 * forward (Green LED on Talon/Victor) when the position sensor is moving in the postive 
 * direction. If this is not the case, flip the boolean input in setSensorPhase().
 * 
 * Controls:
 * Button 1: When pressed, start and run Position Closed Loop on Talon/Victor
 * Button 2: When held, start and run Percent Output
 * Left Joytick Y-Axis:
 * 	+ Position Closed Loop: Servo Talon forward and reverse [-10, 10] rotations
 * 	+ Percent Ouput: Throttle Talon forward and reverse
 * 
 * Gains for Position Closed Loop may need to be adjusted in Constants.java
 * 
 * Supported Version:
 * - Talon SRX: 4.00
 * - Victor SPX: 4.00
 * - Pigeon IMU: 4.00
 * - CANifier: 4.00
 */
package frc.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import frc.core.util.XboxControl;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.ArmPID;
import frc.robot.subsystems.Driver;
import frc.robot.subsystems.Camera;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Pistons;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.*;

import edu.wpi.first.wpilibj.DigitalInput;

//------CÓDIGO-----------CÓDIGO-----------CÓDIGO-----------CÓDIGO-----------CÓDIGO-----------CÓDIGO----->

public class Robot extends TimedRobot {
	XboxControl pilot, copilot;
	public static Driver driver;
	public static Arm arm;
	public static Intake intake;
	public static Pistons pistons;
	public Camera camera;

	/** Arm Sensor */
	DigitalInput limitSwitchArmDown;
	DigitalInput limitSwitchArmUp;

	/** Intake Sensor */
	DigitalInput limitSwitchIntakeDown;
	DigitalInput limitSwitchIntakeUp;

	/** Hardware */
	public static Compressor comp;
	TalonSRX _talon = new TalonSRX(0);
	TalonSRX _follower1 = new TalonSRX(3);
	TalonSRX _talon2 = new TalonSRX(1);
	TalonSRX _follower2 = new TalonSRX(2);
	Joystick _joy = new Joystick(0);
	
    /** Used to create string thoughout loop */
	StringBuilder _sb = new StringBuilder();
	int _loops = 0;
	
    /** Track button state for single press event */
	boolean _lastButton1 = false;

	/** Save the target position to servo to */
	double targetPositionRotations;
	int zeroenc_arm, zeroenc_intake;


	//----Início do Robô--------Início do Robô--------Início do Robô--------Início do Robô--------Início do Robô---->
	public void robotInit() {
		driver   = new Driver();
		arm      = new Arm();
		intake   = new Intake();
		pilot    = new XboxControl(0);
		copilot  = new XboxControl(1);
		pistons  = new Pistons();
		comp 	 = new Compressor();
		camera 	 = new Camera();

		/** Sensores */
		limitSwitchArmDown = new DigitalInput(0);
		limitSwitchArmUp = new DigitalInput(1);
		limitSwitchIntakeDown = new DigitalInput(2);
		limitSwitchIntakeUp = new DigitalInput(9);

		/** Encoder Reset */
		FeedbackDevice.valueOf(0);

		/** Talon 3 */
		_follower1.configFactoryDefault();
		_follower1.setInverted(true);
		_follower1.follow(_talon);
		
		/* Config the sensor used for Primary PID and sensor direction */
        _talon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 
                                            Constants.kPIDLoopIdx,
				                            Constants.kTimeoutMs);

		/* Ensure sensor is positive when output is positive */
		_talon.setSensorPhase(Constants.kSensorPhase);

		/**
		 * Set based on what direction you want forward/positive to be.
		 * This does not affect sensor phase. 
		 */ 
		_talon.setInverted(Constants.kMotorInvert);

		/* Config the peak and nominal outputs, 12V means full */
		_talon.configNominalOutputForward(0, Constants.kTimeoutMs);
		_talon.configNominalOutputReverse(0, Constants.kTimeoutMs);
		_talon.configPeakOutputForward(0.65, Constants.kTimeoutMs);
		_talon.configPeakOutputReverse(-0.65, Constants.kTimeoutMs);

		/**
		 * Config the allowable closed-loop error, Closed-Loop output will be
		 * neutral within this range. See Table in Section 17.2.1 for native
		 * units per rotation.
		 */
		_talon.configAllowableClosedloopError(0, Constants.kPIDLoopIdx, Constants.kTimeoutMs);

		/* Config Position Closed Loop gains in slot0, tsypically kF stays zero. */
		_talon.config_kF(Constants.kPIDLoopIdx, Constants.kGains.kF, Constants.kTimeoutMs);
		_talon.config_kP(Constants.kPIDLoopIdx, Constants.kGains.kP, Constants.kTimeoutMs);
		_talon.config_kI(Constants.kPIDLoopIdx, Constants.kGains.kI, Constants.kTimeoutMs);
		_talon.config_kD(Constants.kPIDLoopIdx, Constants.kGains.kD, Constants.kTimeoutMs);

		/**
		 * Grab the 360 degree position of the MagEncoder's absolute
		 * position, and intitally set the relative sensor to match.
		 */
		int absolutePosition = _talon.getSensorCollection().getPulseWidthPosition();

		/* Mask out overflows, keep bottom 12 bits */
		absolutePosition &= 0xFFF;
		if (Constants.kSensorPhase) { absolutePosition *= -1; }
		if (Constants.kMotorInvert) { absolutePosition *= -1; }
		
		/* Set the quadrature (relative) sensor to match absolute */
		_talon.setSelectedSensorPosition(absolutePosition, Constants.kPIDLoopIdx, Constants.kTimeoutMs);


		//----INTAKE------------------INTAKE------------------INTAKE------------------INTAKE------------------INTAKE--------->

		/** Talon 2 */
		_follower2.configFactoryDefault();
		_follower2.setInverted(true);
		_follower2.follow(_talon2);
		
		/* Config the sensor used for Primary PID and sensor direction */
        _talon2.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 
                                            ConstantsIntake.kPIDLoopIdx,
				                            ConstantsIntake.kTimeoutMs);

											

		/* Ensure sensor is positive when output is positive */
		_talon2.setSensorPhase(ConstantsIntake.kSensorPhase);

		/**
		 * Set based on what direction you want forward/positive to be.
		 * This does not affect sensor phase. 
		 */ 
		_talon2.setInverted(ConstantsIntake.kMotorInvert);

		/* Config the peak and nominal outputs, 12V means full */
		_talon2.configNominalOutputForward(0, ConstantsIntake.kTimeoutMs);
		_talon2.configNominalOutputReverse(0, ConstantsIntake.kTimeoutMs);
		_talon2.configPeakOutputForward(0.5, ConstantsIntake.kTimeoutMs);
		_talon2.configPeakOutputReverse(-0.5, ConstantsIntake.kTimeoutMs);

		/**
		 * Config the allowable closed-loop error, Closed-Loop output will be
		 * neutral within this range. See Table in Section 17.2.1 for native
		 * units per rotation.
		 */
		_talon2.configAllowableClosedloopError(0, ConstantsIntake.kPIDLoopIdx, ConstantsIntake.kTimeoutMs);

		/* Config Position Closed Loop gains in slot0, tsypically kF stays zero. */
		_talon2.config_kF(ConstantsIntake.kPIDLoopIdx, ConstantsIntake.kGains.kF, ConstantsIntake.kTimeoutMs);
		_talon2.config_kP(ConstantsIntake.kPIDLoopIdx, ConstantsIntake.kGains.kP, ConstantsIntake.kTimeoutMs);
		_talon2.config_kI(ConstantsIntake.kPIDLoopIdx, ConstantsIntake.kGains.kI, ConstantsIntake.kTimeoutMs);
		_talon2.config_kD(ConstantsIntake.kPIDLoopIdx, ConstantsIntake.kGains.kD, ConstantsIntake.kTimeoutMs);

		/**
		 * Grab the 360 degree position of the MagEncoder's absolute
		 * position, and intitally set the relative sensor to match.
		 */
		int absolutePosition2 = _talon2.getSensorCollection().getPulseWidthPosition();

		/* Mask out overflows, keep bottom 12 bits */
		absolutePosition2 &= 0xFFF;
		if (ConstantsIntake.kSensorPhase) { absolutePosition2 *= -1; }
		if (ConstantsIntake.kMotorInvert) { absolutePosition2 *= -1; }
		
		/* Set the quadrature (relative) sensor to match absolute */
		_talon2.setSelectedSensorPosition(absolutePosition2, ConstantsIntake.kPIDLoopIdx, ConstantsIntake.kTimeoutMs);
    }
    
	void commonLoop() {

		/** Printar as posições do Arm e Intake */
		_sb.append("\nArm Pos:");
		_sb.append(_talon.getSelectedSensorPosition(0));
		_sb.append("  Intake Pos:");
		_sb.append(_talon2.getSelectedSensorPosition(0));
		_sb.append("  Zero Encoder:");
		_sb.append(zeroenc_arm);
		_sb.append("  Zero Intake:");
		_sb.append(zeroenc_intake);

		/** Printar as informações dos sensores */
		_sb.append("\nArm UP:");
		_sb.append(limitSwitchArmUp.get());

		_sb.append("  Arm DOWN:");
		_sb.append(limitSwitchArmDown.get());

		_sb.append("  Intake UP:");
		_sb.append(limitSwitchIntakeUp.get());

		_sb.append("  Intake DOWN:");
		_sb.append(limitSwitchIntakeDown.get());


		/* Gamepad processing */
		double rightYstick = copilot.getAxisRightY();
		double leftYstick = copilot.getAxisLeftY();

		/* Get Talon/Victor's current output percentage */
		double motorOutput = _talon2.getMotorOutputPercent();

		/* Deadband gamepad */
		if (Math.abs(rightYstick) < 0.10) {
			/* Within 10% of zero */
			rightYstick = 0;
		}
		if (Math.abs(leftYstick) < 0.10) {
			/* Within 10% of zero */
			leftYstick = 0;
		}

		// /* Prepare line to print */
		// _sb.append("\tout:");
		// /* Cast to int to remove decimal places */
		// _sb.append((int) (motorOutput * 100));
		// _sb.append("%");	// Percent

		// _sb.append("\tpos:");
		// _sb.append(_talon.getSelectedSensorPosition(0));
		// _sb.append("u"); 	// Native units

		/** Fica true se passar do valor maximo ou do minimo */
		boolean maxPos = (_talon.getSelectedSensorPosition(0) - zeroenc_arm) <  (- 55000);
		
		/**
		 * When button 1 is pressed, perform Position Closed Loop to selected position,
		 * indicated by Joystick position x10, [-10, 10] rotations
		 */
		
		
		/** Posições PID 
		 *  Arm: Min: -1155 / Max = -65453  
		 *  Intake: Min: -509 / Max = 823
		 */

		if(limitSwitchArmDown.get() == false) zeroenc_arm = _talon.getSelectedSensorPosition(0);
		if(limitSwitchIntakeUp.get() == false) zeroenc_intake = _talon2.getSelectedSensorPosition(0);

		// Posição A inicial
		 if(copilot.getButtonA()){
			 if(limitSwitchArmDown.get() == true) _talon.set(ControlMode.PercentOutput, 0.20);
			 else _talon.set(ControlMode.PercentOutput, 0);
			_talon2.set(ControlMode.Position, zeroenc_intake - 40);
		 }

		//  Posição F - Bola
		 if(copilot.getButtonB()){
			_talon.set(ControlMode.Position, zeroenc_arm - 20000);
			_talon2.set(ControlMode.Position, zeroenc_intake + 750);
		 } 

		//  Posição D - Bola
		 if(copilot.getButtonY()){
			_talon.set(ControlMode.Position, zeroenc_arm - 51000);
			_talon2.set(ControlMode.Position, zeroenc_intake + 670);
		 }

		//  Posição B - Bola
		if(copilot.getButtonX()){
			_talon.set(ControlMode.Position, zeroenc_arm - 40000);
			_talon2.set(ControlMode.Position, zeroenc_intake + 790);
		}

		//  Posição E - Hatch
		 if(copilot.getButtonStart()){
			if(limitSwitchArmDown.get() == true) _talon.set(ControlMode.PercentOutput, 0.20);
			 else _talon.set(ControlMode.PercentOutput, 0);
			_talon2.set(ControlMode.Position, zeroenc_intake + 190);
		 }

		//  Posição C- Hatch
		 if(copilot.getButtonSelect()){
			_talon.set(ControlMode.Position, zeroenc_arm - 42000);
			_talon2.set(ControlMode.Position, zeroenc_intake + 200);
		 } 


		
		/** Analógico Arm MaxPos */
		if ((copilot.getAxisZLeft() >= 0.5) && !maxPos) {
			/* Percent Output */
			_talon.set(ControlMode.PercentOutput, rightYstick * 0.6);
		}

		/** Analógico Intake MaxPos */
		if((copilot.getAxisZLeft() >= 0.5)){
			_talon2.set(ControlMode.PercentOutput, leftYstick * 0.5);
		}

		if (maxPos) {
			_talon.set(ControlMode.PercentOutput, 0);
		}

		/* If Talon is in position closed-loop, print some more info */
		if (_talon.getControlMode() == ControlMode.Position) {
			/* ppend more signals to print when in speed mode. */
			_sb.append("\terr:");
			_sb.append(_talon.getClosedLoopError(0));
			_sb.append("u");	// Native Units

			_sb.append("\ttrg:");
			_sb.append(targetPositionRotations);
			_sb.append("u");	/// Native Units
		}

		/**
		 * Print every ten loops, printing too much too fast is generally bad
		 * for performance.
		 */
		if (++_loops >= 10) {
			_loops = 0;
			System.out.println(_sb.toString());
		}

		/* Reset built string for next loop */
		_sb.setLength(0);

    }

	/**
	 * This function is called periodically during operator control
	 */


	public void teleopPeriodic() {
		
		//Arm and PID
		commonLoop();
		
		//Driver Robot
    	driver.arcadeDrive(pilot.getAxisLeftY(), pilot.getAxisRightX());

		// Intake	
		if (copilot.getButtonL1()) intake.setSpeedRol(0.30);
		else if(copilot.getButtonR1()) intake.setSpeedRolBack(0.50);
		else intake.setSpeedRol(0);

		if (copilot.getAxisZRight() >= 0.3) {
			pistons.setIntake(true);
		}else{
			pistons.setIntake(false);
		}

		//Climb
		if (pilot.getButtonL1()) {
		pistons.setClimbBack(true);
		}else{
		pistons.setClimbBack(false);
		}

		if (pilot.getButtonR1()) {
		pistons.setclimbFront(true);
		}else{
		pistons.setclimbFront(false);
		}

		/** Analógico Arm */
		if (copilot.getAxisZRight() >= 0.5){
		pistons.setArmEnable(true);
		} else {
		pistons.setArmEnable(false);
		}

		driver.print();
	}


	/** Desabilitar */
	public void disabledInit() {
		pistons.setArmEnable(false);
		pistons.setclimbFront(false);
		pistons.setClimbBack(false);
		pistons.setIntake(false);
	}

}
