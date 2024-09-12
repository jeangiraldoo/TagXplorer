package io.github.jeangiraldoo.tagxplorer;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Controller {
    private double screenWidth, screenHeight;
    private FileSystemManager manager;

    @FXML
    private ToolBar searchToolBar;
    @FXML
    private Button searchButton, backButton;
    @FXML
    private VBox top;
    @FXML
    private MenuBar menuBar;
    @FXML
    private ScrollPane fileScroll;
    @FXML
    private VBox fileContainer, sizeContainer;
    @FXML
    private TextField urlBar;

    @FXML
    public void initialize() {
        manager = new FileSystemManager();
        urlBar.setText(manager.getHomePath());
        searchButton.setOnAction(event -> {
        });
        backButton.setOnAction(event ->{
            String currentPath = manager.getCurrentPath();
            File file = new File(currentPath);
            if(file.getParent() != null){
                cleanExplorer();
                String newPath = file.getParent();
                manager.setCurrentPath(newPath);
                manager.setPreviousPath(currentPath);
                File[] files = manager.getDirectoryFiles(newPath);
                updateFiles(files);
            }
        });
    }

    public void initializeUI(){
        searchToolBar.setPrefWidth(screenWidth);
        double topHeight = screenHeight * 0.08;
        top.setPrefHeight(topHeight);
        menuBar.setPrefHeight(topHeight * 0.3);
        searchToolBar.setPrefHeight(topHeight * 0.7);
        fileScroll.setPrefWidth(screenWidth);
        fileScroll.setPrefHeight(screenHeight - topHeight);
        fileScroll.setFitToWidth(false);
        fileContainer.setPrefWidth(fileScroll.getPrefWidth() * 0.5);
        fileContainer.setPrefHeight(fileScroll.getPrefHeight());
        fileContainer.setMinHeight(fileScroll.getPrefHeight());
        sizeContainer.setPrefWidth(fileScroll.getPrefWidth());
        sizeContainer.setPrefHeight(fileScroll.getPrefHeight());
        sizeContainer.setMinHeight(fileScroll.getPrefHeight());
        urlBar.setPrefWidth(screenWidth * 0.3);
        backButton.setPrefWidth(screenWidth * 0.05);
    }
    public void fileMouseClick(MouseEvent event, File file, Label fileLabel, Label sizeLabel){
        if(event.getClickCount() == 2){
            Desktop desktop = Desktop.getDesktop();
            System.out.println(fileLabel.getText());
            manager.setCurrentPath(file.getAbsolutePath());
            manager.setPreviousPath(file.getParent());

            if(file.isDirectory() /*&& newFiles.length > 0*/){
                cleanExplorer();
                initializeUI();
                File[] newFiles = manager.getDirectoryFiles(manager.getCurrentPath());
                updateFiles(newFiles);
            }else if(file.isFile()){
                try{
                    desktop.open(file);
                }catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }else if(event.getClickCount() == 1){
            removeStyles();
            fileLabel.setUserData(true);
            sizeLabel.setUserData(true);
            fileHighlight(fileLabel, sizeLabel);
        }
    }
    public void fileHighlight(Label fileLabel, Label sizeLabel){
        fileLabel.setStyle("-fx-background-color: rgb(100, 100, 100);");
        sizeLabel.setStyle("-fx-background-color: rgb(100, 100, 100);");
    }
    public void fileMouseLeaveHover(Label fileLabel, Label sizeLabel){
        if(!(Boolean) fileLabel.getUserData()){
            fileLabel.setStyle(null);
            sizeLabel.setStyle(null);
        }
    }

    public void removeStyles(){
        ObservableList<Node> fileContainerChildren = fileContainer.getChildren();
        ObservableList<Node> sizeContainerChildren = sizeContainer.getChildren();
        for(Node child:fileContainerChildren){
            child.setStyle(null);
            child.setUserData(false);
        }
        for(Node child:sizeContainerChildren){
            child.setStyle(null);
            child.setUserData(false);
        }
    }

    public void updateFiles(File[] files){
        String formattedSize;
        String currentPath = files[0].getParent();
        manager.setCurrentPath(currentPath);
        urlBar.setText(manager.getCurrentPath());
        Label nameTitle = new Label("Name");
        Label sizeTitle = new Label("Size");
        nameTitle.setPrefWidth(fileContainer.getPrefWidth());
        sizeTitle.setPrefWidth(sizeContainer.getPrefWidth());

        nameTitle.setPadding(new Insets(screenHeight * 0.01));
        sizeTitle.setPadding(new Insets(screenHeight * 0.01));
        fileContainer.getChildren().add(nameTitle);
        sizeContainer.getChildren().add(sizeTitle);
        for(File file:files){
            Label sizeLabel;
            Label fileLabel;
            if(file.isFile()){
                fileLabel = new Label(file.getName());
                Long size = file.length();
                formattedSize = manager.getFormattedSize(size.doubleValue());

                sizeLabel = new Label(formattedSize);
                sizeLabel.setPrefWidth(sizeContainer.getPrefWidth());
                sizeLabel.setPadding(new Insets(0, 0, 10, 0));

                fileLabel.setPrefWidth(fileContainer.getPrefWidth());
                fileLabel.setPadding(new Insets(0, 0, 10, 0));
            } else {
                fileLabel = new Label(file.getName());
                fileLabel.setPrefWidth(fileContainer.getPrefWidth());
                fileLabel.setPadding(new Insets(0, 0, 10, 0));

                sizeLabel = new Label(" ");
                sizeLabel.setPrefWidth(sizeContainer.getPrefWidth());
                sizeLabel.setPadding(new Insets(0, 0, 10, 0));
            }
            fileLabel.setUserData(false);
            sizeLabel.setUserData(false);
            fileLabel.setOnMouseClicked(event ->fileMouseClick(event, file, fileLabel, sizeLabel));
            sizeLabel.setOnMouseClicked(event -> fileMouseClick(event, file, fileLabel, sizeLabel));

            fileLabel.setOnMouseEntered(event -> fileHighlight(fileLabel, sizeLabel));
            fileLabel.setOnMouseExited(event -> fileMouseLeaveHover(fileLabel, sizeLabel));
            sizeLabel.setOnMouseEntered(event -> fileHighlight(fileLabel, sizeLabel));
            sizeLabel.setOnMouseExited(event -> fileMouseLeaveHover(fileLabel, sizeLabel));

            sizeLabel.setPadding(new Insets(0, 0, 0, sizeContainer.getPrefWidth() * 0.01));
            fileLabel.setPadding(new Insets(0, 0, 0, fileContainer.getPrefWidth() * 0.01));
            sizeContainer.getChildren().add(sizeLabel);
            fileContainer.getChildren().add(fileLabel);
        }
    }

    public void cleanExplorer(){
        fileContainer.getChildren().clear();
        sizeContainer.getChildren().clear();
    }

    public void setScreenWidth(double width){
        this.screenWidth = width;
    }

    public void setScreenHeight(double height){
        this.screenHeight = height;
    }
}