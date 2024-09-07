package io.github.jeangiraldoo.tagxplorer;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
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
    public void fileMouseClick(MouseEvent event, File file, Label fileLabel, Label sizeLabel){
        if(event.getClickCount() == 2){
            System.out.println(fileLabel.getText());
            cleanExplorer();
            initializeUI();
            String newPath = file.getAbsolutePath();
            File[] newFiles = manager.getDirectoryFiles(newPath);
            if(newFiles.length > 0){
                updateFiles(newFiles);
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