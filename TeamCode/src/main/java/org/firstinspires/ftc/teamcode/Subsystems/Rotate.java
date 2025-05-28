package org.firstinspires.ftc.teamcode.Subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@Config
public class Rotate {
    public enum States {
        INIT,
        HUMAN_INTAKE,
        BUCKET,
        CHAMBER,
        RESTING,
        SCOUTING,
        DOWN
    }

    States currentState = States.INIT;
    public static int initPos = 0;
    public static int humanIntakePos = -400;
    public static int bucketPos = -1500;
    public static int chamberPos = -1500;
    public static int restingPos = -2300;
    public static int scoutingPos = -2700;
    public static int downPos = -2900;

    private static int targetPosition;

    //setter for target position
    public void setTargetPosition(int target) {
        targetPosition = target;
    }

    public static double kP = -0.003;
    public static double kI = 0;
    public static double kD = 0;
    public static double kF = 0;
    public static double positionTolerance = 5;

    PIDFController pidfController = new PIDFController(kP, kI, kD, kF);

    DcMotor rotateMotor1;
    DcMotor rotateMotor2;

    public void initiate(HardwareMap hardwareMap) {
        rotateMotor1 = hardwareMap.dcMotor.get("rotate1");
        rotateMotor2 = hardwareMap.dcMotor.get("rotate2");
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
        rotateMotor2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rotateMotor1.setDirection(DcMotor.Direction.REVERSE);
    }

    boolean rotateCDF = false;
    boolean rotateCDB = false;

    public void setRotate(boolean LB, boolean RB) {
        if (LB && !rotateCDF) {
            switch (getState()) {
                case HUMAN_INTAKE:
                    setState(States.CHAMBER);
                    break;
                case CHAMBER:
                    setState(States.RESTING);
                    break;
                case RESTING:
                    setState(States.SCOUTING);
                    break;
                case SCOUTING:
                    setState(States.DOWN);
                    break;
            }
            rotateCDF = true;
        } else if (!LB) {
            rotateCDF = false;
        }


        if (RB && !rotateCDB) {
            switch (getState()) {
                case DOWN:
                    setState(States.SCOUTING);
                    break;
                case SCOUTING:
                    setState(States.RESTING);
                    break;
                case RESTING:
                    setState(States.CHAMBER);
                    break;
                case CHAMBER:
                    setState(States.HUMAN_INTAKE);
                    break;
            }
            rotateCDB = true;
        } else if (!RB) {
            rotateCDB = false;
        }
    }

    public void run() {
        pidfController.setP(kP);
        pidfController.setI(kI);
        pidfController.setF(kF);
        pidfController.setD(kD);
        pidfController.setTolerance(positionTolerance);

        double maxPower = 1;
        double power = 0;

        /*
        switch (getState()) {
            case INIT:
                maxPower = .5;
                rotateMotor1.setTargetPosition(initPos);
                break;
            case CHAMBER:
                rotateMotor1.setTargetPosition(chamberPos);
                break;
            case BUCKET:
                rotateMotor1.setTargetPosition(bucketPos);
                break;
            case RESTING:
                rotateMotor1.setTargetPosition(restingPos);
                break;
            case DOWN:
                rotateMotor1.setTargetPosition(downPos);
                break;
            case SCOUTING:
                rotateMotor1.setTargetPosition(scoutingPos);
                break;
            case HUMAN_INTAKE:
                rotateMotor1.setTargetPosition(humanIntakePos);
                break;
        }
        */
        power = pidfController.calculate(rotateMotor1.getCurrentPosition(), rotateMotor1.getTargetPosition());
        if (Math.abs(power) > maxPower) {
            power = (Math.signum(power) * maxPower);
        }
        rotateMotor1.setPower(power);
        rotateMotor2.setPower(power);

    }

    public void status(Telemetry telemetry) {
        telemetry.addData("RotateState ", getState());
        telemetry.addData("RotatePos", rotateMotor1.getCurrentPosition());
        telemetry.addData("RotateTarget", rotateMotor1.getTargetPosition());
        telemetry.addData("Rotate Power 1", rotateMotor1.getPower());
        telemetry.addData("Rotate Power 2", rotateMotor2.getPower());
    }

}
