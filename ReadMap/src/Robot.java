import lejos.hardware.motor.Motor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.RegulatedMotor;
import lejos.utility.Delay;

public class Robot {

	// motors
	private final RegulatedMotor armMotor = Motor.A;
	private final RegulatedMotor leftMotor = Motor.C;
	private final RegulatedMotor rightMotor = Motor.B;
	private final RegulatedMotor[] motors = { rightMotor };

	// move variables
	private boolean move = false;
	private int moveTime = 0;
	private boolean rightTurn = true;

	// color constants
	private final EV3ColorSensor colorSensor = new EV3ColorSensor(SensorPort.S2);
	private final int white = 6;
	private final int black = 7;
	private final int red = 0;

	// arm rotation
	private final int armDeg = -60;

	// motor speeds
	private final int motorSpeedLine = 225;
	private final int motorSpeed = 225;
	private final int armSpeed = 200;

	public void synchronizeMotors() {
		leftMotor.synchronizeWith(motors);
	}

	public void stop() {
		leftMotor.startSynchronization();
		leftMotor.stop(true);
		rightMotor.stop(true);
		leftMotor.endSynchronization();
	}

	public void moveForward(int f) {
		synchronizeMotors();
		leftMotor.setSpeed(motorSpeedLine);
		rightMotor.setSpeed(motorSpeedLine);
		leftMotor.backward();
		rightMotor.backward();
		Delay.msDelay(f);
		stop();
	}

	public void moveForwardSlow(float f) {
		synchronizeMotors();
		leftMotor.setSpeed(motorSpeedLine);
		rightMotor.setSpeed(motorSpeedLine);
		leftMotor.backward();
		rightMotor.backward();
		Delay.msDelay((long) f);
		stop();
	}

	public void moveBackward(float f) {
		synchronizeMotors();
		leftMotor.setSpeed(motorSpeed);
		rightMotor.setSpeed(motorSpeed);
		leftMotor.backward();
		rightMotor.backward();
		Delay.msDelay((long) f);
		stop();
	}

	public void lineMoveBackward(int i) {
		synchronizeMotors();
		leftMotor.setSpeed(motorSpeedLine);
		rightMotor.setSpeed(motorSpeedLine);
		leftMotor.backward();
		rightMotor.backward();
		Delay.msDelay((long) i);
		stop();
	}

	public void rotateRight(int delay) {
		synchronizeMotors();
		leftMotor.setSpeed(motorSpeedLine);
		rightMotor.setSpeed(motorSpeedLine);
		leftMotor.forward();
		rightMotor.backward();
		Delay.msDelay(delay);
		stop();
	}

	public void rotateLeft(int delay) {
		synchronizeMotors();
		leftMotor.setSpeed(motorSpeedLine);
		rightMotor.setSpeed(motorSpeedLine);
		leftMotor.backward();
		rightMotor.forward();
		Delay.msDelay(delay);
		stop();
	}

	public void dropArm() {
		armMotor.setSpeed(armSpeed);
		armMotor.rotate(armDeg);

	}

	public boolean isMove() {
		return move;
	}

	public void setMove(boolean move) {
		this.move = move;
	}

	public boolean isRightTurn() {
		return rightTurn;
	}

	public void setRightTurn(boolean rightTurn) {
		this.rightTurn = rightTurn;
	}

	public int getMoveTime() {
		return moveTime;
	}

	public void setMoveTime(int moveTime) {
		this.moveTime = moveTime;
	}

	public int getBlack() {
		return black;
	}

	public int getRed() {
		return red;
	}

	public int getWhite() {
		return white;
	}

	public EV3ColorSensor getColorSensor() {
		return colorSensor;
	}

}
