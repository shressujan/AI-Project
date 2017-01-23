import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import lejos.hardware.Sound;

public class PlayGolf {

	private Thread thread = null;
	private Robot robot = new Robot();

	public PlayGolf() {
		BufferedReader file = null;
		try {
			file = new BufferedReader(new FileReader(new File("Action.txt")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		robot.dropArm();
		boolean b = true;
		while (b) {
			String str = "";
			int action = 0;
			int time = 0;
			try {
				str = file.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (str != null) {
				Scanner s = new Scanner(str);
				action = s.nextInt();
				String timeRotation = s.next();

				if (action == 0 || action == 4) {
					time = (int) (Double.parseDouble(timeRotation) * 4);
				} else {
					time = (int) (Integer.parseInt(timeRotation));
				}
				synchronized (robot) {
					robot.setMoveTime(time);
				}

				switch (action) {
				case 0:
					// System.out.println("0   " + robot.getMoveTime() + " \n");
					robot.moveForward(robot.getMoveTime());
					// Delay.msDelay(robot.getMoveTime());

					break;
				case 1:
					// System.out.println("1   " + robot.getMoveTime() + " \n");
					robot.rotateRight(robot.getMoveTime());

					// Delay.msDelay(robot.getMoveTime());
					break;
				case 2:
					// System.out.println("2   " + robot.getMoveTime() + " \n");
					robot.rotateLeft(robot.getMoveTime());

					// Delay.msDelay(robot.getMoveTime());
					break;
				case 4:
					// System.out.println("4   " + robot.getMoveTime() + " \n");
					robot.moveForwardSlow(robot.getMoveTime());
					break;

				}
			} else {
				b = false;
				Sound.beep();
				robot.rotateLeft(5000);
				robot.moveForward(1000);
				robot.rotateLeft(5000);
				synchronized (robot) {
					robot.setMove(false);
				}
			}
		}

	}

	public static void main(String[] args) {
		PlayGolf main = new PlayGolf();
	}
}
