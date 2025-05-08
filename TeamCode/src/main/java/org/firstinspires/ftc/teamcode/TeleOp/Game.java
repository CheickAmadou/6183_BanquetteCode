package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Subsystems.Claw;
import org.firstinspires.ftc.teamcode.Subsystems.Diffy;
import org.firstinspires.ftc.teamcode.Subsystems.DriveTrain;

@TeleOp
public class Game extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        waitForStart();
        DriveTrain driveTrain = new DriveTrain();
        driveTrain.initiate(hardwareMap);
        Claw claw = new Claw();
        claw.initiate(hardwareMap);
        Diffy diffy = new Diffy();
        diffy.initiate(hardwareMap);
       // Pivot pivot = new Pivot();
       // pivot.initiate(hardwareMap);
        Gamepad currentGamepad = new Gamepad();
        Gamepad previousGamepad = new Gamepad();
        if (isStopRequested()) return;
        while (opModeIsActive()) {
            previousGamepad.copy(currentGamepad);
            currentGamepad.copy(gamepad1);
            driveTrain.run(gamepad1.left_stick_x,gamepad1.left_stick_y,gamepad1.right_stick_x);
          //  pivot.run(telemetry);
            diffy.run(telemetry);
          //  claw.run(telemetry);
            if (gamepad1.options){
            //    pivot.reset();
            }
            if (gamepad1.dpad_up && !previousGamepad.dpad_up){
                switch (diffy.getVRot()){
                    case DOWNWARD:
                        diffy.setVRot(Diffy.VerticalRotationStates.FORWARD);
                        break;
                    case FORWARD:
                        diffy.setVRot(Diffy.VerticalRotationStates.UPWARD);
                        break;
                }
            }
            if (gamepad1.dpad_down && !previousGamepad.dpad_down){
                switch (diffy.getVRot()){
                    case UPWARD:
                        diffy.setVRot(Diffy.VerticalRotationStates.FORWARD);
                        break;
                    case FORWARD:
                        diffy.setVRot(Diffy.VerticalRotationStates.DOWNWARD);
                        break;
                }
            }

            diffy.setDiffy(gamepad1.dpad_left,gamepad1.dpad_right,gamepad1.dpad_up,gamepad1.dpad_down);

            telemetry.update();
        }
    }
}
