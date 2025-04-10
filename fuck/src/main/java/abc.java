import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;

import org.bytedeco.javacpp.FloatPointer;
import org.bytedeco.opencv.global.opencv_core;
import org.bytedeco.opencv.global.opencv_imgcodecs;
import org.bytedeco.opencv.global.opencv_imgproc;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.MatVector;
import org.bytedeco.opencv.opencv_core.Size;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;

public class abc {
	public static void main(String[] args) {
		Timer timer = new Timer();
		//取得當前系統時間
		Calendar calendar = Calendar.getInstance();
		// 設定爬蟲執行時間
		calendar.set(Calendar.HOUR_OF_DAY, 15); // 設定小時
		calendar.set(Calendar.MINUTE, 00); // 設定分鐘
		calendar.set(Calendar.SECOND, 00); // 設定秒
		Date executionTime = calendar.getTime();

		System.out.println("設定執行時間：" + executionTime);

		//調整執行開始時間 測試設定立即執行0 實際使用可用executionTime
		//TimerTask實作了runnable介面所以可以用run()
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				runCrawler();
			}
		}, 0);
	}

	// 爬蟲主程式
	public static void runCrawler() {
		// driver、連線資訊設定
		System.setProperty("webdriver.chrome.driver", "C:\\ecworkspace\\fuck\\driver\\chromedriver.exe");

		ChromeOptions options = new ChromeOptions();
		//設定身份的字串假裝是一般使用者的 User-Agent
		options.addArguments(
				"user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/110.0.5481.77 Safari/537.36");
		//隱藏 Selenium 自動化痕跡
		options.addArguments("--remote-allow-origins=*");
		options.setExperimentalOption("excludeSwitches", new String[] { "enable-automation" });
		options.setExperimentalOption("useAutomationExtension", false);

		WebDriver driver = new ChromeDriver(options);

		try {
			// 目標網頁->自動點擊
			driver.get("https://bank.sinopac.com/sinopacBT/personal/credit-card/discount/656945976.html");
			WebDriverWait wait = new WebDriverWait(driver, 5);

			//等待5秒直到特定條件可以被點擊 可根據需要的元素修改目標
			WebElement registerButton = wait.until(ExpectedConditions.elementToBeClickable(
					By.xpath("//a[@href='https://mma.sinopac.com/SinoCard/Activity/Register?Code=XB56']")));
			registerButton.click();
//			Thread.sleep(3000); 點擊過去以後已經有WebDriverWait不用這個

			// 切換分頁->自動輸入
			String mainWindowHandle = driver.getWindowHandle();
			Set<String> allWindowHandles = driver.getWindowHandles();
			for (String handle : allWindowHandles) {
				if (!handle.equals(mainWindowHandle)) {
					driver.switchTo().window(handle);
					break;
				}
			}
			WebElement idInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(
					"body > app-root > div > app-activity-register > div > div.outer_container > div > div > section > app-activity-register-verification > form > div.formblock > table > tbody > tr:nth-child(3) > td > input")));
			idInput.sendKeys("A1234567");

			// 目標網頁->驗證碼圖片下載辨識
			WebElement captchaImage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
					"/html/body/app-root/div/app-activity-register/div/div[2]/div/div/section/app-activity-register-verification/form/div[1]/table/tbody/tr[4]/td/div[2]/img")));

			// 執行 JavaScript 來抓取圖片 Blob，並轉換成 Base64 字串
			String base64Image = (String) ((JavascriptExecutor) driver).executeScript(
					"return new Promise((resolve) => {" + "    const img = document.createElement('img');"
							+ "    img.src = arguments[0].src;" + "    fetch(img.src).then(res => res.blob())"
							+ "        .then(blob => {" + "            const reader = new FileReader();"
							+ "            reader.onloadend = () => resolve(reader.result);"
							+ "            reader.readAsDataURL(blob);" + "        });" + "});",
					captchaImage);

			// 去掉 Base64 頭部 "data:image/png;base64,"
			String base64Data = base64Image.split(",")[1];
			// 將 Base64 解碼為位元組數據
			byte[] imageBytes = Base64.getDecoder().decode(base64Data);
			// 保存為 JPG 圖片並進行處理
			try (FileOutputStream fos = new FileOutputStream("123.jpg")) {
				fos.write(imageBytes);
				System.out.println("圖片已保存為 123.jpg");
				BufferedImage image = ImageIO.read(new File("123.jpg"));
				BufferedImage rgbImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
				rgbImage.getGraphics().drawImage(image, 0, 0, null);
				ImageIO.write(rgbImage, "JPEG", new File("123_rgb.jpg"));
				//去噪 增加對比
				//STEP1. 使用 OpenCV 讀取圖片
				Mat matimage = opencv_imgcodecs.imread("123_rgb.jpg");
				//STEP2. 降噪 (高斯模糊)
				opencv_imgproc.GaussianBlur(matimage, matimage, new Size(7, 7), 0);
				//STEP3. 轉換為 Lab 色彩空間
				Mat lab = new Mat();
				opencv_imgproc.cvtColor(matimage, lab, opencv_imgproc.COLOR_BGR2Lab);
				//STEP4. 拆分 Lab 通道
				MatVector channels = new MatVector();
				opencv_core.split(lab, channels);
				//STEP5.  調整 L 通道 (亮度增強)
				Mat lchannel = channels.get(0);
			    opencv_core.normalize(lchannel, lchannel, 0, 255, opencv_core.NORM_MINMAX, -1, new Mat());
//				//STEP6. 合併回 Lab 圖像 儲存
			    opencv_core.merge((MatVector) channels,lab);
			    opencv_imgcodecs.imwrite("afteropencvimg.jpg",lab);
			    //**可選** 
			    //銳化 
//			    Mat sharpened = new Mat();
//				Mat kernel = new Mat(3, 3, opencv_core.CV_32F); // 3x3的濾波核
//				float[] kernelValues = {
//			            -1.0f, -1.0f, -1.0f,
//			            -1.0f, 9.0f, -1.0f,
//			            -1.0f, -1.0f, -1.0f
//			        };
//				FloatPointer floatPointer = new FloatPointer(kernelValues);
//				kernel.data().put(floatPointer);
//				opencv_imgproc.filter2D(matimage, sharpened, matimage.depth(), kernel);
//				opencv_imgcodecs.imwrite("sharpened_image.jpg", sharpened);
			    //提高解析度
//			    opencv_imgproc.resize(matimage, resizedImage, new Size(matimage.cols() * 2, matimage.rows() * 2));  // 擴大 2 倍解析度
//		        opencv_imgcodecs.imwrite("afteropencvimg_high_res.jpg", resizedImage); // 儲存放大後的圖片
			}
			//執行OCR辨識
			ITesseract instance = new Tesseract();
			String captchaText = instance.doOCR(new File("C:\\ecworkspace\\fuck\\afteropencvimg.jpg"));
			System.out.println("識別的驗證碼是：" + captchaText);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
