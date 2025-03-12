module com.example.lab4_map {

    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires org.xerial.sqlitejdbc;
    requires java.sql;

    opens com.example.lab4_map to javafx.fxml;
    exports com.example.lab4_map;
}