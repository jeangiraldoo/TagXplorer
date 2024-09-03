package io.github.jeangiraldoo.tagxplorer;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.VBox;


public class Controller {
    @FXML
    private ToolBar searchToolBar;
    @FXML
    private TextField searchBar;
    @FXML
    private Button searchButton;
    @FXML
    private VBox top;
    @FXML
    private MenuBar menuBar;



    @FXML
    public void setNodeSize(double width, double height){
        searchToolBar.setPrefWidth(width);
        double topHeight = height * 0.08;
        top.setPrefHeight(topHeight);
        menuBar.setPrefHeight(topHeight * 0.3);
        searchToolBar.setPrefHeight(topHeight * 0.7);





    }

}