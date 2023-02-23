package org.example.browserManager;

import com.microsoft.playwright.*;
import org.example.utils.Utilities;

import java.nio.file.Path;

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

    public static Playwright getPlaywright() {
        return PlaywrightManager.getPlaywright();
    }

    public static void stopTracing() {
        getContext().tracing().stop(new Tracing.StopOptions().setPath(Path.of(Utilities.timeStamp() + ".zip")));
    }

    public static void quit() {
        stopTracing();
        PageManager.quit();
        BrowserContextManager.quit();
        BrowserManager.quit();
        PlaywrightManager.quit();
    }

}
