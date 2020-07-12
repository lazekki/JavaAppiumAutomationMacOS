package tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.MainPageObject;
import lib.ui.SearchPageObject;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ArticleTests extends CoreTestCase {

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
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "Appium",
                "Cannot find search input",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Appium']"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        MainPageObject.waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                15
        );

        MainPageObject.swipeUpToFindElement(
                By.xpath("//*[@text='View page in browser']"),
                "Cannot find the end of article",
                20
        );
    }

    @Test
    public void testSaveTwoArticlesToMyListThenDeleteOneAndCheckLast() {
        //find the search field
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        //type 'Java'
        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "Java",
                "Cannot find search input",
                5
        );

        //open as first article the topic about Java (Object-oriented programming language)
        String first_article_xpath_text = "Object-oriented programming language";
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='" + first_article_xpath_text + "']"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        //check if first article is presented
        MainPageObject.waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                5
        );

        //open More options
        MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Cannot find button to open More article options",
                5
        );

        //click Add to reading list
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='Add to reading list']"),
                "Cannot find option to add article to reading list",
                5
        );

        //click Got it on overlay tip
        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/onboarding_button"),
                "Cannot find 'Got it' tip overlay",
                5
        );

        //clean the name for the new reading list
        MainPageObject.waitForElementAndClear(
                By.id("org.wikipedia:id/text_input"),
                "Cannot find input to set name of articles folder",
                5
        );

        //set the name for the new reading list
        String name_of_folder = "Java to read";
        MainPageObject.waitForElementAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                name_of_folder,
                "Cannot put text into articles folder input",
                20
        );

        //confirm that first article will be added to this new reading list
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='OK']"),
                "Cannot press OK button to save an article name to reading list",
                5
        );

        //close article to return back to search
        MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot close article, cannot find X link",
                5
        );

        //check that search input field is available
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        //repeat the search one more time
        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "Java",
                "Cannot find search input",
                5
        );

        //open as second article the topic about Java (Set of several computer software products and specifications)
        String second_article_xpath_text = "Set of several computer software products and specifications";
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='" + second_article_xpath_text + "']"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        //collect second article title as element
        WebElement second_article = MainPageObject.waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                15
        );

        //collect second article title
        String second_article_title = second_article.getAttribute("text");

        //open More options
        MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Cannot find button to open More article options",
                5
        );

        //click Add to reading list
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='Add to reading list']"),
                "Cannot find option to add article to reading list",
                5
        );

        //select already prepared reading list Java to read and click on it to save there second article
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='Java to read']"),
                "Cannot find a list with name 'Java to read'",
                5
        );

        //navigate back to search
        MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot close article, cannot find X link",
                5
        );

        //open My lists
        MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc=\"My lists\"]/android.widget.ImageView"),
                "Cannot find My lists element",
                5
        );

        //select already prepared Java to read reading list
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='Java to read']"),
                "Cannot find my 'Java - to read' reading list",
                5
        );

        //select More options for the first article in Java to read reading list
        MainPageObject.waitForElementAndClick(
                By.xpath("(//android.widget.ImageView[@content-desc=\"More options\"])[2]"),
                "Cannot find 'More options' for the reading list",
                5
        );

        //select remove from the list option for first article
        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/reading_list_item_remove_text"),
                "Cannot find item to remove link from the list",
                5
        );


        //collect last article into element
        WebElement article_in_list = MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='Java (software platform)']"),
                "Cannot find second article in list",
                5
        );

        //assure that this article is second article
        assertEquals(second_article_title, article_in_list.getAttribute("text"));

    }

    @Test
    public void testIfArticleHasTitle() {
        //find the search field
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        //type 'Java'
        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "Java",
                "Cannot find search input",
                5
        );

        //open the topic about Java (Object-oriented programming language)
        String article_xpath_text = "Object-oriented programming language";

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='"+article_xpath_text+"']"),
                "Cannot find required article",
                10
        );

        MainPageObject.assertElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/view_page_title_text']//*[@text='Java (programming language)']"),
                "Cannot get 'title'"
        );
    }
}
