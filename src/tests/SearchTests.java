package tests;

import lib.CoreTestCase;
import lib.ui.MainPageObject;
import lib.ui.SearchPageObject;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

public class SearchTests extends CoreTestCase {

    private lib.ui.MainPageObject MainPageObject;

    protected void setUp() throws Exception {

        super.setUp();
        MainPageObject = new MainPageObject(driver);
    }

    @Test
    public void testSearch() {

        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.waitForSearchResult("Object-oriented programming language");
    }

    @Test
    public void testCancelSearch() {

        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.waitForCancelButtonToAppear();
        SearchPageObject.clickCancelSearch();
        SearchPageObject.waitForCancelButtonToDisappear();
    }

    @Test
    //Ex3 - refactoring for Ex8 homework
    public void testCheckSearchResultAndCancelSearch() {

        SearchPageObject SearchPageObject = new SearchPageObject(driver);
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
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                10
        );

        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                search_string,
                "Cannot find search input",
                10
        );

        MainPageObject.waitForElementPresent(
                By.id(id_locator),
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
    public void testAmountOfNotEmptySearch() {

        SearchPageObject SearchPageObject = new SearchPageObject(driver);
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
    public void testAmountOfEmptySearch() {

        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();
        String search_line = "zxcvasdfqwer";
        SearchPageObject.typeSearchLine(search_line);
        SearchPageObject.waitForEmptyResultsLabel();
        SearchPageObject.assertThereIsNoResultOfSearch();

    }

    @Test
    public void testIfInputFieldContainsText() {

        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find search container",
                5
        );

        MainPageObject.waitForElementPresent(
                By.id("org.wikipedia:id/search_src_text"),
                "Cannot find search_input_field",
                5
        );

        MainPageObject.assertElementHasText(
                By.id("org.wikipedia:id/search_src_text"),
                "Search..",
                "Cannot find 'Search..' text within search input field"
        );
    }

    @Test
    public void testListOfSearchResults() {

        SearchPageObject SearchPageObject = new SearchPageObject(driver);

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
