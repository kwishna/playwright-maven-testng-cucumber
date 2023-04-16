package org.example.cucumber.step_definitions;

import com.microsoft.playwright.Page;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.example.base.BasePage;
import org.example.browserManager.DriverFactory;
import org.example.pages.PlaywrightPage;

/*
        1) Selenium Grid 4 - Standalone
        -------------------------------
        java -jar selenium-server-4.0.0-alpha-7.jar standalone

        ----------------
        2) Hub and Node
        ----------------
        java -jar selenium-server-4.0.0-alpha-7.jar hub
        java -jar selenium-server-4.0.0-alpha-7.jar node --detect-drivers true

        3) Selenium Grid 4 - Distributed
        ---------------------------------
        java -jar selenium-server-4.0.0-alpha-1.jar sessions
        java -jar selenium-server-4.0.0-alpha-1.jar distributor --sessions http://localhost:5556
        java -jar selenium-server-4.0.0-alpha-1.jar router --sessions http://localhost:5556 --distributor http://localhost:5553
 */

public class PlaywrightsSteps {

    Page page;

    @Given("User navigates To URL: {string}")
    public void user_navigates_to_url(String url) {
        new PlaywrightPage(page).openUrl(url);
    }

    @And("User clicks on 'Slow calculator'")
    public void userClicksOnSlowCalculator() {
        new PlaywrightPage(page).clickSlowCalculator();
    }

    @Then("User quits The Browser")
    public void userQuitsTheBrowser() {
        DriverFactory.quit();
    }

    @And("User takes screenshot of 'Calculator' element")
    public void userTakesScreenshotOfCalculatorElement() {
        new PlaywrightPage(page).userTakesScreenshotOfCalculatorElement();
    }

    @And("User opens {string} in a new window")
    public void userOpensANewWindowOnTheBrowser(String url) {
        new PlaywrightPage(page).userOpensWindowOnTheBrowser(url);
    }

    @And("User opens {string} in a new tab")
    public void userOpensANewTabOnTheBrowser(String url) {
        new PlaywrightPage(page).userOpensTabOnTheBrowser(url);
    }

    @And("User verify total tabs opened are: {int}")
    public void userVerifyTotalTabsOpenedAre(int num) {
        new PlaywrightPage(page).userVerifyTotalTabsOpenedAre(num);
    }

    @And("User print the location of the object")
    public void userPrintTheLocationOfTheObject() {
        new PlaywrightPage(page).userPrintTheLocationOfTheObject();
    }

    @And("Login using relative locators")
    public void loginUsingRelativeLocators() {
        new PlaywrightPage(page).loginUsingRelativeLocators();
    }
}
