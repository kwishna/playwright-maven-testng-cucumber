package org.example.utils.assertions;

import org.example.reportings.ExtentLogger;
import org.hamcrest.Matcher;
import org.testng.Assert;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class Assertions {

    public static <T> void assertEquals(T actual, T expected, String message) {
        try {
            assertThat(message, actual, equalTo(expected));
            ExtentLogger.pass(message + "<b><i>Actual: </i> </b>" + actual + " and <b><i> Expected: equal To </i> </b>" + expected);
        } catch (AssertionError assertionError) {
            ExtentLogger.fail("<b><i>" + assertionError.getMessage() + "</i></b>");
            Assert.fail(assertionError.getMessage());
        }
    }

    public static <T> void matches(T actual, Matcher<? super T> matcher) {
        try {
            assertThat(actual, matcher);
            ExtentLogger.pass("<b><i>Actual: </i> </b>" + actual + " and <b><i> Expected: " + matcher.toString() + " </i> </b>");
        } catch (AssertionError assertionError) {
            ExtentLogger.fail("<b><i>" + assertionError.getMessage() + "</i></b>");
            Assert.fail(assertionError.getMessage());
        }
    }

    public static void assertContains(String actual, String expected, String message) {
        try {
            assertThat(message, actual, containsString(expected));
            ExtentLogger.pass(message + "<b><i>Actual: </i> </b>" + actual + " and <b><i> Expected: Contains String </i> </b>" + expected);
        } catch (AssertionError assertionError) {
            ExtentLogger.fail("<b><i>" + assertionError.getMessage() + "</i></b>");
            Assert.fail(assertionError.getMessage());
        }
    }

    public static void assertContainsIgnoreCase(String actual, String expected, String message) {
        try {
            assertThat(message, actual, containsStringIgnoringCase(expected));
            ExtentLogger.pass(message + "<b><i>Actual: </i> </b>" + actual + " and <b><i> Contains String IgnoringCase: </i> </b>" + expected);
        } catch (AssertionError assertionError) {
            ExtentLogger.fail("<b><i>" + assertionError.getMessage() + "</i></b>");
            Assert.fail(assertionError.getMessage());
        }
    }

    public static <T> void assertHasItem(List<T> actual, T expected, String message) {
        try {
            assertThat(message, actual, hasItem(expected));
            ExtentLogger.pass(message + "<b><i>Actual: </i> </b>" + actual + " and <b><i> Expected: Has Item </i> </b>" + expected);
        } catch (AssertionError assertionError) {
            ExtentLogger.fail("<b><i>" + assertionError.getMessage() + "</i></b>");
            Assert.fail(assertionError.getMessage());
        }
    }

    @SafeVarargs
    public static <T> void assertHasItems(List<T> actual, T... expected) {
        try {
            assertThat(actual, hasItems(expected));
            ExtentLogger.pass("<b><i>Actual: </i> </b>" + actual + " and <b><i> Has Items: </i> </b>" + Arrays.toString(expected));
        } catch (AssertionError assertionError) {
            ExtentLogger.fail("<b><i>" + assertionError.getMessage() + "</i></b>");
            Assert.fail(assertionError.getMessage());
        }
    }

    public static void assertTrue(boolean result, String message) {
        try {
            assertThat(message, result, is(true));
            ExtentLogger.pass("<b><i>" + message + "</b></i>");
        } catch (AssertionError assertionError) {
            ExtentLogger.fail("<b><i>" + assertionError.getMessage() + "</i></b>");
            Assert.fail(assertionError.getMessage());
        }
    }

    public static void assertFalse(boolean result, String message) {
        try {
            assertThat(message, result, is(false));
            ExtentLogger.pass("<b><i>" + message + "</b></i>");
        } catch (AssertionError assertionError) {
            ExtentLogger.fail("<b><i>" + assertionError.getMessage() + "</i></b>");
            Assert.fail(assertionError.getMessage());
        }
    }

}
