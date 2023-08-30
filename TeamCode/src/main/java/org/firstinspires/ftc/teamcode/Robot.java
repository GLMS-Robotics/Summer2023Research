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


    // We only want to initialize hardware once, so that encoders keep their positions
    private static boolean setUp = false;

    /**
     * Set up the robot's hardware, if it isn't set up yet.
     */
    public static void initHardware(HardwareMap hardwareMap)
    {
        if(!setUp)
        {
            mecanumDrive = new MecanumDrive(hardwareMap, new Pose2d(0,0,0));
            lift = new Lift(hardwareMap);
            pivot = new Pivot(hardwareMap);
            gripper = new Gripper(hardwareMap);
            camera = new Camera(hardwareMap);
            // Problem: MecanumDrive initializes in its constructor, and also needs extra
            // parameter for starting location!


            subsystems = new Subsystem[] {
                    mecanumDrive,
                    lift,
                    pivot,
                    gripper,
                    camera
            };

            setUp = true;
        }

        // Motor settings reset each opmode anyway, so set them every time
        for (Subsystem subsystem : subsystems) {
            subsystem.setMotorSettings();
        }
    }


}
