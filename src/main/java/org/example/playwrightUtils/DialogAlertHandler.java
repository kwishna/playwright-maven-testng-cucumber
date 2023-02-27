package org.example.playwrightUtils;

import com.microsoft.playwright.Dialog;
import org.example.reportings.ExtentLogger;

import java.util.function.Consumer;

import static org.example.browserManager.DriverFactory.getPage;

public class DialogAlertHandler {

    private static final ThreadLocal<Dialog> DIALOG_THREAD_LOCAL = ThreadLocal.withInitial(() -> null);

    public static Dialog getDialog() {
        return DIALOG_THREAD_LOCAL.get();
    }

    private static void setDialog(Dialog dialog) {
        DIALOG_THREAD_LOCAL.set(dialog);
    }

    public static void registerDialogActionAlways() {
        getPage().onDialog(DialogAlertHandler::setDialog);
    }

    public static void registerDialogActionOnce(Consumer<Dialog> dialogConsumer) {
        getPage().onceDialog(dialogConsumer);
    }

    public static void registerDialogActionAlways(Consumer<Dialog> dialogConsumer) {
        getPage().onDialog(dialogConsumer);
    }

    public static void unRegisterDialogAction(Consumer<Dialog> dialogConsumer) {
        getPage().offDialog(dialogConsumer);
    }

    public String getDialogType() {
        Dialog _dialog = getDialog();
        if (_dialog != null) {
            return _dialog.type();
        } else {
            ExtentLogger.warn("No Dialog Available!");
        }
        return "";
    }

    public String getDialogMessage() {
        Dialog _dialog = getDialog();
        if (_dialog != null) {
            return _dialog.message();
        } else {
            ExtentLogger.warn("No Dialog Available!");
        }
        return "";
    }

    public String getDialogDefaultValue() {
        Dialog _dialog = getDialog();
        if (_dialog != null) {
            return _dialog.defaultValue();
        } else {
            ExtentLogger.warn("No Dialog Available!");
        }
        return "";
    }

    public void dismissDialog() {
        Dialog _dialog = getDialog();
        if (_dialog != null) {
            _dialog.dismiss();
        } else {
            ExtentLogger.warn("No Dialog Available!");
        }
    }

    public void acceptDialog() {
        this.acceptDialog("");
    }

    public void acceptDialog(String promptMsg) {
        Dialog _dialog = getDialog();
        if (_dialog != null) {
            _dialog.accept(promptMsg);
        } else {
            ExtentLogger.warn("No Dialog Available!");
        }
    }
}
