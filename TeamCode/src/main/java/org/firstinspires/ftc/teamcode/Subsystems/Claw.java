package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Claw {
    public enum GrabStates {
        OPEN,
        CLOSE
    }


    GrabStates currentGrab = GrabStates.CLOSE;
    Servo grabServo;
    public static double grabOpen = .5;
    public static double grabClose = .7;
    public GrabStates getGrab() {
        return currentGrab;
    }
    public void setGrab(GrabStates input) {
        currentGrab = input;
    }
    public void initiate(HardwareMap hardwareMap) {
        grabServo = hardwareMap.servo.get("Claw");
    }
    public void run(Telemetry telemetry) {
        telemetry.addData("Grab", getGrab());
        telemetry.addData("Grab Position", grabServo.getPosition());
        switch (getGrab()) {
            case CLOSE:
                grabServo.setPosition(grabClose);
                break;
            case OPEN:
                grabServo.setPosition(grabOpen);
                break;
        }
    }
}
