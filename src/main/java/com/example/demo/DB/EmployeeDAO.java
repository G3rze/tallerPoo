package com.example.demo.DB;

import com.example.demo.Classes.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {
    private Connection connection;

    public EmployeeDAO(Connection connection) {
        this.connection = connection;
    }

    //Add new employee to database
    public void addEmployee(Employee employee) {
        try {
            PreparedStatement prepareStatement = connection.prepareStatement("INSERT INTO employee (name,lastName) VALUES (?,?)");
            prepareStatement.setString(1, employee.getName());
            prepareStatement.setString(2, employee.getLastName());
            prepareStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Obtain every employee from database
    public List<Employee> getEmployee() {
        List<Employee> employees = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM employee");
            while (rs.next()) {
                Employee employee = new Employee(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("lastname")
                );
                employees.add(employee);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return employees;
    }

    //Obtain employee by name and lastname
    public Employee getEmployeeByName(String name, String lastName){
        Employee employee = null;
        try{
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM employee where name = ? and lastName = ?");
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,lastName);
            try(ResultSet rs = preparedStatement.executeQuery()){
                if (rs.next()){
                    employee = new Employee(rs.getInt("id"), rs.getString("name"),rs.getString("lastName"));
                }
            }


        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return employee;
    }

}
