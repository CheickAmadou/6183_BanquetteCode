package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.Diffy;
import org.firstinspires.ftc.teamcode.Subsystems.DriveTrain;
import org.firstinspires.ftc.teamcode.Subsystems.Pivot;

@TeleOp
public class Game extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        waitForStart();
        DriveTrain driveTrain = new DriveTrain();
        driveTrain.initiate(hardwareMap);
        Diffy diffy = new Diffy();
        diffy.initiate(hardwareMap);
        Pivot pivot = new Pivot();
        pivot.initiate(hardwareMap);
        if (isStopRequested()) return;
        while (opModeIsActive()) {
            driveTrain.run(gamepad1.left_stick_x,gamepad1.left_stick_y,gamepad1.right_stick_x);
            pivot.run(telemetry);
            diffy.run(telemetry);
            if (gamepad1.options){
                pivot.reset();
            }
        }
    }
}
