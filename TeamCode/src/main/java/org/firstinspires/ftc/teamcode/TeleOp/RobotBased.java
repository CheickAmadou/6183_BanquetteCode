package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Subsystems.Claw;
import org.firstinspires.ftc.teamcode.Subsystems.Diffy;
import org.firstinspires.ftc.teamcode.Subsystems.DriveTrain;
import org.firstinspires.ftc.teamcode.Subsystems.Extendo;
import org.firstinspires.ftc.teamcode.Subsystems.Rotate;
import org.firstinspires.ftc.teamcode.Subsystems.StateMachine;

@TeleOp
public class RobotBased extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        DriveTrain driveTrain = new DriveTrain();
        driveTrain.initiate(hardwareMap);
        Claw claw = new Claw();
        claw.initiate(hardwareMap);
        Diffy diffy = new Diffy();
        diffy.initiate(hardwareMap);
        Extendo extendo = new Extendo();
        extendo.initiate(hardwareMap);
        Gamepad currentGamepad = new Gamepad();
        Gamepad previousGamepad = new Gamepad();
        Rotate rotate = new Rotate();
        rotate.initiate(hardwareMap);
        rotate.reset();
        StateMachine stateMachine = new StateMachine();
        while (rotate.getState() == Rotate.States.INIT && opModeInInit()){
            rotate.run();
        }

        waitForStart();
        stateMachine.setState(StateMachine.RobotState.RESTING);
        rotate.setState(Rotate.States.RESTING);

        if (isStopRequested()){
            rotate.setState(Rotate.States.RESTING);
            return;
        }
        while (opModeIsActive()) {
            previousGamepad.copy(currentGamepad);
            currentGamepad.copy(gamepad1);
            driveTrain.run(gamepad1.left_stick_x,gamepad1.left_stick_y,gamepad1.right_stick_x);
            rotate.run();
            diffy.run();
            claw.run();
            extendo.run();
            if (gamepad1.options){
                rotate.reset();
            }

            stateMachine.run(gamepad1.left_bumper,gamepad1.right_bumper,gamepad1.left_trigger > .1, gamepad1.right_trigger > .1,claw);
            stateMachine.status(telemetry);
            switch (stateMachine.getState()){
                case RESTING:
                    rotate.setState(Rotate.States.RESTING);
                    extendo.setState(Extendo.States.RETRACTED);
                    diffy.setVRot(Diffy.VerticalRotationStates.FORWARD);
                    diffy.setHRot(Diffy.HorizontalRotationStates.HORIZONTAL);
                    break;
                case SCOUTING:
                    rotate.setState(Rotate.States.SCOUTING);
                    extendo.setState(Extendo.States.RETRACTED);
                    diffy.setVRot(Diffy.VerticalRotationStates.DOWNWARD);
                    break;
                case INTAKING:
                    rotate.setState(Rotate.States.DOWN);
                    extendo.setState(Extendo.States.RETRACTED);
                    diffy.setVRot(Diffy.VerticalRotationStates.DOWNWARD);
                    break;
                case HUMAN_INTAKE:
                    rotate.setState(Rotate.States.HUMAN_INTAKE);
                    extendo.setState(Extendo.States.RETRACTED);
                    diffy.setVRot(Diffy.VerticalRotationStates.FORWARD);
                    diffy.setHRot(Diffy.HorizontalRotationStates.HORIZONTAL);
                    break;
                case CHAMBER:
                    rotate.setState(Rotate.States.CHAMBER);
                    extendo.setState(Extendo.States.RETRACTED);
                    diffy.setVRot(Diffy.VerticalRotationStates.DOWNWARD);
                    diffy.setHRot(Diffy.HorizontalRotationStates.HORIZONTAL);
                    break;
            }

            diffy.setDiffy(gamepad1.dpad_left,gamepad1.dpad_right,false,false);

            claw.status(telemetry);
            diffy.status(telemetry);
            extendo.status(telemetry);
            rotate.status(telemetry);
            telemetry.update();
        }
    }
}
