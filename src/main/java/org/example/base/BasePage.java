package org.example.base;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.ScreenshotAnimations;
import com.microsoft.playwright.options.ScreenshotCaret;
import com.microsoft.playwright.options.ScreenshotScale;
import com.microsoft.playwright.options.ScreenshotType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.browserManager.DriverFactory;
import org.example.pages.GooglePage;
import org.example.playwrightUtils.Actions;
import org.example.reportings.ExtentLogger;
import org.example.utils.ScreenshotUtils;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Base64;

import static org.example.utils.configs.Constants.SCREENSHOT_PATH;
import static org.example.utils.configs.Constants.WAIT_TIMEOUT;

public class BasePage extends Actions {
    Logger logger = LogManager.getLogger(GooglePage.class);
//    private Page page;

//    private BasePage(Page _page) {
//        this.page = _page;
//    }

//    protected BasePage() {
//        this.page = DriverFactory.getPage();
//    }

    protected Page getPage() {
        return DriverFactory.getPage();
    }

//    protected void setPage(Page page) {
//        this.page = page;
//    }

    public void captureScreenshot() {
        ExtentLogger.info("Capturing Screenshot", MediaEntityBuilder.createScreenCaptureFromBase64String(ScreenshotUtils.getScreenshotAsBase64()));
    }

    private void takeScreenshotOnTestFailure(String browser, ITestResult result) throws IOException {
        logger.info("Current Thread info = " + Thread.currentThread().threadId());
        if (result.getStatus() == ITestResult.FAILURE) {
            File destFile = new File("Screenshots" + File.separator + browser + File.separator + result.getTestClass().getRealClass().getSimpleName() + "_" + result.getMethod().getMethodName() + ".png");
            takeScreenshot();
        }
    }

    private String takeScreenshot() {

        Page.ScreenshotOptions screenshotOptions = new Page.ScreenshotOptions();
        screenshotOptions.setAnimations(ScreenshotAnimations.ALLOW);
        screenshotOptions.setCaret(ScreenshotCaret.INITIAL);
        screenshotOptions.setFullPage(true);
        screenshotOptions.setOmitBackground(false);
        screenshotOptions.setQuality(100);
        screenshotOptions.setScale(ScreenshotScale.DEVICE);
        screenshotOptions.setType(ScreenshotType.PNG);
        screenshotOptions.setTimeout(WAIT_TIMEOUT);
        screenshotOptions.setPath(Path.of(SCREENSHOT_PATH, String.valueOf(System.currentTimeMillis()), ".png"));

        byte[] srcFile = getPage().screenshot(screenshotOptions);

        return Base64.getEncoder().encodeToString(srcFile);
    }

}
