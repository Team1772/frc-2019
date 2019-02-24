package frc.core.util;

public class SpeedControl {
	private long currentTime, lastTime, printTime, lastPrintTime;
	private double currentInchesPerSecond, lastInchesPerSecond, nextVoltage, kI;

	public SpeedControl() {
		reset();
	}

	public double calc(double targetInches, double currentDistance, String printName) {
    	currentTime = System.currentTimeMillis() - lastTime;
    	if (currentTime >= 100) {

    		lastTime = System.currentTimeMillis();

    		currentInchesPerSecond = currentDistance - lastInchesPerSecond;
    		lastInchesPerSecond = currentDistance;

    		if (currentInchesPerSecond < targetInches)
    			nextVoltage += kI;
    		else if (currentInchesPerSecond > targetInches)
    			nextVoltage -= kI;

    		print(printName);
    	}
    	return nextVoltage;
	}

	public double calc(double targetInches, double currentDistance) {
    	return this.calc(targetInches, currentDistance, null);
	}

	public void reset(double startVoltage) {
		nextVoltage = startVoltage;
		currentInchesPerSecond = 0;
		lastInchesPerSecond = 0;
		kI = 0.01;
		lastTime      = System.currentTimeMillis();
		lastPrintTime = System.currentTimeMillis();
	}

	public void reset() {
		reset(0.1);
	}

	private void print(String printName) {
		printTime = System.currentTimeMillis() - lastPrintTime;
		if (printTime >= 800 && printName != null) {
			lastPrintTime = System.currentTimeMillis();

			System.out.println(printName + " nextVoltage:" + nextVoltage + " currentInchesPerSecond:" + currentInchesPerSecond);
		}
	}
}
