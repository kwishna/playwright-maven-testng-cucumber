package org.example.browserManager;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.options.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Path;
import java.util.Objects;

import static org.example.utils.Constants.*;

public final class BrowserContextManager {
    private static final ThreadLocal<BrowserContext> BROWSER_CONTEXT_THREAD_LOCAL = ThreadLocal.withInitial(() -> null);
    private static final Logger logger = LogManager.getLogger(BrowserContextManager.class);

    private BrowserContextManager() {
    }

    public static BrowserContext getBrowserContext() {
        if (Objects.isNull(BROWSER_CONTEXT_THREAD_LOCAL.get())) {
            logger.info("Creating a new browser context.");
            Browser.NewContextOptions _newContextOps = new Browser.NewContextOptions()
                    .setAcceptDownloads(true)
                    .setBaseURL(BASE_URL)
                    .setColorScheme(ColorScheme.NO_PREFERENCE)
                    .setDeviceScaleFactor(1.0)
                    .setForcedColors(ForcedColors.NONE)
//                    .setGeolocation(new Geolocation())
//                    .setGeolocation()
//                    .setHasTouch(false)
//                    .setJavaScriptEnabled(true)
//                    .setOffline(false)
                    .setLocale("en-US")
                    .setRecordHarContent(HarContentPolicy.EMBED)
                    .setRecordHarMode(HarMode.FULL)
                    .setRecordHarPath(Path.of(HAR_PATH))
//                    .setRecordVideoDir(Path.of("/video"))
//                    .setStorageStatePath(Path.of(PERSISTENT_DIR))
                    .setStrictSelectors(false)
                    .setViewportSize(1920, 1080);

            Object _i = RECORD_VIDEO ? _newContextOps.setRecordVideoDir(Path.of(VIDEO_DIR)) : "";
            Object _j = PERSISTENT_LOGIN ? _newContextOps.setStorageStatePath(Path.of(PERSISTENT_DIR)) : "";

            BrowserContext _browserCtx = BrowserManager.getBrowser().newContext(_newContextOps);
            setBrowserContext(_browserCtx);
        }
        return BROWSER_CONTEXT_THREAD_LOCAL.get();
    }

    public static void setBrowserContext(BrowserContext ctx) {
        BROWSER_CONTEXT_THREAD_LOCAL.set(ctx);
    }

    public static void quit() {
        if (Objects.nonNull(BROWSER_CONTEXT_THREAD_LOCAL.get())) {
            logger.info("Destroying the context.");
            try {
                BROWSER_CONTEXT_THREAD_LOCAL.get().close();
                BROWSER_CONTEXT_THREAD_LOCAL.remove();
            } catch (Exception err) {
                logger.info("Failed To Close Browser Context: " + err);
                System.err.println(err.getMessage());
            }
        }
    }
}
