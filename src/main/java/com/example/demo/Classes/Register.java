package com.example.demo.Classes;

import java.sql.Date;

public class Register {
    private int id;
    private String name;
    private String description;
    private Employee employee;
    private Category category;
    private Date date;

    public Register(int id, String name, String description, Employee employee, Category category, Date date) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.employee = employee;
        this.category = category;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
