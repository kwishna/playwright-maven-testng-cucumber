package org.example.browserManager;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Tracing;
import com.microsoft.playwright.options.ColorScheme;
import com.microsoft.playwright.options.ForcedColors;
import com.microsoft.playwright.options.HarContentPolicy;
import com.microsoft.playwright.options.HarMode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Path;
import java.util.Objects;

import static org.example.utils.configs.Constants.*;

public final class BrowserContextManager {
    private static final ThreadLocal<BrowserContext> BROWSER_CONTEXT_THREAD_LOCAL = ThreadLocal.withInitial(() -> null);
    private static final Logger LOGGER = LogManager.getLogger(BrowserContextManager.class);

    private BrowserContextManager() {
    }

    static BrowserContext getBrowserContext() {
        if (Objects.isNull(BROWSER_CONTEXT_THREAD_LOCAL.get())) {
            LOGGER.info("Creating a new browser context.");

            Browser.NewContextOptions _newContextOps = _getNewContextOptions();

            _newContextOps = RECORD_VIDEO ? _enableVideoRecording(_newContextOps) : _newContextOps;
            _newContextOps = PERSISTENT_LOGIN ? _enablePersistentData(_newContextOps) : _newContextOps;

            BrowserContext _browserCtx = BrowserManager.getBrowser().newContext(_newContextOps);
            _enableTracing(_browserCtx);
            setBrowserContext(_browserCtx);
        }
        return BROWSER_CONTEXT_THREAD_LOCAL.get();
    }

    static void setBrowserContext(BrowserContext ctx) {
        BROWSER_CONTEXT_THREAD_LOCAL.set(ctx);
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
                .setRecordHarContent(HarContentPolicy.EMBED)
                .setRecordHarMode(HarMode.FULL)
                .setRecordHarPath(Path.of(HAR_PATH))
                .setStrictSelectors(false)
                .setViewportSize(1920, 1080);
    }

    private static Browser.NewContextOptions _enableVideoRecording(Browser.NewContextOptions newContextOptions) {
        return newContextOptions.setRecordVideoDir(Path.of(VIDEO_DIR));
    }

    private static Browser.NewContextOptions _enablePersistentData(Browser.NewContextOptions newContextOptions) {
        return newContextOptions.setStorageStatePath(Path.of(PERSISTENT_DIR));
    }

    private static void _enableTracing(BrowserContext browserCtx) {

//        System.setProperty("PLAYWRIGHT_JAVA_SRC", PROJECT_PATH);
        browserCtx.tracing().start(new Tracing.StartOptions()
                .setScreenshots(true)
                .setSnapshots(true)
                .setSources(false));
    }
}
