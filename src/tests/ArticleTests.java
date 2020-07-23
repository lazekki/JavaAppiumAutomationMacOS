package tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.MainPageObject;
import lib.ui.SearchPageObject;
import org.junit.Test;

public class ArticleTests extends CoreTestCase {

    private static final String
            CONTAINS_TEXT_SEARCH_WIKIPEDIA = "//*[contains(@text,'Search Wikipedia')]",
            CONTAINS_TEXT_SEARCH = "//*[contains(@text,'Search…')]",
            RESOURCE_ID_ORG_WIKIPEDIA_ID_PAGE_LIST_ITEM_CONTAINER_TEXT_APPIUM =
                    "//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Appium']",
            ORG_WIKIPEDIA_ID_VIEW_PAGE_TITLE_TEXT = "org.wikipedia:id/view_page_title_text",
            TEXT_VIEW_PAGE_IN_BROWSER = "//*[@text='View page in browser']";

    private lib.ui.MainPageObject MainPageObject;

    protected void setUp() throws Exception {

        super.setUp();
        MainPageObject = new MainPageObject(driver);
    }

    @Test
    public void testCompareArticleTitle() {

        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubString("Object-oriented programming language");

        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);

        assertEquals(
                "We see unexpected title",
                "Java (programming language)",
                ArticlePageObject.getArticleTitle()
        );
    }

    @Test
    public void testSwipeArticle() {

        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Appium");
        SearchPageObject.clickByArticleWithSubString("Appium");

        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);

        ArticlePageObject.waitForTitleElement();
        ArticlePageObject.swipeToFooter();
    }

    @Test
    public void testSwipeArticleDownToPageFooter() {

        MainPageObject.waitForElementAndClick(
                CONTAINS_TEXT_SEARCH_WIKIPEDIA,
                "Cannot find 'Search Wikipedia' input",
                5
        );

        MainPageObject.waitForElementAndSendKeys(
                CONTAINS_TEXT_SEARCH,
                "Appium",
                "Cannot find search input",
                5
        );

        MainPageObject.waitForElementAndClick(
                RESOURCE_ID_ORG_WIKIPEDIA_ID_PAGE_LIST_ITEM_CONTAINER_TEXT_APPIUM,
                "Cannot find 'Search Wikipedia' input",
                5
        );

        MainPageObject.waitForElementPresent(
                ORG_WIKIPEDIA_ID_VIEW_PAGE_TITLE_TEXT,
                "Cannot find article title",
                15
        );

        MainPageObject.swipeUpToFindElement(
                TEXT_VIEW_PAGE_IN_BROWSER,
                "Cannot find the end of article",
                20
        );
    }

    @Test
    public void testIfArticleHasTitle() {

        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubString("Object-oriented programming language");
        SearchPageObject.assertThereIsArticleWithTitle();
    }
}
