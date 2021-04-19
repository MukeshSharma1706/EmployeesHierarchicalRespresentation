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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class HierarchyRepresentationalHandler {

    /**
     * This function validate the input data before processing
     * @param employees
     * @return
     */
    public boolean validateInput(List<Employee> employees) {
        //check emp_id should not be null
        if (employees.stream().filter(employee -> employee.getEmpId() == null).findAny().isPresent()) {
            System.out.println("Employee Id cannot be null");
            return false;
        }
        if (employees.stream().filter(employee -> employee.getEmpId().equals(employee.getManagerId())).findAny().isPresent()){
            System.out.println("Employee Id and Manager_id cannot be same");
            return false;
        }
        // Validate there is no duplicate emp_id
        Set<Integer> uniqueEmpIds = new HashSet<Integer>();
        for (Employee employee : employees) {
                if(uniqueEmpIds.add(employee.getEmpId()) == false) {
                    System.out.println("Duplicate Employee Id is not allowed");
                    return  false;
                }
        }
        return true;
    }

    /**
     * This function adds list of employees managed by Manager/CEO.
     * @param employees : list of employees
     */
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

    /**
     * This functions add position for the employee
     * @param employees : list of employees
     */
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

    /**
     * This function returns the list of employees based on the position
     * @param position  : position can CEO,MANAGER,ENGINEER,INVALID_EMPLOYEE
     * @param employees : list of employees
     * @return list of employee based on position
     */
    public List<Employee> getEmployeeHierarchy(EmployeePosition position, List<Employee> employees) {
        if (position == null || employees == null || employees.isEmpty()) {
            System.out.println("Employee List and position cannot be null");
            return null;
        }
         return employees.stream()
                .filter(employee -> position.equals(employee.getPosition()))
                .collect(Collectors.toList());
    }

    /**
     * This function creates a representational data.
     * @param employees : list of employees
     * @return
     */
    public List<TabularStructure> representData(List<Employee> employees) {

        List<Employee> employeesHierarchy = getEmployeeHierarchy(EmployeePosition.CEO, employees);
        List<TabularStructure> tabularStructure = null;

        if(employeesHierarchy == null || employeesHierarchy.isEmpty()) {
            System.out.println("There is no CEO in the provided employee list");
        } else {
            tabularStructure = new ArrayList<TabularStructure>();
            int maxNameLen = 0;

            for (Employee ceo : employeesHierarchy) {
                tabularStructure.add(new TabularStructure(ceo.getName(), "", ""));
                maxNameLen = maxNameLen < ceo.getName().length() ? ceo.getName().length() : maxNameLen;
                if (ceo.getListOfManagedEmp() != null) {
                    for (Employee manager : ceo.getListOfManagedEmp()) {
                        tabularStructure.add(new TabularStructure("", manager.getName(), ""));
                        maxNameLen = maxNameLen < manager.getName().length() ? manager.getName().length() : maxNameLen;
                        if (manager.getListOfManagedEmp() != null) {
                            for (Employee emp : manager.getListOfManagedEmp()) {
                                tabularStructure.add(new TabularStructure("", "", emp.getName()));
                                maxNameLen = maxNameLen < emp.getName().length() ? emp.getName().length() : maxNameLen;
                            }
                        }
                    }
                }
            }
            TabularStructure.setMaxWidth(maxNameLen);
        }
        return tabularStructure;
    }
}
