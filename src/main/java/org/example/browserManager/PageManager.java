package org.example.browserManager;

import com.microsoft.playwright.Page;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.playwrightUtils.DialogAlertHandler;

import java.util.Objects;

public final class PageManager {
    private static final ThreadLocal<Page> PAGE_THREAD_LOCAL = ThreadLocal.withInitial(() -> null);
    private static final Logger LOGGER = LogManager.getLogger(PageManager.class);

    private PageManager() {
    }

    static Page getPage() {
        if (Objects.isNull(PAGE_THREAD_LOCAL.get())) {
            LOGGER.info("Creating a new page.");
            Page _driver = BrowserContextManager.getBrowserContext().newPage();
            LOGGER.info("A new page is created.");
            setPage(_driver);
        }
        return PAGE_THREAD_LOCAL.get();
    }

    static void setPage(Page page) {
        LOGGER.info("Setting a new page.");
        PAGE_THREAD_LOCAL.set(page);
    }

    static void quit() {
        if (Objects.nonNull(PAGE_THREAD_LOCAL.get())) {
            try {
                LOGGER.info("Closing the page.");
                PAGE_THREAD_LOCAL.get().close(new Page.CloseOptions().setRunBeforeUnload(false));
                PAGE_THREAD_LOCAL.remove();
                LOGGER.info("Page is closed.");
            } catch (Exception err) {
                LOGGER.error("Error while closing the driver: " + err);
            }
        }
    }
}
