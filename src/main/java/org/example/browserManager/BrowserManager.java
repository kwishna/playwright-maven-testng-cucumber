package org.example.browserManager;

import com.google.gson.JsonObject;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.example.utils.configs.BrowserConfig.*;
import static org.example.utils.configs.Constants.*;

public class BrowserManager {
    private static final ThreadLocal<Browser> BROWSER_THREAD_LOCAL = ThreadLocal.withInitial(() -> null);
    private static final Logger LOGGER = LogManager.getLogger(BrowserManager.class);

    private BrowserManager() {

    }

    protected static Browser getBrowser() {
        if (Objects.isNull(BROWSER_THREAD_LOCAL.get())) {
            LOGGER.info("Launching a new browser.");
            Browser _browser = _launchBrowser();
            LOGGER.info("A new browser is launched.");
            setBrowser(_browser);
        }
        return BROWSER_THREAD_LOCAL.get();
    }

    protected static void setBrowser(Browser browser) {
        LOGGER.info("Launched browser is set.");
        BROWSER_THREAD_LOCAL.set(browser);
    }

    private static Browser _launchBrowser() {
        BrowserType.LaunchOptions _launchOptions = _getLaunchOptions();
        _launchOptions = BROWSER_EXECUTABLE ? _setBrowserExecutable(_launchOptions) : _launchOptions;
        return _getBrowserTypeObj(BROWSER).launch(_launchOptions);
    }

    protected static void quit() {
        if (Objects.nonNull(BROWSER_THREAD_LOCAL.get())) {
            try {
                LOGGER.info("Closing the browser.");
                BROWSER_THREAD_LOCAL.get().close();
                BROWSER_THREAD_LOCAL.remove();
                LOGGER.info("The browser is closed.");
            } catch (Exception err) {
                LOGGER.error("Error while closing the browser: " + err);
            }
        }
    }

    static BrowserType _getBrowserTypeObj(String browserName) {
        return switch (browserName.toLowerCase()) {
            case "chromium", "chrome" -> PlaywrightManager.getPlaywright().chromium();
            case "webkit", "safari" -> PlaywrightManager.getPlaywright().webkit();
            case "firefox" -> PlaywrightManager.getPlaywright().firefox();
            default -> PlaywrightManager.getPlaywright().chromium();
        };
    }

    private static BrowserType.LaunchOptions _getLaunchOptions() {

        Map<String, String> _map = new HashMap<>();
        _map.put("PLAYWRIGHT_SKIP_BROWSER_DOWNLOAD", PLAYWRIGHT_SKIP_BROWSER_DOWNLOAD);

        return new BrowserType.LaunchOptions()
                .setEnv(_map)
                .setDownloadsPath(Path.of(DOWNLOAD_PATH))
//                .setArgs(List.of(START_MAXIMIZED, START_FULLSCREEN, WINDOW_POSITION, WINDOW_SIZE, DISABLE_INFOBARS, DISABLE_NOTIFICATIONS))
                .setArgs(List.of(WINDOW_POSITION, WINDOW_SIZE))
                .setHeadless(HEADLESS)
                .setTracesDir(Path.of(TRACE_DIR))
                .setTimeout(WAIT_TIMEOUT);
//                .setIgnoreDefaultArgs(List.of(ENABLE_AUTOMATION));
    }

    private static Browser _createRemoteBrowser() {

        LOGGER.info("Creating a persistent browser context.");

        JsonObject capabilitiesObject = new JsonObject();
        capabilitiesObject.addProperty("browser", "chrome");    // allowed browsers are `chrome`, `edge`, `playwright-chromium`, `playwright-firefox` and `playwright-webkit`
        capabilitiesObject.addProperty("browser_version", "latest");
        capabilitiesObject.addProperty("os", "osx");
        capabilitiesObject.addProperty("os_version", "catalina");
        capabilitiesObject.addProperty("name", "Playwright first single test");
        capabilitiesObject.addProperty("build", "playwright-java-1");
        capabilitiesObject.addProperty("buildTag", "smoke");
        capabilitiesObject.addProperty("browserstack.username", "BROWSERSTACK_USERNAME");
        capabilitiesObject.addProperty("browserstack.accessKey", "BROWSERSTACK_ACCESS_KEY");
        capabilitiesObject.addProperty("browserstack.debug", "true");
        capabilitiesObject.addProperty("browserstack.networkLogs", "true");
        capabilitiesObject.addProperty("browserstack.console", "disable");
        capabilitiesObject.addProperty("browserstack.local", "false");
        capabilitiesObject.addProperty("browserstack.playwrightLogs", "true");
        capabilitiesObject.addProperty("browserstack.geoLocation", "FR");
        capabilitiesObject.addProperty("resolution", "1024x768");
        capabilitiesObject.addProperty("browserstack.maskCommands", "sendType, sendPress, setHTTPCredentials, setStorageState, setGeolocation");
        capabilitiesObject.addProperty("client.playwrightVersion", "1.31.0");

        HashMap<String, Boolean> networkLogsOptions = new HashMap<>();
        networkLogsOptions.put("captureContent", true);
//        caps.setCapability("browserstack.networkLogs", true);
//        caps.setCapability("browserstack.networkLogsOptions", networkLogsOptions);

        String caps = URLEncoder.encode(capabilitiesObject.toString(), StandardCharsets.UTF_8);
        String ws_endpoint = "wss://cdp.browserstack.com/playwright?caps=" + caps;

        return BrowserManager._getBrowserTypeObj(BROWSER).connect(ws_endpoint);
    }

    private static Browser _createCdpBrowser(String cdpUrl) {
        LOGGER.info("Creating a persistent browser context.");
        return BrowserManager._getBrowserTypeObj(BROWSER).connectOverCDP(cdpUrl);
    }

    private static BrowserType.LaunchOptions _setBrowserExecutable(BrowserType.LaunchOptions _launchOptions) {
        return _launchOptions.setExecutablePath(Path.of(EXECUTABLE_PATH));
    }
}
