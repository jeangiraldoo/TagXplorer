package io.github.jeangiraldoo.tagxplorer;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import java.io.File;

public class Controller {
    private double screenWidth;
    private double screenHeight;
    private FileSystemManager manager;
    @FXML
    private ToolBar searchToolBar;
    @FXML
    private Button searchButton;
    @FXML
    private VBox top;
    @FXML
    private MenuBar menuBar;
    @FXML
    private ScrollPane fileScroll;
    @FXML
    private VBox fileContainer;
    @FXML
    private  VBox sizeContainer;

    @FXML
    public void initialize() {
        searchButton.setOnAction(event -> {
            System.out.println("siuuu");
        });
        manager = new FileSystemManager();
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
    }

    public void updateFiles(File[] files){
        String formattedSize;
        Label nameTitle = new Label("Name");
        Label sizeTitle = new Label("Size");
        nameTitle.setPrefWidth(fileContainer.getPrefWidth());
        sizeTitle.setPrefWidth(sizeContainer.getPrefWidth());

        nameTitle.setPadding(new Insets(screenHeight * 0.01));
        sizeTitle.setPadding(new Insets(screenHeight * 0.01));
        fileContainer.getChildren().add(nameTitle);
        sizeContainer.getChildren().add(sizeTitle);
        System.out.println(files.length);
        for(File file:files){
            Label sizeLabel;
            Label fileLabel;
            if(file.isFile()){
                fileLabel = new Label(file.getName());
                double size = file.length() / (1024.0 * 1024.0);
                formattedSize = String.format("%.5f MB", size);

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
            fileLabel.setOnMouseClicked(event ->{
                System.out.println(fileLabel.getText());
                cleanExplorer();
                initializeUI();
                String newPath = file.getAbsolutePath();
                File[] newFiles = manager.getDirectoryFiles(newPath);
                if(newFiles.length > 0){
                    updateFiles(newFiles);
                }
            });
            sizeLabel.setOnMouseClicked(mouseEvent ->{
                System.out.println(fileLabel.getText());
                cleanExplorer();
                initializeUI();
                String newPath = file.getAbsolutePath();
                File[] newFiles = manager.getDirectoryFiles(newPath);
                if(newFiles.length > 0){
                    updateFiles(newFiles);
                }
            });
            fileLabel.setOnMouseEntered(event -> {
                fileLabel.setStyle("-fx-background-color: rgb(0, 204, 255);");
                sizeLabel.setStyle("-fx-background-color: rgb(0, 204, 255);");
            });
            fileLabel.setOnMouseExited(event -> {
                fileLabel.setStyle(null);
                sizeLabel.setStyle(null);
            });
            sizeLabel.setOnMouseEntered(event -> {
                fileLabel.setStyle("-fx-background-color: rgb(0, 204, 255);");
                sizeLabel.setStyle("-fx-background-color: rgb(0, 204, 255);");
            });
            sizeLabel.setOnMouseExited(event -> {
                fileLabel.setStyle(null);
                sizeLabel.setStyle(null);
            });
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

    public double getScreenWidth(){
        return this.screenWidth;
    }

    public void setScreenWidth(double width){
        this.screenWidth = width;
    }

    public void setScreenHeight(double height){
        this.screenHeight = height;
    }
}