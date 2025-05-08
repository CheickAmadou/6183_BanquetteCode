package org.firstinspires.ftc.teamcode.Subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
@Config
public class Claw {
    public enum States {
        OPEN,
        CLOSE
    }


    States currentGrab = States.CLOSE;
    Servo grabServo;
    public static double grabOpen = .3;
    public static double grabClose = .12;
    public States getGrab() {
        return currentGrab;
    }
    public void setGrab(States input) {
        currentGrab = input;
    }
    public void initiate(HardwareMap hardwareMap) {
        grabServo = hardwareMap.servo.get("top");
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
