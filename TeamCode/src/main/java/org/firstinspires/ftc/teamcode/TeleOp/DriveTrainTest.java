package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.DriveTrain;

@TeleOp
public class DriveTrainTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        DriveTrain driveTrain = new DriveTrain();
        driveTrain.initiate(hardwareMap);
        waitForStart();
        if (isStopRequested()) {
            return;
        }
        while (opModeIsActive()) {
            double x = gamepad1.left_stick_x;
            double y = gamepad1.left_stick_y;
            double rx = gamepad1.right_stick_x;

            driveTrain.run(y,x, rx);

            telemetry.addData("X", x);
            telemetry.addData("Y", y);
            telemetry.addData("RX", rx);
            telemetry.update();
        }

    }
}
