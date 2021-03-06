package com.txl.plugin.xmlutils;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileScan {
    public static void main(String[] args){
        String path = "D:\\AndroidStudioProjects\\easySmallWidth";
        List<File> files = mathFiles(path);
        for (File file : files){
            System.out.println("file name is :"+file.getName());
        }
    }

    private static List<File> mathFiles(String path){
        ArrayList<File> fileList = new ArrayList<>();
        File file = new File(path);
        if(file.exists()){
            matchFile(file,fileList);
        }
        return fileList;
    }

    private static void matchFile(File file,List<File> fileList){
        if(file !=  null &&  file.exists()){
            File[] files = file.listFiles(new FileFilter() {
                @Override
                public boolean accept(File pathname) {
                    return matchName(pathname.getName());
                }
            });
            if(files != null && files.length != 0){
                int length = files.length;
                fileList.addAll(Arrays.asList(files).subList(0, length));
            }
            File[] fileDir = file.listFiles(new FileFilter() {
                @Override
                public boolean accept(File pathname) {
                    return pathname.isDirectory();
                }
            });
            if(fileDir != null){
                for (File item :fileDir ){
                    matchFile(item,fileList);
                }
            }

        }
    }

    private static boolean matchName(String fileName){
        return fileName!=null&&(fileName.contains("a") || fileName.contains("A"));
    }

}
