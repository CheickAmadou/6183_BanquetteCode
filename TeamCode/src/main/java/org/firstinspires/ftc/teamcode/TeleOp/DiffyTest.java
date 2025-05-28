package org.firstinspires.ftc.teamcode.TeleOp;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Subsystems.Diffy;

@Config
@TeleOp
public class DiffyTest extends LinearOpMode {
    @Override
    public void runOpMode() {
        Diffy diffy = new Diffy();

        Gamepad currentGamepad = new Gamepad();
        Gamepad previousGamepad = new Gamepad();

        waitForStart();

        if(isStopRequested()){
            return;
        }
        while(opModeIsActive()){
            previousGamepad.copy(currentGamepad);
            currentGamepad.copy(gamepad1);
            diffy.setDiffy(gamepad1.dpad_left,gamepad1.dpad_right,gamepad1.dpad_up,gamepad1.dpad_down);
            diffy.run();
            diffy.status(telemetry);
            telemetry.update();
        }

    }
}
