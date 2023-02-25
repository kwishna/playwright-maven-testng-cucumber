package org.example.browserManager;

import com.microsoft.playwright.*;
import org.example.utils.Utilities;

import java.io.File;
import java.nio.file.Path;
import java.util.Objects;

import static org.example.utils.configs.Constants.TRACE_DIR;

public final class DriverFactory {
    private DriverFactory() {
    }

    public static Browser getBrowser() {
        return BrowserManager.getBrowser();
    }

    public static BrowserContext getContext() {
        return BrowserContextManager.getBrowserContext();
    }

    public static Page getPage() {
        return PageManager.getPage();
    }

    public static void setPage(Page page) {
        PageManager.setPage(Objects.requireNonNullElse(page, getPage()));
    }

    public static Playwright getPlaywright() {
        return PlaywrightManager.getPlaywright();
    }

    public static void stopTracing() {
        getContext().tracing().stop(new Tracing.StopOptions().setPath(Path.of(TRACE_DIR + File.separator + "Trace_" + Utilities.timeStamp() + ".zip")));
    }

    public static void quit() {
        stopTracing();
        PageManager.quit();
        BrowserContextManager.quit();
        BrowserManager.quit();
        PlaywrightManager.quit();
    }

}
