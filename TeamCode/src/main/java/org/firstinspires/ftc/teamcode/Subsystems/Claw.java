package org.firstinspires.ftc.teamcode.Subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@Config
public class Claw {


    //Two States for the claw, These are regular servo and not continuous rotation
    public enum States {
        OPEN,
        CLOSE
    }

    //Starts with the claw closed
    States currentGrab = States.CLOSE;
    //Defines the servo for the claw
    Servo grabServo;

    //The servo claw positions
    public static double grabOpen = 0.5;
    public static double grabClose = 0;
    // Setters for the claw positions
    public static void setGrabOpen(double grabOpen) {
        Claw.grabOpen = grabOpen;
    }
    public static void setGrabClose(double grabClose) {
        Claw.grabClose = grabClose;
    }

    //The cooldown time for the claw
    public static int cooldownTimeInMiliS = 2000;
    //Time it takes to move the claw
    public static int moveTimeInMiliS = 250;
    // Variable to store the time when the claw was last set
    long timeSnapshot = 0;

    // Get the current grab state
    public States getGrab() {
        return currentGrab;
    }

    // Set the grab state and update the time snapshot
    public void setGrab(States input) {
        timeSnapshot = System.currentTimeMillis();
        currentGrab = input;
        grab();
    }
    // Moves the claw to the current grab position. Has two states: OPEN and CLOSE based off of the Enums
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
    // Initiates the claw by getting the servo from the hardware map
    public void initiate(HardwareMap hardwareMap) {
        grabServo = hardwareMap.servo.get("top");
        setGrab(States.CLOSE);
    }
    //Cool down for the claw, Sets it to false intially as the claw is not being pressed
    boolean clawCD = false;

    // Prevents spamming as the trigger can't be held down to always be true
    public void setClaw(boolean LT) {
        // If the left trigger is pressed and the cooldown is not active, toggle the claw state
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
        // If the left trigger is not pressed, reset the cooldown
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



    public void status(Telemetry telemetry){
        telemetry.addData("Grab", getGrab());
        telemetry.addData("Grab Position", grabServo.getPosition());
        telemetry.addData("Grab Time", System.currentTimeMillis() - timeSnapshot);
    }
}
