package org.example.base;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.google.common.util.concurrent.Uninterruptibles;
import com.microsoft.playwright.Keyboard;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.options.ElementState;
import com.microsoft.playwright.options.WaitForSelectorState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.browserManager.PageManager;
import org.example.enums.WaitStrategy;
import org.example.pages.GooglePage;
import org.example.reportings.ExtentLogger;
import org.example.utils.ScreenshotUtils;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.example.utils.Constants.WAIT_TIMEOUT;

public class BasePage {
    Logger logger = LogManager.getLogger(GooglePage.class);
    private Page page;

    public BasePage(Page driver) {
        this.page = driver;
    }

    protected Page getPage() {
        return this.page;
    }

    protected void setPage(Page page) {
        this.page = page;
    }

    protected Locator explicitWait(Locator locator, WaitStrategy.LocatorStrategy strategy) {
        logger.info("Explict Wait For: " + strategy + " For Locator -> " + locator);

        Locator.WaitForOptions _waitForOps = new Locator.WaitForOptions();
        _waitForOps.setTimeout(WAIT_TIMEOUT);

        switch (strategy) {
            case CLICKABLE -> _waitForOps.setState(WaitForSelectorState.VISIBLE);
            case PRESENCE -> _waitForOps.setState(WaitForSelectorState.ATTACHED);
            case HIDDEN -> _waitForOps.setState(WaitForSelectorState.HIDDEN);
            case DETTACHED -> _waitForOps.setState(WaitForSelectorState.DETACHED);
            default -> _waitForOps.setState(WaitForSelectorState.VISIBLE);
        }
        locator.first().waitFor(_waitForOps);
        return locator;
    }

    protected ElementHandle explicitWait(ElementHandle handle, WaitStrategy.ElementStrategy strategy) {
        logger.info("Explict Wait For: " + strategy + " For Locator -> " + handle);

        ElementState _state;

        switch (strategy) {
            case CLICKABLE -> _state = ElementState.ENABLED;
            case PRESENCE -> _state = ElementState.STABLE;
            case VISIBLE -> _state = ElementState.VISIBLE;
            case HIDDEN -> _state = ElementState.HIDDEN;
            case ENABLED -> _state = ElementState.ENABLED;
            case DISABLED -> _state = ElementState.DISABLED;
            default -> _state = ElementState.VISIBLE;
        }
        handle.waitForElementState(_state, new ElementHandle.WaitForElementStateOptions().setTimeout(WAIT_TIMEOUT));
        return handle;
    }

    protected void goTo(String url) {
        logger.info("Navigating To URL: " + url);
        getPage().navigate(url);
    }

    protected void click(Locator locator) {
        explicitWait(locator, WaitStrategy.LocatorStrategy.CLICKABLE).click();
        ExtentLogger.pass("<b>" + locator + "</b> is clicked", true);
    }

    protected void sendKeys(Locator locator, String value) {
        explicitWait(locator, WaitStrategy.LocatorStrategy.VISIBLE).fill(value);
        ExtentLogger.pass("<b>" + value + "</b> is entered successfully in <b>" + locator + "</b>", true);
    }

    protected void sendKeyboardKeys(Locator locator, String key) {
        explicitWait(locator, WaitStrategy.LocatorStrategy.VISIBLE);
        PageManager.getPage().keyboard().press(key);
        ExtentLogger.pass("<b>" + key + "</b> is entered successfully in <b>" + locator + "</b>", true);
    }

    protected void clear(Locator selector) {
        explicitWait(selector, WaitStrategy.LocatorStrategy.VISIBLE).clear();
        ExtentLogger.info("Clearing the field  <b>" + selector + "</b>");
    }

    protected void clearAndSendKeys(Locator selector, String value) {
        clear(selector);
        sendKeys(selector, value);
        ExtentLogger.pass("<b>" + value + "</b> is entered successfully in <b>" + selector + "</b>", true);
    }

    public String getPageTitle() {
        return getPage().title();
    }

    protected void captureScreenshot() {
        ExtentLogger.info("Capturing Screenshot", MediaEntityBuilder.createScreenCaptureFromBase64String(ScreenshotUtils.getScreenshotAsBase64()));
    }

    protected boolean isDisplayed(Locator selector) {
        explicitWait(selector, WaitStrategy.LocatorStrategy.VISIBLE);
        return selector.isVisible();
    }

    protected boolean isPresent(Locator selector) {
        explicitWait(selector, WaitStrategy.LocatorStrategy.PRESENCE);
        return selector.count() > 0;
    }

    protected List<ElementHandle> getAllElements(Locator selector) {
        explicitWait(selector, WaitStrategy.LocatorStrategy.PRESENCE);
        return selector.elementHandles();
    }

    public String getCurrentUrl() {
        return this.page.url();
    }

    protected void waitFor(long time) {
        Uninterruptibles.sleepUninterruptibly(time, TimeUnit.SECONDS);
    }
}
