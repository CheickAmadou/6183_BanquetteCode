package org.firstinspires.ftc.teamcode.Subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.geometry.Vector2d;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@Config
public class Diffy {

    public enum VerticalRotationStates {
        UPWARD,
        FORWARD,
        DOWNWARD
    }

    public enum HorizontalRotationStates {
        VERTICAL,
        DIAGONAL_RIGHT,
        HORIZONTAL,
        DIAGONAL_LEFT
    }

    VerticalRotationStates currentVRot = VerticalRotationStates.FORWARD;
    HorizontalRotationStates currentHRot = HorizontalRotationStates.HORIZONTAL;

    //Right Side dominant
    Servo rightRotServo;
    Servo leftRotServo;


    //Offsets, make horizontal default
    public static double rightOffset = 0.27;
    public static double leftOffset = 0.27;

    // vRot positions
    public static double vRotUpward = .1;
    public static double vRotForward = .36;
    public static double vRotDownward = .57;

    // hRot positions (Will be added to vRot)
    public static double hRotVertical = -.25;
    public static double hRotDiagonalRight = -.12;
    public static double hRotHorizontal = 0;
    public static double hRotDiagonalLeft = -.09;

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
        rightRotServo = hardwareMap.servo.get("right");
        leftRotServo = hardwareMap.servo.get("left");
    }

    boolean hRotCDR = false;
    boolean hRotCDL = false;

    public void setDiffy(boolean dLeft, boolean dRight, boolean dUp, boolean dDown){
        if (dRight && !hRotCDR){
            switch (getHRot()) {
                case DIAGONAL_LEFT:
                    setHRot(Diffy.HorizontalRotationStates.HORIZONTAL);
                    break;
                case HORIZONTAL:
                    setHRot(Diffy.HorizontalRotationStates.DIAGONAL_RIGHT);
                    break;
                case DIAGONAL_RIGHT:
                    setHRot(Diffy.HorizontalRotationStates.VERTICAL);
                    break;
            }
            hRotCDR = true;
        }else if (!dRight){
            hRotCDR = false;
        }
        if (dLeft && !hRotCDL){
            switch (getHRot()) {
                case HORIZONTAL:
                    setHRot(HorizontalRotationStates.DIAGONAL_LEFT);
                    break;
                case DIAGONAL_RIGHT:
                    setHRot(HorizontalRotationStates.HORIZONTAL);
                    break;
                case VERTICAL:
                    setHRot(HorizontalRotationStates.DIAGONAL_RIGHT);
                    break;
            }
            hRotCDL = true;
        }else if (!dLeft){
            hRotCDL = false;
        }
    }


    public void run(Telemetry telemetry) {
        telemetry.addData("VRot", getVRot());
        telemetry.addData("RRot Position", rightRotServo.getPosition());
        telemetry.addData("HRot", getHRot());
        telemetry.addData("LRot Position", leftRotServo.getPosition());











        double vRotPos = vRotForward;
        switch (getVRot()) {
            case UPWARD:
                vRotPos = (vRotUpward);
                break;
            case FORWARD:
                vRotPos = (vRotForward);
                break;
            case DOWNWARD:
                vRotPos = (vRotDownward);
                break;
        }
        double hRotPos = 0;
        switch (getHRot()){
            case DIAGONAL_LEFT:
                hRotPos = hRotDiagonalLeft;
                break;
            case HORIZONTAL:
                hRotPos = hRotHorizontal;
                break;
            case DIAGONAL_RIGHT:
                hRotPos = hRotDiagonalRight;
                break;
            case VERTICAL:
                hRotPos = hRotVertical;;
                break;
        }
        rightRotServo.setPosition(vRotPos + hRotPos + rightOffset);
        leftRotServo.setPosition((1 -vRotPos) + hRotPos + leftOffset);

    }
}
