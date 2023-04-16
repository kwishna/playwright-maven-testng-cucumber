package org.example.base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.listeners.ListenerClass;
import org.testng.annotations.*;

@Listeners({ListenerClass.class})
public class BaseTest {

    Logger logger = LogManager.getLogger(BaseTest.class);

//    public BaseTest() {
//        PropertyReader.loadAsSystemProperties(Paths.get(System.getProperty("user.dir") + "/config.properties")); // Loading From pom.xml
//    }

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
