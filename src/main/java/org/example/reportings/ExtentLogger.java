package org.example.reportings;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.markuputils.Markup;
import org.example.utils.ScreenshotUtils;
import org.testng.ITestResult;

import java.util.Calendar;
import java.util.Date;

public final class ExtentLogger {

    private ExtentLogger() {
    }

    public static void pass(String message) {
        ExtentManager.getExtentTest().pass(message);
    }

    public static void pass(Markup message) {
        ExtentManager.getExtentTest().pass(message);
    }

    public static void fail(String message) {
        ExtentManager.getExtentTest().fail(message);
    }

    public static void fail(Markup message) {
        ExtentManager.getExtentTest().fail(message);
    }

    public static void skip(String message) {
        ExtentManager.getExtentTest().skip(message);
    }

    public static void skip(Markup message) {
        ExtentManager.getExtentTest().skip(message);
    }

    public static void info(Markup message) {
        ExtentManager.getExtentTest().info(message);
    }

    public static void info(String message) {
        ExtentManager.getExtentTest().info(message);
    }

    public static void pass(String message, boolean isScreeshotNeeded) {
        if (isScreeshotNeeded) {
            ExtentManager.getExtentTest().pass(message, MediaEntityBuilder.createScreenCaptureFromBase64String(ScreenshotUtils.getScreenshotAsBase64()).build());
        } else {
            pass(message);
        }
    }

    public static void pass(Markup message, boolean isScreeshotNeeded) {
        if (isScreeshotNeeded) {
            ExtentManager.getExtentTest().pass(MediaEntityBuilder.createScreenCaptureFromBase64String(ScreenshotUtils.getScreenshotAsBase64()).build());
            ExtentManager.getExtentTest().pass(message);
        } else {
            pass(message);
        }
    }

    public static void fail(String message, boolean isScreeshotNeeded) {
        if (isScreeshotNeeded) {
            ExtentManager.getExtentTest().fail(message, MediaEntityBuilder.createScreenCaptureFromBase64String(ScreenshotUtils.getScreenshotAsBase64()).build());
        } else {
            fail(message);
        }
    }

    public static void fail(Markup message, boolean isScreeshotNeeded) {
        if (isScreeshotNeeded) {
            ExtentManager.getExtentTest().fail(MediaEntityBuilder.createScreenCaptureFromBase64String(ScreenshotUtils.getScreenshotAsBase64()).build());
            ExtentManager.getExtentTest().fail(message);
        } else {
            fail(message);
        }
    }

    public static void skip(String message, boolean isScreeshotNeeded) {
        if (isScreeshotNeeded) {
            ExtentManager.getExtentTest().skip(message, MediaEntityBuilder.createScreenCaptureFromBase64String(ScreenshotUtils.getScreenshotAsBase64()).build());
        } else {
            skip(message);
        }
    }

    public static void skip(Markup message, boolean isScreeshotNeeded) {
        if (isScreeshotNeeded) {
            ExtentManager.getExtentTest().skip(MediaEntityBuilder.createScreenCaptureFromBase64String(ScreenshotUtils.getScreenshotAsBase64()).build());
            ExtentManager.getExtentTest().skip(message);
        } else {
            skip(message);
        }
    }

    public static void info(String message, MediaEntityBuilder builder) {
        ExtentManager.getExtentTest().info(message, builder.build());
    }

    public static void setEndTime(ITestResult result) {
        ExtentReport.getExtentTest().getModel().setEndTime(getTime(result.getStartMillis()));
    }

    public static void setStartTime(ITestResult result) {
        ExtentReport.getExtentTest().getModel().setStartTime(getTime(result.getStartMillis()));
    }

    private static Date getTime(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.getTime();
    }
}
