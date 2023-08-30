package org.firstinspires.ftc.teamcode.vision;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.acmerobotics.dashboard.config.Config;

import org.firstinspires.ftc.robotcore.internal.camera.calibration.CameraCalibration;
import org.firstinspires.ftc.vision.VisionProcessor;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

@Config
public class ConeFinder implements VisionProcessor {

    public static class High {
        public int h = 10;
        public int s = 255;
        public int v = 255;
    }

    public static class Low {
        public int h = 0;
        public int s = 100;
        public int v = 100;
    }

    public static High HIGH = new High();
    public static Low LOW = new Low();

    //public static Scalar lower = new Scalar(0,0,0);
    //public static Scalar upper = new Scalar(255,255,255);

    public static double MIN_AREA = 500;

    public boolean hasResult = false;

    public class ConeDetection
    {
        public double x;
        public double y;
    }

    public Mat blurred = new Mat();
    public Mat hsv = new Mat();
    public Mat thresholded = new Mat();

    List<MatOfPoint> contours = new ArrayList<>();

    Rect boundingBox = new Rect();
    // Also the center of the box for later
    Point boxCenter = new Point();


    @Override
    public void init(int width, int height, CameraCalibration calibration) {
        //Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
    }

    @Override
    public Object processFrame(Mat frame, long captureTimeNanos) {

        // Blur the image slightly
        Imgproc.GaussianBlur(frame, blurred, new Size(11,11), 1.0);

        // To HSV
        Imgproc.cvtColor(blurred, hsv, Imgproc.COLOR_RGB2HSV);

        // Threshold the image
        Core.inRange(hsv, new Scalar(LOW.h, LOW.s, LOW.v), new Scalar(HIGH.h, HIGH.s, HIGH.v), thresholded);

        // Find contours (blobs) in the image
        contours.clear();
        Imgproc.findContours(thresholded, contours, new Mat(), Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);


        // Pick the biggest contour
        int biggestIndex = -1;
        double biggestArea = 0.0;
        for(int j=0;j<contours.size();j++)
        {
            // Check if this one is bigger
            double area = Imgproc.contourArea(contours.get(j));
            if(area > biggestArea && area > MIN_AREA)
            {
                biggestIndex = j;
                biggestArea = area;
            }
        }

        if(biggestIndex >= 0)
        {
            hasResult = true;

            boundingBox = Imgproc.boundingRect(contours.get(biggestIndex));
            boxCenter = new Point(
                    (boundingBox.br().x + boundingBox.tl().x)/2,
                    (boundingBox.br().y + boundingBox.tl().y)/2);
        }
        else
        {
            hasResult = false;
        }

        // Send a pipeline stage to the FTC dashboard for examination
        // Warning: This should be in an opmode
        //Utils.matToBitmap(thresholded, camOut);
        //FtcDashboard.getInstance().sendImage(camOut);


        return contours;
    }

    @Override
    public void onDrawFrame(Canvas canvas, int onscreenWidth, int onscreenHeight, float scaleBmpPxToCanvasPx, float scaleCanvasDensity, Object userContext) {

        Paint p = new Paint();
        p.setColor(Color.BLUE);
        p.setStrokeWidth(5);
        p.setStyle(Paint.Style.STROKE);

        if(hasResult) {
            canvas.drawRect(boundingBox.x * scaleBmpPxToCanvasPx, boundingBox.y * scaleBmpPxToCanvasPx,
                    (boundingBox.width + boundingBox.x) * scaleBmpPxToCanvasPx, (boundingBox.height + boundingBox.y) * scaleBmpPxToCanvasPx, p);
            //canvas.drawLine(boundingBox.x, boundingBox.y, boundingBox.x + boundingBox.width, boundingBox.y, p);
            //canvas.drawLine(boundingBox.x, boundingBox.y, boundingBox.x, boundingBox.y + boundingBox.height, p);
            //canvas.drawLine(boundingBox.x + boundingBox.width, boundingBox.y + boundingBox.height, boundingBox.x, boundingBox.y + boundingBox.height, p);
            //canvas.drawLine(boundingBox.x + boundingBox.width, boundingBox.y + boundingBox.height, boundingBox.x + boundingBox.width, boundingBox.y, p);
            // Draw contours on a mat
            //Mat overlay = new Mat(thresholded.size(), CV_8UC4);
            //Imgproc.drawContours(overlay, contours, -1, new Scalar(255,0,0),5);
        }
        //canvas.drawPoints(contours.get(0).toArray());

        // Attempt to manually draw contours
        // Not sure exactly what the contours data structure contains
        p.setColor(Color.YELLOW);

        for (MatOfPoint contour : contours) {
            Point[] pts = contour.toArray();

            for(int j=0; j<pts.length;j++)
            {
                canvas.drawPoint((float) pts[j].x * scaleBmpPxToCanvasPx,(float) pts[j].y * scaleBmpPxToCanvasPx, p);
            }
        }


    }
}
