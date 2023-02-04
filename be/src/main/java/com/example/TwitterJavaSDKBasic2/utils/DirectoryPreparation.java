package com.example.TwitterJavaSDKBasic2.utils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class DirectoryPreparation {

    private String directoryPath = "D:\\Projects\\TwitterJavaSDKBasic2\\be\\data";

    public void clearDirectory() {
        try{
            File directory = new File(directoryPath + "\\likes");
            if(!directory.exists())
                directory.mkdir();
            else
                FileUtils.cleanDirectory(directory);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clearDirectoryBookmarks() {
        try{
            File directory = new File(directoryPath + "\\bookmarks");
            if(!directory.exists())
                directory.mkdir();
            else
                FileUtils.cleanDirectory(directory);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
