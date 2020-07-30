package lib.ui.android;

import io.appium.java_client.AppiumDriver;
import lib.ui.NavigationUI;

public class AndroidNavigationUI extends NavigationUI {

    static {
        MY_LISTS_LINK = "xpath://android.widget.FrameLayout[@content-desc=\"My lists\"]";

        ADD_TO_READING_LIST_XPATH = "xpath://*[@text='Add to reading list']";
        ONBOARDING_BUTTON_ID = "id:org.wikipedia:id/onboarding_button";
        READING_LIST_INPUT_ID = "id:org.wikipedia:id/text_input";
        TEXT_OK = "xpath://*[@text='OK']";
        READING_LIST_NAME_TPL = "xpath://*[@text='{SUBSTRING}']";
        MY_LISTS_XPATH = "xpath://android.widget.FrameLayout[@content-desc=\"My lists\"]/android.widget.ImageView";
        MY_ARTICLE_ID = "id:org.wikipedia:id/page_list_item_title";
        NAVIGATE_UP_LINK = "xpath://android.widget.ImageButton[@content-desc='Navigate up']";
    }

    public AndroidNavigationUI(AppiumDriver driver) {
        super(driver);
    }
}
