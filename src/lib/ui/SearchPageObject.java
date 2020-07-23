package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;

public class SearchPageObject extends MainPageObject {

    private static final String
        SEARCH_INIT_ELEMENT = "xpath://*[contains(@text,'Search Wikipedia')]",
        SEARCH_INPUT = "xpath://*[contains(@text,'Search…')]",
        SEARCH_INPUT_ID = "id:org.wikipedia:id/search_src_text",
        SEARCH_CANCEL_BUTTON = "id:org.wikipedia:id/search_close_btn",
        SEARCH_RESULT_BY_SUBSTRING_TPL = "xpath://*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='{SUBSTRING}']",
        SEARCH_RESULT_ELEMENT = "xpath://*[@resource-id='org.wikipedia:id/page_list_item_container']",
        SEARCH_EMPTY_RESULT_ELEMENT = "xpath://*[@text='No results found']",
        ARTICLE_TITLE_ID = "id:org.wikipedia:id/view_page_title_text",
        ARTICLE_TITLE_XPATH = "xpath://*[@text='Java (software platform)']",
        SEARCH_RESULT_ARTICLE_TITLE = "xpath://*[@resource-id='org.wikipedia:id/view_page_title_text']//*[@text='Java (programming language)']",
        SEARCH_RESULT_ARTICLE_ITEM_CONTAINER_TPL =
                "xpath:"
              + "//*[@resource-id='org.wikipedia:id/search_container']"
              + "//*[@resource-id='org.wikipedia:id/page_list_item_container']"
              + "//*[@text='{TITLE_SUBSTRING}' or @text='{DESCRIPTION_SUBSTRING}']";

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
                5
        );

        this.waitForElementPresent(SEARCH_INIT_ELEMENT,
                "Cannot find search input after cicking search init element",
                5
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
                5
        );
    }

    public void typeSearchLine(String search_line) {
        this.waitForElementAndSendKeys(SEARCH_INPUT,
                search_line,
                "Cannot find and type into search input",
                5
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