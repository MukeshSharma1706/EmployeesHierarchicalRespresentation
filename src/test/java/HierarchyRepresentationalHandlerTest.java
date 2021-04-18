/**
 *  Class : HierarchyRepresentationalHandlerTest
 *  Description : To test the working of the HierarchyRepresentationalHandler
 *
 * */

import handler.HierarchyRepresentationalHandler;
import model.Employee;
import model.EmployeePosition;
import model.TabularStructure;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Test.*;
import static org.junit.Assert.*;


public class HierarchyRepresentationalHandlerTest {

    HierarchyRepresentationalHandler handler = new HierarchyRepresentationalHandler();

    @Test
    public void updateEmployeeInfoTest() {
        Employee e1 = new Employee("Alan",100,150);
        Employee e2 = new Employee("Martin",220,100);
        Employee e3 = new Employee("Jamie",150,null);
        Employee e4 = new Employee("Alex",275,100);
        Employee e5 = new Employee("Steve",400,150);
        Employee e6 = new Employee("David",190,400);

        List<Employee> listOfemployees = new ArrayList<Employee>();
        listOfemployees.add(e1);
        listOfemployees.add(e2);
        listOfemployees.add(e3);
        listOfemployees.add(e4);
        listOfemployees.add(e5);
        listOfemployees.add(e6);

        //
        handler.updateEmployeeInfo(null);
        handler.updateEmployeeInfo(new ArrayList<>());

        //Case A : Success secenarios = CEO -> Manager -> Employee
        handler.updateEmployeeInfo(listOfemployees);
        validateData(listOfemployees,e3,EmployeePosition.CEO, Arrays.asList(e1,e5));
        validateData(listOfemployees,e1,EmployeePosition.MANAGER, Arrays.asList(e2,e4));
        validateData(listOfemployees,e5,EmployeePosition.MANAGER, Arrays.asList(e6));

        //Case B : CEO with no manager reporting
        Employee e7 = new Employee("Jen",951,null);  //Manager empIs is not valid empId
        listOfemployees.add(e7);
        handler.updateEmployeeInfo(listOfemployees);
        validateData(listOfemployees,e7,EmployeePosition.CEO,null);

        //Case C :  Manager who is not employee will not print
        Employee e8 = new Employee("Max",951,81);
        listOfemployees.add(e8);
        handler.updateEmployeeInfo(listOfemployees);
        validateData(listOfemployees,e8,EmployeePosition.INVALID_EMPLOYEE,null);

        //Case D :  Employee with invalid emp_id
        Employee e9 = new Employee("Eleni",-1,151);
        listOfemployees.add(e9);
        handler.updateEmployeeInfo(listOfemployees);
        validateData(listOfemployees,e9,EmployeePosition.INVALID_EMPLOYEE,null);
  }

    @Test
    public void getEmployeeHierarchyTest() {
        Employee e1 = new Employee("Martin",220,100);
        Employee e2 = new Employee("Alex",275,100);
        Employee e3 = new Employee("Alan",100,150);
        e3.setPosition(EmployeePosition.MANAGER);
        e3.addManagedEmployees(e1);
        e3.addManagedEmployees(e2);
        Employee e4 = new Employee("David",190,400);
        Employee e5 = new Employee("Steve",400,150);
        e5.setPosition(EmployeePosition.MANAGER);
        e5.addManagedEmployees(e4);
        Employee e6 = new Employee("Jamie",150,null);
        e6.setPosition(EmployeePosition.CEO);
        e6.addManagedEmployees(e3);
        e6.addManagedEmployees(e5);


        List<Employee> listOfemployees = new ArrayList<Employee>();
        listOfemployees.add(e1);
        listOfemployees.add(e2);
        listOfemployees.add(e3);
        listOfemployees.add(e4);
        listOfemployees.add(e5);
        listOfemployees.add(e6);

        List<Employee> managers = handler.getEmployeeHierarchy(EmployeePosition.MANAGER,listOfemployees);
        assertTrue((managers.contains(e3)));
        assertTrue((managers.contains(e5)));

        List<Employee> ceos = handler.getEmployeeHierarchy(EmployeePosition.CEO,listOfemployees);
        assertTrue((ceos.contains(e6)));

        List<Employee> employees = handler.getEmployeeHierarchy(EmployeePosition.MANAGER,null);
        assertEquals(employees,null);

        employees = handler.getEmployeeHierarchy(EmployeePosition.MANAGER,new ArrayList<>());
        assertEquals(employees,null);
    }

    @Test
    public void representDataTest() {
        Employee e1 = new Employee("Martin",220,100);
        Employee e2 = new Employee("Alex",275,100);
        Employee e3 = new Employee("Alan",100,150);
        e3.setPosition(EmployeePosition.MANAGER);
        e3.addManagedEmployees(e1);
        e3.addManagedEmployees(e2);
        Employee e4 = new Employee("David",190,400);
        Employee e5 = new Employee("Steve",400,150);
        e5.setPosition(EmployeePosition.MANAGER);
        e5.addManagedEmployees(e4);
        Employee e6 = new Employee("Jamie",150,null);
        e6.setPosition(EmployeePosition.CEO);
        e6.addManagedEmployees(e3);
        e6.addManagedEmployees(e5);

        List<Employee> listOfemployees = new ArrayList<Employee>();
        listOfemployees.add(e1);
        listOfemployees.add(e2);
        listOfemployees.add(e3);
        listOfemployees.add(e4);
        listOfemployees.add(e5);
        listOfemployees.add(e6);

        List<TabularStructure> tabularStructures = handler.representData(Arrays.asList(e6));
        assertEquals(listOfemployees.size(),tabularStructures.size());
    }

    private void validateData(List<Employee> employees, Employee checkFor,
                              EmployeePosition expectedPosition, List<Employee> expectedManagedEmployee) {

        for (Employee emp : employees) {
            if (emp.getName().equals(checkFor.getName())) {
                if (expectedManagedEmployee == null) {
                    assertTrue(emp.getListOfManagedEmp() == null);
                } else {
                    assertEquals(expectedManagedEmployee.size(), emp.getListOfManagedEmp().size());
                    assertTrue(checkFor.getListOfManagedEmp().containsAll(expectedManagedEmployee));
                }
                assertTrue(checkFor.getPosition().equals(expectedPosition));
            }
        }
    }

}