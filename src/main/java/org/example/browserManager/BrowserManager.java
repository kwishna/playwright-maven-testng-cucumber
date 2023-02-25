package org.example.browserManager;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
            LOGGER.info("Creating a new browser.");
            Browser _browser = _launchBrowser();
            setBrowser(_browser);
        }
        return BROWSER_THREAD_LOCAL.get();
    }

    protected static void setBrowser(Browser browser) {
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
                .setArgs(List.of(START_MAXIMIZED, START_FULLSCREEN, WINDOW_POSITION, WINDOW_SIZE, DISABLE_INFOBARS, DISABLE_NOTIFICATIONS))
                .setHeadless(HEADLESS)
                .setTracesDir(Path.of(TRACE_DIR))
                .setTimeout(WAIT_TIMEOUT)
                .setIgnoreDefaultArgs(List.of(ENABLE_AUTOMATION));
    }

    private static BrowserType.LaunchOptions _setBrowserExecutable(BrowserType.LaunchOptions _launchOptions) {
        return _launchOptions.setExecutablePath(Path.of(EXECUTABLE_PATH));
    }
}
