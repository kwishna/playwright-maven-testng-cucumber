package org.example.browserManager;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Tracing;
import com.microsoft.playwright.options.ColorScheme;
import com.microsoft.playwright.options.ForcedColors;
import com.microsoft.playwright.options.HarContentPolicy;
import com.microsoft.playwright.options.HarMode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.example.utils.configs.BrowserConfig.*;
import static org.example.utils.configs.Constants.*;

public final class BrowserContextManager {
    private static final ThreadLocal<BrowserContext> BROWSER_CONTEXT_THREAD_LOCAL = ThreadLocal.withInitial(() -> null);
    private static final Logger LOGGER = LogManager.getLogger(BrowserContextManager.class);

    private BrowserContextManager() {
    }

    static BrowserContext getBrowserContext() {
        if (Objects.isNull(BROWSER_CONTEXT_THREAD_LOCAL.get())) {
            LOGGER.info("Creating a new browser context.");

            BrowserContext _browserCtx;

            _browserCtx = PERSISTENT_LOGIN ? _createPersistentContext() : _createNormalContext();

            if (ENABLE_TRACING) {
                _enableTracing(_browserCtx);
            }

            setBrowserContext(_browserCtx);
        }
        return BROWSER_CONTEXT_THREAD_LOCAL.get();
    }

    static void setBrowserContext(BrowserContext ctx) {
        BROWSER_CONTEXT_THREAD_LOCAL.set(ctx);
    }

    private static BrowserContext _createNormalContext() {
        Browser.NewContextOptions _newContextOps = _getNewContextOptions();

        _newContextOps = RECORD_VIDEO ? _enableVideoRecording(_newContextOps) : _newContextOps;
        _newContextOps = STORE_AUTHENTICATION ? _setAuthenticationStorage(_newContextOps) : _newContextOps;

        return BrowserManager.getBrowser().newContext(_newContextOps);
    }

    private static BrowserContext _createPersistentContext() {
        LOGGER.info("Creating a persistent browser context.");
        BrowserType.LaunchPersistentContextOptions _launchOptions = _getPersistentContextOptions();
        return BrowserManager._getBrowserTypeObj(BROWSER).launchPersistentContext(Path.of(PERSISTENT_DIR), _launchOptions);
    }

    static void quit() {
        if (Objects.nonNull(BROWSER_CONTEXT_THREAD_LOCAL.get())) {
            LOGGER.info("Destroying the context.");
            try {
                BROWSER_CONTEXT_THREAD_LOCAL.get().close();
                BROWSER_CONTEXT_THREAD_LOCAL.remove();
            } catch (Exception err) {
                LOGGER.info("Failed To Close Browser Context: " + err);
            }
        }
    }

    private static Browser.NewContextOptions _getNewContextOptions() {
        return new Browser.NewContextOptions()
                .setAcceptDownloads(true)
                .setBaseURL(BASE_URL)
                .setColorScheme(ColorScheme.NO_PREFERENCE)
                .setDeviceScaleFactor(1.0)
                .setForcedColors(ForcedColors.NONE)
                .setLocale("en-US")
                .setRecordHarMode(HarMode.FULL)
                .setRecordHarPath(Path.of(HAR_PATH))
                .setRecordHarContent(HarContentPolicy.EMBED)
                .setStrictSelectors(false)
                .setViewportSize(SCREEN_WIDTH, SCREEN_HEIGHT)
                .setJavaScriptEnabled(true);
    }

    private static BrowserType.LaunchPersistentContextOptions _getPersistentContextOptions() {

        Map<String, String> _map = new HashMap<>();
        _map.put("PLAYWRIGHT_SKIP_BROWSER_DOWNLOAD", PLAYWRIGHT_SKIP_BROWSER_DOWNLOAD);

        BrowserType.LaunchPersistentContextOptions persistentContextOptions = new BrowserType.LaunchPersistentContextOptions()
                .setEnv(_map)
                .setDownloadsPath(Path.of(DOWNLOAD_PATH))
                .setArgs(List.of(START_MAXIMIZED, START_FULLSCREEN, WINDOW_POSITION, WINDOW_SIZE, DISABLE_INFOBARS, DISABLE_NOTIFICATIONS))
                .setHeadless(HEADLESS)
                .setTracesDir(Path.of(TRACE_DIR))
                .setTimeout(WAIT_TIMEOUT)
                .setIgnoreDefaultArgs(List.of(ENABLE_AUTOMATION))
                .setAcceptDownloads(true)
                .setBaseURL(BASE_URL)
                .setColorScheme(ColorScheme.NO_PREFERENCE)
                .setDeviceScaleFactor(1.0)
                .setForcedColors(ForcedColors.NONE)
                .setLocale("en-US")
                .setRecordHarMode(HarMode.FULL)
                .setRecordHarPath(Path.of(HAR_PATH))
                .setRecordHarContent(HarContentPolicy.EMBED)
                .setStrictSelectors(false)
                .setViewportSize(SCREEN_WIDTH, SCREEN_HEIGHT)
                .setJavaScriptEnabled(true);

        persistentContextOptions = RECORD_VIDEO ? _enableVideoRecording(persistentContextOptions) : persistentContextOptions;
        return persistentContextOptions;
    }

    private static Browser.NewContextOptions _enableVideoRecording(Browser.NewContextOptions newContextOptions) {
        return newContextOptions.setRecordVideoDir(Path.of(VIDEO_DIR));
    }

    private static Browser.NewContextOptions _setAuthenticationStorage(Browser.NewContextOptions newContextOptions) {
        return newContextOptions.setStorageStatePath(Paths.get("auth.json"));
    }

    private static BrowserType.LaunchPersistentContextOptions _enableVideoRecording(BrowserType.LaunchPersistentContextOptions persistentContextOptions) {
        return persistentContextOptions.setRecordVideoDir(Path.of(VIDEO_DIR));
    }

    private static BrowserContext _enableTracing(BrowserContext browserCtx) {

//        System.setProperty("PLAYWRIGHT_JAVA_SRC", PROJECT_PATH);
        browserCtx.tracing().start(new Tracing.StartOptions()
                .setScreenshots(true)
                .setSnapshots(true)
                .setSources(false));
        return browserCtx;
    }
}
