package tests;

import lib.CoreTestCase;
import lib.ui.MainPageObject;
import lib.ui.SearchPageObject;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

public class SearchTests extends CoreTestCase {

    private static final String
            CONTAINS_TEXT_SEARCH = "//*[contains(@text,'Search…')]",
            CONTAINS_TEXT_SEARCH_WIKIPEDIA = "//*[contains(@text,'Search Wikipedia')]",
            ORG_WIKIPEDIA_ID_SEARCH_CONTAINER = "org.wikipedia:id/search_container",
            ORG_WIKIPEDIA_ID_SEARCH_SRC_TEXT = "org.wikipedia:id/search_src_text";

    private lib.ui.MainPageObject MainPageObject;

    protected void setUp() throws Exception {

        super.setUp();
        MainPageObject = new MainPageObject(driver);
    }

    @Test
    public void testSearch() throws Exception {

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.waitForSearchResult("Object-oriented programming language");
    }

    @Test
    public void testCancelSearch() throws Exception {

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.waitForCancelButtonToAppear();
        SearchPageObject.clickCancelSearch();
        SearchPageObject.waitForCancelButtonToDisappear();
    }

    @Test
    //Ex3 - refactoring for Ex8 homework
    public void testCheckSearchResultAndCancelSearch() throws Exception {

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Moscow");
        assertThat("Amount of found articles more than 0 ", (SearchPageObject.getAmountOfFoundArticles() > 0));

        SearchPageObject.clearSearchInput();
        SearchPageObject.clickCancelSearch();
        SearchPageObject.assertThereIsNoResultOfSearch();
    }

    @Test
    public void testMatchSearchResults() {

        String search_string = "Java";
        String id_locator = "org.wikipedia:id/page_list_item_title";

        MainPageObject.waitForElementAndClick(
                CONTAINS_TEXT_SEARCH_WIKIPEDIA,
                "Cannot find 'Search Wikipedia' input",
                10
        );

        MainPageObject.waitForElementAndSendKeys(
                CONTAINS_TEXT_SEARCH,
                search_string,
                "Cannot find search input",
                10
        );

        MainPageObject.waitForElementPresent(
                id_locator,
                "Doesn't get search result list",
                30
        );

        List<WebElement> elements = driver.findElements(By.id(id_locator));
        List<String> actual = new ArrayList<>();

        Iterator<WebElement> iterator = elements.iterator();
        while (iterator.hasNext()) {
            actual.add(iterator.next().getAttribute("text"));
        }

        for (String item : actual) {
            assertThat(search_string, item.contains(search_string));
        }
    }

    @Test
    public void testAmountOfNotEmptySearch() throws Exception {

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        String search_line = "Linkin Park Discography";
        SearchPageObject.typeSearchLine(search_line);
        int amount_of_search_results = SearchPageObject.getAmountOfFoundArticles();

        assertTrue(
                "We found too few results",
                amount_of_search_results > 0
        );
    }

    @Test
    public void testAmountOfEmptySearch() throws Exception {

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        String search_line = "zxcvasdfqwer";
        SearchPageObject.typeSearchLine(search_line);
        SearchPageObject.waitForEmptyResultsLabel();
        SearchPageObject.assertThereIsNoResultOfSearch();

    }

    @Test
    public void testIfInputFieldContainsText() {

        MainPageObject.waitForElementAndClick(
                ORG_WIKIPEDIA_ID_SEARCH_CONTAINER,
                "Cannot find search container",
                5
        );

        MainPageObject.waitForElementPresent(
                ORG_WIKIPEDIA_ID_SEARCH_SRC_TEXT,
                "Cannot find search_input_field",
                5
        );

        MainPageObject.assertElementHasText(
                ORG_WIKIPEDIA_ID_SEARCH_SRC_TEXT,
                "Search..",
                "Cannot find 'Search..' text within search input field"
        );
    }

    @Test
    public void testListOfSearchResults() throws Exception {

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");

        HashMap<String, String> testData = new HashMap<String, String>();
        testData.put("Java", "Island of Indonesia");
        testData.put("Java (programming language)", "Object-oriented programming language");
        testData.put("Java (software platform)", "Set of several computer software products and specifications");

        for (String i : testData.keySet()) {
            SearchPageObject.waitForElementByTitleAndDescription(i, testData.get(i));
        }
    }
}