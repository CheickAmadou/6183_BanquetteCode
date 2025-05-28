package org.firstinspires.ftc.teamcode.TeleOp;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.Claw;
import org.firstinspires.ftc.teamcode.Subsystems.DriveTrain;
import org.firstinspires.ftc.teamcode.Subsystems.Extendo;
import org.firstinspires.ftc.teamcode.Subsystems.SimpleDiffy;

@Config
@TeleOp
public class ExtendTest extends LinearOpMode {
    public static int retractedPos = 0;
    public static int bucketPos = 450;

    @Override
    public void runOpMode() throws InterruptedException {
        Extendo extend = new Extendo();
        SimpleDiffy simpleDiffy = new SimpleDiffy();
        Claw claw = new Claw();
        DriveTrain driveTrain = new DriveTrain();
        driveTrain.initiate(hardwareMap);
        extend.initiate(hardwareMap);
        simpleDiffy.initiate(hardwareMap);
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
            claw.setClaw(gamepad1.left_trigger > .1);
            claw.run();
            claw.status(telemetry);
            extend.setRetractedPos(retractedPos);
            extend.setBucketPos(bucketPos);
            extend.setExtendo(gamepad1.right_trigger > .1);
            extend.run();
            extend.status(telemetry);

            double x = gamepad1.left_stick_x;
            double y = gamepad1.left_stick_y;
            double rx = gamepad1.right_stick_x;

            driveTrain.run(y,x,rx);

            telemetry.addData("X", x);
            telemetry.addData("Y", y);
            telemetry.addData("RX", rx);
            telemetry.update();
        }
    }

}
