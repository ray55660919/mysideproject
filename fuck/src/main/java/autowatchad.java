import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class autowatchad {
    private WebDriver driver;
    private WebDriverWait wait;

    static {
        // 禁用 Selenium 的日誌輸出
        Logger.getLogger("org.openqa.selenium").setLevel(Level.OFF);
        System.setProperty("webdriver.edge.silentOutput", "true");
    }

    public autowatchad() {
        // 設置 EdgeDriver 路徑
        System.setProperty("webdriver.edge.driver", "C:\\ecworkspace\\fuck\\driver\\msedgedriver.exe");
        
     // 配置 Edge 選項
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");
        
        // 安全性和隱私設定
        options.addArguments("--disable-web-security");
        options.addArguments("--allow-running-insecure-content");
        options.addArguments("--ignore-certificate-errors");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--remote-allow-origins=*");
        
        // 禁用自動化檢測
        options.addArguments("--disable-blink-features=AutomationControlled");
        
        // 禁用追蹤保護和隱私功能
        options.addArguments("--disable-features=IsolateOrigins,site-per-process");
        options.addArguments("--disable-features=BlockInsecurePrivateNetworkRequests");
        options.addArguments("--disable-features=msTrackingPrevention");
        options.addArguments("--disable-features=msEnhancedTrackingPrevention");
        
        // 允許第三方 Cookie 和跨域請求
        options.addArguments("--disable-features=SameSiteByDefaultCookies");
        options.addArguments("--disable-features=CookiesWithoutSameSiteMustBeSecure");
        
        // 禁用其他可能影響廣告載入的功能
        options.addArguments("--disable-features=PrivacySandboxAdsAPIsOverride");
        options.addArguments("--disable-features=PrivacySandboxSettings4");
        
        // 設定實驗性功能
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        options.setExperimentalOption("useAutomationExtension", false);
        
        // 初始化 WebDriver
        driver = new EdgeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    private void handleCloudflareChallenge() {
        try {
            Thread.sleep(5000);
            List<WebElement> iframes = driver.findElements(By.xpath("//iframe[contains(@src, 'challenges.cloudflare.com')]"));
            if (!iframes.isEmpty()) {
                System.out.println("檢測到 Cloudflare 驗證，等待處理...");
                Thread.sleep(15000);
            }
        } catch (Exception e) {
            System.out.println("處理 Cloudflare 驗證時發生錯誤: " + e.getMessage());
        }
    }

    public void start(String url) {
        try {
            // 訪問目標網頁
            driver.get(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void login(String username, String password) {
        try {
            // 點擊登入按鈕
            WebElement loginLink = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[contains(@href, 'login.php') and contains(text(), '我要登入')]")
            ));
            loginLink.click();

            // 等待登入表單出現
            wait.until(ExpectedConditions.presenceOfElementLocated(By.name("userid")));

            // 輸入帳號
            WebElement usernameField = driver.findElement(By.name("userid"));
            usernameField.clear();
            usernameField.sendKeys(username);

            // 輸入密碼
            WebElement passwordField = driver.findElement(By.name("password"));
            passwordField.clear();
            passwordField.sendKeys(password);

            // 點擊登入按鈕
            WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.id("btn-login")
            ));
            loginButton.click();

            // 等待登入完成
            wait.until(ExpectedConditions.urlContains("gamer.com.tw"));
            System.out.println("登入成功！");
        } catch (Exception e) {
            System.out.println("登入時發生錯誤: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void watchAdAndExchange() {
        for (int i = 0; i < 10; i++) {
            try {
                System.out.println("開始第 " + (i + 1) + " 次兌換流程");
                
                // 處理 Cloudflare 驗證
                handleCloudflareChallenge();
                
                // 嘗試觀看廣告，直到成功
                boolean adStarted = false;
                while (!adStarted) {
                    try {
                        // 點擊「看廣告免費兌換」按鈕
                        WebElement watchAdButton = wait.until(ExpectedConditions.elementToBeClickable(
                            By.xpath("//a[contains(@onclick, 'FuliAd.checkAd')]")
                        ));
                        watchAdButton.click();
                        System.out.println("已點擊看廣告按鈕");
                        Thread.sleep(3000);

                        // 等待並點擊「確定」按鈕
                        WebElement confirmButton = wait.until(ExpectedConditions.elementToBeClickable(
                            By.xpath("//button[@type='submit' and @class='btn btn-insert btn-primary']")
                        ));
                        confirmButton.click();
                        System.out.println("已點擊確定按鈕");
                        Thread.sleep(3000);

                        // TODO 檢查是否出現無法觀看廣告的提示視窗
                        List<WebElement> errorMessages = driver.findElements(
                            By.xpath("//div[contains(text(), '無法觀看廣告') or contains(text(), '廣告載入失敗')]")
                        );
                        
                        if (!errorMessages.isEmpty()) {
                            System.out.println("檢測到無法觀看廣告的提示，嘗試重試...");
                            // 點擊確認按鈕關閉提示視窗
                            WebElement errorConfirmButton = wait.until(ExpectedConditions.elementToBeClickable(
                                By.xpath("//button[contains(@class, 'btn') and contains(text(), '確認')]")
                            ));
                            errorConfirmButton.click();
                            System.out.println("已點擊確認按鈕關閉提示視窗");
                            Thread.sleep(3000);
                            continue;
                        }
                        // 如果沒有出現錯誤提示，表示可以觀看廣告
                        adStarted = true;
                    } catch (Exception e) {
                        System.out.println("嘗試觀看廣告時發生錯誤: " + e.getMessage());
                        Thread.sleep(3000);
                    }
                }

                // 等待廣告載入和播放
                System.out.println("等待廣告載入和播放...");
                Thread.sleep(25000);

                // 嘗試關閉廣告
                try {
                    List<WebElement> closeButtons = driver.findElements(
                        By.xpath("//button[contains(@class, 'close') or contains(@class, 'skip')]")
                    );
                    if (!closeButtons.isEmpty()) {
                        closeButtons.get(0).click();
                        System.out.println("已關閉廣告");
                    }
                } catch (Exception e) {
                    System.out.println("無法找到廣告關閉按鈕，嘗試繼續流程");
                    driver.navigate().refresh();
                }

                // 點擊「看廣告免費兌換」按鈕
                WebElement watchAdButton2 = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//a[contains(@onclick, 'FuliAd.checkAd')]")
                ));
                watchAdButton2.click();
                System.out.println("已點擊看廣告按鈕");

                // 等待並點擊「確定」按鈕
                WebElement confirmButton2 = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[@type='submit' and @class='btn btn-insert btn-primary']")
                ));
                confirmButton2.click();
                System.out.println("已點擊確定按鈕");

                // 處理同意確認框
                try {
                    WebElement agreeCheckbox = wait.until(ExpectedConditions.elementToBeClickable(
                        By.id("agree-confirm")
                    ));
                    agreeCheckbox.click();
                    System.out.println("已點擊同意確認框");
                } catch (Exception e) {
                    System.out.println("無法找到同意確認框，嘗試繼續流程");
                }

                // 點擊確認兌換按鈕
                WebElement exchangeButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//a[contains(@onclick, 'buy()')]")
                ));
                exchangeButton.click();
                System.out.println("已點擊確認兌換按鈕");
                Thread.sleep(5000);

                // 如果不是最後一次，返回商品頁面
                if (i < 9) {
                    WebElement returnButton = wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//button[contains(@onclick, 'shop_detail.php')]")
                    ));
                    returnButton.click();
                    System.out.println("已點擊返回按鈕");
                    Thread.sleep(8000);
                }
            } catch (Exception e) {
                System.out.println("第 " + (i + 1) + " 次觀看廣告或兌換時發生錯誤: " + e.getMessage());
                try {
                    Thread.sleep(15000);
                    driver.navigate().refresh();
                    Thread.sleep(8000);
                    continue;
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
            }
        }
    }

    public void close() {
        if (driver != null) {
            driver.quit();
        }
    }

    public static void main(String[] args) {
        autowatchad bot = new autowatchad();
        try {
            // 訪問目標網頁並登入
            bot.start("https://fuli.gamer.com.tw/shop_detail.php?sn=5156");
            bot.login("a55663613", "ray53166317");
            
            // 觀看廣告並兌換
            bot.watchAdAndExchange();
        } finally {
            // 保持瀏覽器開啟，讓用戶可以查看結果
            // bot.close();
        }
    }
} 
