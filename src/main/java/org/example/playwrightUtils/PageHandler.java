package org.example.playwrightUtils;

import com.microsoft.playwright.Page;
import org.example.browserManager.DriverFactory;

import java.util.List;

import static org.example.browserManager.DriverFactory.getPage;

public class PageHandler {

//    private static final ThreadLocal<Page> PAGE_THREAD_LOCAL = ThreadLocal.withInitial(() -> null);
//
//    public static Page getPage() {
//        return PAGE_THREAD_LOCAL.get();
//    }
//
//    private static void setPage(Page page) {
//        PAGE_THREAD_LOCAL.set(page);
//    }
//
//    public static void registerPageActionAlways() {
//        getPage().onPopup(PageHandler::setPage);
//    }
//
//    public static void registerPageActionAlways(Consumer<Page> dialogConsumer) {
//        getPage().onPopup(dialogConsumer);
//    }
//
//    public static void unRegisterPageAction(Consumer<Page> dialogConsumer) {
//        getPage().offPopup(dialogConsumer);
//    }

    public static void switchToPage(int index) {
        if (index >= 0) {
            List<Page> _pages = DriverFactory.getContext().pages();
            DriverFactory.setPage(_pages.get(index));
            bringToFront();
        } else {
            throw new RuntimeException("Invalid index to switch to page: " + index);
        }
    }

    public static void switchBackToFirstPage() {
        List<Page> _pages = DriverFactory.getContext().pages();
        if (_pages.size() == 0) {
            throw new RuntimeException("All the pages are closed.");
        } else {
            DriverFactory.setPage(_pages.get(0));
            bringToFront();
        }
    }

    public static int getPageCount() {
        return DriverFactory.getContext().pages().size();
    }

    public static void closeCurrentPage() {
        boolean isClosed = getPage().isClosed();
        if (!isClosed) {
            getPage().close();
        }
    }

    public static void newPage() {
        Page _page = DriverFactory.getContext().newPage();
        DriverFactory.setPage(_page);
    }

    private static void bringToFront() {
        getPage().bringToFront();
    }

    public void openInNewTab(String _url) {
        getPage().evaluate("url => window.open(url, '_blank');", _url);
    }
}
