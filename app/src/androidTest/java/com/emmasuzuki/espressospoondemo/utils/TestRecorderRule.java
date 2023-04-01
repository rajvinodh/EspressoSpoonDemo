package com.emmasuzuki.espressospoondemo.utils;


import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.ParcelFileDescriptor;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.UiDevice;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Calendar;
import java.util.Objects;

import io.qameta.allure.kotlin.Allure;

public class TestRecorderRule implements TestRule {
    UiDevice uiDevice;

    public TestRecorderRule(UiDevice uiDevice) {
        this.uiDevice = uiDevice;
    }

    @Override
    public Statement apply(Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                try {
                    startRecording();
                    base.evaluate();
                    stopRecording();
                } catch (Throwable exception) {
                    stopRecording();
                    dump();
                    throw exception;
                }
            }
        };
    }


    private void startRecording() throws IOException {
        String out = executeShellCommandSafely("am start -n io.appium.settings/io.appium.settings.Settings -a io.appium.settings.recording.ACTION_START --es filename abc.mp4 --es priority high --es max_duration_sec 900 --es resolution 1920x1080");
        io.qameta.allure.kotlin.Allure.step(out, io.qameta.allure.kotlin.model.Status.PASSED);
    }

    private void stopRecording() throws IOException {
        io.qameta.allure.kotlin.Allure.step("Called stopRecording", io.qameta.allure.kotlin.model.Status.PASSED);
        String out = executeShellCommandSafely("am start -n io.appium.settings/io.appium.settings.Settings -a io.appium.settings.recording.ACTION_STOP");
        io.qameta.allure.kotlin.Allure.step("executed stopRecording", io.qameta.allure.kotlin.model.Status.PASSED);
        io.qameta.allure.kotlin.Allure.step(out, io.qameta.allure.kotlin.model.Status.PASSED);
    }

    private void dump() throws IOException {
//        io.qameta.allure.kotlin.Allure.attachment
//                ("failure-video",
//                        getVideoContent(Uri.parse("/storage/emulated/0/Android/data/io.appium.settings/files/abc.mp4")),
//                "video/mp4",
//                "mp4");
        io.qameta.allure.kotlin.Allure.step("Called dump", io.qameta.allure.kotlin.model.Status.PASSED);
        String out = executeShellCommandSafely("am start -n io.appium.settings/io.appium.settings.Settings -a io.appium.settings.recording.ACTION_STOP");
        io.qameta.allure.kotlin.Allure.step("executed dump", io.qameta.allure.kotlin.model.Status.PASSED);
        io.qameta.allure.kotlin.Allure.step(out, io.qameta.allure.kotlin.model.Status.PASSED);

    }

    private String executeShellCommandSafely(String cmd) throws IOException {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
            return null;
        return uiDevice.executeShellCommand(cmd);
    }

    private InputStream getVideoContent(Uri videoUri) throws IOException {
        InputStream inputStream =  InstrumentationRegistry
                .getInstrumentation()
                .getTargetContext()
                .getContentResolver()
                .openInputStream(videoUri);

        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int len = 0;

        while ((len = inputStream.read(buffer)) != -1)
        {
            byteBuffer.write(buffer, 0, len);
        }
        return new ByteArrayInputStream(byteBuffer.toByteArray());
    }
}
