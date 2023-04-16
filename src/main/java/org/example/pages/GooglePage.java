package org.example.pages;

import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.base.BasePage;
import org.example.utils.Keys;

public class GooglePage extends BasePage {

    Logger logger = LogManager.getLogger(GooglePage.class);

    private Locator searchBox = this.getPage().locator("[name='q']").first();
    private Locator searchBtns = this.getPage().locator("[name='btnK']");
    private Locator results = this.getPage().locator("div.g a h3");

    public void navigate() {
        logger.info("Navigating To Google GooglePage");
        this.navigateTo("https://google.com/");
    }

    public boolean isSearchPageOpen() {
        logger.info("Wait For Successful Navigation To Google Search Page.");
        return this.isVisible(searchBox);
    }

    public void performSearch(final String keyword) {
        this.clearAndSendKeys(searchBox, keyword);
        this.sendKeyboardKeys(searchBox, Keys.ENTER);
    }

    public void navigateToFirstLink() {
        this.getAllElements(results)
                .stream()
                .filter(_el -> _el.isVisible() && _el.isEnabled())
                .findFirst()
                .ifPresent(ElementHandle::click);
    }

    public int getResultCount() {
        logger.info("Fetching Search Result Count: " + results.count());
        return results.count();
    }

    public boolean isResultPageLoaded() {
        logger.info("Wait For Successful Navigation To Google Result Page.");
        return this.isPresent(results);
    }
}
