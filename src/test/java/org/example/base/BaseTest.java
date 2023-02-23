package org.example.base;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.ScreenshotAnimations;
import com.microsoft.playwright.options.ScreenshotCaret;
import com.microsoft.playwright.options.ScreenshotScale;
import com.microsoft.playwright.options.ScreenshotType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.browserManager.DriverFactory;
import org.example.utils.data.PropertyReader;
import org.testng.ITestResult;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

import static org.example.utils.configs.Constants.SCREENSHOT_PATH;
import static org.example.utils.configs.Constants.WAIT_TIMEOUT;

public class BaseTest {

    Logger logger = LogManager.getLogger(BasePage.class);

    public BaseTest() {
        PropertyReader.loadAsSystemProperties(Paths.get(System.getProperty("user.dir")+"/config.properties"));
    }

    protected Page getPage() {
        return DriverFactory.getPage();
    }

    @Parameters("browser")
    @BeforeTest
    public synchronized void startDriver(@Optional String browser) {
        logger.info("Starting Driver");
        getPage();
        logger.info("Current Thread info = " + Thread.currentThread().getId() + ", Driver = " + getPage());
    }

    @Parameters("browser")
    @AfterTest
    public synchronized void quitDriver(@Optional String browser) {
        logger.info("Quitting Driver");
        DriverFactory.quit();
    }

    private void takeScreenshotOnTestFailure(String browser, ITestResult result) throws IOException {
        logger.info("Current Thread info = " + Thread.currentThread().getId() + ", Driver = " + getPage());
        if (result.getStatus() == ITestResult.FAILURE) {
            File destFile = new File("Screenshots" + File.separator + browser + File.separator + result.getTestClass().getRealClass().getSimpleName() + "_" + result.getMethod().getMethodName() + ".png");
            takeScreenshot(destFile);
        }
    }

    private String takeScreenshot(File destFile) {

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
