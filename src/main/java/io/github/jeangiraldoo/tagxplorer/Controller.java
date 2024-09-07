package io.github.jeangiraldoo.tagxplorer;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import java.io.File;

public class Controller {
    private double screenWidth;
    private double screenHeight;
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
    }

    public void initializeUI(){
        searchToolBar.setPrefWidth(screenWidth);
        double topHeight = screenHeight * 0.08;
        top.setPrefHeight(topHeight);
        menuBar.setPrefHeight(topHeight * 0.3);
        searchToolBar.setPrefHeight(topHeight * 0.7);
        fileScroll.setPrefWidth(screenWidth * 1);
        fileScroll.setFitToWidth(false);
        fileContainer.setPrefWidth(fileScroll.getPrefWidth() * 0.5);
        sizeContainer.setPrefWidth(fileScroll.getPrefWidth() * 0.5);
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
            fileLabel.setOnMouseClicked(event ->{
                System.out.println(fileLabel.getText());
            });
            sizeLabel.setOnMouseClicked(mouseEvent ->{
                System.out.println(fileLabel.getText());
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