package org.example.playwrightUtils;

import com.microsoft.playwright.*;

import java.util.function.Consumer;

import static org.example.browserManager.DriverFactory.getContext;

public class ContextHandler {

    public void clickToOpenNewTab(Locator locator) {
        getContext().waitForPage(locator::click);
    }

    public void registerNewTabHandler() {
        getContext().onPage(Page::waitForLoadState);
    }

    public void registerSkipImagesHandler() {
        getContext().route("**/*.{png,jpg,jpeg,gif}", Route::abort);
    }

    public void registerOnRequestFinishedHandler(Consumer<Request> requestConsumer) {
        getContext().onRequestFinished(requestConsumer);
    }

    public void registerOnResponse(Consumer<Response> requestConsumer) {
        getContext().onResponse(requestConsumer);
    }

    public void registerOnRequest(Consumer<Request> requestConsumer) {
        getContext().onRequest(requestConsumer);
    }


    public void registerOnRequestFailed(Consumer<Request> requestFailedConsumer) {
        getContext().onRequestFailed(requestFailedConsumer);
    }
}
