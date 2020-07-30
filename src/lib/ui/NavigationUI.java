package lib.ui;

import io.appium.java_client.AppiumDriver;

abstract public class NavigationUI extends MainPageObject {

    protected static String
            MY_LISTS_LINK,

            ADD_TO_READING_LIST_XPATH,
            ONBOARDING_BUTTON_ID,
            READING_LIST_INPUT_ID,
            TEXT_OK,
            READING_LIST_NAME_TPL,
            MY_LISTS_XPATH,
            MY_ARTICLE_ID,
            NAVIGATE_UP_LINK;

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
                MY_LISTS_LINK,
                "Cannot find navigation button to my list",
                5
        );
    }

    public void clickMoreOptions(String locator) {
        this.waitForElementAndClick(
                locator,
                "Cannot find button to open More article options",
                5
        );
    }

    public void clickAddToReadingList() {
        this.waitForElementAndClick(
                ADD_TO_READING_LIST_XPATH,
                "Cannot find option to add article to reading list",
                5
        );
    }

    public void clickGotItOnOverlay() {
        this.waitForElementAndClick(
                ONBOARDING_BUTTON_ID,
                "Cannot find 'Got it' tip overlay",
                5
        );
    }

    public void cleanNameForReadingList() {
        this.waitForElementAndClear(
                READING_LIST_INPUT_ID,
                "Cannot find input to set name of articles folder",
                5
        );
    }

    public void setNameForReadingList(String reading_list_name) {
        this.waitForElementAndSendKeys(
                READING_LIST_INPUT_ID,
                reading_list_name,
                "Cannot put text into articles folder input",
                20
        );
    }

    public void confirmAddingArticleToReadingLIst() {
        this.waitForElementAndClick(
                TEXT_OK,
                "Cannot press OK button to save an article name to reading list",
                5
        );
    }

    public void selectExistedReadingList(String reading_list_name) {
        String reading_list_xpath = getResultReadingList(reading_list_name);
        this.waitForElementAndClick(
                reading_list_xpath,
                "Cannot find a list with name 'Java to read'",
                5
        );
    }

    public void openMyLists() {
        this.waitForElementAndClick(
                MY_LISTS_XPATH,
                "Cannot find My lists element",
                5
        );
    }

    public void openMyArticle() {
        this.waitForElementAndClick(
                MY_ARTICLE_ID,
                "Cannot find my article in the list",
                5
        );
    }

    public void removeFromTheListOptionForItem(String locator) {
        this.waitForElementAndClick(
                locator,
                "Cannot find item to remove link from the list",
                5
        );
    }

    public void navigateUpToCloseArticle() {
        this.waitForElementAndClick(
                NAVIGATE_UP_LINK,
                "Cannot close article, cannot find X link",
                5
        );
    }
}
