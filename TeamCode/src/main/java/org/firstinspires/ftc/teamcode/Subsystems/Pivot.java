package org.firstinspires.ftc.teamcode.Subsystems;

import com.arcrobotics.ftclib.controller.PIDFController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.opencv.core.Mat;

public class Pivot {
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

    DcMotor pivotMotor;

    public void initiate(HardwareMap hardwareMap) {
        pivotMotor = hardwareMap.dcMotor.get("pivot");
        pidfController.setTolerance(positionTolerance);
    }

    public States getState() {
        return currentState;
    }

    public void setState(States input) {
        currentState = input;
    }

    public void reset() {
        pivotMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
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
                pivotMotor.setTargetPosition(chamberPos);
                break;
            case BUCKET:
                pivotMotor.setTargetPosition(bucketPos);
                break;
            case RESTING:
                maxPower = .1;
                pivotMotor.setTargetPosition(restingPos);
                break;
            case DOWN:
                pivotMotor.setTargetPosition(downPos);
                break;
            case SCOUTING:
                pivotMotor.setTargetPosition(scoutingPos);
                break;
        }
        power = pidfController.calculate(pivotMotor.getCurrentPosition(),pivotMotor.getTargetPosition());
        if (Math.abs(power) > maxPower){
            power = Math.signum(power) * maxPower;
        }

        telemetry.addData("PivotState ", getState());
        telemetry.addData("PivotPos", pivotMotor.getCurrentPosition());
        telemetry.addData("PivotTarget", pivotMotor.getTargetPosition());

    }

}
