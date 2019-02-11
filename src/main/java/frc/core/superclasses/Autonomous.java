package frc.core.superclasses;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Autonomous extends CommandGroup {
	private Timer timer;

	public Autonomous(Subsystem subsys) {
		timer = new Timer();

		requires(subsys);
	}

	public boolean timeOut(double seconds) {
		timer = timer == null ? new Timer() : timer;
		timer.start();

		if (timer.get() > seconds) {
			timer.reset();
			timer.stop();

			return true;
		}
		return false;
	}
}
