package com.emmasuzuki.espressospoondemo.utils;


import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class FileDirectoryMerger {

    public static void mergeDirectory(File source1, File source2, File destination) {
        try {
            FileUtils.copyDirectory(source1, destination);
            FileUtils.copyDirectory(source2, destination);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
