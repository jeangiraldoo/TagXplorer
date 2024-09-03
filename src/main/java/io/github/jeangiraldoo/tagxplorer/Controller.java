package io.github.jeangiraldoo.tagxplorer;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.VBox;


public class Controller {
    @FXML
    private ToolBar mainTool;
    @FXML
    private TextField searchBar;
    @FXML
    private Button searchButton;
    @FXML
    private VBox top;


    @FXML
    public void setNodeSize(double width, double height){
        mainTool.setPrefWidth(width);
        searchBar.setPadding(new Insets(height * 0.008));
        top.setPrefHeight(height * 0.10);




    }

}