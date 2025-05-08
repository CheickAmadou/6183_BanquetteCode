package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Diffy {

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

    VerticalRotationStates currentVRot = VerticalRotationStates.FORWARD;
    HorizontalRotationStates currentHRot = HorizontalRotationStates.VERTICAl;

    //Right Side dominant
    Servo rightRotServo;
    Servo leftRotServo;


    // vRot positions
    public static double vRotUpward = .8;
    public static double vRotForward = .5;
    public static double vRotDownward = .2;

    // hRot positions (Will be added to vRot)
    public static double hRotRight = 0.2;
    public static double hRotDiagonalRight = 0.1;
    public static double hRotVertical = 0;
    public static double hRotDiagonalLeft = -.1;
    public static double hRotLeft = -.2;

    //Get states

    public VerticalRotationStates getVRot() {
        return currentVRot;
    }

    public HorizontalRotationStates getHRot() {
        return currentHRot;
    }

    //Set states

    public void setVRot(VerticalRotationStates input) {
        currentVRot = input;
    }

    public void setHRot(HorizontalRotationStates input) {
        currentHRot = input;
    }

    public void initiate(HardwareMap hardwareMap) {
        rightRotServo = hardwareMap.servo.get("rightRot");
        leftRotServo = hardwareMap.servo.get("leftRot");
    }

    public void run(Telemetry telemetry) {
        telemetry.addData("RRot", getVRot());
        telemetry.addData("RRot Position", rightRotServo.getPosition());
        telemetry.addData("LRot", getHRot());
        telemetry.addData("LRot Position", leftRotServo.getPosition());

        switch (getVRot()) {
            case UPWARD:
                rightRotServo.setPosition(vRotUpward);
                break;
            case FORWARD:
                rightRotServo.setPosition(vRotForward);
                break;
            case DOWNWARD:
                rightRotServo.setPosition(vRotDownward);
                break;
        }
        leftRotServo.setPosition(1 - rightRotServo.getPosition());

        switch (getHRot()){
            case LEFT:
                rightRotServo.setPosition(rightRotServo.getPosition() + hRotLeft);
                leftRotServo.setPosition(leftRotServo.getPosition() - hRotLeft);
                break;
            case DIAGONAL_LEFT:
                rightRotServo.setPosition(rightRotServo.getPosition() + hRotDiagonalLeft);
                leftRotServo.setPosition(leftRotServo.getPosition() - hRotDiagonalLeft);
                break;
            case VERTICAl:
                rightRotServo.setPosition(rightRotServo.getPosition() + hRotVertical);
                leftRotServo.setPosition(leftRotServo.getPosition() - hRotVertical);
                break;
            case DIAGONAL_RIGHT:
                rightRotServo.setPosition(rightRotServo.getPosition() + hRotDiagonalRight);
                leftRotServo.setPosition(leftRotServo.getPosition() - hRotDiagonalRight);
                break;
            case RIGHT:
                rightRotServo.setPosition(rightRotServo.getPosition() + hRotRight);
                leftRotServo.setPosition(leftRotServo.getPosition() - hRotRight);
                break;
        }

    }
}
