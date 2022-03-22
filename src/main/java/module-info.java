module com.example.ziibdprojekt {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;


    opens com.example.ziibdprojekt to javafx.fxml;
    exports com.example.ziibdprojekt;
    exports com.example.ziibdprojekt.controllers;
    opens com.example.ziibdprojekt.controllers to javafx.fxml;
    exports com.example.ziibdprojekt.services;
    opens com.example.ziibdprojekt.services to javafx.fxml;
}