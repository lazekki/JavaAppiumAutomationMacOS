package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;

abstract public class SearchPageObject extends MainPageObject {

    protected static String
        SEARCH_INIT_ELEMENT,
        SEARCH_INPUT,
        SEARCH_INPUT_ID,
        SEARCH_CANCEL_BUTTON,
        SEARCH_RESULT_BY_SUBSTRING_TPL,
        SEARCH_RESULT_ELEMENT,
        SEARCH_EMPTY_RESULT_ELEMENT,
        ARTICLE_TITLE_ID,
        ARTICLE_TITLE_XPATH,
        SEARCH_RESULT_ARTICLE_TITLE,
        SEARCH_RESULT_ARTICLE_ITEM_CONTAINER_TPL;

    public SearchPageObject(AppiumDriver driver) {
        super(driver);
    }

    /* TEMPLATES METHODS */
    private static String getResultSearchElement(String substring) {
        return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}", substring);
    }

    public static String getResultSearchElementItem(String title_substring, String description_substring) {
        String temp = SEARCH_RESULT_ARTICLE_ITEM_CONTAINER_TPL.replace("{TITLE_SUBSTRING}", title_substring);
        return temp.replace("{DESCRIPTION_SUBSTRING}", description_substring);
    }
    /* TEMPLATES METHODS */

    public void initSearchInput() {
        this.waitForElementAndClick(SEARCH_INIT_ELEMENT,
                "Cannot find and click search init element",
                10
        );

        this.waitForElementPresent(SEARCH_INIT_ELEMENT,
                "Cannot find search input after cicking search init element",
                10
        );
    }

    public void waitForCancelButtonToAppear() {
        this.waitForElementPresent(SEARCH_CANCEL_BUTTON,
                "Cannot find search cancel button",
                5
                );
    }

    public void waitForCancelButtonToDisappear() {
        this.waitForElementNotPresent(SEARCH_CANCEL_BUTTON,
                "Search cancel button is still present",
                5
        );
    }

    public void clickCancelSearch() {
        this.waitForElementAndClick(SEARCH_CANCEL_BUTTON,
                "Cannot find and click search cancel button",
                10
        );
    }

    public void typeSearchLine(String search_line) {
        this.waitForElementAndSendKeys(SEARCH_INPUT,
                search_line,
                "Cannot find and type into search input",
                10
                );
    }

    public void waitForSearchResult(String substring) {
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementPresent(search_result_xpath,
                "Cannot find search result with substring " + substring,
                5
        );
    }

    public void clickByArticleWithSubString (String substring){
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementAndClick(search_result_xpath,
                "Cannot find and click search result with substring " + substring,
                10
        );
    }

    public int getAmountOfFoundArticles() {

        this.waitForElementPresent(
                SEARCH_RESULT_ELEMENT,
                "Cannot find anything by the request.",
                20
        );

        return this.getAmountOfElements(SEARCH_RESULT_ELEMENT);
    }

    public void waitForEmptyResultsLabel() {

        this.waitForElementPresent(
                SEARCH_EMPTY_RESULT_ELEMENT,
                "Cannot find empty result element",
                15
        );
    }

    public void assertThereIsNoResultOfSearch() {

        this.assertElementNotPresent(
                SEARCH_RESULT_ELEMENT,
                "We supposed not to find any results"
        );
    }

    public void clearSearchInput() {
        this.waitForElementAndClear(
                SEARCH_INPUT_ID,
                "Cannot find search field",
                10
        );
    }

    public void assertThereIsArticleWithTitle() {
        this.waitForElementPresent(ARTICLE_TITLE_ID,
                        "Cannot find article title",
                15);
    }

    public WebElement collectSearchResultAsElement() {
        return this.waitForElementAndClick(
                ARTICLE_TITLE_XPATH,
                "Cannot find article title",
                15
        );
    }

    public void assertIfArticleHasTitle() {
        this.assertElementPresent(
                SEARCH_RESULT_ARTICLE_TITLE,
                "Cannot get 'title'"
        );
    }

    //Ex8*
    public void waitForElementByTitleAndDescription(String title, String description) {
        String search_result_item_xpath = getResultSearchElementItem(title, description);
        this.waitForElementPresent(search_result_item_xpath,
                "Cannot find and click search result with title " + title + " and description " + description,
                10
        );
    }
}