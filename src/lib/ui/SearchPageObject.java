package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class SearchPageObject extends MainPageObject {

    private static final String
        SEARCH_INIT_ELEMENT = "//*[contains(@text,'Search Wikipedia')]",
        SEARCH_INPUT = "//*[contains(@text,'Search…')]",
        SEARCH_INPUT_ID = "org.wikipedia:id/search_src_text",
        SEARCH_CANCEL_BUTTON = "org.wikipedia:id/search_close_btn",
        SEARCH_RESULT_BY_SUBSTRING_TPL = "//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='{SUBSTRING}']",
        SEARCH_RESULT_ELEMENT = "//*[@resource-id='org.wikipedia:id/page_list_item_container']",
        SEARCH_EMPTY_RESULT_ELEMENT = "//*[@text='No results found']",
        ARTICLE_TITLE_ID = "org.wikipedia:id/view_page_title_text",
        ARTICLE_TITLE_XPATH = "//*[@text='Java (software platform)']";

    public SearchPageObject(AppiumDriver driver) {
        super(driver);
    }

    /* TEMPLATES METHODS */
    private static String getResultSearchElement(String substring) {
        return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}", substring);
    }
    /* TEMPLATES METHODS */

    public void initSearchInput() {
        this.waitForElementAndClick(By.xpath(SEARCH_INIT_ELEMENT),
                "Cannot find and click search init element",
                5
        );

        this.waitForElementPresent(By.xpath(SEARCH_INIT_ELEMENT),
                "Cannot find search input after cicking search init element",
                5
        );
    }

    public void waitForCancelButtonToAppear() {
        this.waitForElementPresent(By.id(SEARCH_CANCEL_BUTTON),
                "Cannot find search cancel button",
                5
                );
    }

    public void waitForCancelButtonToDisappear() {
        this.waitForElementNotPresent(By.id(SEARCH_CANCEL_BUTTON),
                "Search cancel button is still present",
                5
        );
    }

    public void clickCancelSearch() {
        this.waitForElementAndClick(By.id(SEARCH_CANCEL_BUTTON),
                "Cannot find and click search cancel button",
                5
        );
    }

    public void typeSearchLine(String search_line) {
        this.waitForElementAndSendKeys(By.xpath(SEARCH_INPUT),
                search_line,
                "Cannot find and type into search input",
                5
                );
    }

    public void waitForSearchResult(String substring) {
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementPresent(By.xpath(search_result_xpath),
                "Cannot find search result with substring " + substring,
                5
        );
    }

    public void clickByArticleWithSubString (String substring){
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementAndClick(By.xpath(search_result_xpath),
                "Cannot find and click search result with substring " + substring,
                10
        );
    }

    public int getAmountOfFoundArticles() {

        this.waitForElementPresent(
                By.xpath(SEARCH_RESULT_ELEMENT),
                "Cannot find anything by the request.",
                20
        );

        return this.getAmountOfElements(By.xpath(SEARCH_RESULT_ELEMENT));
    }

    public void waitForEmptyResultsLabel() {

        this.waitForElementPresent(
                By.xpath(SEARCH_EMPTY_RESULT_ELEMENT),
                "Cannot find empty result element",
                15
        );
    }

    public void assertThereIsNoResultOfSearch() {

        this.assertElementNotPresent(
                By.xpath(SEARCH_RESULT_ELEMENT),
                "We supposed not to find any results"
        );
    }

    public void clearSearchInput() {
        this.waitForElementAndClear(
                By.id(SEARCH_INPUT_ID),
                "Cannot find search field",
                10
        );
    }

    public void assertThereIsArticleWithTitle() {
        this.waitForElementPresent(By.id(ARTICLE_TITLE_ID),
                        "Cannot find article title",
                15);
    }

    public WebElement collectSearchResultAsElement() {
        return this.waitForElementAndClick(
                By.xpath(ARTICLE_TITLE_XPATH),
                "Cannot find article title",
                15
        );
    }
}
