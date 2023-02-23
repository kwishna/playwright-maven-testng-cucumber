package org.example.utils.assertions;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.assertions.LocatorAssertions;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class PwAssertions {

    public static void assertText(Locator locator, String expectedText) {
        assertThat(locator).hasText(expectedText, new LocatorAssertions.HasTextOptions().setIgnoreCase(true));
    }

    public static void assertAttribute(Locator locator, String attributeName, String attributeValue) {
        assertThat(locator).hasAttribute(attributeName, attributeValue);
    }
}
