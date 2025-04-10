import java.io.File;

import org.bytedeco.javacpp.FloatPointer;
import org.bytedeco.opencv.global.opencv_core;
import org.bytedeco.opencv.global.opencv_imgcodecs;
import org.bytedeco.opencv.global.opencv_imgproc;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.MatVector;
import org.bytedeco.opencv.opencv_core.Size;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class test {
	public static void main(String[] args) {
		Mat matimage = opencv_imgcodecs.imread("123_rgb.jpg");
		// STEP2. 降噪 (高斯模糊)
		opencv_imgproc.GaussianBlur(matimage, matimage, new Size(7, 7), 0);
		// STEP3. 轉換為 Lab 色彩空間
		Mat lab = new Mat();
		opencv_imgproc.cvtColor(matimage, lab, opencv_imgproc.COLOR_BGR2Lab);
		// STEP4. 拆分 Lab 通道
		MatVector channels = new MatVector();
		opencv_core.split(lab, channels);
		// STEP5. 調整 L 通道 (亮度增強)
		Mat lchannel = channels.get(0);
		opencv_core.normalize(lchannel, lchannel, 0, 255, opencv_core.NORM_MINMAX, -1, new Mat());
//		//STEP6. 合併回 Lab 圖像 儲存
		opencv_core.merge((MatVector) channels, lab);
		
//		Mat resizedImage = new Mat();
		// 提高解析度測試
//        opencv_imgproc.resize(matimage, resizedImage, new Size(matimage.cols() * 2, matimage.rows() * 2));  // 擴大 2 倍解析度
//        opencv_imgcodecs.imwrite("afteropencvimg_high_res.jpg", resizedImage); // 儲存放大後的圖片
		// 銳化測試
//		Mat sharpened = new Mat();
//		Mat kernel = new Mat(3, 3, opencv_core.CV_32F); // 3x3的濾波核
//		float[] kernelValues = {
//	            -1.0f, -1.0f, -1.0f,
//	            -1.0f, 9.0f, -1.0f,
//	            -1.0f, -1.0f, -1.0f
//	        };
//		FloatPointer floatPointer = new FloatPointer(kernelValues);
//		kernel.data().put(floatPointer);
//		opencv_imgproc.filter2D(matimage, sharpened, matimage.depth(), kernel);
//		opencv_imgcodecs.imwrite("sharpened_image.jpg", sharpened);
		//存儲無額外處理圖片
		opencv_imgcodecs.imwrite("afteropencvimg.jpg",lab);
		
		// 執行OCR辨識
		
		ITesseract instance = new Tesseract();
		String captchaText;
		try {
			captchaText = instance.doOCR(new File("C:\\ecworkspace\\fuck\\afteropencvimg.jpg"));
			System.out.println("識別的驗證碼是：" + captchaText);
		} catch (TesseractException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
