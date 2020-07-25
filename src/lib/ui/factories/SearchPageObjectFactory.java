package lib.ui.factories;

import io.appium.java_client.AppiumDriver;
import lib.Platform;
import lib.ui.SearchPageObject;
import lib.ui.android.AndroidSearchPageObject;
import lib.ui.ios.iOSSearchPageObject;

public class SearchPageObjectFactory {

    public static SearchPageObject get(AppiumDriver driver) throws Exception {
        if (Platform.getInstance().isAndroid()) {
            return new AndroidSearchPageObject(driver);
        } else if (Platform.getInstance().isIOS()) {
            return new iOSSearchPageObject(driver);
        } else {
            throw new Exception("Cannot identify platform and OS.");
        }
    }
}
