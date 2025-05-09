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
    public static int cooldownTimeInMiliS = 2000;
    public static int moveTimeInMiliS = 250;
    long timeSnapshot = 0;

    public States getGrab() {
        return currentGrab;
    }

    public void setGrab(States input) {
        timeSnapshot = System.currentTimeMillis();
        currentGrab = input;
        grab();
    }

    public void initiate(HardwareMap hardwareMap) {
        grabServo = hardwareMap.servo.get("top");
    }

    boolean clawCD = false;

    public void setClaw(boolean LT) {
        if (LT && !clawCD) {
            clawCD = true;
            switch (getGrab()) {
                case CLOSE:
                    setGrab(States.OPEN);
                    break;
                case OPEN:
                    setGrab(States.CLOSE);
                    break;
            }
        } else if (!LT) {
            clawCD = false;
        }
    }
    public void run() {
        //Only power the claw briefly, prevent axon from heating
        long difference = System.currentTimeMillis() - timeSnapshot;
        if (difference > cooldownTimeInMiliS || difference < moveTimeInMiliS){
            if (difference > cooldownTimeInMiliS){
                timeSnapshot = System.currentTimeMillis();
            }
            grab();
        }
    }

    public void grab(){
        switch (getGrab()) {
            case CLOSE:
                grabServo.setPosition(grabClose);
                break;
            case OPEN:
                grabServo.setPosition(grabOpen);
                break;
        }
    }

    public void status(Telemetry telemetry){
        telemetry.addData("Grab", getGrab());
        telemetry.addData("Grab Position", grabServo.getPosition());
        telemetry.addData("Grab Time", System.currentTimeMillis() - timeSnapshot);
    }
}
