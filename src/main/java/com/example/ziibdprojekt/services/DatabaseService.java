package com.example.ziibdprojekt.services;

import com.example.ziibdprojekt.ConnectionManager;
import com.example.ziibdprojekt.EmployeeModel;
import javafx.collections.ObservableList;

import java.sql.*;

public class DatabaseService {

    public ConnectionManager connectionManager;

    public Connection connection;

    public DatabaseService(){
        connectionManager = new ConnectionManager();
        connection = connectionManager.getConnection();
    }


    public void getEmployeeTableValues(ObservableList<EmployeeModel> data){
        String query = "SELECT * FROM EMPLOYEES";

        try{
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                EmployeeModel employee = new EmployeeModel();
                employee.setEmployeeId((rs.getInt(1)));
                employee.setFirst_name((rs.getString(2)));
                employee.setLast_name((rs.getString(3)));
                employee.setEmail((rs.getString(4)));
                employee.setPhoneNumber((rs.getString(5)));
                employee.setDate((rs.getString(6)));
                employee.setJobId((rs.getString(7)));
                employee.setSalary((rs.getString(8)));
                employee.setCommissionPct((rs.getDouble(9)));
                employee.setManagerId((rs.getInt(10)));
                employee.setDepartmentId((rs.getInt(11)));
                data.add(employee);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println("[DatabaseService] Wykonano zapytanie 'SELECT * FROM EMPLOYEES' dla %employeesTableView");
    }

    public void addEmployee(int employeeId, String first_name, String last_name, String email, String phoneNumber,
                            Date date, String jobId, String salary, double commissionPct, int managerId, int departmentId) {

        String query = "INSERT INTO EMPLOYEES (EMPLOYEE_ID, FIRST_NAME, LAST_NAME, EMAIL, PHONE_NUMBER, HIRE_DATE, JOB_ID," +
                " SALARY, COMMISSION_PCT, MANAGER_ID, DEPARTMENT_ID) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, employeeId);
            preparedStatement.setString(2, first_name);
            preparedStatement.setString(3, last_name);
            preparedStatement.setString(4, email);
            preparedStatement.setString(5, phoneNumber);
            preparedStatement.setDate(6, date);
            preparedStatement.setString(7, jobId);
            preparedStatement.setString(8, salary);
            preparedStatement.setDouble(9, commissionPct);
            preparedStatement.setInt(10, managerId);
            preparedStatement.setInt(11, departmentId);

            preparedStatement.execute();
        } catch (SQLException e) {
            System.err.println("Błąd podczas wykonnywania zapytania INSERT INTO!");
            e.printStackTrace();
        }

        System.out.println("[DatabaseService] Dodano pracownika " + first_name + " " + last_name);
    }

    public void deleteEmployeeById(int id){
        String query = "DELETE FROM EMPLOYEES WHERE employee_id = " + id;
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }
        System.out.println("[DatabaseService] Usunięto użytkownika z id " + id);
    }

    public void updateEmployee(int employeeId, String first_name, String last_name, String email, String phoneNumber,
                                   Date date, String jobId, String salary, double commissionPct, int managerId, int departmentId) {

        String query = "UPDATE EMPLOYEES SET EMPLOYEE_ID = ?," +
                "FIRST_NAME = ?," +
                "LAST_NAME = ?," +
                "EMAIL = ?," +
                "PHONE_NUMBER = ?," +
                "HIRE_DATE = ?," +
                "JOB_ID = ?," +
                "SALARY = ?," +
                "COMMISSION_PCT = ?," +
                "MANAGER_ID = ?," +
                "DEPARTMENT_ID = ?" +
                "WHERE EMPLOYEE_ID = " + employeeId;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, employeeId);
            preparedStatement.setString(2, first_name);
            preparedStatement.setString(3, last_name);
            preparedStatement.setString(4, email);
            preparedStatement.setString(5, phoneNumber);
            preparedStatement.setDate(6, date);
            preparedStatement.setString(7, jobId);
            preparedStatement.setString(8, salary);
            preparedStatement.setDouble(9, commissionPct);
            preparedStatement.setInt(10, managerId);
            preparedStatement.setInt(11, departmentId);

            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("[DatabaseService] zaaktualizowano pracownika " + first_name + " " + last_name);
    }
}
