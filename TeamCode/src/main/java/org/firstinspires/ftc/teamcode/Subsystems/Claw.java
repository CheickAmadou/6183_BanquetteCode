package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Claw {
    public enum GrabStates {
        OPEN,
        CLOSE
    }

    public enum VerticalRotationStates {
        UPWARD,
        FORWARD,
        DOWNWARD
    }

    public enum HorizontalRotationStates {
        //Naming based on orientation of claw for grabbing pieces
        RIGHT,
        DIAGONAL_RIGHT,
        VERTICAl,
        DIAGONAL_LEFT,
        LEFT
    }

    GrabStates currentGrab = GrabStates.CLOSE;
    VerticalRotationStates currentVRot = VerticalRotationStates.FORWARD;
    HorizontalRotationStates currentHRot = HorizontalRotationStates.VERTICAl;

    Servo grabServo;
    Servo vRotServo;
    Servo hRotServo;

    //Grab positions
    public static double grabOpen = .5;
    public static double grabClose = .7;

    // vRot positions
    public static double vRotUpward = .8;
    public static double vRotForward = .5;
    public static double vRotDownward = .2;

    // hRot positions
    public static double hRotRight = .7;
    public static double hRotDiagonalRight = .6;
    public static double hRotVertical = .5;
    public static double hRotDiagonalLeft = .4;
    public static double hRotLeft = .3;

    //Get states
    public GrabStates getGrab() {
        return currentGrab;
    }

    public VerticalRotationStates getVRot() {
        return currentVRot;
    }

    public HorizontalRotationStates getHRot() {
        return currentHRot;
    }

    //Set states
    public void setGrab(GrabStates input) {
        currentGrab = input;
    }

    public void setVRot(VerticalRotationStates input) {
        currentVRot = input;
    }

    public void setHRot(HorizontalRotationStates input) {
        currentHRot = input;
    }

    public void initiate(HardwareMap hardwareMap) {
        grabServo = hardwareMap.servo.get("Claw");
        vRotServo = hardwareMap.servo.get("vRot");
        hRotServo = hardwareMap.servo.get("hRot");
    }

    public void run(Telemetry telemetry) {
        telemetry.addData("Grab", getGrab());
        telemetry.addData("Grab Position", grabServo.getPosition());
        telemetry.addData("vRot", getVRot());
        telemetry.addData("vRot Position", vRotServo.getPosition());
        telemetry.addData("hRot", getHRot());
        telemetry.addData("hRot Position", hRotServo.getPosition());

        switch (getGrab()) {
            case CLOSE:
                grabServo.setPosition(grabClose);
                break;
            case OPEN:
                grabServo.setPosition(grabOpen);
                break;
        }

        switch (getVRot()) {
            case UPWARD:
                vRotServo.setPosition(vRotUpward);
                break;
            case FORWARD:
                vRotServo.setPosition(vRotForward);
                break;
            case DOWNWARD:
                vRotServo.setPosition(vRotDownward);
                break;
        }
        switch (getHRot()) {
            case RIGHT:
                hRotServo.setPosition(hRotRight);
                break;
            case DIAGONAL_RIGHT:
                hRotServo.setPosition(hRotDiagonalRight);
                break;
            case VERTICAl:
                hRotServo.setPosition(hRotVertical);
                break;
            case DIAGONAL_LEFT:
                hRotServo.setPosition(hRotDiagonalLeft);
                break;
            case LEFT:
                hRotServo.setPosition(hRotLeft);
                break;
        }

    }
}
