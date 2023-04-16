package org.example.base;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.ScreenshotAnimations;
import com.microsoft.playwright.options.ScreenshotCaret;
import com.microsoft.playwright.options.ScreenshotScale;
import com.microsoft.playwright.options.ScreenshotType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.browserManager.DriverFactory;
import org.example.listeners.ListenerClass;
import org.example.utils.data.PropertyReader;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

import static org.example.utils.configs.Constants.SCREENSHOT_PATH;
import static org.example.utils.configs.Constants.WAIT_TIMEOUT;

@Listeners({ListenerClass.class})
public class BaseTest {

    Logger logger = LogManager.getLogger(BasePage.class);

    public BaseTest() {
//        PropertyReader.loadAsSystemProperties(Paths.get(System.getProperty("user.dir") + "/config.properties")); // Loading From pom.xml
    }

//    protected Page getPage() {
//        return DriverFactory.getPage();
//    }

    @Parameters("browser")
    @BeforeTest
    public synchronized void startDriver(@Optional("chrome") String browser) {
        logger.info("Starting Driver");
//        getPage();
//        logger.info("Current Thread info = " + Thread.currentThread().getId() + ", Driver = " + getPage());
        logger.info("Current Thread info = " + Thread.currentThread().threadId());
    }

    @Parameters("browser")
    @AfterTest
    public synchronized void quitDriver(@Optional("chrome") String browser) {
        logger.info("Quitting Driver");
//        DriverFactory.quit();
    }
}
