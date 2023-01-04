package com.example.TwitterJavaSDKBasic2.utils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class DirectoryPreparation {

    private String directoryPath = "D:\\Projects\\TwitterJavaSDKBasic2\\data";

    public void clearDirectory() {
        try{
            File directory = new File(directoryPath);
            FileUtils.cleanDirectory(directory);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
