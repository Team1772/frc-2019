// package frc.robot.subsystems;

// import org.opencv.core.Mat;
// import org.opencv.imgproc.Imgproc;

// import edu.wpi.cscore.CvSink;
// import edu.wpi.cscore.CvSource;
// import edu.wpi.cscore.UsbCamera;

// import edu.wpi.first.wpilibj.CameraServer;


// public class Camera{
    
//     UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
                    
//     CvSink cvSink = CameraServer.getInstance().getVideo();
//     CvSource outputStream = CameraServer.getInstance().putVideo("Gray", 640, 480);

//     public Camera(){

//                 camera.setResolution(640, 480);

//                 Mat source = new Mat();
//                 Mat output = new Mat();
                
//                 cvSink.grabFrame(source);
//                 Imgproc.
//                 outputStream.putFrame(output);

//     }
// }
