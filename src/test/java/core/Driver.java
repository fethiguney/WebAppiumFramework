package core;




import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.time.Duration;

public class Driver {

    private static WebDriver driver = null;

    public static WebDriver getDriver() {
        if(driver==null){
            driver = initialize();
        }
        return driver;
    }

    public static WebDriver initialize(){
        if (driver == null) {
            String browser = System.getProperty("browser") != null ? browser = System.getProperty("browser") : ConfigReader.getProperty("browser");
            switch (browser) {
                case "chrome" -> {
                    ChromeOptions options = new ChromeOptions();
                    options.addArguments("--remote-allow-origins=*");
                    driver = new ChromeDriver();
                }
                case "edge" -> driver = new EdgeDriver();
                case "firefox" -> driver = new FirefoxDriver();
                case "safari" -> {
                    if (!System.getProperty("os.name").toLowerCase().contains("mac"))
                        throw new WebDriverException("Your OS doesn't support Safari");
                    driver = new SafariDriver();
                }
                case "headless-chrome" -> {
                    ChromeOptions options = new ChromeOptions();
                    options.setHeadless(true);
                    driver = new ChromeDriver(options);
                }
            }
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(15));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        return driver;
    }


    public static void closeWebDriver(){
        if (driver!=null) {
            getDriver().manage().deleteAllCookies();
            driver.close();
            driver=null;
        }

    }

    public static void quitDrivers(){
        if (driver!=null) {
            driver.quit();
            driver=null;
        }
    }

}