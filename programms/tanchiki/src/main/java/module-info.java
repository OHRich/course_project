module org.example.tanchiki {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jetbrains.annotations;
    requires java.datatransfer;
    requires java.desktop;
    requires jlayer;

    opens org.example.tanchiki to javafx.fxml;
    exports org.example.tanchiki;
    exports org.example.tanchiki.services;
    opens org.example.tanchiki.services to javafx.fxml;
}