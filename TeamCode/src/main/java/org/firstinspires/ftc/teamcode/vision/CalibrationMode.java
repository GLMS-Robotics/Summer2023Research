package org.firstinspires.ftc.teamcode.vision;

import android.graphics.Bitmap;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.ActionSchedulerOpMode;
import org.firstinspires.ftc.teamcode.Robot;
import org.opencv.android.Utils;

@TeleOp(name="Camera Color Calibration", group="vision")
public class CalibrationMode extends ActionSchedulerOpMode {

    Bitmap camOut = Bitmap.createBitmap(640, 480, Bitmap.Config.RGB_565);

    @Override
    public void initOpMode() {

    }

    @Override
    public void loopOpMode(TelemetryPacket p) {

    }

    @Override
    public void init_loop()
    {
        if(getRuntime() > 5) {
            Utils.matToBitmap(Robot.camera.coneFinder.thresholded, camOut);
            FtcDashboard.getInstance().sendImage(camOut);
        }
    }

}
