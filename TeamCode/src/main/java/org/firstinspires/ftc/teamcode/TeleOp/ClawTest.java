package org.firstinspires.ftc.teamcode.TeleOp;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.Claw;
@Config
@TeleOp
public class ClawTest extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Claw claw = new Claw();
        claw.initiate(hardwareMap);

        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        waitForStart();
        if (isStopRequested()) {
            return;
        }
        while (opModeIsActive()) {
            claw.setClaw(gamepad1.left_bumper);
            claw.status(telemetry);
            telemetry.update();
        }
    }
}
