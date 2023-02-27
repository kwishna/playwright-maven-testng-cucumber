package org.example.playwrightUtils;

import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Frame;
import com.microsoft.playwright.FrameLocator;
import com.microsoft.playwright.Locator;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import static org.example.browserManager.DriverFactory.getPage;

public class FrameHandler {

    private static final ThreadLocal<Deque<Frame>> FRAME_THREAD_LOCAL = ThreadLocal.withInitial(ArrayDeque::new);

    private static void setFrame(Frame frame) {
        FRAME_THREAD_LOCAL.get().push(frame);
    }

    private static void removeFrame(Frame frame) {
        FRAME_THREAD_LOCAL.get().remove(frame);
    }

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

    public static FrameLocator getFrameLocator(String locator) {
        Objects.requireNonNull(locator, "Invalid locator for get iframe: " + locator);
        return getPage().frameLocator(locator);
    }

    public static Frame getFrameByName(String name) {
        Objects.requireNonNull(name, "Invalid name for get iframe.");
        return getPage().frame(name);
    }

    public static Frame getFrameByUrl(String url) {
        Objects.requireNonNull(url, "Invalid url to get iframe.");
        return getPage().frameByUrl(url);
    }

    public static Frame getFrameByUrlPattern(Pattern url) {
        Objects.requireNonNull(url, "Invalid url pattern to get iframe");
        return getPage().frameByUrl(url);
    }

    public static Frame getFrameByUrlCondition(Predicate<String> predicate) {
        Objects.requireNonNull(predicate, "Invalid predicate to get iframe.");
        return getPage().frameByUrl(predicate);
    }

    public static Frame getContentFrame(ElementHandle handle) {
        Objects.requireNonNull(handle, "Invalid handle to get iframe.");
        return handle.contentFrame();
    }

    public static Frame getOwnerFrame(ElementHandle handle) {
        Objects.requireNonNull(handle, "Invalid handle to get iframe.");
        return handle.ownerFrame();
    }

    public static FrameLocator getFrameLocator(FrameLocator locator, String frameLocator) {
        Objects.requireNonNull(locator, "Invalid locator to get iframe: " + locator);
        Objects.requireNonNull(locator, "Invalid locator to get iframe: " + frameLocator);
        return locator.frameLocator(frameLocator);
    }

    public static Locator getLocator(FrameLocator frame, String frameLocator) {
        Objects.requireNonNull(frame, "Invalid locator to get iframe: " + frame);
        Objects.requireNonNull(frameLocator, "Invalid locator to get iframe: " + frameLocator);
        return frame.locator(frameLocator);
    }

    public static Locator getLocator(String iFrameLocator, String locator) {
        Objects.requireNonNull(iFrameLocator, "Invalid locator to get iframe: " + iFrameLocator);
        Objects.requireNonNull(locator, "Invalid locator of element: " + locator);
        return getPage().frameLocator(iFrameLocator).locator(locator);
    }

    public static boolean isFrameAttached(Frame frame) {
        Objects.requireNonNull(frame, "Invalid iframe.");
        return !frame.isDetached();
    }

    public static String getFrameUrl(Frame frame) {
        Objects.requireNonNull(frame, "Invalid iframe.");
        return frame.url();
    }

    public static ElementHandle getFrameElement(Frame frame) {
        Objects.requireNonNull(frame, "Invalid iframe.");
        return frame.frameElement();
    }

    public static Frame getPageMainFrame() {
        return getPage().mainFrame();
    }

    public static String getFrameContent(Frame frame) {
        return frame.content();
    }

    public static List<Frame> getChildFrames(Frame frame) {
        return frame.childFrames();
    }

    public static String getTitle(Frame frame) {
        return frame.title();
    }

    public static String getName(Frame frame) {
        return frame.name();
    }

    public static void registerFrameAttached(Consumer<Frame> frameConsumer) {
        getPage().onFrameAttached(frameConsumer);
    }

    public static void unRegisterFrameDettached(Consumer<Frame> frameConsumer) {
        getPage().onFrameDetached(frameConsumer);
    }

    public static void registerFrameAttached() {
        registerFrameAttached(FrameHandler::setFrame);
    }

    public static void unRegisterFrameDettached() {
        unRegisterFrameDettached(FrameHandler::removeFrame);
    }

}
