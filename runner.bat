@rem
@rem Copyright 2023 the original author or authors.
@rem
@echo off
@rem ##########################################################################
@rem
@rem  Test Runner Of Automation
@rem
@rem ##########################################################################


SET PROJECT_DIR=%CD%
set PROJECT_NAME=TestQA
set TEST_ENV=QA
SET BROWSER=chrome
SET TIMEOUT_WAIT=30000
SET TEST_ENV=QA
SET BASE_URL=https://google.co.in/

SET HEADLESS=false
SET SEND_MAIL=false
SET RECORD_VIDEO=false
SET PERSISTENT_LOGIN=false
SET STORE_AUTHENTICATION=false
SET ENABLE_TRACING=true

SET DOWNLOAD_PATH=%PROJECT_DIR%\output_data\downloads
SET SCREENSHOT_PATH=%PROJECT_DIR%\output_data\screenshots
SET TRACE_DIR=%PROJECT_DIR%\output_data\traces
SET HAR_PATH=%PROJECT_DIR%\output_data\har_path
SET VIDEO_DIR=%PROJECT_DIR%\output_data\videos
SET PERSISTENT_DIR=%PROJECT_DIR%\output_data\dir

CD %PROJECT_DIR%

echo Running Test...
echo mvn clean verify -DBROWSER=%BROWSER% -DTEST_ENV=%TEST_ENV% -DHEADLESS=%HEADLESS% -DTIMEOUT_WAIT=%TIMEOUT_WAIT% -DSEND_MAIL=%SEND_MAIL% -DPROJECT_NAME=%PROJECT_NAME% -DPROJECT_NAME=%PROJECT_NAME% -DBASE_URL=%BASE_URL% -DSEND_MAIL=%SEND_MAIL% -DDOWNLOAD_PATH=%DOWNLOAD_PATH% -DSCREENSHOT_PATH=%SCREENSHOT_PATH% -DTRACE_DIR=%TRACE_DIR% -DHAR_PATH=%HAR_PATH% -DVIDEO_DIR=%VIDEO_DIR% -DPERSISTENT_LOGIN=%PERSISTENT_LOGIN% -DPERSISTENT_DIR=%PERSISTENT_DIR% -DRECORD_VIDEO=%RECORD_VIDEO% -DSTORE_AUTHENTICATION=%STORE_AUTHENTICATION% -DENABLE_TRACING=%ENABLE_TRACING%
CALL mvn clean verify -DBROWSER=%BROWSER% -DTEST_ENV=%TEST_ENV% -DHEADLESS=%HEADLESS% -DTIMEOUT_WAIT=%TIMEOUT_WAIT% -DSEND_MAIL=%SEND_MAIL% -DPROJECT_NAME=%PROJECT_NAME% -DPROJECT_NAME=%PROJECT_NAME% -DBASE_URL=%BASE_URL% -DSEND_MAIL=%SEND_MAIL% -DDOWNLOAD_PATH=%DOWNLOAD_PATH% -DSCREENSHOT_PATH=%SCREENSHOT_PATH% -DTRACE_DIR=%TRACE_DIR% -DHAR_PATH=%HAR_PATH% -DVIDEO_DIR=%VIDEO_DIR% -DPERSISTENT_LOGIN=%PERSISTENT_LOGIN% -DPERSISTENT_DIR=%PERSISTENT_DIR% -DRECORD_VIDEO=%RECORD_VIDEO% -DSTORE_AUTHENTICATION=%STORE_AUTHENTICATION% -DENABLE_TRACING=%ENABLE_TRACING%

:: mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="codegen demo.playwright.dev/todomvc"
:: mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="show-trace trace.zip"
:: mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="install msedge"
:: set PLAYWRIGHT_BROWSERS_PATH=%USERPROFILE%\pw-browsers
:: mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="install"
:: mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="install --help"
:: mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="install-deps"
:: mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args='open --device="iPhone 11" wikipedia.org'
:: mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args='open --timezone="Europe/Rome" --geolocation="41.890221,12.492348" --lang="it-IT" maps.google.com'
:: mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args='screenshot --full-page en.wikipedia.org wiki-full.png'
:: mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="pdf https://en.wikipedia.org/wiki/PDF wiki.pdf"