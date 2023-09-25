package com.emmasuzuki.espressospoondemo.utils;

import android.os.Bundle;

import androidx.test.internal.runner.listener.InstrumentationResultPrinter;
import androidx.test.internal.runner.listener.InstrumentationRunListener;

import com.emmasuzuki.espressospoondemo.utils.annotations.CaseId;
import com.emmasuzuki.espressospoondemo.utils.annotations.DependeeMethod;
import com.emmasuzuki.espressospoondemo.utils.annotations.Device;
import com.emmasuzuki.espressospoondemo.utils.annotations.RetryRule;

import org.junit.runner.Description;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.List;

public class AnnotationsTestPrinter extends InstrumentationRunListener {

    @Override
    public void testStarted(Description description) throws Exception {
        super.testStarted(description);

        Collection<Annotation> classAnnotations = List.of(description.getTestClass().getAnnotations());
        if (classAnnotations == null) {
            return;
        }

        Bundle bundle = new Bundle();
        StringBuilder classStringBuilder = new StringBuilder();
        boolean classComm = false;
        String classDevicesStr = "";
        int retry = 0;
        for (Annotation annotation : classAnnotations) {
            if (classComm) classStringBuilder.append(",");
            classStringBuilder.append(annotation.annotationType().getSimpleName());

            if (annotation instanceof Device) {
                Device device = (Device) annotation;
                classDevicesStr = buildTags(device.value());
            }

            if (annotation instanceof RetryRule) {
                RetryRule retryRule = (RetryRule) annotation;
                retry = retryRule.value();
            }



            classComm = true;
        }

        bundle.putString("classAnnotations", classStringBuilder.toString());
        bundle.putString("classDeviceSerialId", classDevicesStr);
        bundle.putInt("classRetry", retry);

        Collection<Annotation> annotations = description.getAnnotations();
        if (annotations == null) {
            return;
        }


        StringBuilder stringBuilder = new StringBuilder();
        boolean comm = false;
        String dependencyStr = "";
        String devicesStr = "";
        int testrailId = Integer.MIN_VALUE;
        for (Annotation annotation : annotations) {
            if (comm) stringBuilder.append(",");
            stringBuilder.append(annotation.annotationType().getSimpleName());

            if (annotation instanceof DependeeMethod) {
                DependeeMethod dependeeMethod = (DependeeMethod) annotation;
                dependencyStr = buildTags(dependeeMethod.dependsOnMethods());
            }

            if (annotation instanceof Device) {
                Device device = (Device) annotation;
                devicesStr = buildTags(device.value());
            }

            if (annotation instanceof CaseId) {
                CaseId caseId = (CaseId) annotation;
                testrailId = caseId.value();
            }

            comm = true;
        }

        bundle.putString("annotations", stringBuilder.toString());
        bundle.putString("dependedMethods", dependencyStr);
        bundle.putString("deviceSerialId", devicesStr);
        bundle.putInt("testrailId", testrailId);
        getInstrumentation().sendStatus(
                InstrumentationResultPrinter.REPORT_VALUE_RESULT_START, bundle);
    }

    private String buildTags(String[] methods) {
        StringBuilder stringBuilder = new StringBuilder();
        boolean comm = false;
        for (String tag : methods) {
            if (comm) stringBuilder.append(",");
            stringBuilder.append(tag);
            comm = true;
        }
        return stringBuilder.toString();
    }
}