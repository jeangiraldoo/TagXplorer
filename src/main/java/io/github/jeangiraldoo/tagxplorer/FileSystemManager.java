package io.github.jeangiraldoo.tagxplorer;

import java.io.File;
import java.util.Objects;

public class FileSystemManager {
    private String operatingSystem;
    private String userName;
    private String homePath;
    private String currentPath;

    public FileSystemManager(){
        String operatingSystem = System.getProperty("os.name");
        String userName = System.getProperty("user.name");

        this.operatingSystem = operatingSystem;
        this.userName = userName;
        if(Objects.equals(this.operatingSystem, "Windows 11")){
            this.homePath = "C:\\Users\\" + userName;
            this.currentPath = "C:\\Users\\" + userName;
        }
    }
    public File[] getDirectoryFiles(String dir){
        File directory = new File(dir);
        return directory.listFiles();

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
}
