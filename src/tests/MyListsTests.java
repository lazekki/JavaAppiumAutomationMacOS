package tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.MyListsPageObject;
import lib.ui.NavigationUI;
import lib.ui.SearchPageObject;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class MyListsTests extends CoreTestCase {

    private static final String
            MORE_ARTICLE_OPTIONS_XPATH = "//android.widget.ImageView[@content-desc='More options']",
            MORE_ARTICLE_OPTIONS_XPATH_2 = "(//android.widget.ImageView[@content-desc=\"More options\"])[2]",
            READING_LIST_REMOVE_OPTION_ID = "org.wikipedia:id/reading_list_item_remove_text";

    @Test
    public void testSaveFirstArticleToMyList() {

        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubString("Object-oriented programming language");

        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        ArticlePageObject.waitForTitleElement();

        String article_title = ArticlePageObject.getArticleTitle();
        String name_of_folder = "Learning programming";

        ArticlePageObject.addArticleToMyList(name_of_folder);
        ArticlePageObject.closeArticle();

        NavigationUI NavigationUI = new NavigationUI(driver);
        NavigationUI.clickMyList();

        MyListsPageObject MyListPageObject = new MyListsPageObject(driver);
        MyListPageObject.openFolderByName(name_of_folder);
        MyListPageObject.swipeByArticleToDelete(article_title);
    }

    @Test
    //Ex5 - refactoring for Ex8 homework
    public void testSaveTwoArticlesToMyListThenDeleteOneAndCheckLast() {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubString("Object-oriented programming language");
        SearchPageObject.assertThereIsArticleWithTitle();

        NavigationUI NavigationUI = new NavigationUI(driver);

        NavigationUI.clickMoreOptions(By.xpath(MORE_ARTICLE_OPTIONS_XPATH));
        NavigationUI.clickAddToReadingList();
        NavigationUI.clickGotItOnOverlay();
        NavigationUI.cleanNameForReadingList();
        NavigationUI.setNameForReadingList("Java to read");
        NavigationUI.confirmAddingArticleToReadingLIst();
        NavigationUI.navigateUpToCloseArticle();

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubString("Set of several computer software products and specifications");
        SearchPageObject.assertThereIsArticleWithTitle();

        WebElement second_article = SearchPageObject.collectSearchResultAsElement();
        String second_article_title = second_article.getAttribute("text");

        NavigationUI.clickMoreOptions(By.xpath(MORE_ARTICLE_OPTIONS_XPATH));
        NavigationUI.clickAddToReadingList();
        NavigationUI.selectExistedReadingList("Java to read");
        NavigationUI.navigateUpToCloseArticle();
        NavigationUI.openMyLists();
        NavigationUI.selectExistedReadingList("Java to read");
        NavigationUI.clickMoreOptions(By.xpath(MORE_ARTICLE_OPTIONS_XPATH_2));
        NavigationUI.removeFromTheListOptionForItem(By.id(READING_LIST_REMOVE_OPTION_ID));

        WebElement article_in_list = SearchPageObject.collectSearchResultAsElement();
        assertEquals(second_article_title, article_in_list.getAttribute("text"));

    }

}
