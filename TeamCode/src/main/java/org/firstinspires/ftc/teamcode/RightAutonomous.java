package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Right Autonomous", group = "Right")
public class RightAutonomous extends ActionAutonomous{
    /**
     * Opmodes should define the action to be taken during autonomous here.
     * Usually, this should be done using Robot.mecanumDrive's actionBuilder
     *
     * @return an Action representing autonomous (probably built by TrajectoryActionBuilder in
     */
    @Override
    public Action autonomousActions() {
        Robot.mecanumDrive.updatePoseEstimate();
        Robot.mecanumDrive.pose = new Pose2d(33.99, -65.14, Math.toRadians(90.00));
        return Robot.mecanumDrive.actionBuilder(new Pose2d(33.99, -65.14, Math.toRadians(90.00)))
                .splineTo(new Vector2d(33.54, 0.15), Math.toRadians(90.39))
                .stopAndAdd(new SequentialAction(
                        new ParallelAction(
                            Robot.lift.placeHigh(),
                                new SequentialAction(
                                        new SleepAction(1),
                                        Robot.pivot.toSide()
                                )
                        ),
                        Robot.gripper.open(),
                        Robot.pivot.toForward(),
                        Robot.lift.toGround()
                ))
                //.afterDisp(0, new SequentialAction(
                //        Robot.pivot.toForward(),
                //        Robot.lift.toGround()
                //))
                .splineToSplineHeading(new Pose2d(48.00, -11.93, Math.toRadians(-1.62)), Math.toRadians(-2.31))
                .splineTo(new Vector2d(57.00, -12.22), Math.toRadians(0.00))
                .stopAndAdd(Robot.gripper.close())
                .setReversed(true)
                .splineTo(new Vector2d(23.70, -11.33), Math.toRadians(176.97))
                .stopAndAdd(new SequentialAction(
                        new ParallelAction(
                                Robot.lift.placeHigh(),
                                new SequentialAction(
                                        new SleepAction(1),
                                        Robot.pivot.toSide()
                                )
                        ),
                        Robot.gripper.open(),
                        Robot.pivot.toForward(),
                        Robot.lift.toGround()
                ))

                .build();
    }
}
