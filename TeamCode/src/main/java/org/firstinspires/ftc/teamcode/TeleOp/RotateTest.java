package org.firstinspires.ftc.teamcode.TeleOp;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.Claw;
import org.firstinspires.ftc.teamcode.Subsystems.Extendo;
import org.firstinspires.ftc.teamcode.Subsystems.Rotate;
import org.firstinspires.ftc.teamcode.Subsystems.SimpleDiffy;
import org.firstinspires.ftc.teamcode.Subsystems.StateMachine;
import com.qualcomm.robotcore.hardware.Gamepad;

@Config
@TeleOp
public class RotateTest extends LinearOpMode {
    // Static variables for dashboard tuning
    public static int targetPos = 0;

    @Override
    public void runOpMode() throws InterruptedException {
        Rotate rotate = new Rotate();
        Extendo extend = new Extendo();
        SimpleDiffy simpleDiffy = new SimpleDiffy();
        Claw claw = new Claw();

        rotate.initiate(hardwareMap);
        extend.initiate(hardwareMap);
        simpleDiffy.initiate(hardwareMap);
        claw.initiate(hardwareMap);
        rotate.reset();

        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        waitForStart();
        if (isStopRequested()) return;

        while (opModeIsActive()) {
            // Set PIDF parameters and target position
            rotate.setTargetPosition(targetPos);

            simpleDiffy.setSimpleDiffy(gamepad1.dpad_up, gamepad1.dpad_down, gamepad1.dpad_right);
            simpleDiffy.run();
            simpleDiffy.status(telemetry);
            claw.setClaw(gamepad1.left_trigger > .1);
            claw.run();
            claw.status(telemetry);
            extend.setExtendo(gamepad1.right_trigger > .1);
            extend.run();
            extend.status(telemetry);

            // Run the rotation subsystem
            rotate.run();

            // Telemetry for dashboard
            rotate.status(telemetry);
            telemetry.addData("Target Position", targetPos);
            telemetry.update();
        }
    }
}
