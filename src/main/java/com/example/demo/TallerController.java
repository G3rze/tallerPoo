package com.example.demo;

import com.example.demo.Classes.Category;
import com.example.demo.Classes.Employee;
import com.example.demo.Classes.Register;
import com.example.demo.DB.CategoryDAO;
import com.example.demo.DB.Db;
import com.example.demo.DB.EmployeeDAO;
import com.example.demo.DB.RegisterDAO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

import java.util.List;
import java.util.ResourceBundle;

public class TallerController implements Initializable {
    @FXML
    private Label welcomeText;
    @FXML
    private TextField txtEmpleadoNombre;
    @FXML
    private TextField txtEmpleadoApellido;
    @FXML
    private TextField tareaNombre;
    @FXML
    private TextField txtTareaDescripcion;
    @FXML
    private ComboBox<Category> cmbCategoria;
    @FXML
    private DatePicker dpFechaInicio;
    @FXML
    private TextField txtEmpleadoNombreInsert;
    @FXML
    private TextField txtEmpleadoApellidoInsert;
    @FXML
    private TextField txtCategoryInsert;
    @FXML
    private VBox vboxTareaEmpleado;
    @FXML
    private DatePicker dpFechaFin;
    @FXML
    private TextField txtEmpleadoID;

    private EmployeeDAO employeeDAO;
    private CategoryDAO categoryDAO;
    private RegisterDAO registerDAO;

    public TallerController() {
        initializeDatabaseConnections();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources){
        List<Category> categories = categoryDAO.getCategories();
        cmbCategoria.getItems().addAll(categories);
    }

    private void initializeDatabaseConnections() {
        try {
            Db db = new Db();
            Connection connection = db.getCon();

            if (connection != null) {
                this.employeeDAO = new EmployeeDAO(connection);
                this.categoryDAO = new CategoryDAO(connection);
                this.registerDAO = new RegisterDAO(connection);
            } else {
                throw new SQLException("Failed to establish a database connection.");
            }
        } catch (SQLException e) {
            // Log detailed error or notify user
            System.err.println("Error initializing DAOs: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void addEmployeeInsert() throws SQLException {
        String name = txtEmpleadoNombreInsert.getText();
        String lastName = txtEmpleadoApellidoInsert.getText();
        Employee employee = new Employee(0, name, lastName);

        employeeDAO.addEmployee(employee);
    }

    @FXML
    public void addCategoryInsert() throws SQLException{
        String name = txtCategoryInsert.getText();
        Category category = new Category(0,name);
        categoryDAO.addCategory(category);
    }

    @FXML
    private void addRegister() throws SQLException {
        String name = txtEmpleadoNombre.getText();
        String lastName = txtEmpleadoApellido.getText();
        String description = txtTareaDescripcion.getText();
        Category category = cmbCategoria.getValue();
        String workName = tareaNombre.getText();
        Date date = java.sql.Date.valueOf(dpFechaInicio.getValue());
        Employee employee;
        employee = employeeDAO.getEmployeeByName(name,lastName);
        if(employee == null){
            employee = new Employee(0,name,lastName);
            employeeDAO.addEmployee(employee);
            employeeDAO.getEmployeeByName(name,lastName);
        }

        Register register = new Register(0,workName,description,employee,category,date);
        registerDAO.addRegister(register);

    }

    @FXML
    public void findEmployeesDate() throws SQLException {
        vboxTareaEmpleado.getChildren().clear();
        int empleadoId = Integer.parseInt(txtEmpleadoID.getText());
        Date startDate = java.sql.Date.valueOf(dpFechaInicio.getValue());
        Date endDate = java.sql.Date.valueOf(dpFechaFin.getValue());

        List<Register> registros = registerDAO.getRegisterByEmployee(empleadoId, startDate, endDate);
        for (Register registro : registros) {
            Label label = new Label(registro.getName() + ": " + registro.getDescription() + " (" + registro.getCategory().getName() + ")");
            vboxTareaEmpleado.getChildren().add(label);
        }
    }


}