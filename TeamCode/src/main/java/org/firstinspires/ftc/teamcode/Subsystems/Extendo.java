package org.firstinspires.ftc.teamcode.Subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@Config
public class Extendo {
    public enum States {
        EXTENDED,
        RETRACTED
    }

    States currentState = States.RETRACTED;
    public static int retractedPos = 0;
    public static int bucketPos = 700;

    public static double kP = 0.005;
    public static double kI = 0;
    public static double kD = 0;
    public static double kF = 0;
    public static double positionTolerance = 5;

    PIDFController pidfController = new PIDFController(kP, kI, kD, kF);

    DcMotor extendoMotor1;
    DcMotor extendoMotor2;

    public void initiate(HardwareMap hardwareMap) {
        extendoMotor1 = hardwareMap.dcMotor.get("extend1");
        extendoMotor2 = hardwareMap.dcMotor.get("extend2");
        pidfController.setTolerance(positionTolerance);
        //Reset encoder on startup
        reset();
    }

    public States getState() {
        return currentState;
    }

    public void setState(States input) {
        currentState = input;
    }

    public void reset() {
        extendoMotor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    boolean extendoCD = false;

    public void setExtendo(boolean RT) {
        if (RT && !extendoCD) {
            switch (getState()) {
                case EXTENDED:
                    setState(States.RETRACTED);
                    break;
                case RETRACTED:
                    setState(States.EXTENDED);
                    break;
            }
            extendoCD = true;
        } else if (!RT) {
            extendoCD = false;
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
        switch (getState()) {
            case EXTENDED:
                extendoMotor1.setTargetPosition(bucketPos);
                break;
            case RETRACTED:
                maxPower = .5;
                extendoMotor1.setTargetPosition(retractedPos);
                break;
        }
        power = pidfController.calculate(extendoMotor1.getCurrentPosition(), extendoMotor1.getTargetPosition());
        if (Math.abs(power) > maxPower) {
            power = (Math.signum(power) * maxPower);
        }
        extendoMotor1.setPower(power);
        extendoMotor2.setPower(power);
    }

    public void status(Telemetry telemetry) {
        telemetry.addData("ExtendoState ", getState());
        telemetry.addData("ExtendoPos", extendoMotor1.getCurrentPosition());
        telemetry.addData("ExtendoTarget", extendoMotor1.getTargetPosition());
        telemetry.addData("ExtndoPower", extendoMotor1.getPower());
    }

}
