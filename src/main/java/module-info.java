module io.github.jeangiraldoo.tagxplorer {
    requires javafx.controls;
    requires javafx.fxml;


    opens io.github.jeangiraldoo.tagxplorer to javafx.fxml;
    exports io.github.jeangiraldoo.tagxplorer;
}