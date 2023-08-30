package org.firstinspires.ftc.teamcode.subsystems;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.actions.ConditionalAction;
import org.firstinspires.ftc.teamcode.actions.ServoToPositionAction;

public class Gripper extends Subsystem{

    private Servo servo;

    private boolean isClosed = false;


    public Gripper(HardwareMap hardwareMap)
    {
        servo = hardwareMap.get(Servo.class, "Gripper");
    }



    /**
     * Method that runs each time the opmode loops.
     * Usually, you will want to output telemetry here (encoder values and such).
     * Warning: Actions may be running, so generally you shouldn't control
     * actuators here, unless you are doing something fun like a state machine
     * (in which case you probably shouldn't control actuators outside of this method...).
     * Instead, have your autonomous run actions, and your teleop schedule actions or run methods
     * directly.
     *
     * @param p Telemetry packet for sending data to the driver hub / dashboard
     */
    @Override
    public void periodic(TelemetryPacket p) {
        p.put("Gripper Closed", isClosed);
    }


    /**
     * Action to open the claw.
     * @return Action that opens the claw
     */
    public Action open()
    {
        return new SequentialAction(
                new ServoToPositionAction(servo, 0.68),
                new Action() {
                    @Override
                    public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                        isClosed = false;
                        return false;
                    }
                },
                new SleepAction(0.5)
        );
    }

    /**
     * Action to close the claw.
     * @return Action that closes the claw
     */
    public Action close()
    {
        return new SequentialAction(
                new ServoToPositionAction(servo, 0.45),
                new Action() {
                    @Override
                    public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                        isClosed = true;
                        return false;
                    }
                },
                new SleepAction(0.5)

        );
    }

    /**
     * Action that opens or closes the gripper, depending on whether it is open or closed already
     * @return Action that toggles the gripper
     */
    public Action toggleGripper()
    {
        return new ConditionalAction(
                this::isClosed,
                open(),
                close()
        );
    }

    public boolean isClosed()
    {
        return isClosed;
    }
}
