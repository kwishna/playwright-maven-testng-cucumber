package org.example.playwrightUtils;

import com.microsoft.playwright.Frame;
import com.microsoft.playwright.Locator;
import org.example.browserManager.DriverFactory;

import java.util.List;

import static org.example.browserManager.DriverFactory.getPage;

public class FrameHandler {
    public static List<Frame> getAllFrames() {
        return getPage().frames();
    }

    public static Frame getFrame(int index) {
        if (index >= 0) {
            return getAllFrames().get(index);
        } else {
            throw new RuntimeException("Invalid index to fetch to frame: " + index);
        }
    }
}
