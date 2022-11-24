module com.jfxtetris.jfxtetris {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;

    opens com.jfxtetris to javafx.fxml;
    exports com.jfxtetris;
    exports com.jfxtetris.Controllers;
    opens com.jfxtetris.Controllers to javafx.fxml;
}