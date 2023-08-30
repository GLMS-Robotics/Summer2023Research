package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.actions.MotorToPositionAction;

@Config
public class Lift extends Subsystem{


    private DcMotorEx motor;


    // PIDF parameters
    public static volatile double vp = 2.048;
    public static volatile double vi = 0.2048;
    public static volatile double vd = 0;
    public static volatile double vf = 20.48;

    public static volatile double pp = 5;

    private double maxSpeed = 0;

    public Lift(HardwareMap hardwareMap)
    {
        // Set up motors etc
        motor = (DcMotorEx) hardwareMap.get(DcMotor.class, "Lift");

        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        // Max 2400

        // PID parameters
        motor.setVelocityPIDFCoefficients(vp, vi, vd, vf);
        motor.setPositionPIDFCoefficients(pp);

    }



    @Override
    public void periodic(TelemetryPacket p) {

        p.put("Lift Encoder", motor.getCurrentPosition());

        p.put("Lift Current", motor.getCurrent(CurrentUnit.AMPS));
        p.put("Lift Power", motor.getPower());

        double s = Math.abs(motor.getVelocity());
        if (s > maxSpeed) {
            maxSpeed = s;
            p.put("Lift Max Speed", maxSpeed);
        }
    }

    public void setSpeed(double speed)
    {
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motor.setPower(speed);
    }

    public Action toPositionAction(int position, int error, double speed)
    {
        return new MotorToPositionAction(motor, position, error, speed);
    }

    public Action placeHigh()
    {
        return toPositionAction(-2000, 150, 1.0);
    }

    public Action toGround()
    {
        return toPositionAction(0, 50, 0.9);
    }

}
