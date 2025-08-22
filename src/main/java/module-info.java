module com.example.asteroiddestroyer {
    requires javafx.controls;
    requires javafx.fxml;
            
        requires org.controlsfx.controls;
                        requires org.kordamp.bootstrapfx.core;
    requires javafx.media;

    opens com.example.asteroiddestroyer to javafx.fxml;
    exports com.example.asteroiddestroyer;
}