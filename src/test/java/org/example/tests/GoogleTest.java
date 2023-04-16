package org.example.tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.base.BaseTest;
import org.example.pages.GooglePage;
import org.example.utils.assertions.Assertions;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

public class GoogleTest extends BaseTest {

    Logger logger = LogManager.getLogger(BaseTest.class);

    @Test
    public void homePageTest() {
        System.out.println("EMAIL_TO: " + System.getProperty("EMAIL_TO"));
        logger.info("Home Page Navigation");
        GooglePage _googlePg = new GooglePage(getPage());
        _googlePg.navigate();
        Assertions.assertTrue(_googlePg.isSearchPageOpen(), "Search Page Is Not Opened.");
        _googlePg.performSearch("2047");
        Assertions.assertTrue(_googlePg.isResultPageLoaded(), "Result Page Is Not Opened.");
        Assertions.matches(_googlePg.getResultCount(), is(greaterThanOrEqualTo(5)));
        _googlePg.navigateToFirstLink();
        System.out.println(_googlePg.getPageTitle());
        System.out.println(_googlePg.getCurrentUrl());
    }

    @Test
    public void homePageTest1() {
        System.out.println("EMAIL_TO: " + System.getProperty("EMAIL_TO"));
        logger.info("Home Page Navigation");
        GooglePage _googlePg = new GooglePage(getPage());
        _googlePg.navigate();
        Assertions.assertTrue(_googlePg.isSearchPageOpen(), "Search Page Is Not Opened.");
        _googlePg.performSearch("2047");
        Assertions.assertTrue(_googlePg.isResultPageLoaded(), "Result Page Is Not Opened.");
        Assertions.matches(_googlePg.getResultCount(), is(greaterThanOrEqualTo(5)));
        _googlePg.navigateToFirstLink();
        System.out.println(_googlePg.getPageTitle());
        System.out.println(_googlePg.getCurrentUrl());
    }

    @Test
    public void homePageTest2() {
        System.out.println("EMAIL_TO: " + System.getProperty("EMAIL_TO"));
        logger.info("Home Page Navigation");
        GooglePage _googlePg = new GooglePage(getPage());
        _googlePg.navigate();
        Assertions.assertTrue(_googlePg.isSearchPageOpen(), "Search Page Is Not Opened.");
        _googlePg.performSearch("2047");
        Assertions.assertTrue(_googlePg.isResultPageLoaded(), "Result Page Is Not Opened.");
        Assertions.matches(_googlePg.getResultCount(), is(greaterThanOrEqualTo(5)));
        _googlePg.navigateToFirstLink();
        System.out.println(_googlePg.getPageTitle());
        System.out.println(_googlePg.getCurrentUrl());
    }
}