package core;




import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class MobileDriver {

    private static UiAutomator2Options options;
    private static AppiumDriver driver;

    public static AppiumDriver getAppiumDriver() {

        if (driver == null) {
            if (ConfigReader.getProperty("platformName").equals("Android")) {
                options = new UiAutomator2Options()
                        .setAppPackage(ConfigReader.getProperty("appPackage"))
                        .setAppActivity(ConfigReader.getProperty("appActivity"))
                        .setUdid("emulator-5554")
                        .setAutomationName("uiautomator2")
                        .setNoReset(false)
                        .setNewCommandTimeout(Duration.ofMinutes(10));


                try {
                    driver = new AndroidDriver(
                            new URL("http://127.0.0.1:4723"), options
                    );
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        return driver;
    }

    public static void closeMobilDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
