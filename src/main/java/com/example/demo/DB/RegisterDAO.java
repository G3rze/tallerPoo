package com.example.demo.DB;

import com.example.demo.Classes.Category;
import com.example.demo.Classes.Employee;
import com.example.demo.Classes.Register;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RegisterDAO {
    private Connection connection;
    public RegisterDAO(Connection connection){
        this.connection = connection;
    }

    //Add new register to database
    public void addRegister(Register register){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO register (name,description,id_employee,id_category,work_date) values (?,?,?,?,?)");
            preparedStatement.setString(1,register.getName());
            preparedStatement.setString(2,register.getDescription());
            preparedStatement.setInt(3,register.getEmployee().getId());
            preparedStatement.setInt(4,register.getCategory().getId());
            preparedStatement.setDate(5, register.getDate());
            preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    //Obtain register of work by employee and date range
    public List<Register> getRegisterByEmployee(int employeeID, Date start, Date finish){
        List<Register> registers = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM register where id_employee = ? and AND work_date BETWEEN ? and ?");
            preparedStatement.setInt(1,employeeID);
            preparedStatement.setDate(2, start);
            preparedStatement.setDate(3, finish);
            try (ResultSet rs = preparedStatement.executeQuery()){
                while (rs.next()){
                    Register register = new Register(
                           rs.getInt("id"),
                           rs.getString("name"),
                           rs.getString("description"),
                           new Employee(rs.getInt("id_employee"),"",""),
                            new Category(rs.getInt("id_category"),""),
                            rs.getDate("date")
                    );
                    registers.add(register);
                }

            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return registers;
    }

    //Obtain employees with a specific category
    public List<Employee> getEmployeeByCategory(int idCategory){
        List<Employee> employees = new ArrayList<>();

            try {
                PreparedStatement preparedStatement =
                        connection.prepareStatement("SELECT e.id, e.name from employee inner join" +
                                "register r on e.id = r.id_employee where r.id_category = ? order by e.lastName");
                preparedStatement.setInt(1,idCategory);
                try(ResultSet rs = preparedStatement.executeQuery()) {
                    while (rs.next()){
                        Employee employee = new Employee(
                                rs.getInt("id"),
                                rs.getString("name"),
                                rs.getString("lastName")
                        );

                        employees.add(employee);
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        return employees;
    }

}
