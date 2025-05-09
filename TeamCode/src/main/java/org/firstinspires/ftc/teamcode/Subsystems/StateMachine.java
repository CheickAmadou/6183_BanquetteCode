package org.firstinspires.ftc.teamcode.Subsystems;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.TeleOp.RobotBased;

public class StateMachine {
    public enum RobotState {
        INIT,
        HUMAN_INTAKE,
        RESTING,
        SCOUTING,
        INTAKING,
        BUCKET,
        CHAMBER
    }

    RobotState currentState = RobotState.INIT;
    boolean LBCD = false;
    boolean LTCD = false;
    boolean RTCD = false;

    public void run(boolean LB, boolean RB, boolean LT, boolean RT, Claw claw) {
        RobotState newState = currentState;

        if (LB && !LBCD) {
            switch (currentState) {
                case RESTING:
                    newState = RobotState.SCOUTING;
                    break;
                case SCOUTING:
                    newState = RobotState.INTAKING;
                    break;
                case INTAKING:
                    newState = RobotState.SCOUTING;
                    break;
            }
            LBCD = true;
        } else if (!LB) {
            LBCD = false;
        }


        if (LT && !LTCD) {
            switch (currentState) {
                case RESTING:
                    newState = RobotState.HUMAN_INTAKE;
                    break;
                case SCOUTING:
                    claw.grab();
                    break;
                case INTAKING:
                    claw.grab();
                    break;
                case HUMAN_INTAKE:
                    claw.grab();
                    break;
                case CHAMBER:
                    claw.grab();
                    break;
                case BUCKET:
                    claw.grab();
                    break;
            }
            LTCD = true;
        } else if (!LT) {
            LTCD = false;
        }
        if (RT && !RTCD) {
            switch (currentState) {
                case RESTING:
                    newState = RobotState.CHAMBER;
                    break;
                case CHAMBER:
                case BUCKET:
                    if (claw.getGrab() == Claw.States.CLOSE) {
                        claw.grab();
                    } else {
                        newState = RobotState.RESTING;
                    }
                    break;
                case INTAKING:
                    claw.grab();
                    break;
                case HUMAN_INTAKE:
                    claw.grab();
                    break;
            }
            RTCD = true;
        } else if (!RT) {
            RTCD = false;
        }

        if (RB) {
            newState = RobotState.RESTING;
        }

        currentState = newState;
    }

    public RobotState getState() {
        return currentState;
    }

    public void setState(RobotState newState) {
        currentState = newState;
    }

    public void status(Telemetry telemetry) {
        telemetry.addData("ROBOT STATE", currentState);
    }

}
