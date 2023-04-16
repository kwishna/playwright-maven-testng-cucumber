package org.example.listeners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import static org.example.utils.configs.Constants.RETRY_FAILED_TESTS;

public class RetryFailedTests implements IRetryAnalyzer {
    private int count = 0;
    private int retries = 1;

    @Override
    public boolean retry(ITestResult result) {
        boolean value = false;
        if (RETRY_FAILED_TESTS && (count < retries)) {
            count++;
            return true;
        }
        return value;
    }
}
