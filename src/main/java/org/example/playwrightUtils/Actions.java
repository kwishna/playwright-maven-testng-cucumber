package org.example.playwrightUtils;

import com.google.common.util.concurrent.Uninterruptibles;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.microsoft.playwright.*;
import com.microsoft.playwright.assertions.LocatorAssertions;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.options.*;
import org.example.browserManager.DriverFactory;
import org.example.config.ConfigurationManager;
import org.example.enums.WaitStrategy;
import org.example.reportings.ExtentLogger;

import java.awt.*;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import static org.example.browserManager.DriverFactory.getContext;
import static org.example.browserManager.DriverFactory.getPage;
import static org.example.utils.configs.Constants.WAIT_TIMEOUT;

public abstract class Actions {

    public static ElementHandle getElementHandle(JSHandle jsHandle) {
        return jsHandle.asElement();
    }

    public boolean navigateTo(String url) {
        return this.navigateTo(url, new Page.NavigateOptions().setWaitUntil(WaitUntilState.DOMCONTENTLOADED));
    }

    public boolean navigateTo(String url, Page.NavigateOptions options) {
        try {
            getPage().navigate(url, options);
            return true;
        } catch (PlaywrightException e) {
            ExtentLogger.warn(e.getMessage());
            return false;
        }
    }

//    public boolean isVisible(String locator) {
//        return this.isVisible(getLocator(locator));
//    }

    public boolean navigateTo(String url, WaitUntilState state) {
        return this.navigateTo(url, new Page.NavigateOptions().setWaitUntil(state));
    }

//    public boolean fill(String locator, String value) {
//        return this.fill(getLocator(locator), value);
//    }

    public void maximize() {
        try {
            GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
            getPage().setViewportSize(gd.getDisplayMode().getWidth(), gd.getDisplayMode().getHeight());
        } catch (HeadlessException e) {
            ExtentLogger.warn(e.getMessage());
        }
    }

//    public boolean fill(String locator, String value, Locator.FillOptions options) {
//        return this.fill(getLocator(locator), value, options);
//    }

    public boolean isVisible(Locator locator) {
        boolean _isVisible = false;
        try {
            _isVisible = locator.isVisible();
        } catch (PlaywrightException e) {
            ExtentLogger.warn(e.getMessage());
        }
        return _isVisible;
    }

//    public boolean type(String locator, String value) {
//        return this.type(getLocator(locator), value);
//    }

    public boolean fill(Locator locator, String value) {
        try {
            locator.fill(value);
            return true;
        } catch (PlaywrightException e) {
            ExtentLogger.warn(e.getMessage());
            return false;
        }
    }

//    public boolean type(String locator, String value, Locator.TypeOptions options) {
//        return this.type(getLocator(locator), value, options);
//    }

    public boolean fill(Locator locator, String value, Locator.FillOptions options) {
        try {
            locator.fill(value, options);
            return true;
        } catch (PlaywrightException e) {
            ExtentLogger.warn(e.getMessage());
            return false;
        }
    }

//    public boolean typeAndEnter(String locator, String value) {
//        return this.typeAndEnter(getLocator(locator), value);
//    }

    public boolean type(Locator locator, String value) {
        try {
            locator.type(value);
            return true;
        } catch (PlaywrightException e) {
            ExtentLogger.warn(e.getMessage());
            return false;
        }
    }

//    public boolean uploadFile(String locator, Path path) {
//        return this.uploadFiles(getLocator(locator), path);
//    }

    public boolean type(Locator locator, String value, Locator.TypeOptions options) {
        try {
            locator.type(value, options);
            return true;
        } catch (PlaywrightException e) {
            ExtentLogger.warn(e.getMessage());
            return false;
        }
    }


//    public boolean uploadFiles(String locator, Path... paths) {
//        return this.uploadFiles(getLocator(locator), paths);
//    }

    public boolean typeAndEnter(Locator locator, String value) {
        return this.type(locator, value) && this.sendKeyboardKeys(locator, value);
    }

//    public boolean uploadBuffer(String locator, FilePayload payload) {
//        return this.uploadBuffer(getLocator(locator), payload);
//    }

    public boolean uploadFile(Locator locator, Path path) {
        return this.uploadFiles(locator, path);
    }

//    public boolean clickToUploadFile(String locator, Path path) {
//        return this.clickToUploadFile(getLocator(locator), path);
//    }

    public boolean uploadFiles(Locator locator, Path... paths) {
        try {
            locator.setInputFiles(paths);
            return true;
        } catch (PlaywrightException e) {
            ExtentLogger.warn(e.getMessage());
            return false;
        }
    }

    //    public boolean click(String locator) {
//        return this.click(getLocator(locator));
//    }

    public boolean uploadBuffer(Locator locator, FilePayload payload) {
        try {
            locator.setInputFiles(payload);
            return true;
        } catch (PlaywrightException e) {
            ExtentLogger.warn(e.getMessage());
            return false;
        }
    }

//    public boolean click(String locator, Locator.ClickOptions options) {
//        return this.click(getLocator(locator), options);
//    }

    public boolean clickToUploadFile(Locator locator, Path path) {
        try {
            FileChooser fileChooser = getPage().waitForFileChooser(locator::click);
            fileChooser.setFiles(path);
            return true;
        } catch (PlaywrightException e) {
            ExtentLogger.warn(e.getMessage());
            return false;
        }
    }

//    public boolean forceClick(String locator) {
//        return this.forceClick(getLocator(locator));
//    }

    public boolean click(Locator locator) {
        try {
            locator.click();
            ExtentLogger.pass("<b>" + locator + "</b> is clicked", true);
            return true;
        } catch (PlaywrightException e) {
            return false;
        }
    }

//    public boolean programmaticClick(String locator) {
//        return this.programmaticClick(getLocator(locator));
//    }

    public boolean click(Locator locator, Locator.ClickOptions clickOptions) {
        try {
            locator.click(clickOptions);
            ExtentLogger.pass("<b>" + locator + "</b> is clicked", true);
            return true;
        } catch (PlaywrightException e) {
            return false;
        }
    }

//    public boolean dispatchEvent(String locator, String event) {
//        return this.dispatchEvent(getLocator(locator), event);
//    }

    public boolean forceClick(Locator locator) {
        try {
            locator.click(new Locator.ClickOptions().setForce(true));
            ExtentLogger.pass("<b>" + locator + "</b> is clicked", true);
            return true;
        } catch (PlaywrightException e) {
            return false;
        }
    }

//    public boolean doubleClick(String locator) {
//        return this.doubleClick(getLocator(locator));
//    }

    public boolean programmaticClick(Locator locator) {
        return this.dispatchEvent(locator, "click");
    }

//    public boolean doubleClick(String locator, Locator.DblclickOptions dblclickOptions) {
//        return this.doubleClick(getLocator(locator), dblclickOptions);
//    }

    public boolean dispatchEvent(Locator locator, String event) {
        try {
            locator.dispatchEvent(event);
            ExtentLogger.pass("<b>" + event + "</b>" + "dispatched to " + "<b>" + locator + "</b> is clicked", true);
            return true;
        } catch (PlaywrightException e) {
            return false;
        }
    }

//    public boolean click(String locator, int x_coord, int y_coord) {
//        return this.click(getLocator(locator), new Locator.ClickOptions().setPosition(x_coord, y_coord));
//    }

    public boolean doubleClick(Locator locator) {
        try {
            locator.dblclick();
            ExtentLogger.pass("<b>" + locator + "</b> is double clicked", true);
            return true;
        } catch (PlaywrightException e) {
            return false;
        }
    }

//    public boolean click(String locator, KeyboardModifier... keys) {
//        return this.click(getLocator(locator), new Locator.ClickOptions().setModifiers(Arrays.asList(keys)));
//    }

    public boolean doubleClick(Locator locator, Locator.DblclickOptions dblclickOptions) {
        try {
            locator.dblclick(dblclickOptions);
            ExtentLogger.pass("<b>" + locator + "</b> is double clicked", true);
            return true;
        } catch (PlaywrightException e) {
            return false;
        }
    }

//    public boolean shiftClick(String locator) {
//        return this.click(locator, KeyboardModifier.SHIFT);
//    }

    public boolean click(Locator locator, int x_coord, int y_coord) {
        return this.click(locator, new Locator.ClickOptions().setPosition(x_coord, y_coord));
    }

//    public boolean controlClick(String locator) {
//        return this.click(getLocator(locator), KeyboardModifier.CONTROL);
//    }

    public boolean click(Locator locator, KeyboardModifier... keys) {
        return this.click(locator, new Locator.ClickOptions().setModifiers(Arrays.asList(keys)));
    }

//    public boolean rightClick(String locator) {
//        return this.rightClick(getLocator(locator));
//    }

    public boolean shiftClick(Locator locator) {
        return this.click(locator, KeyboardModifier.SHIFT);
    }

//    public boolean jsClick(String locator) {
//        return this.jsClick(getLocator(locator));
//    }

    public boolean controlClick(Locator locator) {
        return this.click(locator, KeyboardModifier.CONTROL);
    }

//    public boolean scrollIntoViewIfNeeded(String locator) {
//        return this.scrollIntoViewIfNeeded(getLocator(locator));
//    }

    public boolean rightClick(Locator locator) {
        return this.click(locator, new Locator.ClickOptions().setButton(MouseButton.RIGHT));
    }

//    public Object locatorEvaluate(String locator, String js) {
//        return this.locatorEvaluate(getLocator(locator), js);
//    }

    public boolean jsClick(Locator locator) {
        try {
            locator.scrollIntoViewIfNeeded();
            locator.evaluate("node => node.click();");
            return true;
        } catch (PlaywrightException e) {
            ExtentLogger.warn(e.getMessage());
            return false;
        }
    }

    public boolean scrollIntoViewIfNeeded(Locator locator) {
        try {
            locator.scrollIntoViewIfNeeded();
            return true;
        } catch (PlaywrightException e) {
            ExtentLogger.warn(e.getMessage());
            return false;
        }
    }

    public Object locatorEvaluate(Locator locator, String js) {
        try {
            return locator.evaluate(js);
        } catch (PlaywrightException e) {
            ExtentLogger.warn(e.getMessage());
            return false;
        }
    }

//    public boolean jsScrollIntoView(String locator) {
//        return this.jsScrollIntoView(getLocator(locator));
//    }

    public Object pageEvaluate(String js) {
        try {
            return getPage().evaluate(js);
        } catch (PlaywrightException e) {
            ExtentLogger.warn(e.getMessage());
            return false;
        }
    }

//    public boolean check(String locator) {
//        return this.check(getLocator(locator));
//    }
//

    public Object pageEvaluate(String js, Object... args) {
        try {
            return getPage().evaluate(js, Arrays.asList(args));
        } catch (PlaywrightException e) {
            ExtentLogger.warn(e.getMessage());
            return false;
        }
    }

//    public boolean check(String locator, Locator.CheckOptions checkOptions) {
//        return this.check(getLocator(locator), checkOptions);
//    }

    public boolean jsScrollIntoView(Locator locator) {
        try {
            locator.evaluate("node => node.scrollIntoView();");
            return true;
        } catch (PlaywrightException e) {
            ExtentLogger.warn(e.getMessage());
            return false;
        }
    }

//    public boolean uncheck(String locator) {
//        return this.uncheck(getLocator(locator));
//    }
//

    public boolean check(Locator locator) {
        try {
            locator.check();
            return true;
        } catch (PlaywrightException e) {
            ExtentLogger.warn(e.getMessage());
            return false;
        }
    }
//    public boolean uncheck(String locator, Locator.UncheckOptions uncheckOptions) {
//        return this.uncheck(getLocator(locator), uncheckOptions);
//    }

    public boolean check(Locator locator, Locator.CheckOptions checkOptions) {
        try {
            locator.check(checkOptions);
            return true;
        } catch (PlaywrightException e) {
            ExtentLogger.warn(e.getMessage());
            return false;
        }
    }

//    public boolean isChecked(String locator) {
//        return this.isChecked(getLocator(locator));
//    }

    public boolean uncheck(Locator locator) {
        try {
            locator.uncheck();
            return true;
        } catch (PlaywrightException e) {
            ExtentLogger.warn(e.getMessage());
            return false;
        }
    }

//    public List<String> selectByText(String locator, String text) {
//        return this.selectByText(getLocator(locator), text);
//    }

    public boolean uncheck(Locator locator, Locator.UncheckOptions uncheckOptions) {
        try {
            locator.uncheck(uncheckOptions);
            return true;
        } catch (PlaywrightException e) {
            ExtentLogger.warn(e.getMessage());
            return false;
        }
    }

//    public List<String> selectByIndex(String locator, int index) {
//        return this.selectByIndex(getLocator(locator), index);
//    }

    public boolean isChecked(Locator locator) {
        try {
            return locator.isChecked();
        } catch (PlaywrightException e) {
            ExtentLogger.warn(e.getMessage());
            return false;
        }
    }

//    public List<String> selectByValue(String locator, String value) {
//        return this.selectByValue(getLocator(locator), value);
//    }

    public List<String> selectByText(Locator locator, String text) {
        return this.selectDropdown(locator, new SelectOption().setLabel(text));
    }

//    public List<String> selectDropdown(String locator, SelectOption selectOption) {
//        return this.selectDropdown(getLocator(locator), selectOption);
//    }

    public List<String> selectByIndex(Locator locator, int index) {
        return this.selectDropdown(locator, new SelectOption().setIndex(index));
    }

//    public List<String> multiSelectByValue(String locator, String[] values) {
//        return this.multiSelectByValue(getLocator(locator), values);
//    }

    public List<String> selectByValue(Locator locator, String value) {
        return this.selectDropdown(locator, new SelectOption().setValue(value));
    }

//    public List<String> multiSelectByValue(String locator, String[] values, Locator.SelectOptionOptions selectOptions) {
//        return this.multiSelectByValue(getLocator(locator), values, selectOptions);
//    }

    public List<String> selectDropdown(Locator locator, SelectOption selectOption) {
        try {
            return locator.selectOption(selectOption);
        } catch (PlaywrightException e) {
            ExtentLogger.warn(e.getMessage());
            return List.of();
        }
    }

//    public boolean clickAndChooseInDropdown(String selectLocator, String optionLocator) {
//        return this.clickAndChooseInDropdown(getLocator(selectLocator), getLocator(optionLocator));
//    }

    public List<String> multiSelectByValue(Locator locator, String[] values) {
        try {
            return locator.selectOption(values);
        } catch (PlaywrightException e) {
            ExtentLogger.warn(e.getMessage());
            return List.of();
        }
    }

//    public boolean mouseOver(String locator) {
//        return this.mouseOver(getLocator(locator));
//    }

    public List<String> multiSelectByValue(Locator locator, String[] values, Locator.SelectOptionOptions selectOptions) {
        try {
            return locator.selectOption(values, selectOptions);
        } catch (PlaywrightException e) {
            ExtentLogger.warn(e.getMessage());
            return List.of();
        }
    }

//    public boolean mouseOver(String locator, Locator.HoverOptions hoverOptions) {
//        return this.mouseOver(getLocator(locator), hoverOptions);
//    }

    public boolean clickAndChooseInDropdown(Locator selectLocator, Locator optionLocator) {
        return this.click(selectLocator) && this.click(optionLocator);
    }

//    public String getInnerText(String locator) {
//        return this.getInnerText(getLocator(locator));
//    }

    public boolean mouseOver(Locator locator) {
        try {
            locator.hover();
            return true;
        } catch (PlaywrightException e) {
            ExtentLogger.warn(e.getMessage());
            return false;
        }
    }

//    public String getInnerHtml(String locator) {
//        return this.getInnerHtml(getLocator(locator));
//    }

    public boolean mouseOver(Locator locator, Locator.HoverOptions hoverOptions) {
        try {
            locator.hover(hoverOptions);
            return true;
        } catch (PlaywrightException e) {
            ExtentLogger.warn(e.getMessage());
            return false;
        }
    }

//    public String getTextContent(String locator) {
//        return this.getTextContent(getLocator(locator));
//    }

    public String getInnerText(Locator locator) {
        return locator.innerText();
    }

//    public boolean isEnabled(String locator) {
//        return this.isEnabled(getLocator(locator));
//    }

    public String getInnerHtml(Locator locator) {
        return locator.innerHTML();
    }

//    public boolean isDisabled(String locator) {
//        return this.isDisabled(getLocator(locator));
//    }

    public String getTextContent(Locator locator) {
        return locator.textContent();
    }

//    public boolean waitForDisappearance(String locator) {
//        return this.waitForDisappearance(getLocator(locator));
//    }

    public boolean isEnabled(Locator locator) {
        return locator.isEnabled();
    }

//    public boolean waitForAppearance(String locator) {
//        return this.waitForDisappearance(getLocator(locator));
//    }

    public boolean isDisabled(Locator locator) {
        return locator.isDisabled();
    }

//    public boolean waitFor(String locator, Locator.WaitForOptions waitForOptions) {
//        return this.waitFor(getLocator(locator), waitForOptions);
//    }

    public boolean waitForDisappearance(Locator locator) {
        try {
            locator.waitFor(new Locator.WaitForOptions().setTimeout(ConfigurationManager.configuration().timeout()).setState(WaitForSelectorState.HIDDEN));
            return true;
        } catch (PlaywrightException e) {
            return false;
        }
    }

//    public String getInputValue(String locator) {
//        return this.getInputValue(getLocator(locator));
//    }

    public boolean waitForAppearance(Locator locator) {
        try {
            locator.waitFor(new Locator.WaitForOptions().setTimeout(ConfigurationManager.configuration().timeout()).setState(WaitForSelectorState.VISIBLE));
            return true;
        } catch (PlaywrightException e) {
            return false;
        }
    }

//    public String getAttribute(String locator, String attribute) {
//        return this.getAttribute(getLocator(locator), attribute);
//    }

    public boolean waitFor(Locator locator, Locator.WaitForOptions waitForOptions) {
        try {
            locator.waitFor(waitForOptions);
            return true;
        } catch (PlaywrightException e) {
            return false;
        }
    }

    public String getInputValue(Locator locator) {
        try {
            return locator.inputValue();
        } catch (PlaywrightException e) {
            ExtentLogger.fail(e.getMessage());
            return "";
        }
    }

    public String getAttribute(Locator locator, String attribute) {
        try {
            return locator.getAttribute(attribute);
        } catch (PlaywrightException e) {
            ExtentLogger.fail(e.getMessage());
            return "";
        }
    }

    public String getTitle() {
        return getPage().title();
    }

    public void pause(String type) {
        switch (type.toLowerCase()) {
            case "low" -> sleep(ConfigurationManager.configuration().pauseLow());
            case "medium" -> sleep(ConfigurationManager.configuration().pauseMedium());
            case "high" -> sleep(ConfigurationManager.configuration().pauseHigh());
            default -> sleep(ConfigurationManager.configuration().pauseLow());
        }
    }

    protected boolean sleep(long time) {
        Uninterruptibles.sleepUninterruptibly(time, TimeUnit.SECONDS);
        return true;
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

    public boolean setDefaultTimeout(double timeout) {
        getPage().setDefaultTimeout(timeout);
        return true;
    }

    public boolean resetDefaultTimeout() {
        getPage().setDefaultTimeout(WAIT_TIMEOUT);
        return true;
    }

//    public Locator explicitWait(String locator, WaitStrategy.LocatorStrategy strategy) {
//        return this.explicitWait(getLocator(locator), strategy);
//    }

    public Locator getVisible(String locator) {
        return this.getVisible(getLocator(locator));
    }

    public Locator getVisible(Locator locator) {
        return locator.locator("visible=true");
    }

    public Locator getLocator(String _locator) {
        return getPage().locator(_locator);
    }

    public Locator getFirst(Locator _locator) {
        return _locator.first();
    }

    public Locator getParentLocator(Locator _locator) {
        return _locator.locator("..");
    }

    public Locator getIndexedLocator(Locator _locator, int nth) {
        return _locator.locator("nth=" + nth);
    }

    public Locator getLocatorAtIndex(String _locator, int nth) {
        return this.getLocatorAtIndex(getLocator(_locator), nth);
    }

    public Locator getLocatorAtIndex(Locator _locator, int nth) {
        return _locator.nth(nth);
    }

    public Locator getLastLocator(Locator _locator) {
        return this.getIndexedLocator(_locator, -1);
    }

    public Locator getVisibleLocator(Locator _locator) {
        return _locator.locator("visible=true");
    }

    public Locator getLocatorByRole(AriaRole role, Page.GetByRoleOptions roleOptions) {
        return getPage().getByRole(role, roleOptions);
    }

    public Locator getLocatorByText(String text) {
        return getPage().getByText(text);
    }

    public Locator getLocatorByText(Pattern text) {
        return getPage().getByText(text);
    }

    public Locator getLocatorByLabel(String text) {
        return getPage().getByLabel(text);
    }

    public Locator getLocatorByLabel(Pattern text) {
        return getPage().getByLabel(text);
    }

    public Locator getLocatorByPlaceholder(String text) {
        return getPage().getByPlaceholder(text);
    }

    public Locator getLocatorByPlaceholder(Pattern text) {
        return getPage().getByPlaceholder(text);
    }

    public Locator getLocatorByAltText(String text) {
        return getPage().getByAltText(text);
    }

    public Locator getLocatorByAltText(Pattern text) {
        return getPage().getByAltText(text);
    }

    public Locator getLocatorByTitle(String text) {
        return getPage().getByTitle(text);
    }

    public Locator getLocatorByTitle(Pattern text) {
        return getPage().getByTitle(text);
    }

    public Locator withChild(String parent, String child) {
        return getLocator(parent).filter(new Locator.FilterOptions().setHas(getLocator(child)));
    }

    public Locator withText(String parent, String text) {
        return getLocator(parent).filter(new Locator.FilterOptions().setHasText(text));
    }

    public Locator withText(String parent, Pattern text) {
        return getLocator(parent).filter(new Locator.FilterOptions().setHasText(text));
    }

    public Locator withChild(Locator parent, Locator child) {
        return parent.filter(new Locator.FilterOptions().setHas(child));
    }

    public Locator withText(Locator parent, String text) {
        return parent.filter(new Locator.FilterOptions().setHasText(text));
    }

    public Locator withText(Locator parent, Pattern text) {
        return parent.filter(new Locator.FilterOptions().setHasText(text));
    }

    public Locator explicitWait(Locator locator, WaitStrategy.LocatorStrategy strategy) {
        Locator.WaitForOptions _waitForOps = new Locator.WaitForOptions();
        _waitForOps.setTimeout(WAIT_TIMEOUT);

        switch (strategy) {
            case CLICKABLE -> _waitForOps.setState(WaitForSelectorState.VISIBLE);
            case PRESENCE -> _waitForOps.setState(WaitForSelectorState.ATTACHED);
            case HIDDEN -> _waitForOps.setState(WaitForSelectorState.HIDDEN);
            case DETTACHED -> _waitForOps.setState(WaitForSelectorState.DETACHED);
            default -> _waitForOps.setState(WaitForSelectorState.VISIBLE);
        }
        try {
            locator.first().waitFor(_waitForOps);
        } catch (PlaywrightException e) {
            ExtentLogger.fail(e.getMessage());
        }
        return locator;
    }

    public boolean pageWait(double timeout) {
        try {
            getPage().waitForTimeout(timeout);
            return true;
        } catch (PlaywrightException e) {
            ExtentLogger.fail(e.getMessage());
            return false;
        }
    }

//    public boolean sendKeyboardKeys(String locator, String key) {
//        return this.sendKeyboardKeys(getLocator(locator), key);
//    }

    public ElementHandle explicitWait(ElementHandle handle, WaitStrategy.ElementStrategy strategy) {
        ElementHandle.WaitForElementStateOptions _waitForOps = new ElementHandle.WaitForElementStateOptions();
        _waitForOps.setTimeout(WAIT_TIMEOUT);

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
        try {
            handle.waitForElementState(_state, _waitForOps);
        } catch (PlaywrightException e) {
            ExtentLogger.fail(e.getMessage());
        }
        return handle;
    }

//    public boolean clear(String locator) {
//        return this.click(getLocator(locator));
//    }

    public boolean sendKeyboardKeys(String key) {
        try {
            DriverFactory.getPage().keyboard().press(key);
            ExtentLogger.pass("<b>" + key + "</b> is entered successfully.", true);
            return true;
        } catch (PlaywrightException e) {
            ExtentLogger.fail(e.getMessage());
            return false;
        }
    }

//    public boolean clear(String locator, Locator.ClearOptions options) {
//        return this.clear(getLocator(locator), options);
//    }

    public boolean sendKeyboardKeys(Locator locator, String key) {
        try {
            locator.press(key);
            ExtentLogger.pass("<b>" + key + "</b> is entered successfully in <b>" + locator + "</b>", true);
            return true;
        } catch (PlaywrightException e) {
            ExtentLogger.fail(e.getMessage());
            return false;
        }
    }

    public boolean clear(Locator locator) {
        try {
            locator.clear();
            ExtentLogger.info("Clearing the field  <b>" + locator + "</b>");
            return true;
        } catch (PlaywrightException e) {
            ExtentLogger.fail(e.getMessage());
            return false;
        }
    }

    public boolean clear(Locator locator, Locator.ClearOptions options) {
        try {
            locator.clear(options);
            ExtentLogger.info("Clearing the field  <b>" + locator + "</b>");
            return true;
        } catch (PlaywrightException e) {
            ExtentLogger.fail(e.getMessage());
            return false;
        }
    }

    public boolean clearAndSendKeys(String locator, String value) {
        return this.clearAndSendKeys(getLocator(locator), value);
    }

//    public boolean isPresent(String locator) {
//        try {
//            return getLocator(locator).count() > 0;
//        } catch (PlaywrightException e) {
//            ExtentLogger.fail(e.getMessage());
//            return false;
//        }
//    }

    public boolean clearAndSendKeys(Locator locator, String value) {
        try {
            ExtentLogger.pass("<b>" + value + "</b> is entered successfully in <b>" + locator + "</b>", true);
            return clear(locator) && fill(locator, value);
        } catch (PlaywrightException e) {
            ExtentLogger.fail(e.getMessage());
            return false;
        }
    }

//    public List<ElementHandle> getAllElements(String selector) {
//        return this.getAllElements(getPage().locator(selector));
//    }

    public String getPageTitle() {
        return getPage().title();
    }

    public boolean isPresent(Locator locator) {
        try {
            return locator.count() > 0;
        } catch (PlaywrightException e) {
            ExtentLogger.fail(e.getMessage());
            return false;
        }
    }

    public List<ElementHandle> getAllElements(Locator selector) {
        try {
            return selector.elementHandles();
        } catch (PlaywrightException e) {
            ExtentLogger.fail(e.getMessage());
            return List.of();
        }
    }

//    public boolean focus(String locator) {
//        return this.focus(getLocator(locator));
//    }

    public String getCurrentUrl() {
        return getPage().url();
    }

//    public boolean dragDrop(String from, String to) {
//        return this.dragDrop(getLocator(from), getLocator(to));
//    }

    public boolean dragDrop(Locator from, Locator to) {
        try {
            from.dragTo(to);
            return true;
        } catch (PlaywrightException e) {
            ExtentLogger.fail(e.getMessage());
            return false;
        }
    }

    public String getCurrentHref() {
        try {
            return (String) this.pageEvaluate("document.location.href");
        } catch (PlaywrightException e) {
            ExtentLogger.fail(e.getMessage());
            return "";
        }
    }

    public int getAllPagesCount() {
        return getContext().pages().size();
    }

    public boolean focus(String locator) {
        return this.focus(getLocator(locator));
    }

    public boolean focus(Locator locator) {
        try {
            locator.focus();
            return true;
        } catch (PlaywrightException e) {
            ExtentLogger.fail(e.getMessage());
            return false;
        }
    }

    public boolean dragDrop(String from, String to, Locator.DragToOptions dragToOptions) {
        return this.dragDrop(getLocator(from), getLocator(to), dragToOptions);
    }

    public boolean dragDrop(Locator from, Locator to, Locator.DragToOptions dragToOptions) {
        try {
            from.dragTo(to, dragToOptions);
            return true;
        } catch (PlaywrightException e) {
            ExtentLogger.fail(e.getMessage());
            return false;
        }
    }

    public boolean selectText(String locator) {
        return this.selectText(getLocator(locator));
    }

    public boolean selectText(Locator locator) {
        try {
            locator.selectText();
            return true;
        } catch (PlaywrightException e) {
            ExtentLogger.fail(e.getMessage());
            return false;
        }
    }

    public void pause() {
        getPage().pause();
    }

    public Download clickToDownload(ElementHandle elementHandle) {
        return DownloadHandler.clickToDownload(elementHandle);
    }

    public Download clickToDownload(String _locator) {
        return DownloadHandler.clickToDownload(_locator);
    }

    public Download clickToDownload(Locator _locator) {
        return DownloadHandler.clickToDownload(_locator);
    }

    public Download actionToDownload(Runnable _runnable) {
        return DownloadHandler.actionToDownload(_runnable);
    }

    public Download actionToDownload(Page.WaitForDownloadOptions _downloadOptions, Runnable _runnable) {
        return DownloadHandler.actionToDownload(_downloadOptions, _runnable);
    }

    public double getElementWidth(String locator) {
        return this.getElementWidth(getLocator(locator));
    }

    public double getElementHeight(String locator) {
        return this.getElementHeight(getLocator(locator));
    }

    public double getElementWidth(Locator locator) {
        return locator.boundingBox().width;
    }

    public double getElementHeight(Locator locator) {
        return locator.boundingBox().height;
    }

    public boolean waitForNavigationTo(Predicate<String> condition) {
        try {
            getPage().waitForURL(condition);
            return true;
        } catch (PlaywrightException e) {
            ExtentLogger.fail(e.getMessage());
            return false;
        }

    }

    public void setViewport(int width, int height) {
        getPage().setViewportSize(width, height);
    }

    public LocatorAssertions assertThat(Locator locator) {
        return PlaywrightAssertions.assertThat(locator);
    }

}
