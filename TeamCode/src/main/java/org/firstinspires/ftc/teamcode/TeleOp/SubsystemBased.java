package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Subsystems.Claw;
import org.firstinspires.ftc.teamcode.Subsystems.Diffy;
import org.firstinspires.ftc.teamcode.Subsystems.DriveTrain;
import org.firstinspires.ftc.teamcode.Subsystems.Extendo;
import org.firstinspires.ftc.teamcode.Subsystems.Rotate;

@TeleOp
public class
SubsystemBased extends LinearOpMode {
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
        while (rotate.getState() == Rotate.States.INIT && opModeInInit()){
            rotate.run();
        }

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
            extendo.setExtendo(gamepad1.right_trigger > .1);
            claw.setClaw(gamepad1.left_trigger > .1);
            rotate.setRotate(gamepad1.left_bumper,gamepad1.right_bumper);
            diffy.setDiffy(gamepad1.dpad_left,gamepad1.dpad_right,gamepad1.dpad_up,gamepad1.dpad_down);

            claw.status(telemetry);
            extendo.status(telemetry);
            diffy.status(telemetry);
            rotate.status(telemetry);
            telemetry.update();
        }
    }
}
