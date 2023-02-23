package org.example.utils;

import com.google.common.io.Files;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.ScreenshotAnimations;
import com.microsoft.playwright.options.ScreenshotCaret;
import com.microsoft.playwright.options.ScreenshotScale;
import com.microsoft.playwright.options.ScreenshotType;
import org.apache.commons.io.FileUtils;
import org.example.browserManager.PageManager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Base64;

import static org.example.utils.Constants.SCREENSHOT_PATH;
import static org.example.utils.Constants.WAIT_TIMEOUT;

public final class ScreenshotUtils {

    private ScreenshotUtils() {
    }

    public static String getScreenshotAsBase64() {
        try {
            String screenshot = saveScreenshot();
            byte[] fileContent = FileUtils.readFileToByteArray(new File(screenshot));
            return Base64.getEncoder().encodeToString(fileContent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static File getScreenshotAsFile() {
        String screenshot = saveScreenshot();
        return new File(screenshot);
    }

    public static String saveScreenshot() {

        Path screenshotPath = Path.of(SCREENSHOT_PATH, System.currentTimeMillis() + ".png");

        Page.ScreenshotOptions screenshotOptions = new Page.ScreenshotOptions();
        screenshotOptions.setAnimations(ScreenshotAnimations.ALLOW);
        screenshotOptions.setCaret(ScreenshotCaret.INITIAL);
        screenshotOptions.setFullPage(true);
        screenshotOptions.setOmitBackground(false);
//        screenshotOptions.setQuality(100);
        screenshotOptions.setScale(ScreenshotScale.DEVICE);
        screenshotOptions.setType(ScreenshotType.PNG);
        screenshotOptions.setTimeout(WAIT_TIMEOUT);
        screenshotOptions.setPath(screenshotPath);

        byte[] bytes = PageManager.getPage().screenshot(screenshotOptions);
        try {
            Files.write(bytes, new File(screenshotPath.toUri()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return screenshotPath.toString();
    }

    private static byte[] saveScreenshot(Path path) {
        Page.ScreenshotOptions screenshotOptions = new Page.ScreenshotOptions();
        screenshotOptions.setAnimations(ScreenshotAnimations.ALLOW);
        screenshotOptions.setCaret(ScreenshotCaret.INITIAL);
        screenshotOptions.setFullPage(true);
        screenshotOptions.setOmitBackground(false);
        screenshotOptions.setQuality(100);
        screenshotOptions.setScale(ScreenshotScale.DEVICE);
        screenshotOptions.setType(ScreenshotType.PNG);
        screenshotOptions.setTimeout(WAIT_TIMEOUT);
        screenshotOptions.setPath(path);

        byte[] bytes = PageManager.getPage().screenshot(screenshotOptions);
        try {
            Files.write(bytes, new File(path.toUri()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return bytes;
    }

    public static byte[] getScreenshotAsBytes() {
        Path screenshotPath = Path.of(SCREENSHOT_PATH, System.currentTimeMillis() + ".png");
        return saveScreenshot(screenshotPath);

    }

}
