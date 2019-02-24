// package frc.robot.subsystems;

// import frc.core.util.GripPipeline;
// import org.opencv.core.Rect;
// import org.opencv.imgproc.Imgproc;

// import edu.wpi.cscore.UsbCamera;
// import edu.wpi.first.wpilibj.CameraServer;
// import edu.wpi.first.wpilibj.vision.VisionRunner;
// import edu.wpi.first.wpilibj.vision.VisionThread;

// public class Camera2{

//     private static final int IMG_WIDTH = 320;
// 	private static final int IMG_HEIGHT = 240;
	
// 	private VisionThread visionThread;

//     private final Object imgLock = new Object();
    
//     public Camera2(){
//         UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
//         camera.setResolution(IMG_WIDTH, IMG_HEIGHT);
    
//         visionThread = new VisionThread(camera, new GripPipeline(), pipeline -> {
//             System.out.println(pipeline. "Zack pau mole");
//         });
//         visionThread.start();
//     }
// }