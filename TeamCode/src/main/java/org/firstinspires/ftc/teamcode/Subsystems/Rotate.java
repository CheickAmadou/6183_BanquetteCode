package org.firstinspires.ftc.teamcode.Subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
@Config
public class Rotate {
    public enum States {
        BUCKET,
        CHAMBER,
        RESTING,
        SCOUTING,
        DOWN
    }

    States currentState = States.RESTING;
    public static int bucketPos = 100;
    public static int chamberPos = 80;
    public static int restingPos = 0;
    public static int scoutingPos = 0;
    public static int downPos = -10;

    public static double kP = 0.00001;
    public static double kI = 0;
    public static double kD = 0;
    public static double kF = 0;
    public static double positionTolerance = 5;

    PIDFController pidfController = new PIDFController(kP, kI, kD, kF);

    DcMotor rotateMotor1;

    public void initiate(HardwareMap hardwareMap) {
        rotateMotor1 = hardwareMap.dcMotor.get("rotate1");
        pidfController.setTolerance(positionTolerance);
    }

    public States getState() {
        return currentState;
    }

    public void setState(States input) {
        currentState = input;
    }

    public void reset() {
        rotateMotor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void run(Telemetry telemetry) {
        pidfController.setP(kP);
        pidfController.setI(kI);
        pidfController.setF(kF);
        pidfController.setD(kD);
        pidfController.setTolerance(positionTolerance);

        double maxPower = .3;
        double power = 0;
        switch (getState()) {
            case CHAMBER:
                rotateMotor1.setTargetPosition(chamberPos);
                break;
            case BUCKET:
                rotateMotor1.setTargetPosition(bucketPos);
                break;
            case RESTING:
                maxPower = .1;
                rotateMotor1.setTargetPosition(restingPos);
                break;
            case DOWN:
                rotateMotor1.setTargetPosition(downPos);
                break;
            case SCOUTING:
                rotateMotor1.setTargetPosition(scoutingPos);
                break;
        }
        power = pidfController.calculate(rotateMotor1.getCurrentPosition(), rotateMotor1.getTargetPosition());
        if (Math.abs(power) > maxPower){
            power = Math.signum(power) * maxPower;
        }

        telemetry.addData("RotateState ", getState());
        telemetry.addData("RotatePos", rotateMotor1.getCurrentPosition());
        telemetry.addData("RotateTarget", rotateMotor1.getTargetPosition());

    }

}
