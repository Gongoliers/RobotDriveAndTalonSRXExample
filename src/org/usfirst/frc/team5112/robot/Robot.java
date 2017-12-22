/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5112.robot;

import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.SpeedController;

import com.ctre.phoenix.MotorControl.CAN.TalonSRX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.drive.MecanumDrive;

public class Robot extends SampleRobot {

	private boolean isMecanum = false; // Change this to play around with mecanum drive

	/**
	 * Motor numbers, I don't remember which are which, so change these up
	 */
	private int frontLeftNum = 0;
	private int rearLeftNum = 1;
	private int frontRightNum = 2;
	private int rearRightNum = 3;

	/**
	 * TalonSRX speed controller
	 */
	private SpeedController frontLeft = new TalonSRX(frontLeftNum).getWPILIB_SpeedController(); // This is weird... But
																								// it's the only way
	private SpeedController rearLeft = new TalonSRX(rearLeftNum).getWPILIB_SpeedController();
	private SpeedController frontRight = new TalonSRX(frontRightNum).getWPILIB_SpeedController();
	private SpeedController rearRight = new TalonSRX(rearRightNum).getWPILIB_SpeedController();

	/**
	 * SpeedControllerGroups
	 */
	private SpeedController left = new SpeedControllerGroup(frontLeft, rearLeft); // They added this new class, does
																					// what the Hardware class used to
																					// do
	private SpeedController right = new SpeedControllerGroup(frontRight, rearRight);

	/**
	 * DifferentialDrive (AKA Tank Drive)
	 */
	private DifferentialDrive differentialDrive = new DifferentialDrive(left, right);

	/**
	 * Mecanum Drive
	 */
	private MecanumDrive mecanumDrive = new MecanumDrive(frontLeft, rearLeft, frontRight, rearRight);

	private Joystick stick = new Joystick(0);

	public Robot() {
		differentialDrive.setExpiration(0.1);
		mecanumDrive.setExpiration(0.1);
	}

	@Override
	public void robotInit() {
	}

	@Override
	public void autonomous() {

	}

	@Override
	public void operatorControl() {
		differentialDrive.setSafetyEnabled(true);
		while (isOperatorControl() && isEnabled()) {
			if (isMecanum) {
				mecanumDrive.drivePolar(stick.getMagnitude(), stick.getDirectionDegrees(), stick.getZ());
			} else {
				differentialDrive.arcadeDrive(-stick.getY(), stick.getX());
			}

			// The motors will be updated every 5ms
			Timer.delay(0.005);
		}
	}

	@Override
	public void test() {
	}
}
