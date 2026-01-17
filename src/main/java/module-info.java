module com.example.chatsocket {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.chatsocket to javafx.fxml;
    exports com.example.chatsocket;
}