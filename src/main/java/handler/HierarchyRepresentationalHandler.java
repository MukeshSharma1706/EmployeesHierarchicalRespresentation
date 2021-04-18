/**
 *  Class : HierarchyRepresentationalHandler
 *  Description : This class is used process the list of the employee and create a representational data.
 *
 * */

package handler;
import model.Employee;
import model.EmployeePosition;
import model.TabularStructure;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HierarchyRepresentationalHandler {

    public HierarchyRepresentationalHandler() {
    }

    public void initializeEmployeeList(List<Employee> employees) {

    }

    public void updateEmployeeInfo(List<Employee> employees) {

        if (employees != null && !employees.isEmpty()) {
            for (Employee emp1 : employees) {
                for (Employee emp2 : employees) {
                    if (emp1.getEmpId().equals(emp2.getManagerId()) && emp1.getEmpId() != emp1.getManagerId()) {
                        emp1.addManagedEmployees(emp2);
                    }
                }
            }
            updateEmployeePosition(employees);
        } else {
            System.out.println("Employee List cannot be null");
        }
    }

    private void updateEmployeePosition(List<Employee> employees){
        List<Integer> empIds = employees.stream()
                .filter(employee -> employee.getEmpId() != null)
                .map(Employee::getEmpId).collect(Collectors.toList());

        List<Integer> ceoIds = employees.stream()
                .filter(employee -> employee.getManagerId() == null)
                .map(Employee::getEmpId).collect(Collectors.toList());

        for (Employee emp: employees) {

            Integer managerId = emp.getManagerId();
            if (managerId == null && emp.getEmpId() > 0) {
                emp.setPosition(EmployeePosition.CEO);
            } else if (managerId != null) {
                if (ceoIds.contains(managerId) && emp.getEmpId() > 0) {
                    emp.setPosition(EmployeePosition.MANAGER);
                } else if (empIds.contains(managerId) && emp.getEmpId() > 0) {
                    emp.setPosition(EmployeePosition.ENGINEER);
                } else {
                    emp.setPosition(EmployeePosition.INVALID_EMPLOYEE);
                }
            }
        }

    }

    public List<Employee> getEmployeeHierarchy(EmployeePosition position, List<Employee> employees) {
        if (position == null || employees == null || employees.isEmpty()) {
            System.out.println("Employee List and position cannot be null");
            return null;
        }
         return employees.stream()
                .filter(employee -> position.equals(employee.getPosition()))
                .collect(Collectors.toList());
    }

    public List<TabularStructure>  representData(List<Employee> employees) {

        List<TabularStructure> tabularStructure = new ArrayList<TabularStructure>();
        int maxNamelen = 0;

        for (Employee ceo : employees) {
            tabularStructure.add(new TabularStructure(ceo.getName(),"",""));
            maxNamelen = maxNamelen < ceo.getName().length() ? ceo.getName().length() : maxNamelen;
            if (ceo.getListOfManagedEmp() != null) {
                for (Employee manager : ceo.getListOfManagedEmp()) {
                    tabularStructure.add(new TabularStructure("",manager.getName(),""));
                    maxNamelen = maxNamelen < manager.getName().length() ? manager.getName().length() : maxNamelen;
                    if (manager.getListOfManagedEmp() != null) {
                        for (Employee emp : manager.getListOfManagedEmp()) {
                            tabularStructure.add(new TabularStructure("","",emp.getName()));
                            maxNamelen = maxNamelen < emp.getName().length() ? emp.getName().length() : maxNamelen;
                        }
                    }
                }
            }
        }
        TabularStructure.setMaxWidth(maxNamelen);
        return tabularStructure;
    }
}
