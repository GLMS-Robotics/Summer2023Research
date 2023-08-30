package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.MotorControlAlgorithm;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

import org.firstinspires.ftc.teamcode.actions.MotorToPositionAction;

@Config
public class Pivot extends Subsystem {

    private DcMotorEx motor;


    public static volatile PIDFCoefficients pidvel = new PIDFCoefficients(1.223,0.1223,0,12.23, MotorControlAlgorithm.PIDF);
    public static volatile PIDFCoefficients pidpos = new PIDFCoefficients(5,0,0,0, MotorControlAlgorithm.PIDF);


    public double maxSpeed = 0;

    public Pivot(HardwareMap hardwareMap)
    {
        motor = (DcMotorEx) hardwareMap.get(DcMotor.class, "Pivot");

        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        motor.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidvel);
        motor.setPIDFCoefficients(DcMotor.RunMode.RUN_TO_POSITION, pidpos);
        // Mid -800
        // Max -1400
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

        p.put("Pivot Encoder", motor.getCurrentPosition());

        double s = motor.getVelocity();
        if(s > maxSpeed)
        {
            maxSpeed = s;
            p.put("Pivot Max Speed", maxSpeed);
        }
    }

    public void setSpeed(double s)
    {
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motor.setPower(s);
    }


    public Action toPositionAction(int position, int error, double speed)
    {
        return new MotorToPositionAction(motor, position, error, speed);
    }

    public Action toForward()
    {
        return toPositionAction(0, 20, 1.0);
    }

    public Action toSide()
    {
        return toPositionAction(-800, 20, 1.0);
    }


}
