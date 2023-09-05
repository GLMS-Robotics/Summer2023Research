package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        /*myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(0, 0, 0))
                .lineToX(30)
                .turn(Math.toRadians(90))
                .lineToY(30)
                .turn(Math.toRadians(90))
                .lineToX(0)
                .turn(Math.toRadians(90))
                .lineToY(0)
                .turn(Math.toRadians(90))
                .build());*/

        //RightAutonomous r = new RightAutonomous();

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(33.99, -65.14, Math.toRadians(90.00)))
                .splineTo(new Vector2d(33.54, 0.15), Math.toRadians(90.39))
                .stopAndAdd(new SleepAction(1))
                //.afterDisp(0, new SequentialAction(
                //        Robot.pivot.toForward(),
                //        Robot.lift.toGround()
                //))
                .splineToSplineHeading(new Pose2d(48.00, -11.93, Math.toRadians(-1.62)), Math.toRadians(-2.31))
                .splineTo(new Vector2d(57.00, -12.22), Math.toRadians(0.00))
                .stopAndAdd(new SleepAction(1))
                .setReversed(true)
                .splineTo(new Vector2d(23.70, -11.33), Math.toRadians(176.97))
                .stopAndAdd(new SleepAction(1))

                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_POWERPLAY_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}

