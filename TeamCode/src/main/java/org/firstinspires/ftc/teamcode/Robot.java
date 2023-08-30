package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.subsystems.Camera;
import org.firstinspires.ftc.teamcode.subsystems.Gripper;
import org.firstinspires.ftc.teamcode.subsystems.Lift;
import org.firstinspires.ftc.teamcode.subsystems.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.Pivot;
import org.firstinspires.ftc.teamcode.subsystems.Subsystem;

/**
 * This class should hold static references to all your subsystems,
 * and an array of those subsystems for iteration by other classes.
 */
public class Robot {

    // Your Subsystems Here
    public static MecanumDrive mecanumDrive;
    public static Lift lift;

    public static Pivot pivot;

    public static Gripper gripper;

    public static Camera camera;


    // Also put an aray of them here
    public static Subsystem[] subsystems;

    /**
     * Set up the robot's hardware, if it isn't set up yet.
     */
    public static void initHardware(HardwareMap hardwareMap)
    {

        // We want to keep the current pose if this isn't our first op mode
        Pose2d startPose;
        if(mecanumDrive != null)
            startPose = mecanumDrive.pose;
        else
            startPose = new Pose2d(0,0,0);

        mecanumDrive = new MecanumDrive(hardwareMap, startPose);


        lift = new Lift(hardwareMap);
        pivot = new Pivot(hardwareMap);
        gripper = new Gripper(hardwareMap);
        camera = new Camera(hardwareMap);


        subsystems = new Subsystem[] {
                mecanumDrive,
                lift,
                pivot,
                gripper,
                camera
        };

    }


}
