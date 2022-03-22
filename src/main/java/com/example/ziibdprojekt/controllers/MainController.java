package com.example.ziibdprojekt.controllers;

import com.example.ziibdprojekt.EmployeeModel;
import com.example.ziibdprojekt.services.DatabaseService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML private Button goToEmployeesTableBtn;

    @FXML private Button goToDepartmentsBtn;

    @FXML private Label headerLabel;
    @FXML private GridPane employeesGridPane;
    @FXML private GridPane departmentsGridPane;
    @FXML private GridPane addEmployeeGridPane;
    @FXML private GridPane mainGridPane;

    @FXML private TableView<EmployeeModel> employeesTableView;

    @FXML private TableColumn EMPLOYEE_ID = new TableColumn("EMPLOYEE_ID");
    @FXML private TableColumn FIRST_NAME = new TableColumn("FIRST_NAME");
    @FXML private TableColumn LAST_NAME = new TableColumn("LAST_NAME");
    @FXML private TableColumn EMAIL = new TableColumn("EMAIL");
    @FXML private TableColumn PHONE_NUMBER = new TableColumn("PHONE_NUMBER");
    @FXML private TableColumn DATE = new TableColumn("HIRE_DATE");
    @FXML private TableColumn SALARY = new TableColumn("SALARY");
    @FXML private TableColumn JOB_ID = new TableColumn("JOB_ID");
    @FXML private TableColumn COMMISSION_PCT = new TableColumn("COMMISSION_PCT");
    @FXML private TableColumn MANAGER_ID = new TableColumn("MANAGER_ID");
    @FXML private TableColumn DEPARTMENT_ID = new TableColumn("DEPARTMENT_ID");

    @FXML private Button addEmployeeBtn;
    @FXML private Button deleteEmployeeBtn;
    @FXML private Button editEmployeeBtn;
    @FXML private Button submitEmployeeButton;
    @FXML private Button refreshButton;

    @FXML private TextField idTextField;
    @FXML private TextField nameTextField;
    @FXML private TextField lastnameTextField;
    @FXML private TextField emailTextField;
    @FXML private TextField phoneNumberTextField;
    @FXML private DatePicker datePicker;
    @FXML private TextField salaryTextField;
    @FXML private TextField jobIdTextField;
    @FXML private TextField commissionTextField;
    @FXML private TextField managerIdTextField;
    @FXML private TextField departmentIdTextField;


    @FXML private GridPane updateEmployeeGridPane;

    @FXML private TextField idTextFieldUpd;
    @FXML private TextField nameTextFieldUpd;
    @FXML private TextField lastnameTextFieldUpd;
    @FXML private TextField emailTextFieldUpd;
    @FXML private TextField phoneNumberTextFieldUpd;
    @FXML private DatePicker datePickerUpd;
    @FXML private TextField salaryTextFieldUpd;
    @FXML private TextField jobIdTextFieldUpd;
    @FXML private TextField commissionTextFieldUpd;
    @FXML private TextField managerIdTextFieldUpd;
    @FXML private TextField departmentIdTextFieldUpd;

    @FXML private Button updateEmployeeBtn;

    private ObservableList<EmployeeModel> data;

    private DatabaseService databaseService;

    private EmployeeModel tmpEmployee;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        databaseService = new DatabaseService();

        data = FXCollections.observableArrayList();
        headerLabel.setText("EMPLOYEE MANAGEMENT SYSTEM | ZIIBD 2021");

        initTableView();
        setValuesToEmployeeTable();
    }

    private void initTableView() {
        EMPLOYEE_ID.setCellValueFactory(new PropertyValueFactory<EmployeeModel, Integer>("employeeId"));
        FIRST_NAME.setCellValueFactory(new PropertyValueFactory<EmployeeModel, String>("first_name"));
        LAST_NAME.setCellValueFactory(new PropertyValueFactory<EmployeeModel, String>("last_name"));
        EMAIL.setCellValueFactory(new PropertyValueFactory<EmployeeModel, String>("email"));
        PHONE_NUMBER.setCellValueFactory(new PropertyValueFactory<EmployeeModel, String>("phoneNumber"));
        DATE.setCellValueFactory(new PropertyValueFactory<EmployeeModel, String>("date"));
        SALARY.setCellValueFactory(new PropertyValueFactory<EmployeeModel, Long>("salary"));
        JOB_ID.setCellValueFactory(new PropertyValueFactory<EmployeeModel, String>("jobId"));
        COMMISSION_PCT.setCellValueFactory(new PropertyValueFactory<EmployeeModel, Double>("commissionPct"));
        MANAGER_ID.setCellValueFactory(new PropertyValueFactory<EmployeeModel, Integer>("managerId"));
        DEPARTMENT_ID.setCellValueFactory(new PropertyValueFactory<EmployeeModel, Integer>("departmentId"));

        employeesTableView.getColumns().addAll(EMPLOYEE_ID, FIRST_NAME, LAST_NAME, EMAIL, PHONE_NUMBER, DATE, SALARY, JOB_ID, COMMISSION_PCT, MANAGER_ID, DEPARTMENT_ID);
    }

    public void setValuesToEmployeeTable() {
        databaseService.getEmployeeTableValues(data);
        employeesTableView.setItems(data);
    }

    @FXML
    private void menuButtonClicked(ActionEvent event){
        mainGridPane.setVisible(false);

        if(event.getSource() == goToEmployeesTableBtn){
            System.out.println("-> Pracownicy");
            headerLabel.setText("Pracownicy");
            employeesGridPane.setVisible(true);
            addEmployeeGridPane.setVisible(false);
            departmentsGridPane.setVisible(false);
            updateEmployeeGridPane.setVisible(false);
        } else if(event.getSource() == goToDepartmentsBtn){
            System.out.println("-> Departamenty");
            headerLabel.setText("Departamenty");
            employeesGridPane.setVisible(false);
            addEmployeeGridPane.setVisible(false);
            departmentsGridPane.setVisible(true);
            updateEmployeeGridPane.setVisible(false);
        } else if(event.getSource() == addEmployeeBtn){
            System.out.println("-> Dodaj Pracownika");
            headerLabel.setText("Dodaj Pracownika");
            employeesGridPane.setVisible(false);
            departmentsGridPane.setVisible(false);
            addEmployeeGridPane.setVisible(true);
            updateEmployeeGridPane.setVisible(false);
        } else if(event.getSource() == deleteEmployeeBtn){
            deleteEmployee();
        } else if(event.getSource() == refreshButton){
            refresh();
        } else if(event.getSource() == editEmployeeBtn){
            System.out.println("-> Edytuj Informacje o pracowniku");
            headerLabel.setText("Edytuj Informacje o pracowniku");
            updateEmployeeGridPane.setVisible(true);
            employeesGridPane.setVisible(false);
            departmentsGridPane.setVisible(false);
            addEmployeeGridPane.setVisible(false);
            setUpdateEmployeeGridPaneValues();
        }
    }

    @FXML
    private void updateEmployeeButtonClicked(ActionEvent event) {

        databaseService.updateEmployee(
                Integer.parseInt(idTextFieldUpd.getText()),
                nameTextFieldUpd.getText(),
                lastnameTextFieldUpd.getText(),
                emailTextFieldUpd.getText(),
                phoneNumberTextFieldUpd.getText(),
                Date.valueOf(datePickerUpd.getValue()),
                jobIdTextFieldUpd.getText(),
                salaryTextFieldUpd.getText(),
                Double.parseDouble(commissionTextFieldUpd.getText()),
                Integer.parseInt(managerIdTextFieldUpd.getText()),
                Integer.parseInt(departmentIdTextFieldUpd.getText()));
    }

    private void refresh() {
        data.removeAll();
        employeesTableView.getItems().clear();
        setValuesToEmployeeTable();
        System.out.println("REFRESH");
    }

    public void addEmployeeButtonClicked(ActionEvent event) {
        if(event.getSource() == submitEmployeeButton){

            if(validateJobID(jobIdTextField.getText())){
                databaseService.addEmployee(
                        Integer.parseInt(idTextField.getText()),
                        nameTextField.getText(),
                        lastnameTextField.getText(),
                        emailTextField.getText(),
                        phoneNumberTextField.getText(),
                        Date.valueOf(datePicker.getValue()),
                        jobIdTextField.getText(),
                        salaryTextField.getText(),
                        Double.parseDouble(commissionTextField.getText()),
                        Integer.parseInt(managerIdTextField.getText()),
                        Integer.parseInt(departmentIdTextField.getText()));
            }
        }
    }

    public void deleteEmployee(){
        EmployeeModel employee = employeesTableView.getSelectionModel().getSelectedItem();
        databaseService.deleteEmployeeById(employee.getEmployeeId());
    }

    public void setUpdateEmployeeGridPaneValues(){
        tmpEmployee = employeesTableView.getSelectionModel().getSelectedItem();

        idTextFieldUpd.setText(String.valueOf(tmpEmployee.getEmployeeId()));
        nameTextFieldUpd.setText(tmpEmployee.getFirst_name());
        lastnameTextFieldUpd.setText(tmpEmployee.getLast_name());
        emailTextFieldUpd.setText(tmpEmployee.getEmail());
        phoneNumberTextFieldUpd.setText(tmpEmployee.getPhoneNumber());
        // BRAK USTAWIANIA WARTOŚCI DLA DATEPICKERA
        jobIdTextFieldUpd.setText(tmpEmployee.getJobId());
        salaryTextFieldUpd.setText(tmpEmployee.getSalary());
        commissionTextFieldUpd.setText(String.valueOf(tmpEmployee.getCommissionPct()));
        managerIdTextFieldUpd.setText(String.valueOf(tmpEmployee.getManagerId()));
        departmentIdTextFieldUpd.setText(String.valueOf(tmpEmployee.getDepartmentId()));
    }

    private boolean validateJobID(String jobId){
        String[] jobIds = new String[]{"AD_ASST", "MK_MAN", "MK_REP", "AC_MGR", "AC_ACCOUNT", "AD_PRES", "AD_VP", "IT_PROG", "ST_MAN", "ST_CLERK", "SA_MAN", "SA_REP"};
        for(String jid : jobIds){
            if (jid.equals(jobId)){
                return true;
            }
        }
        System.out.println("NIEPOPRAWNY JOB_ID");
        return false;
    }
}