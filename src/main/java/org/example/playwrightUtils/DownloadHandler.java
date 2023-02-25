package org.example.playwrightUtils;

import com.microsoft.playwright.*;
import org.example.reportings.ExtentLogger;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.function.Consumer;

import static org.example.browserManager.DriverFactory.getPage;

public class DownloadHandler {
    private static final ThreadLocal<Download> DOWNLOAD_THREAD_LOCAL = ThreadLocal.withInitial(() -> null);

    public static Download getDownload() {
        return DOWNLOAD_THREAD_LOCAL.get();
    }

    private static void setDownload(Download download) {
        DOWNLOAD_THREAD_LOCAL.set(download);
    }

    public static void registerDownloadActionAlways() {
        getPage().onDownload(DownloadHandler::setDownload);
        ExtentLogger.info("Download File Name : " + getDownloadFileName());
    }

    public static void registerDownloadActionAlways(Consumer<Download> downloadConsumer) {
        getPage().onDownload(downloadConsumer);
    }

    public static void unRegisterDownloadAction(Consumer<Download> downloadConsumer) {
        getPage().offDownload(downloadConsumer);
    }

    public static String getDownloadFileName() {
        Download download = getDownload();
        if (download != null) {
            return download.suggestedFilename();
        } else {
            ExtentLogger.warn("No Download Available!");
        }
        return "";
    }

    public static void saveDownloadFile(Path path) {
        Download download = getDownload();
        if (download != null) {
            download.saveAs(path);
        } else {
            ExtentLogger.warn("No Download Available!");
        }
    }

    public static InputStream getDownloadReadStream() {
        Download download = getDownload();
        if (download != null) {
            return download.createReadStream();
        } else {
            ExtentLogger.warn("No Download Available!");
        }
        return null;
    }

    public static Path getDownloadPath() {
        Download download = getDownload();
        if (download != null) {
            return download.path();
        } else {
            ExtentLogger.warn("No Download Available!");
        }
        return null;
    }

    public static Download clickToDownload(ElementHandle elementHandle) {
        return getPage().waitForDownload(elementHandle::click);
    }

    public static Download clickToDownload(String locator) {
        return clickToDownload(getPage().locator(locator));
    }

    public static Download clickToDownload(Locator locator) {
        return getPage().waitForDownload(locator::click);
    }

    public static Download actionToDownload(Runnable runnable) {
        return getPage().waitForDownload(runnable);
    }

    public static Download actionToDownload(Page.WaitForDownloadOptions downloadOptions, Runnable runnable) {
        return getPage().waitForDownload(downloadOptions, runnable);
    }
}
