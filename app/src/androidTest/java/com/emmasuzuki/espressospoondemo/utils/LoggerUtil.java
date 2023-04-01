package com.emmasuzuki.espressospoondemo.utils;

import android.util.Log;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class LoggerUtil {
    Level logLevel;
    private static LoggerUtil sInstance = null;
    private final String TAG_NAME = "Espresso-LoggerUtil";
    private static final ThreadLocal<DateFormat> DATE_FORMAT =
            ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US));


    LoggerUtil(Level level) {
        logLevel = level;
    }

    public static LoggerUtil getInstance() {
        if(sInstance == null) {
            sInstance = new LoggerUtil(Level.INFO);
        }
        return sInstance;
    }

    public static LoggerUtil getInstance(Level level) {
        if(sInstance == null) {
            sInstance = new LoggerUtil(level);
        }
        return sInstance;
    }

    public enum Level {
        INFO,
        DEBUG,
        TEST,
        WARN,
        ERROR
    }

    public enum Status {
        FAILED,
        BROKEN,
        PASSED,
        SKIPPED;
    }

    public void debug(String message) {
        if(logLevel == Level.DEBUG) {
            log(getPrefix() + message);
            Log.d(TAG_NAME, message);
        }
    }

    public void info(String message) {
        if(logLevel == Level.DEBUG || logLevel == Level.INFO) {
            log(getPrefix() + message);
            Log.i(TAG_NAME, message);
        }
    }

    public void test(String message) {
        if(logLevel == Level.DEBUG || logLevel == Level.INFO || logLevel == Level.TEST) {
            log(getPrefix() + message);
            Log.i(TAG_NAME, message);
        }
    }

    public void warn(String message) {
        if(logLevel == Level.DEBUG || logLevel == Level.INFO || logLevel == Level.TEST || logLevel == Level.WARN) {
            log(getPrefix() + message);
            Log.w(TAG_NAME, message);
        }
    }

    public void error(String message) {
        if(logLevel == Level.DEBUG || logLevel == Level.INFO || logLevel == Level.TEST || logLevel == Level.WARN || logLevel == Level.ERROR) {
            log(getPrefix() + message);
            Log.e(TAG_NAME, message);
        }
    }

    private void log(final String message) {
        log(message, Status.PASSED);
    }


    public void log(final String message, LoggerUtil.Status status){
        io.qameta.allure.kotlin.model.Status allureStatus;
        switch (status) {
            case BROKEN: allureStatus = io.qameta.allure.kotlin.model.Status.BROKEN; break;
            case FAILED: allureStatus = io.qameta.allure.kotlin.model.Status.FAILED; break;
            case PASSED: allureStatus = io.qameta.allure.kotlin.model.Status.PASSED; break;
            default: allureStatus = io.qameta.allure.kotlin.model.Status.SKIPPED;
        }
        io.qameta.allure.kotlin.Allure.step(message, allureStatus);
    }

    public static void screenShot(String screenShotName) {
        io.qameta.allure.android.AllureScreenshotKt.allureScreenshot(screenShotName, 50, 1.0f);
    }

    private static String getPrefix() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        if (stackTrace == null || stackTrace.length < 4) return "[BOGUS]";
        String className = stackTrace[4].getClassName();
        String methodName = stackTrace[4].getMethodName();
        className = className.replaceAll("[a-z\\.]", "");
        String timestamp = DATE_FORMAT.get().format(new Date());
        return String.format("%s [%s.%s] ", timestamp, className, methodName);
    }
}
