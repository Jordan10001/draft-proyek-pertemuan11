package com.example.bdsqltester.scenes.user;

import com.example.bdsqltester.Alert.AlertClass;
import com.example.bdsqltester.DBsources.DBSourceManager;
import com.example.bdsqltester.SceneLoader.SceneLoader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import com.example.bdsqltester.dtos.Menu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserController {

    @FXML
    private ComboBox<String> filter;

    @FXML
    private TableView<Menu> menu_table;

    @FXML
    private TableColumn<Menu, Integer> menu_table_no;

    @FXML
    private TableColumn<Menu, Long> menu_table_id; // ✅ tambahkan ini

    @FXML
    private TableColumn<Menu, String> menu_table_name;

    @FXML
    private TableColumn<Menu, String> menu_table_branch;

    @FXML
    private TableColumn<Menu, Double> menu_table_price;

    @FXML
    private TableColumn<Menu, String> menu_table_description;

    @FXML
    public Label welcome_text;

    private ObservableList<Menu> menuList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Setup nomor urut
        menu_table_no.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(String.valueOf(getIndex() + 1));
                }
            }
        });

        // Set cell value factories
        menu_table_id.setCellValueFactory(new PropertyValueFactory<>("menuId")); // ✅ map ke getter di Menu.java
        menu_table_name.setCellValueFactory(new PropertyValueFactory<>("menuName"));
        menu_table_branch.setCellValueFactory(new PropertyValueFactory<>("branchName"));
        menu_table_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        menu_table_description.setCellValueFactory(new PropertyValueFactory<>("description"));

        loadMenuData();
    }

    private void loadMenuData() {
        String query = "SELECT ID, menu_name, branch, price, description FROM menus";

        try (Connection conn = DBSourceManager.getUserConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String id = rs.getString("ID");
                String name = rs.getString("menu_name");
                String branch = rs.getString("branch");
                double price = rs.getDouble("price");
                String desc = rs.getString("description");

                Menu menu = new Menu(id, name, branch, price, desc);
                menuList.add(menu);
            }

            menu_table.setItems(menuList);

        } catch (SQLException e) {
            AlertClass.ErrorAlert("Database Error", "Failed to load menu data", e.getMessage());
        }
    }

    @FXML
    public void onNextButtonClick(ActionEvent event) {
        SceneLoader.loadFXML(event, "/com/example/bdsqltester/inputmenu.fxml", "Input Menu");
    }

    @FXML
    public void onbackbutton(ActionEvent event) {
        SceneLoader.loadFXML(event, "/com/example/bdsqltester/login-view.fxml", "Login");
    }
}
