package io.github.jeangiraldoo.tagxplorer;

import java.io.File;
import java.util.Objects;

public class FileSystemManager {
    private final String operatingSystem;
    private final String userName;
    private String homePath;
    private String currentPath;
    private String previousPath;

    public FileSystemManager(){
        String operatingSystem = System.getProperty("os.name");
        String userName = System.getProperty("user.name");

        this.operatingSystem = operatingSystem;
        this.userName = userName;
        if(Objects.equals(this.operatingSystem, "Windows 11")){
            this.homePath = "C:\\Users\\" + userName;
            this.currentPath = "C:\\Users\\" + userName;
            this.previousPath = "C:\\Users\\" + userName;
        }
    }
    public File[] getDirectoryFiles(String dir){
        File directory = new File(dir);
        return directory.listFiles();
    }
    public String getFormattedSize(Double size){
        String formattedSize;
        Double newSize;
        String unit = "";
        Double KB = size/1024;
        Double MB = KB/1024;
        Double GB = MB/1024;
        if(KB <= 1){
            newSize = size;
            unit = "Bytes";
        }else if(MB <= 1){
            newSize = KB;
            unit = "KB";
        } else if (GB <= 1) {
            newSize = MB;
            unit = "MB";
        }else{
            newSize = GB;
            unit = "GB";
        }
        String roundedSize = String.format("%.2f", newSize);
        formattedSize = roundedSize + " " + unit;
        return formattedSize;
    }
    public String getUserName(){
        return userName;
    }
    public String getOperatingSystem(){
        return operatingSystem;
    }
    public String getHomePath(){
        return homePath;
    }
    public String getCurrentPath(){
        return currentPath;
    }
    public String getPreviousPath(){ return previousPath;}
    public void setCurrentPath(String path){
        this.currentPath = path;
    }
    public void setPreviousPath(String path){
        this.previousPath = path;
    }
}
