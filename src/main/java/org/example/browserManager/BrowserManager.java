package org.example.browserManager;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Playwright;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.example.utils.Constants.*;

public class BrowserManager {
    private static final ThreadLocal<Browser> BROWSER_THREAD_LOCAL = ThreadLocal.withInitial(() -> null);

    private static final Logger logger = LogManager.getLogger(BrowserManager.class);

    public static Browser getBrowser() {
        if (Objects.isNull(BROWSER_THREAD_LOCAL.get())) {
            logger.info("Creating a new browser.");
            Browser _browser = _launchBrowser();
            setDriver(_browser);
        }
        return BROWSER_THREAD_LOCAL.get();
    }

    public static void setDriver(Browser driver) {
        BROWSER_THREAD_LOCAL.set(driver);
    }

    private static Browser _launchBrowser() {
        Map<String, String> _map = new HashMap<>();
//        _map.put("PLAYWRIGHT_SKIP_BROWSER_DOWNLOAD", "1");

        BrowserType.LaunchOptions _launchOptions = new BrowserType.LaunchOptions()
                .setEnv(_map)
                .setDownloadsPath(Path.of(DOWNLOAD_PATH))
                .setArgs(List.of("--start-maximized", "--start-fullscreen", "--window-position=-5,-5", "--window-size=1920x1080", "--disable-infobars", "--disable-notifications"))
                .setHeadless(HEADLESS)
                .setTracesDir(Path.of(TRACE_DIR))
                .setTimeout(WAIT_TIMEOUT)
                .setIgnoreDefaultArgs(List.of("--enable-automation"));

        return _getBrowserTypeObj(BROWSER).launch(_launchOptions);
    }

    private static BrowserType _getBrowserTypeObj(String browserName) {
        Map<String, String> _map = new HashMap<>();
//        _map.put("PLAYWRIGHT_SKIP_BROWSER_DOWNLOAD", "1");
        Playwright.CreateOptions _cOps = new Playwright.CreateOptions();
        BrowserType _type;
        Playwright _pw = Playwright.create(_cOps);
        switch (browserName.toLowerCase()) {
            case "chromium" -> _type = _pw.chromium();
            case "chrome" -> _type = _pw.chromium();
            case "firefox" -> _type = _pw.firefox();
            case "webkit" -> _type = _pw.webkit();
            case "safari" -> _type = _pw.webkit();
            default -> _type = _pw.chromium();
        }
        return _type;
    }
}
