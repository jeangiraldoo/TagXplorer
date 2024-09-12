module io.github.jeangiraldoo.tagxplorer {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens io.github.jeangiraldoo.tagxplorer to javafx.fxml;
    exports io.github.jeangiraldoo.tagxplorer;
}