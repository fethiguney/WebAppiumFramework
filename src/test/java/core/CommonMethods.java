package core;

import com.google.common.collect.ImmutableMap;
import core.enums.ClickType;
import core.enums.MobileDirections;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static core.Driver.getDriver;
import static core.MobileDriver.getAppiumDriver;

public class CommonMethods {


    public static void click(WebElement element, ClickType clickType){
        JavascriptExecutor js=(JavascriptExecutor) getDriver();
        Actions actions = new Actions(getDriver());
        switch (clickType) {
            case DEFAULT -> element.click();
            case ACTIONS -> actions.moveToElement(element).click().perform();
            case JSEXECUTOR -> {
                js.executeScript("arguments[0].scrollIntoView();", element);
                js.executeScript("arguments[0].click();", element);
            }
        }
    }

    public static void sendKeysJS(WebElement element, String keysToSend) {
        JavascriptExecutor js=(JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].value='"+ keysToSend +"';", element);
    }


    public static void longPress(WebElement element) {
        ((JavascriptExecutor) getAppiumDriver()).executeScript(
                "mobile: longClickGesture",
                ImmutableMap.of("elementId", ((RemoteWebElement) element).getId()
                ));
    }

    public static void doubleClick(WebElement element) {
        ((JavascriptExecutor) getAppiumDriver()).executeScript(
                "mobile: doubleClickGesture",
                ImmutableMap.of("elementId", ((RemoteWebElement) element).getId()
                ));
    }

    public static void clickMobilJS(WebElement element) {
        waitForClickAbilityMobil(element, 10);
        ((JavascriptExecutor) getAppiumDriver()).executeScript(
                "mobile: clickGesture",
                ImmutableMap.of("elementId", ((RemoteWebElement) element).getId()
                ));
    }

    public static void dragAndDrop(WebElement element, double endX, double endY) {
        ((JavascriptExecutor) getAppiumDriver()).executeScript(
                "mobile: dragGesture",
                ImmutableMap.of(
                        "elementId", ((RemoteWebElement) element).getId(),
                        "endX", endX,
                        "endY", endY,
                        "speed", 5000
                ));
    }

    public static void dragAndDrop(double startX, double startY, double endX, double endY) {
        ((JavascriptExecutor) getAppiumDriver()).executeScript(
                "mobile: dragGesture",
                ImmutableMap.of(
                        "startX", startX,
                        "startY", startY,
                        "endX", endX,
                        "endY", endY,
                        "speed", 5000
                ));
    }

    public static void dragAndDrop(double startX, double startY, double endX, double endY, int speed1to5) {
        ((JavascriptExecutor) getAppiumDriver()).executeScript(
                "mobile: dragGesture",
                ImmutableMap.of(
                        "startX", startX,
                        "startY", startY,
                        "endX", endX,
                        "endY", endY,
                        "speed", 1000 * speed1to5
                ));
    }

    public static void dragAndDrop(WebElement element, WebElement targetElement) {
        Actions actions = new Actions(getAppiumDriver());
        actions.clickAndHold(element)
                .moveToElement(targetElement)
                .release()
                .perform();
    }

    public static Point getMiddlePointOfTheScreen() {
        Dimension dimension = getAppiumDriver().manage().window().getSize();
        return new Point((int) (dimension.width * 0.5), (int) (dimension.height * 0.5));
    }

    public static void swipe(MobileDirections direction, int speed1to100, int count) {
        Point midPoint = getMiddlePointOfTheScreen();
        for (int i = 0; i < count; i++) {
            waitFor(2);
            ((JavascriptExecutor) getAppiumDriver()).executeScript("mobile: swipeGesture",
                    ImmutableMap.of(
                            "left", midPoint.x * 0.5,
                            "top", midPoint.y * 0.5,
                            "width", midPoint.x,
                            "height", midPoint.y,
                            "direction", direction.toString(),
                            "percent", 0.75,
                            "speed", speed1to100 * 1000
                    ));
        }
    }

    public static void swipe(WebElement element, MobileDirections direction, int speed1to100) {
        waitFor(1);
        ((JavascriptExecutor) getAppiumDriver()).executeScript("mobile: swipeGesture",
                ImmutableMap.of(
                        "elementId", ((RemoteWebElement) element).getId(),
                        "direction", direction.toString(),
                        "percent", 0.75,
                        "speed", speed1to100 * 1000
                ));
    }

    public static void scroll(MobileDirections direction, int count) {
        Point midPoint = getMiddlePointOfTheScreen();
        for (int i = 0; i < count; i++) {
            waitFor(2);
            ((JavascriptExecutor) getAppiumDriver()).executeScript("mobile: scrollGesture",
                    ImmutableMap.of(
                            "left", midPoint.x * 0.5,
                            "top", midPoint.y * 0.5,
                            "width", midPoint.x,
                            "height", midPoint.y,
                            "direction", direction.toString(),
                            "percent", 30,
                            "speed", 4000
                    ));
        }
    }

    public static void scroll(WebElement element, MobileDirections direction) {
        waitFor(2);
        ((JavascriptExecutor) getAppiumDriver()).executeScript("mobile: scrollGesture",
                ImmutableMap.of(
                        "elementId", ((RemoteWebElement) element).getId(),
                        "direction", direction.toString(),
                        "percent", 3
                ));
    }

    public static void scrollIntoView(String uiSelector) {
        String command = "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(" + uiSelector + ");";
        getAppiumDriver().findElement(AppiumBy.androidUIAutomator(command));
    }

    public static void waitFor(int sec) {
        try {
            Thread.sleep(sec * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void scrollRight(WebElement element, int swipePercentage) {
        Actions actions = new Actions(getAppiumDriver());
        waitForClickAbilityMobil(element, 10);
        actions.clickAndHold(element)
                .moveByOffset(calculateOffsetForLeftAndRightScroll(swipePercentage), 0)
                .release()
                .perform();
    }

    public static void scrollLeft(WebElement element, int swipePercentage, int count) {
        for (int i = 0; i < count; i++) {
            waitForClickAbilityMobil(element, 10);
            Actions actions = new Actions(getAppiumDriver());
            actions.clickAndHold(element)
                    .moveByOffset(-calculateOffsetForLeftAndRightScroll(swipePercentage), 0)
                    .release()
                    .perform();
            waitFor(1);
        }
    }

    public static void scrollDown(WebElement element, int scrollPercentage) {
        waitForClickAbilityMobil(element, 10);
        Actions actions = new Actions(getAppiumDriver());
        actions.clickAndHold(element)
                .moveByOffset(0, -(calculateOffsetForUpAndDownScroll(scrollPercentage)))
                .release()
                .perform();
        waitFor(1);
        System.out.println(calculateOffsetForUpAndDownScroll(scrollPercentage));
    }

    public static void scrollUp(WebElement element, int scrollPercentage) {
        waitForClickAbilityMobil(element, 10);
        Actions actions = new Actions(getAppiumDriver());
        actions.clickAndHold(element)
                .moveByOffset(0, (calculateOffsetForUpAndDownScroll(scrollPercentage)))
                .release()
                .perform();
        waitFor(1);
    }


    private static int calculateOffsetForUpAndDownScroll(int scrollAmount) {
        Dimension dimension = getAppiumDriver().manage().window().getSize();
        return ((scrollAmount * dimension.height) / 100);
    }

    private static int calculateOffsetForLeftAndRightScroll(int scrollAmount) {
        Dimension dimension = getAppiumDriver().manage().window().getSize();
        return ((scrollAmount * dimension.width) / 100);
    }
    public static WebElement waitForClickAbilityMobil(WebElement element, int timeout) {
        WebDriverWait wait = new WebDriverWait(getAppiumDriver(), Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public static WebElement waitForVisibilityMobil(WebElement element, int timeout) {
        WebDriverWait wait = new WebDriverWait(getAppiumDriver(), Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    public static WebElement waitForVisibilityWeb(WebElement element, int timeout){
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    public static WebElement waitForClickAbilityWeb(WebElement element, int timeout) {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public static void waitForStalenessOfElementWeb(WebElement element, int timeout) {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(timeout));
        wait.until(ExpectedConditions.refreshed(ExpectedConditions.stalenessOf(element)));

    }
}
