package com.emmasuzuki.espressospoondemo.tests;

import com.emmasuzuki.espressospoondemo.utils.FileDirectoryMerger;

import java.io.File;

public class TestFIles {
    public static void main(String[] args) {
        FileDirectoryMerger.mergeDirectory(new File("/Users/vinodh.raj/Reports/Spoon/75/developersProductionAutomationtest/device"),
                new File("/Users/vinodh.raj/Reports/Spoon/74/developersProductionAutomationtest/device"),
                new File("/Users/vinodh.raj/Reports/Spoon/Merged"));
    }
}
