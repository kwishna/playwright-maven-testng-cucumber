package org.example.playwrightUtils;

import com.google.common.util.concurrent.Uninterruptibles;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.*;
import org.example.browserManager.DriverFactory;
import org.example.config.ConfigurationManager;
import org.example.enums.WaitStrategy;
import org.example.reportings.ExtentLogger;

import java.awt.*;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.example.browserManager.DriverFactory.getPage;
import static org.example.utils.configs.Constants.WAIT_TIMEOUT;

public abstract class Actions {

    public boolean navigateTo(String url) {
        try {
            getPage().navigate(url, new Page.NavigateOptions().setWaitUntil(WaitUntilState.DOMCONTENTLOADED));
            return true;
        } catch (PlaywrightException e) {
            ExtentLogger.info(e.getMessage());
            return false;
        }
    }

    public boolean navigateTo(String url, WaitUntilState state) {
        try {
            getPage().navigate(url, new Page.NavigateOptions().setWaitUntil(state));
        } catch (PlaywrightException e) {
            ExtentLogger.info(e.getMessage());
            return false;
        }
        return true;
    }

    public void maximize() {
        try {
            GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
            getPage().setViewportSize(gd.getDisplayMode().getWidth(), gd.getDisplayMode().getHeight());
        } catch (HeadlessException e) {
            ExtentLogger.info(e.getMessage());
        }
    }

    private boolean isObjVisible(Object locator) {
        boolean _isVisible = false;
        try {
            _isVisible = getLocator(locator).isVisible();
        } catch (PlaywrightException e) {
            ExtentLogger.info(e.getMessage());
        }
        return _isVisible;
    }

    public boolean isVisible(String locator) {
        return this.isObjVisible(locator);
    }

    public boolean isVisible(Locator locator) {
        return this.isObjVisible(locator);
    }

    public boolean fill(String locator, String value) {
        try {
            getLocator(locator).fill(value);
        } catch (PlaywrightException e) {
            ExtentLogger.info(e.getMessage());
            return false;
        }
        return true;
    }

    public boolean fill(Locator locator, String value) {
        try {
            locator.fill(value);
        } catch (PlaywrightException e) {
            ExtentLogger.info(e.getMessage());
            return false;
        }
        return true;
    }

    public boolean type(String locator, String value) {
        try {
            getLocator(locator).clear();
            getLocator(locator).type(value);
        } catch (PlaywrightException e) {
            ExtentLogger.info(e.getMessage());
            return false;
        }
        return true;
    }

    public boolean typeAndEnter(String locator, String value) {
        try {
            getLocator(locator).clear();
            getLocator(locator).type(value);
            getLocator(locator).press("Enter");
            return true;
        } catch (PlaywrightException e) {
            ExtentLogger.info(e.getMessage());
            return false;
        }
    }

    public boolean uploadFile(String locator, String filePath) {
        try {
            getLocator(locator).setInputFiles(Paths.get(filePath));
            return true;
        } catch (PlaywrightException e) {
            ExtentLogger.info(e.getMessage());
            return false;
        }
    }

    public boolean jsClick(String locator) {
        try {
            getLocator(locator).scrollIntoViewIfNeeded();
            getLocator(locator).evaluate("node => node.click();");
            return true;
        } catch (PlaywrightException e) {
            ExtentLogger.info(e.getMessage());
            return false;
        }
    }

    public boolean jsScrollIntoView(String locator) {
        try {
            getLocator(locator).evaluate("node => node.scrollIntoView();");
            return true;
        } catch (PlaywrightException e) {
            ExtentLogger.info(e.getMessage());
            return false;
        }
    }

    public boolean check(String locator) {
        try {
            getLocator(locator).check();
            return true;
        } catch (PlaywrightException e) {
            ExtentLogger.info(e.getMessage());
            return false;
        }
    }

    public boolean selectByText(String locator, String text) {
        try {
            getLocator(locator).selectOption(new SelectOption().setLabel(text));
            return true;
        } catch (PlaywrightException e) {
            ExtentLogger.info(e.getMessage());
            return false;
        }
    }

    public boolean selectByIndex(String locator, int index) {
        try {
            getLocator(locator).selectOption(new SelectOption().setIndex(index));
            return true;
        } catch (PlaywrightException e) {
            ExtentLogger.info(e.getMessage());
            return false;
        }
    }

    public boolean selectByValue(String locator, String value) {
        try {
            getLocator(locator).selectOption(new SelectOption().setValue(value));
            return true;
        } catch (PlaywrightException e) {
            ExtentLogger.info(e.getMessage());
            return false;
        }
    }

    public boolean clickAndChoose(String locator, String optionLocator) {
        try {
            getLocator(locator).click();
            getLocator(optionLocator).click();
            return true;
        } catch (PlaywrightException e) {
            ExtentLogger.info(e.getMessage());
            return false;
        }
    }

    public boolean mouseOver(String locator) {
        try {
            getLocator(locator).hover();
            return true;
        } catch (PlaywrightException e) {
            ExtentLogger.info(e.getMessage());
            return false;
        }
    }

    public String getInnerText(String locator) {
        return getLocator(locator).innerText();
    }

    public boolean isEnabled(String locator) {
        return getLocator(locator).isEnabled();
    }

    public boolean isDisabled(String locator) {
        return getLocator(locator).isDisabled();
    }

    public boolean waitForDisappearance(String locator) {
        try {
            getLocator(locator).waitFor(new Locator.WaitForOptions().setTimeout(ConfigurationManager.configuration().timeout()).setState(WaitForSelectorState.HIDDEN));
            return true;
        } catch (PlaywrightException e) {
            return false;
        }
    }

    public boolean waitForAppearance(String locator) {
        try {
            getPage().locator(locator).waitFor(new Locator.WaitForOptions().setTimeout(ConfigurationManager.configuration().timeout()).setState(WaitForSelectorState.VISIBLE));
            return true;
        } catch (PlaywrightException e) {
            return false;
        }
    }

    public String getTitle() {
        return getPage().title();
    }

    public String getInputValue(String locator) {
        try {
            return getLocator(locator).inputValue();
        } catch (PlaywrightException e) {
            ExtentLogger.fail(e.getMessage());
        }
        return "";
    }

    public String getAttribute(String locator, String attribute) {
        try {
            return getLocator(locator).getAttribute(attribute);
        } catch (PlaywrightException e) {
            ExtentLogger.fail(e.getMessage());
        }
        return "";
    }

    public void pause(String type) {
        switch (type.toLowerCase()) {
            case "low" -> sleep(ConfigurationManager.configuration().pauseLow());
            case "medium" -> sleep(ConfigurationManager.configuration().pauseMedium());
            case "high" -> sleep(ConfigurationManager.configuration().pauseHigh());
            default -> sleep(ConfigurationManager.configuration().pauseLow());
        }
    }

    protected void sleep(long time) {
        Uninterruptibles.sleepUninterruptibly(time, TimeUnit.SECONDS);
    }


    public JsonElement getRequest(String baseUrl, String resource, Map<String, String> headers) {
        APIRequestContext request = DriverFactory.getPlaywright().request().newContext(
                new APIRequest.NewContextOptions()
                        .setBaseURL(baseUrl)
                        .setExtraHTTPHeaders(headers));

        APIResponse response = request.get(resource);
        return new Gson().fromJson(response.text(), JsonElement.class);
    }

    public JsonElement postRequest(String baseUrl, String resource, Map<String, String> headers, String jsonBody) {
        APIRequestContext request = DriverFactory.getPlaywright().request().newContext(
                new APIRequest.NewContextOptions()
                        .setBaseURL(baseUrl)
                        .setExtraHTTPHeaders(headers));

        APIResponse response = request.post(resource, RequestOptions.create().setData(jsonBody));
        return new Gson().fromJson(response.text(), JsonElement.class);
    }

    public void setDefaultTimeout(double timeout) {
        getPage().setDefaultTimeout(timeout);
    }

    public void resetDefaultTimeout() {
        getPage().setDefaultTimeout(WAIT_TIMEOUT);
    }

    public Locator getLocator(Object _locator) {
        if (_locator.getClass().getSimpleName().equals("String")) {
            return getPage().locator(_locator.toString());
        } else {
            return (Locator) _locator;
        }
    }

    public void pageWait(double timeout) {
        getPage().waitForTimeout(timeout);
    }

    protected Locator explicitWait(Locator locator, WaitStrategy.LocatorStrategy strategy) {
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

    protected void click(Locator locator) {
        explicitWait(locator, WaitStrategy.LocatorStrategy.CLICKABLE).click();
        ExtentLogger.pass("<b>" + locator + "</b> is clicked", true);
    }

    protected void sendKeyboardKeys(Locator locator, String key) {
        explicitWait(locator, WaitStrategy.LocatorStrategy.VISIBLE);
        DriverFactory.getPage().keyboard().press(key);
        ExtentLogger.pass("<b>" + key + "</b> is entered successfully in <b>" + locator + "</b>", true);
    }

    protected void clear(Locator selector) {
        explicitWait(selector, WaitStrategy.LocatorStrategy.VISIBLE).clear();
        ExtentLogger.info("Clearing the field  <b>" + selector + "</b>");
    }

    protected void clearAndSendKeys(Locator selector, String value) {
        clear(selector);
        fill(selector, value);
        ExtentLogger.pass("<b>" + value + "</b> is entered successfully in <b>" + selector + "</b>", true);
    }

    public String getPageTitle() {
        return getPage().title();
    }

    public boolean isPresent(Locator selector) {
        explicitWait(selector, WaitStrategy.LocatorStrategy.PRESENCE);
        return selector.count() > 0;
    }

    public List<ElementHandle> getAllElements(Locator selector) {
        explicitWait(selector, WaitStrategy.LocatorStrategy.PRESENCE);
        return selector.elementHandles();
    }

    public String getCurrentUrl() {
        return getPage().url();
    }

}
