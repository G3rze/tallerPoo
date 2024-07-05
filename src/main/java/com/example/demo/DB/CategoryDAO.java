package com.example.demo.DB;

import com.example.demo.Classes.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {
    private Connection connection;

    public CategoryDAO(Connection connection){
        this.connection = connection;
    }

    //Add new category to database
    public void addCategory(Category category){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO category (name) values (?)");
            preparedStatement.setString(1,category.getName());
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    //Obtain categories from database
    public List<Category> getCategories(){
        List<Category> categories = new ArrayList<>();
        try{
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM category");
            while (rs.next()){
                Category category = new Category(
                        rs.getInt("id"),
                        rs.getString("name")
                );
                categories.add(category);
            }
        }catch(SQLException e){
            throw  new RuntimeException(e);
        }
        return categories;
    }

    //Obtain Category by name
    public Category getCategoryByName(String name){
        Category category = null;
        try{
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM category WHERE name = ?");
            preparedStatement.setString(1,name);
            try (ResultSet rs = preparedStatement.executeQuery()){
                if(rs.next()){
                    category = new Category(rs.getInt("id"),rs.getString("name"));
                }
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return category;
    }


}
