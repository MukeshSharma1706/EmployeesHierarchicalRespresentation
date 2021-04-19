import handler.HierarchyRepresentationalHandler;
import model.Employee;
import model.EmployeePosition;
import model.TabularStructure;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        HierarchyRepresentationalHandler representationalHandler = new HierarchyRepresentationalHandler();

        List<Employee> listOfemployees = new ArrayList<Employee>();
        listOfemployees.add(new Employee("Alan",100,150));
        listOfemployees.add(new Employee("Martin",220,100));
        listOfemployees.add(new Employee("Jamie",150,null));
        listOfemployees.add(new Employee("Alex",275,100));
        listOfemployees.add(new Employee("Steve",400,150));
        listOfemployees.add(new Employee("David",190,400));

//        listOfemployees.add(new Employee("Murphy",230,150));
//        listOfemployees.add(new Employee("Owen",231,230));
//        listOfemployees.add(new Employee("Justin",151,null));
//        listOfemployees.add(new Employee("Ges",670,151));
//        listOfemployees.add(new Employee("June",671,151));
//        listOfemployees.add(new Employee("Max",673,151));
//        listOfemployees.add(new Employee("Arman",675,671));
//        listOfemployees.add(new Employee("Jo",676,671));
//        listOfemployees.add(new Employee("Jen",951,81));      //Manager who is not employee will not print
//        listOfemployees.add(new Employee("Brenton",941,null)); // CEO with no manager under will print

        representationalHandler.updateEmployeeInfo(listOfemployees);
        boolean validInput  = representationalHandler.validateInput(listOfemployees);

        if(validInput) {

            List<TabularStructure> tabularStructures = representationalHandler.representData(listOfemployees);

            int nameWidth = TabularStructure.getMaxWidth();
            String hierarchyFormat = "|%" + nameWidth + "s" + "|%" + nameWidth + "s" + "|%" + nameWidth + "s|";
            for (TabularStructure ts : tabularStructures) {
                System.out.format(hierarchyFormat, ts.getCeo(), ts.getManager(), ts.getEngineer());
                System.out.println();
            }
        } else {
            System.out.println("Not valid input");
        }

    }
}
