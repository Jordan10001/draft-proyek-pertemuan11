package com.example.bdsqltester.scenes.user;

import com.example.bdsqltester.Alert.AlertClass;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import com.example.bdsqltester.DBsources.DBSourceManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InputmenuController {
    public TextField idmenu;
    public TextField namemenu;
    public TextField branch;
    public TextField price;
    public TextField description;

    public void Inputmenu(ActionEvent actionEvent) throws SQLException {
        String id = idmenu.getText();
        String name = namemenu.getText();
        String branchName = branch.getText();
        String priceText = price.getText();
        String desc = description.getText();

        // Cek apakah ada field yang kosong
        if (id.isEmpty() || name.isEmpty() || branchName.isEmpty() || priceText.isEmpty() || desc.isEmpty()) {
            AlertClass.ErrorAlert("Error", "Input Error", "Please fill in all fields.");
            return;
        }

        double priceValue;
        try {
            priceValue = Double.parseDouble(priceText);
        } catch (NumberFormatException e) {
            AlertClass.ErrorAlert("Error", "Format Error", "Price must be a valid number.");
            return;
        }

        Connection conn = DBSourceManager.getUserConnection();

        // Insert the menu into the database
        String sql = "INSERT INTO menus (ID, Menu_Name, Branch, Price, Description) VALUES (?, ?, ?, ?, ?)";
        try (Connection c = DBSourceManager.getUserConnection()) {
            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setString(1, id);
            stmt.setString(2, name);
            stmt.setString(3, branchName);
            stmt.setDouble(4, priceValue);
            stmt.setString(5, desc);
            stmt.executeUpdate();
            System.out.println("Menu inserted successfully.");
            AlertClass.InformationAlert("Success", "Menu Inserted", "Menu has been successfully inserted.");
            reloadForm();
        } catch (Exception e) {
            e.printStackTrace();
            AlertClass.ErrorAlert("Database Error", "Failed to insert menu", e.getMessage());
        }
    }

    private void reloadForm() {
        idmenu.clear();
        namemenu.clear();
        branch.clear();
        price.clear();
        description.clear();
    }
}
/*
INSERT INTO Menus (ID, Menu_Name, Branch, Price, Description) VALUES
('M006', 'Soto Ayam Lamongan', 'Cabang Jakarta', 23000.00, 'Soto ayam khas Lamongan dengan koya dan sambal'),
('M007', 'Bakso Urat Jumbo', 'Cabang Bandung', 27000.00, 'Bakso urat besar dengan kuah kaldu gurih'),
('M008', 'Gado-Gado Betawi', 'Cabang Surabaya', 19000.00, 'Sayur dengan bumbu kacang dan kerupuk'),
('M009', 'Nasi Uduk Komplit', 'Cabang Yogyakarta', 26000.00, 'Nasi uduk dengan telur, ayam, dan sambal'),
('M011', 'Sop Buntut', 'Cabang Jakarta', 45000.00, 'Sop buntut sapi dengan kuah gurih dan sayuran'),
('M012', 'Rendang Daging', 'Cabang Padang', 38000.00, 'Rendang daging sapi khas Padang'),
('M013', 'Nasi Campur Bali', 'Cabang Denpasar', 30000.00, 'Nasi campur dengan sate lilit, lawar, dan ayam'),
('M014', 'Tahu Gejrot', 'Cabang Cirebon', 12000.00, 'Tahu goreng dengan kuah pedas manis khas Cirebon'),
('M015', 'Pempek Palembang', 'Cabang Palembang', 24000.00, 'Pempek kapal selam dan lenjer dengan cuko'),
('M016', 'Sate Padang', 'Cabang Padang', 25000.00, 'Sate daging sapi dengan kuah kental khas Padang'),
('M017', 'Lontong Sayur', 'Cabang Medan', 18000.00, 'Lontong dengan sayur labu dan telur'),
('M018', 'Kopi Tubruk', 'Cabang Jakarta', 10000.00, 'Kopi hitam khas Indonesia disajikan panas'),
('M019', 'Teh Tarik', 'Cabang Aceh', 12000.00, 'Teh tarik khas Aceh yang manis dan berbusa'),
('M020', 'Ayam Geprek Mozarella', 'Cabang Bandung', 29000.00, 'Ayam geprek dengan topping keju mozarella leleh'),
('M021', 'Martabak Manis Coklat Keju', 'Cabang Jakarta', 23000.00, 'Martabak tebal dengan topping coklat dan keju'),
('M022', 'Kerak Telor', 'Cabang Jakarta', 20000.00, 'Makanan khas Betawi dengan telur bebek dan serundeng'),
('M023', 'Capcay Kuah', 'Cabang Semarang', 21000.00, 'Sayuran capcay kuah ala Chinese food'),
('M024', 'Mie Ayam Bakso', 'Cabang Bogor', 23000.00, 'Mie ayam dengan tambahan bakso sapi'),
('M025', 'Es Cendol Dawet', 'Cabang Solo', 9000.00, 'Minuman manis dengan santan, gula merah, dan cendol');

 */