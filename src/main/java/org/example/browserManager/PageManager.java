package org.example.browserManager;

import com.microsoft.playwright.Page;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

public final class PageManager {
    private static final ThreadLocal<Page> PAGE_THREAD_LOCAL = ThreadLocal.withInitial(() -> null);
    private static final Logger logger = LogManager.getLogger(PageManager.class);

    private PageManager() {
    }

    public static Page getPage() {
        if (Objects.isNull(PAGE_THREAD_LOCAL.get())) {
            logger.info("Creating a new driver.");
            Page _driver = BrowserContextManager.getBrowserContext().newPage();
            setDriver(_driver);
        }
        return PAGE_THREAD_LOCAL.get();
    }

    public static void setDriver(Page driver) {
        PAGE_THREAD_LOCAL.set(driver);
    }

    public static void quit() {
        if (Objects.nonNull(PAGE_THREAD_LOCAL.get())) {
            try {
                logger.info("Closing the driver.");
                PAGE_THREAD_LOCAL.get().close(new Page.CloseOptions().setRunBeforeUnload(false));
                PAGE_THREAD_LOCAL.remove();
            } catch (Exception err) {
                logger.error("Error while closing the driver: " + err);
                System.err.println(err.getMessage());
            }
        }
    }
}