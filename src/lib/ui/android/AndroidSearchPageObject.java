package lib.ui.android;

import io.appium.java_client.AppiumDriver;
import lib.ui.SearchPageObject;

public class AndroidSearchPageObject extends SearchPageObject {

    static {
        SEARCH_INIT_ELEMENT = "xpath://*[contains(@text,'Search Wikipedia')]";
        SEARCH_INPUT = "xpath://*[contains(@text,'Search…')]";
        SEARCH_INPUT_ID = "id:org.wikipedia:id/search_src_text";
        SEARCH_CANCEL_BUTTON = "id:org.wikipedia:id/search_close_btn";
        SEARCH_RESULT_BY_SUBSTRING_TPL = "xpath://*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='{SUBSTRING}']";
        SEARCH_RESULT_ELEMENT = "xpath://*[@resource-id='org.wikipedia:id/page_list_item_container']";
        SEARCH_EMPTY_RESULT_ELEMENT = "xpath://*[@text='No results found']";
        ARTICLE_TITLE_ID = "id:org.wikipedia:id/view_page_title_text";
        ARTICLE_TITLE_XPATH = "xpath://*[@text='Java (software platform)']";
        SEARCH_RESULT_ARTICLE_TITLE = "xpath://*[@resource-id='org.wikipedia:id/view_page_title_text']//*[@text='Java (programming language)']";
        SEARCH_RESULT_ARTICLE_ITEM_CONTAINER_TPL =
                "xpath:"
                        + "//*[@resource-id='org.wikipedia:id/search_container']"
                        + "//*[@resource-id='org.wikipedia:id/page_list_item_container']"
                        + "//*[@text='{TITLE_SUBSTRING}' or @text='{DESCRIPTION_SUBSTRING}']";
    }

    public AndroidSearchPageObject(AppiumDriver driver) {
        super(driver);
    }
}
