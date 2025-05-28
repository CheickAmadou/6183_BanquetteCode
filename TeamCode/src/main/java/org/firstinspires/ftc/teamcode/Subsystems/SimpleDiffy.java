package org.firstinspires.ftc.teamcode.Subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.Telemetry;

@Config
public class SimpleDiffy {

    public enum States {
        INTAKE,
        OUTTAKE_HIGH,
        OUTTAKE_LOW
    }

    public static double intakeLeftServoPos = 0.3;
    public static double intakeRightServoPos = 1;
    public static double outtakeLeftHighPos = 0.7;
    public static double outtakeLeftLowPos = 0.8;
    public static double outtakeRightHighPos = 0.6;
    public static double outtakeRightLowPos = 0.5;

    States current = States.INTAKE;

    Servo rightServo;
    Servo leftServo;

    // Cooldown booleans
    private boolean upCD = false;
    private boolean downCD = false;
    private boolean rightCD = false;

    public void initiate(HardwareMap hardwareMap) {
        rightServo = hardwareMap.servo.get("right");
        leftServo = hardwareMap.servo.get("left");
    }

    public States getState() {
        return current;
    }

    public void set(States input) {
        current = input;
    }

    // Handles D-pad up/down/right to switch states
    public void setSimpleDiffy(boolean dUp, boolean dDown, boolean dRight) {
        if (dUp && !upCD) {
            set(States.OUTTAKE_HIGH);
            upCD = true;
        } else if (!dUp) {
            upCD = false;
        }

        if (dDown && !downCD) {
            set(States.INTAKE);
            downCD = true;
        } else if (!dDown) {
            downCD = false;
        }

        if (dRight && !rightCD) {
            set(States.OUTTAKE_LOW);
            rightCD = true;
        } else if (!dRight) {
            rightCD = false;
        }
    }

    // Sets servo positions based on state
    public void run() {
        switch (current) {
            case INTAKE:
                leftServo.setPosition(intakeLeftServoPos);
                rightServo.setPosition(intakeRightServoPos);
                break;
            case OUTTAKE_HIGH:
                leftServo.setPosition(outtakeLeftHighPos);
                rightServo.setPosition(outtakeRightHighPos);
                break;
            case OUTTAKE_LOW:
                leftServo.setPosition(outtakeLeftLowPos);
                rightServo.setPosition(outtakeRightLowPos);
                break;
        }
    }

    public void status(Telemetry telemetry) {
        telemetry.addData("State", getState());
        telemetry.addData("RRot Position", rightServo.getPosition());
        telemetry.addData("LRot Position", leftServo.getPosition());
    }
}