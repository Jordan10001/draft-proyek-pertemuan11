package com.example.bdsqltester.scenes;

import com.example.bdsqltester.Alert.AlertClass;
import com.example.bdsqltester.HelloApplication;
import com.example.bdsqltester.DBsources.DBSourceManager;
import com.example.bdsqltester.SceneLoader.SceneLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.*;

public class LoginController {

    @FXML
    private PasswordField passwordField;

    @FXML
    private ChoiceBox<String> selectRole;

    @FXML
    private TextField usernameField;

    private int userId;

    public LoginController() throws SQLException {
    }

    public int getUserId() {
        return userId;
    }

    Connection con = DBSourceManager.getUserConnection();

    boolean verifyCredentials(String username, String password, String role) throws SQLException {
        // Call the database to verify the credentials
        // This is insecure as this stores the password in plain text.
        // In a real application, you should hash the password and store it securely.
        // Get a connection to the database
        try (Connection c = DBSourceManager.getUserConnection()) {
            // Create a prepared statement to prevent SQL injection
            String sql = "SELECT * FROM Userss WHERE Username = ? AND Role = ?";
            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, role);
            System.out.println("in");

            // Execute the query
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // User found, check the password
                String dbPassword = rs.getString("Password");
                if (dbPassword.equals(password)) {
                    this.userId = rs.getInt("id");
                    return true; // Credentials are valid
                }
            }
        }
        // If we reach here, the credentials are invalid
        return false;
    }

    @FXML
    void initialize() {
        selectRole.getItems().addAll("Admin_Pusat");
        selectRole.getItems().addAll("Admin_Cabang");
        selectRole.setValue("Choice Role");
    }

    @FXML
    void onLoginClick(ActionEvent event) throws SQLException, IOException {
        // Get the username and password from the text fields
        String username = usernameField.getText();
        String password = passwordField.getText();
        String role = selectRole.getValue();
        // Verify the credentials
            if (verifyCredentials(username, password, role)) {
                HelloApplication app = HelloApplication.getApplicationInstance();
                app.setUserId(this.userId);// Store the user_id in HelloApplication
                app.getPrimaryStage().setTitle("User View");

                FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("user-view.fxml"));
                Scene scene = new Scene(loader.load(), 1500, 700);
                app.getPrimaryStage().setScene(scene);
                app.getPrimaryStage().sizeToScene();
            } else {
                // Show an error message
                AlertClass.ErrorAlert("Login Failed", "Invalid Credentials",
                        "Please check your username and password.");
            }
    }
}
