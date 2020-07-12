package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainPageObject {

    protected AppiumDriver driver;

    public MainPageObject(AppiumDriver driver) {
        this.driver = driver;
    }


    public boolean assertElementHasText(By by, String expected_text, String error_message) {
        WebElement element = waitForElementPresent(by, error_message, 5);
        return (element.getAttribute("text").equals(expected_text));
    }

    public WebElement waitForElementPresent(By by, String error_message, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public WebElement waitForElementPresent(By by, String error_message) {
        return waitForElementPresent(by, error_message, 5);
    }

    public WebElement waitForElementAndClick(By by, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.click();
        return element;
    }

    public WebElement waitForElementAndSendKeys(By by, String value, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.sendKeys(value);
        return element;
    }

    public boolean waitForElementNotPresent(By by, String error_message, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(by)
        );
    }

    public WebElement waitForElementAndClear(By by, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.clear();
        return element;
    }

    public void swipeUp(PointOption start, PointOption end, long duration) {
        AppiumDriver appiumDriver = (AppiumDriver) driver;
        TouchAction action = new TouchAction(driver);
        action
                .press(start)
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(duration)))
                .moveTo(end)
                .release()
                .perform();
    }

    public PointOption getPointOption(int x, int y) {
        return new PointOption().withCoordinates(x, y);
    }

    public void swipeUpQuick() {
        Dimension size = driver.manage().window().getSize();
        int x = size.width / 2;
        int start_y = (int) (size.height * 0.8);
        int end_y = (int) (start_y * 0.2);

        swipeUp(getPointOption(x, start_y), getPointOption(x, end_y), 10);
    }

    public void swipeUpToFindElement(By by, String error_message, int max_swipes) {
        int already_swiped = 0;
        while (driver.findElements(by).size() == 0) {
            if (already_swiped > max_swipes) {
                waitForElementPresent(by, "Cannot find element by swiping up \n" + error_message, 0);
                return;
            }

            swipeUpQuick();
            ++already_swiped;
        }
    }

    public void swipeElementToLeft(By by, String error_message) {
        WebElement element = waitForElementPresent(
                by,
                error_message,
                10);

        int left_x = element.getLocation().getX(); //left_x will contain left side point of the element by X axis
        int right_x = element.getSize().getWidth() + left_x;
        int upper_y = element.getLocation().getY();
        int lower_y = element.getSize().getHeight() + upper_y;
        int middle_y = (upper_y + lower_y) / 2;

        TouchAction action = new TouchAction(driver);
        action
                .press(getPointOption(right_x, middle_y))
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(5)))
                .moveTo(getPointOption(left_x, middle_y))
                .release()
                .perform();
    }

    public int getAmountOfElements(By by) {
        return driver.findElements(by).size();
    }

    public void assertElementNotPresent(By by, String error_message) {
        if (getAmountOfElements(by) > 0) {
            throw new AssertionError(
                    "An element '" + by.toString() + "' supposed to be not present" + " " + error_message);
        }
    }

    public void assertElementPresent(By by, String error_message) {
        WebElement item = driver.findElement(by);
        if (item.equals(null)) {
            throw new AssertionError(
                    "An expected element supposed not to be presented"
            );
        }
        String item_title = item.getAttribute("title");
    }

    public String waitForElementAndGetAttribute(By by, String attribute, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        return element.getAttribute(attribute);
    }

}
