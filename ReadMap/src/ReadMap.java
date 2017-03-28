import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import lejos.utility.Delay;

public class ReadMap {

	private Thread thread;
	private LineFollower follow = new LineFollower();
	private Rotate right = new Rotate();
	private Robot robot = new Robot();
	private ArrayList<Integer> timeList;
	private ArrayList<Integer> actionList;
	private FileWriter file = null;
	private BufferedWriter bf = null;

	public ReadMap() {
		TestFollowTheRedLine();
	}

	public void TestFollowTheRedLine() {

		timeList = new ArrayList<Integer>();
		actionList = new ArrayList<Integer>();

		boolean isBlack = false;
		do {
			thread = new Thread(follow);
			robot.setMove(true);
			thread.start();
			while (robot.getColorSensor().getColorID() == robot.getRed()) {
				Delay.msDelay(10);
				synchronized (robot) {
					robot.setMove(true);
				}
			}
			if (robot.getColorSensor().getColorID() == robot.getWhite()) {
				synchronized (robot) {
					robot.setMove(false);
				}

				robot.lineMoveBackward(330);
				timeList.add(330);
				actionList.add(4);
				// Delay.msDelay(330);

				thread = new Thread(right);
				robot.setMove(true);
				thread.start();
				while (robot.getColorSensor().getColorID() != robot.getRed()) {
					Delay.msDelay(10);
					synchronized (robot) {
						robot.setMove(true);
					}
				}
				synchronized (robot) {
					robot.setMove(false);
				}
			} else if (robot.getColorSensor().getColorID() == robot.getBlack()) {
				isBlack = true;
				synchronized (robot) {
					robot.setMove(false);
				}
			}
		} while (!isBlack);
		try {
			file = new FileWriter("Action.txt");
			bf = new BufferedWriter(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < actionList.size(); i++) {
			try {
				if (actionList.get(i) != null && timeList.get(i) != null) {
					bf.write(actionList.get(i) + " " + timeList.get(i) + "\n");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			bf.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	class LineFollower implements Runnable {

		@Override
		public void run() {
			int currentTime = 0;
			while (robot.isMove()) {
				synchronized (robot) {
					robot.lineMoveBackward(20);
					currentTime += 20;
				}
			}
			timeList.add(currentTime);
			actionList.add(0);
		}
	}

	class Rotate implements Runnable {

		public void run() {
			int currentTime = 0;
			int rotation = 1;
			int time = 0;
			while (robot.isMove() && time < 1400) {
				synchronized (robot) {
					if (robot.isRightTurn()) {
						robot.rotateRight(10);
						rotation = 1;
					} else {
						robot.rotateLeft(10);
						rotation = 2;
					}
					currentTime += 10;
					time += 10;
				}
			}
			timeList.add(currentTime);
			actionList.add(rotation);

			currentTime = 0;

			if (robot.isRightTurn() && robot.isMove()) {
				robot.setRightTurn(false);
			} else if (!robot.isRightTurn() && robot.isMove()) {
				robot.setRightTurn(true);
			}

			while (robot.isMove()) {
				if (robot.isRightTurn()) {
					robot.rotateRight(10);
					rotation = 1;
				} else {
					robot.rotateLeft(10);
					rotation = 2;
				}
				synchronized (robot) {
					currentTime += 10;
				}
			}
			if (currentTime != 0) {
				timeList.add(currentTime);
				actionList.add(rotation);
			}
		}
	}

	public static void main(String[] args) {

		ReadMap t = new ReadMap();
	}
}
