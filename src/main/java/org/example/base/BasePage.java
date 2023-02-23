package org.example.base;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.microsoft.playwright.Page;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.pages.GooglePage;
import org.example.playwrightUtils.Actions;
import org.example.reportings.ExtentLogger;
import org.example.utils.ScreenshotUtils;

public class BasePage extends Actions {
    Logger logger = LogManager.getLogger(GooglePage.class);
    private Page page;

    public BasePage(Page _page) {
        this.page = _page;
    }

    protected Page getPage() {
        return this.page;
    }

    protected void setPage(Page page) {
        this.page = page;
    }

    public void captureScreenshot() {
        ExtentLogger.info("Capturing Screenshot", MediaEntityBuilder.createScreenCaptureFromBase64String(ScreenshotUtils.getScreenshotAsBase64()));
    }

}
