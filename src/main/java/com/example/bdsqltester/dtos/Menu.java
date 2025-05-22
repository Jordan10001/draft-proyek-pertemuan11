package com.example.bdsqltester.dtos;

public class Menu {
    private String menuId;
    private String menuName;
    private String branchName;
    private double price;
    private String description;

    public Menu(String menuId, String menuName, String branchName, double price, String description) {
        this.menuId = menuId;
        this.menuName = menuName;
        this.branchName = branchName;
        this.price = price;
        this.description = description;
    }

    public String getMenuId() {
        return menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public String getBranchName() {
        return branchName;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return menuName + " (" + branchName + ")";
    }
}
