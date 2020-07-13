package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class NavigationUI extends MainPageObject {

    private static final String
            MY_LISTS_LINK = "//android.widget.FrameLayout[@content-desc=\"My lists\"]",
            ADD_TO_READING_LIST_XPATH = "//*[@text='Add to reading list']",
            ONBOARDING_BUTTON_ID = "org.wikipedia:id/onboarding_button",
            READING_LIST_INPUT_ID = "org.wikipedia:id/text_input",
            TEXT_OK = "//*[@text='OK']",
            READING_LIST_NAME_TPL = "//*[@text='{SUBSTRING}']",
            MY_LISTS_XPATH = "//android.widget.FrameLayout[@content-desc=\"My lists\"]/android.widget.ImageView",
            MY_ARTICLE_ID = "org.wikipedia:id/page_list_item_title";

    /* TEMPLATES METHODS */
    private static String getResultReadingList(String substring) {
        return READING_LIST_NAME_TPL.replace("{SUBSTRING}", substring);
    }
    /* TEMPLATES METHODS */

    public NavigationUI(AppiumDriver driver) {
        super(driver);
    }


    public void clickMyList() {
        this.waitForElementAndClick(
                By.xpath(MY_LISTS_LINK),
                "Cannot find navigation button to my list",
                5
        );
    }

    public void clickMoreOptions(By by) {
        this.waitForElementAndClick(
                by,
                "Cannot find button to open More article options",
                5
        );
    }

    public void clickAddToReadingList() {
        this.waitForElementAndClick(
                By.xpath(ADD_TO_READING_LIST_XPATH),
                "Cannot find option to add article to reading list",
                5
        );
    }

    public void clickGotItOnOverlay() {
        this.waitForElementAndClick(
                By.id(ONBOARDING_BUTTON_ID),
                "Cannot find 'Got it' tip overlay",
                5
        );
    }

    public void cleanNameForReadingList() {
        this.waitForElementAndClear(
                By.id(READING_LIST_INPUT_ID),
                "Cannot find input to set name of articles folder",
                5
        );
    }

    public void setNameForReadingList(String reading_list_name) {
        this.waitForElementAndSendKeys(
                By.id(READING_LIST_INPUT_ID),
                reading_list_name,
                "Cannot put text into articles folder input",
                20
        );
    }

    public void confirmAddingArticleToReadingLIst() {
        this.waitForElementAndClick(
                By.xpath(TEXT_OK),
                "Cannot press OK button to save an article name to reading list",
                5
        );
    }

    public void selectExistedReadingList(String reading_list_name) {
        String reading_list_xpath = getResultReadingList(reading_list_name);
        this.waitForElementAndClick(
                By.xpath(reading_list_xpath),
                "Cannot find a list with name 'Java to read'",
                5
        );
    }

    public void openMyLists() {
        this.waitForElementAndClick(
                By.xpath(MY_LISTS_XPATH),
                "Cannot find My lists element",
                5
        );
    }

    public void openMyArticle() {
        this.waitForElementAndClick(
                By.xpath(MY_ARTICLE_ID),
                "Cannot find my article in the list",
                5
        );
    }

    public void removeFromTheListOptionForItem(By by) {
        this.waitForElementAndClick(
                by,
                "Cannot find item to remove link from the list",
                5
        );
    }

    public void navigateUpToCloseArticle() {
        this.waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot close article, cannot find X link",
                5
        );
    }
}
