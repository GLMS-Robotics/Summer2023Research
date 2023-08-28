package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "TeleOp", group="Competition")
public class Teleop extends ActionSchedulerOpMode{

    @Override
    public void initOpMode() {

    }

    @Override
    public void loopOpMode(TelemetryPacket p) {

        // Run mecanum drive
        if(!isBusy(Robot.mecanumDrive))
        {
            Robot.mecanumDrive.setDrivePowers(new PoseVelocity2d(
                    new Vector2d(
                            -gamepad1.left_stick_y,
                            -gamepad1.left_stick_x
                    ),
                    gamepad1.right_stick_x
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
