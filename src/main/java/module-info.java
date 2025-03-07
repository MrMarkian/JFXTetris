module com.jfxtetris.jfxtetris {
    requires javafx.controls;
    requires javafx.fxml;

    requires java.desktop;
    requires javafx.media;
    requires javafx.graphics;
    requires javafx.base;
    requires javafx.swing;

    opens com.jfxtetris to javafx.fxml;
    exports com.jfxtetris;
    exports com.jfxtetris.Controllers;
    exports com.jfxtetris.Models;
    exports com.jfxtetris.Views;
    opens com.jfxtetris.Controllers to javafx.fxml;
}