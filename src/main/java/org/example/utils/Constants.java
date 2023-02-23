package org.example.utils;

public interface Constants {
    String PROJECT_PATH = System.getProperty("user.dir");
    String ICON_SMILEY_PASS = "<i class='fa fa-smile-o' style='font-size:24px'></i>";
    String ICON_SMILEY_SKIP = "<i class='fas fa-frown-open'></i>";
    String ICON_SMILEY_FAIL = "<i class='fa fa-frown-o' style='font-size:24px'></i>";
    String ICON_OS_WINDOWS = "<i class='fa fa-windows' ></i>";
    String ICON_OS_MAC = "<i class='fa fa-apple' ></i>";
    String ICON_OS_LINUX = "<i class='fa fa-linux' ></i>";
    String ICON_BROWSER_OPERA = "<i class='fa fa-opera' aria-hidden='true'></i>";
    String ICON_BROWSER_EDGE = "<i class='fa fa-edge' aria-hidden='true'></i>";
    String ICON_BROWSER_CHROME = "<i class='fa fa-chrome' aria-hidden='true'></i>";
    String ICON_BROWSER_FIREFOX = "<i class='fa fa-firefox' aria-hidden='true'></i>";
    String ICON_BROWSER_SAFARI = "<i class='fa fa-safari' aria-hidden='true'></i>";
    String ICON_NAVIGATE_RIGHT = "<i class='fa fa-arrow-circle-right' ></i>";
    String ICON_LAPTOP = "<i class='fa fa-laptop' style='font-size:18px'></i>";
    String ICON_BUG = "<i class='fa fa-bug' ></i>";
    String ICON_SOCIAL_LINKEDIN_URL = "https://www.linkedin.com/in/{{}}";
    String ICON_SOCIAL_GITHUB_URL = "https://github.com/{{}}";
    String ICON_SOCIAL_LINKEDIN = "<a href='" + ICON_SOCIAL_LINKEDIN_URL + "'><i class='fa fa-linkedin-square' style='font-size:24px'></i></a>";
    String ICON_SOCIAL_GITHUB = "<a href='" + ICON_SOCIAL_GITHUB_URL + "'><i class='fa fa-github-square' style='font-size:24px'></i></a>";
    String ICON_CAMERA = "<i class='fa fa-camera' aria-hidden='true'></i>";
    String ICON_BROWSER_PREFIX = "<i class='fa fa-";
    String ICON_BROWSER_SUFFIX = "' aria-hidden='true'></i>";
    String YES = "yes";
    String NO = "no";
    int WAIT_TIMEOUT = Integer.parseInt(System.getProperty("TIMEOUT_WAIT", "30000"));
    String EXTENT_REPORT_FOLDER_PATH = PROJECT_PATH + "/outputs/";
    String EXTENT_REPORT_NAME = "ExtentReport.html";
    String ZIPPED_EXTENT_REPORTS_FOLDER_NAME = "ExtentReports.zip";
    String PROJECT_NAME = System.getProperty("PROJECT_NAME", "PROJECT_NAME");
    String TEST_ENV = System.getProperty("TEST_ENV", "QA");
    String BROWSER = System.getProperty("BROWSER", "chrome");
    boolean HEADLESS = Boolean.parseBoolean(System.getProperty("HEADLESS", "false"));
    boolean SEND_MAIL = Boolean.parseBoolean(System.getProperty("SEND_MAIL", "false"));
    boolean RECORD_VIDEO = Boolean.parseBoolean(System.getProperty("RECORD_VIDEO", "false"));
    boolean PERSISTENT_LOGIN = Boolean.parseBoolean(System.getProperty("PERSISTENT_LOGIN", "false"));
    String BASE_URL = System.getProperty("BASE_URL", "https://google.com/");
    String DOWNLOAD_PATH = System.getProperty("DOWNLOAD_PATH", PROJECT_PATH + "/downloads");
    String SCREENSHOT_PATH = System.getProperty("SCREENSHOT_PATH", PROJECT_PATH + "/screenshots");
    String TRACE_DIR = System.getProperty("TRACE_DIR", PROJECT_PATH + "/traces");
    String HAR_PATH = System.getProperty("HAR_PATH", PROJECT_PATH + "/har_path");
    String VIDEO_DIR = System.getProperty("VIDEO_DIR", PROJECT_PATH + "/videos");
    String PERSISTENT_DIR = System.getProperty("PERSISTENT_DIR", PROJECT_PATH + "/persistent_data");

}
