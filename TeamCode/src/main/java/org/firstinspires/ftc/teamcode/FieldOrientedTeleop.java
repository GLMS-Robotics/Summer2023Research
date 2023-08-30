package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Rotation2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Field Oriented TeleOp", group="Competition")
public class FieldOrientedTeleop extends ActionSchedulerOpMode{

    @Override
    public void initOpMode() {

    }

    @Override
    public void loopOpMode(TelemetryPacket p) {

        if(gamepad1.back)
        {
            Robot.mecanumDrive.pose = new Pose2d(
                    Robot.mecanumDrive.pose.position,
                    0);
        }

        // Run mecanum drive
        if(!isBusy(Robot.mecanumDrive))
        {
            // Field oriented drive
            double lr = gamepad1.left_stick_x;
            double fb = -gamepad1.left_stick_y;
            double rot = -gamepad1.right_stick_x;


            // Option 1
            Pose2d pose = Robot.mecanumDrive.pose;
            /*
            Vector2d vec = new Vector2d(lr, fb);
            Vector2d out = pose.heading.times(vec);

            Robot.mecanumDrive.setDrivePowers(new PoseVelocity2d(
                    out,
                    rot
            ));
            */


            // Option 2
            // x = x * cos + y * sin
            // y = -x * sin + y * cos

            // Add 1/2 pi since we are looking from the side in this coordinate system
            double a = pose.heading.log() + Math.PI / 2.0;
            Robot.mecanumDrive.setDrivePowers(new PoseVelocity2d(
                    new Vector2d(
                            lr * Math.cos(a) + fb * Math.sin(a),
                            -lr * Math.sin(a) + fb * Math.cos(a)
                    ),
                    rot
            ));
        }

        if(!isBusy(Robot.lift))
        {
            Robot.lift.setSpeed(gamepad2.left_stick_y);

            if(gamepad2.b)
            {
                runAction(Robot.lift.placeHigh(), Robot.lift);
            }
            if(gamepad2.a)
            {
                runAction(Robot.lift.toGround(), Robot.lift);
            }

        }

        if(!isBusy(Robot.pivot))
        {
            Robot.pivot.setSpeed(gamepad2.right_stick_x);

            if(gamepad2.dpad_up)
            {
                runAction(Robot.pivot.toForward(), Robot.pivot);
            }

            if(gamepad2.dpad_right)
            {
                runAction(Robot.pivot.toSide(), Robot.pivot);
            }
        }

        if(!isBusy(Robot.gripper))
        {
            if(gamepad2.right_bumper)
                //runAction(Robot.gripper.open(), Robot.gripper);
                runAction(Robot.gripper.toggleGripper(), Robot.gripper);
        }

        if(gamepad1.start || gamepad2.start)
        {
            cancelActions();
        }

    }
}
