package frc.robot.subsystems;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoMode.PixelFormat;

public class Camera{
  /*public Camera() {
        new Thread(() -> {
            CameraServer.getInstance().startAutomaticCapture();
        }).start();
    } */

    public Camera(){
            UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
            camera.setResolution(640, 480);
            
            CvSink cvSink = CameraServer.getInstance().getVideo();
            CvSource outputStream = new CvSource("Blur", PixelFormat.kMJPEG, 640, 480, 10);
            
            Mat source = new Mat();
            Mat output = new Mat();

            cvSink.grabFrame(source);
            // Imgproc.cvtColor(source, output, Imgproc.COLOR_BGR2GRAY);
            outputStream.putFrame(output);
    }
}
