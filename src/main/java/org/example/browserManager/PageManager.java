package org.example.browserManager;

import com.microsoft.playwright.Page;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

public final class PageManager {
    private static final ThreadLocal<Page> PAGE_THREAD_LOCAL = ThreadLocal.withInitial(() -> null);
    private static final Logger LOGGER = LogManager.getLogger(PageManager.class);

    private PageManager() {
    }

    static Page getPage() {
        if (Objects.isNull(PAGE_THREAD_LOCAL.get())) {
            LOGGER.info("Creating a new driver.");
            Page _driver = BrowserContextManager.getBrowserContext().newPage();
            setPage(_driver);
        }
        return PAGE_THREAD_LOCAL.get();
    }

    private static void setPage(Page page) {
        PAGE_THREAD_LOCAL.set(page);
    }

    static void quit() {
        if (Objects.nonNull(PAGE_THREAD_LOCAL.get())) {
            try {
                LOGGER.info("Closing the driver.");
                PAGE_THREAD_LOCAL.get().close(new Page.CloseOptions().setRunBeforeUnload(false));
                PAGE_THREAD_LOCAL.remove();
            } catch (Exception err) {
                LOGGER.error("Error while closing the driver: " + err);
            }
        }
    }
}
