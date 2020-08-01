package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.ArticlePageObject;
import lib.ui.MyListsPageObject;
import lib.ui.NavigationUI;
import lib.ui.SearchPageObject;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.MyListsPageObjectFactory;
import lib.ui.factories.NavigationUIFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;
import org.openqa.selenium.WebElement;

public class MyListsTests extends CoreTestCase {

    private static final String
            MORE_ARTICLE_OPTIONS_XPATH = "xpath://android.widget.ImageView[@content-desc='More options']",
            MORE_ARTICLE_OPTIONS_XPATH_2 = "xpath:(//android.widget.ImageView[@content-desc=\"More options\"])[2]",
            READING_LIST_REMOVE_OPTION_ID = "id:org.wikipedia:id/reading_list_item_remove_text";

    private static final String
            name_of_folder = "Learning programming";

    @Test
    public void testSaveFirstArticleToMyList() throws Exception {

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubString("Object-oriented programming language");

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        ArticlePageObject.waitForTitleElement();

        String article_title = ArticlePageObject.getArticleTitle();

        if (Platform.getInstance().isAndroid()) {
            ArticlePageObject.addArticleToMyList(name_of_folder);
        } else {
            ArticlePageObject.addArticlesToMySaved();
            ArticlePageObject.closeOverlayIfSaveArticleFirstTime();
        }
        ArticlePageObject.closeArticle();

        NavigationUI NavigationUI = NavigationUIFactory.get(driver);
        NavigationUI.clickMyList();

        MyListsPageObject MyListPageObject = MyListsPageObjectFactory.get(driver);

        if(Platform.getInstance().isAndroid()) {
            MyListPageObject.openFolderByName(name_of_folder);
        }

        MyListPageObject.swipeByArticleToDelete(article_title);

    }

    @Test

    public void testSaveTwoArticlesToMyListThenDeleteOneAndCheckLast() throws Exception {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubString("Object-oriented programming language");

        if (Platform.getInstance().isAndroid()) {
            SearchPageObject.assertThereIsArticleWithTitle();
        } else {
            SearchPageObject.iOSAssertThereIsFirstArticleWithTitle();
        }

        NavigationUI NavigationUI = NavigationUIFactory.get(driver);
        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        if (Platform.getInstance().isAndroid()) {
            NavigationUI.clickMoreOptions(MORE_ARTICLE_OPTIONS_XPATH);
            NavigationUI.clickAddToReadingList();
            NavigationUI.clickGotItOnOverlay();
            NavigationUI.cleanNameForReadingList();
            NavigationUI.setNameForReadingList("Java to read");
            NavigationUI.confirmAddingArticleToReadingLIst();
            NavigationUI.navigateUpToCloseArticle();
        } else {
            ArticlePageObject.addArticlesToMySaved();
            ArticlePageObject.closeOverlayIfSaveArticleFirstTime();
            ArticlePageObject.closeArticle();
        }

        SearchPageObject.initSearchInput();
        SearchPageObject.clearSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubString("Set of several computer software products and specifications");

        if (Platform.getInstance().isAndroid()) {
            SearchPageObject.assertThereIsArticleWithTitle();
        } else {
            SearchPageObject.iOSAssertThereIsSecondArticleWithTitle();
        }

        WebElement second_article = SearchPageObject.collectSearchResultAsElement();
        String second_article_title;

        if (Platform.getInstance().isAndroid()) {
            second_article_title = second_article.getAttribute("text");
            NavigationUI.clickMoreOptions(MORE_ARTICLE_OPTIONS_XPATH);
            NavigationUI.clickAddToReadingList();
            NavigationUI.selectExistedReadingList("Java to read");
            NavigationUI.navigateUpToCloseArticle();
            NavigationUI.openMyLists();
            NavigationUI.selectExistedReadingList("Java to read");
            NavigationUI.clickMoreOptions(MORE_ARTICLE_OPTIONS_XPATH_2);
            NavigationUI.removeFromTheListOptionForItem(READING_LIST_REMOVE_OPTION_ID);
            WebElement article_in_list = SearchPageObject.collectSearchResultAsElement();
            assertEquals(second_article_title, article_in_list.getAttribute("text"));
        } else {
            second_article_title = second_article.getAttribute("name");
            ArticlePageObject.addArticlesToMySaved();
            ArticlePageObject.closeArticle();
            NavigationUI.clickMyList();
            MyListsPageObject MyListPageObject = MyListsPageObjectFactory.get(driver);
            MyListPageObject.swipeByArticleToDelete("Java (programming language)");
            assertEquals(second_article_title, "Java (software platform)");
        }
    }
}
