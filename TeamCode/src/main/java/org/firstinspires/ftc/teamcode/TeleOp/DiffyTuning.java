package org.firstinspires.ftc.teamcode.TeleOp;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Subsystems.Claw;
import org.firstinspires.ftc.teamcode.Subsystems.SimpleDiffy;

@Config
@TeleOp
public class DiffyTuning extends LinearOpMode {


    @Override
    public void runOpMode() throws InterruptedException {
        SimpleDiffy simpleDiffy = new SimpleDiffy();
        simpleDiffy.initiate(hardwareMap);
        Claw claw = new Claw();
        claw.initiate(hardwareMap);


        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        waitForStart();
        if (isStopRequested()) {
            return;
        }
        while (opModeIsActive()) {
            simpleDiffy.setSimpleDiffy(gamepad1.dpad_up, gamepad1.dpad_down, gamepad1.dpad_right);
            simpleDiffy.run();
            simpleDiffy.status(telemetry);
            claw.setClaw(gamepad1.left_bumper);
            claw.status(telemetry);
            telemetry.update();
        }

    }

}
