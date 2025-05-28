package org.firstinspires.ftc.teamcode.TeleOp;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Config
@TeleOp
public class MotorTest extends LinearOpMode {
    public static double power = 0;


    @Override
    public void runOpMode() throws InterruptedException {
        DcMotor rotate1 = hardwareMap.dcMotor.get("rotate1");
        DcMotor rotate2 = hardwareMap.dcMotor.get("rotate2");


        rotate1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rotate2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        rotate1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rotate2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        rotate1.setDirection(DcMotorSimple.Direction.REVERSE);



        waitForStart();
        if (isStopRequested()) {
            return;
        }
        //front left = back left
        //front right = front right
        //back left = back right
        //back right = front left
        while (opModeIsActive()) {
            if (gamepad1.triangle) {
                rotate1.setPower(power);
                rotate2.setPower(power);
            }
            else{
                rotate1.setPower(0);
                rotate2.setPower(0);
            }
            telemetry.addData("Rotate1", rotate1.getPower());
            telemetry.addData("Rotate2", rotate2.getPower());
            telemetry.update();


        }
    }
}