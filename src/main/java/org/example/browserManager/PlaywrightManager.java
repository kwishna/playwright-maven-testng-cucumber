package org.example.browserManager;

import com.microsoft.playwright.Playwright;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.example.utils.configs.Constants.PLAYWRIGHT_SKIP_BROWSER_DOWNLOAD;

public final class PlaywrightManager {
    private static final ThreadLocal<Playwright> PLAYWRIGHT_THREAD_LOCAL = ThreadLocal.withInitial(() -> null);
    private static final Logger LOGGER = LogManager.getLogger(PlaywrightManager.class);

    private PlaywrightManager() {
    }

    static Playwright getPlaywright() {
        if (Objects.isNull(PLAYWRIGHT_THREAD_LOCAL.get())) {
            LOGGER.info("Creating a playwright.");

            Map<String, String> _map = new HashMap<>();
            _map.put("PLAYWRIGHT_SKIP_BROWSER_DOWNLOAD", PLAYWRIGHT_SKIP_BROWSER_DOWNLOAD);
            
            Playwright.CreateOptions _cOps = new Playwright.CreateOptions();
            _cOps.setEnv(_map);

            Playwright pw = Playwright.create(_cOps);
            LOGGER.info("A new playwright is created.");
            setPlaywright(pw);
        }
        return PLAYWRIGHT_THREAD_LOCAL.get();
    }

    private static void setPlaywright(Playwright pw) {
        LOGGER.info("Setting a new playwright.");
        PLAYWRIGHT_THREAD_LOCAL.set(pw);
    }

    static void quit() {
        if (Objects.nonNull(PLAYWRIGHT_THREAD_LOCAL.get())) {
            LOGGER.info("Destroying the playwright.");
            try {
                PLAYWRIGHT_THREAD_LOCAL.get().close();
                PLAYWRIGHT_THREAD_LOCAL.remove();
                LOGGER.info("The playwright is closed.");
            } catch (Exception err) {
                LOGGER.info("Failed To Close playwright: " + err);
                System.err.println(err.getMessage());
            }
        }
    }
}
