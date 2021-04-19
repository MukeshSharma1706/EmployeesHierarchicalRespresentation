/**
 *  Class : Employee
 *  Description : To keep Employee record
 *
 * */

package model;

import java.util.ArrayList;
import java.util.List;

public class Employee {
    private String name;
    private Integer empId;
    private Integer managerId;
    private EmployeePosition position;
    private List<Employee> listOfManagedEmp;

    public Employee() {
    }
    public Employee(String name, Integer empId, Integer managerId) {
        this.name = name;
        this.empId = empId;
        this.managerId = managerId;
        this.position = null;
        this.listOfManagedEmp =null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public Integer getManagerId() {
        return managerId;
    }

    public void setManagerId(Integer managerId) {
        this.managerId = managerId;
    }

    public EmployeePosition getPosition() {
        return position;
    }

    public void setPosition(EmployeePosition position) {
        this.position = position;
    }

    public List<Employee> getListOfManagedEmp() {
        return listOfManagedEmp;
    }

    public void addManagedEmployees(Employee emp) {
        if(listOfManagedEmp == null) {
            listOfManagedEmp = new ArrayList<Employee>();
        }
        listOfManagedEmp.add(emp);
    }
}
