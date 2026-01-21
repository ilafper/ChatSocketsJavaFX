module com.example.chatsocket {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;


    opens com.example.chatsocket to javafx.fxml;
    exports com.example.chatsocket;
}