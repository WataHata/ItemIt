module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.graphics;
    // requires mysql.connector.java;
//    requires com.microsoft.sqlserver.jdbc;


    opens com.ooad to javafx.fxml;
    opens com.ooad.Forms to javafx.fxml;
    opens com.ooad.Controllers to javafx.fxml;
    
    exports com.ooad;
    exports com.ooad.Forms;
    exports com.ooad.Controllers;
}
