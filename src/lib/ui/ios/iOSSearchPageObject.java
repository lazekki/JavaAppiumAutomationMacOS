package lib.ui.ios;

import io.appium.java_client.AppiumDriver;
import lib.ui.SearchPageObject;

public class iOSSearchPageObject extends SearchPageObject {

    static {
        //SEARCH_INIT_ELEMENT = "xpath://XCUIElementTypeSearchField[@name='Search Wikipedia']"; - don't work for iOS after VI.05.
        SEARCH_INIT_ELEMENT = "id:Search Wikipedia";
        //SEARCH_INPUT = "xpath://XCUIElementTypeSearchField[@value='Search Wikipedia']"; - don't work for iOS after VI.05.
        SEARCH_INPUT = "id:Search Wikipedia";
        SEARCH_INPUT_ID = "id:Search Wikipedia";
        SEARCH_CANCEL_BUTTON = "id:Close";
        SEARCH_RESULT_BY_SUBSTRING_TPL = "xpath://XCUIElementTypeLink[contains(@name, '{SUBSTRING}')]";
        SEARCH_RESULT_ELEMENT = "xpath://XCUIElementTypeLink";
        SEARCH_EMPTY_RESULT_ELEMENT = "xpath://XCUIElementTypeStaticText[@name='No results found']";
        ARTICLE_SEARCH_FIRST_TITLE_XPATH = "xpath://XCUIElementTypeOther[contains(@name, 'Java (programming language)')]";
        ARTICLE_SEARCH_SECOND_TITLE_XPATH = "xpath://XCUIElementTypeOther[contains(@name, 'Java (software platform)')]";
        //TO DO for iOS tests:
        //ARTICLE_TITLE_ID = "id:org.wikipedia:id/view_page_title_text";
        ARTICLE_TITLE_XPATH = "xpath://*[@name='Java (software platform)']";
        ARTICLE_IN_SAVED_LIST_TITLE_XPATH = "xpath://XCUIElementTypeLink[contains(@name, 'Java (software platform) Set of several computer software products and specifications')]";
        FIRST_ARTICLE_TITLE_XPATH = "xpath://*[@name='Java (programming language)']";
        //SEARCH_RESULT_ARTICLE_TITLE = "xpath://*[@resource-id='org.wikipedia:id/view_page_title_text']//*[@text='Java (programming language)']";
        SEARCH_RESULT_ARTICLE_ITEM_CONTAINER_TPL =
                "xpath://XCUIElementTypeLink[contains(@name, '{TITLE_SUBSTRING}') or contains (@name, '{DESCRIPTION_SUBSTRING}')]";
    }

    public iOSSearchPageObject(AppiumDriver driver) {
        super(driver);
    }
}
