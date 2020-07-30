package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import lib.Platform;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.regex.Pattern;

public class MainPageObject {

    protected AppiumDriver driver;

    public MainPageObject(AppiumDriver driver) {
        this.driver = driver;
    }


    public boolean assertElementHasText(String locator, String expected_text, String error_message) {
        WebElement element = waitForElementPresent(locator, error_message, 5);
        return (element.getAttribute("text").equals(expected_text));
    }

    public WebElement waitForElementPresent(String locator, String error_message, long timeoutInSeconds) {
        By by = this.getLocatorByString(locator);
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public WebElement waitForElementPresent(String locator, String error_message) {
        return waitForElementPresent(locator, error_message, 5);
    }

    public WebElement waitForElementAndClick(String locator, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(locator, error_message, timeoutInSeconds);
        element.click();
        return element;
    }

    public WebElement waitForElementAndSendKeys(String locator, String value, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(locator, error_message, timeoutInSeconds);
        element.sendKeys(value);
        return element;
    }

    public boolean waitForElementNotPresent(String locator, String error_message, long timeoutInSeconds) {
        By by = this.getLocatorByString(locator);
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(by)
        );
    }

    public WebElement waitForElementAndClear(String locator, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(locator, error_message, timeoutInSeconds);
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

    public void swipeUpToFindElement(String locator, String error_message, int max_swipes) {
        By by = this.getLocatorByString(locator);
        int already_swiped = 0;
        while (driver.findElements(by).size() == 0) {
            if (already_swiped > max_swipes) {
                waitForElementPresent(locator, "Cannot find element by swiping up \n" + error_message, 0);
                return;
            }

            swipeUpQuick();
            ++already_swiped;
        }
    }

    public void swipeUpTillElementAppear(String locator, String error_message, int max_swipes) {

        int already_swiped = 0;
        while (!this.isElementLocatedOnTheScreen(locator)) {

            if(already_swiped > max_swipes) {
                Assert.assertTrue(error_message, this.isElementLocatedOnTheScreen(locator));
            }

            swipeUpQuick();
            ++already_swiped;
        }

    }

    public boolean isElementLocatedOnTheScreen(String locator) {

        int element_location_by_y = this.waitForElementPresent(locator,
                "Cannot find element by locator",
                1).getLocation().getY();

        int screen_size_by_y = driver.manage().window().getSize().getHeight();

        return (element_location_by_y < screen_size_by_y);
    }

    public void clickElementToTheRightUpperCorner(String locator, String error_message) {
        //sample to move from locator to 1 level up (parent element): + "/.."
        WebElement element = this.waitForElementPresent(locator + "/..", error_message);

        int right_x = element.getLocation().getX();
        int upper_y = element.getLocation().getY();
        int lower_y = element.getSize().getHeight() + upper_y;
        int middle_y = (upper_y + lower_y) / 2;
        int width = element.getSize().getWidth();

        int point_to_click_x = (right_x + width) - 3;
        int point_to_click_y = middle_y;

        TouchAction action = new TouchAction(driver);
        action.tap(getPointOption(point_to_click_x, point_to_click_y)).perform();

    }

    public void swipeElementToLeft(String locator, String error_message) {
        WebElement element = waitForElementPresent(
                locator,
                error_message,
                10);

        int left_x = element.getLocation().getX(); //left_x will contain left side point of the element by X axis
        int right_x = element.getSize().getWidth() + left_x;
        int upper_y = element.getLocation().getY();
        int lower_y = element.getSize().getHeight() + upper_y;
        int middle_y = (upper_y + lower_y) / 2;

        TouchAction action = new TouchAction(driver);
        action.press(getPointOption(right_x, middle_y));
        action.waitAction(WaitOptions.waitOptions(Duration.ofSeconds(5)));
        /*
        * Swipe calculation for iOS starts from right size
        */
        if(Platform.getInstance().isAndroid()) {
            action.moveTo(getPointOption(left_x, middle_y));
        } else {
            int offset_x = (-1) * (element.getSize().getWidth());
            action.moveTo(getPointOption(offset_x, 0));
        }
        action.release();
        action.perform();
    }

    public int getAmountOfElements(String locator) {
        By by = this.getLocatorByString(locator);
        return driver.findElements(by).size();
    }

    public void assertElementNotPresent(String locator, String error_message) {
        if (getAmountOfElements(locator) > 0) {
            throw new AssertionError(
                    "An element '" + locator + "' supposed to be not present" + " " + error_message);
        }
    }

    public void assertElementPresent(String locator, String error_message) {
        WebElement item = driver.findElement(this.getLocatorByString(locator));
        if (item.equals(null)) {
            throw new AssertionError(
                    "An expected element supposed not to be presented"
            );
        }
        String item_title = item.getAttribute("title");
    }

    public String waitForElementAndGetAttribute(String locator, String attribute, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(locator, error_message, timeoutInSeconds);
        return element.getAttribute(attribute);
    }

    By getLocatorByString(String locator_with_type) {

        String[] exploded_locator = locator_with_type.split(Pattern.quote(":"), 2);
        String by_type = exploded_locator[0];
        String locator = exploded_locator[1];

        if (by_type.equals("xpath")) {
            return By.xpath(locator);
        } else if (by_type.equals("id")) {
            return By.id(locator);
        } else {
            throw new IllegalArgumentException("Cannot get type of locator. Locator: " + locator_with_type);
        }
    }



}
