/**
 *  Class : TabularStructure
 *  Description : This class is used to store the data for display in tabular form.
 *
 * */
package model;

/**
 * TabularStructure to store the employee hierarchy in the representable form.
 */
public class TabularStructure {
    String ceo;
    String manager;
    String engineer;
    static int maxWidth;

    public TabularStructure(String ceo, String manager, String engineer) {
        this.ceo = ceo;
        this.manager = manager;
        this.engineer = engineer;
    }

    public String getCeo() {
        return ceo;
    }

    public void setCeo(String ceo) {
        this.ceo = ceo;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getEngineer() {
        return engineer;
    }

    public void setEngineer(String engineer) {
        this.engineer = engineer;
    }

    public static int getMaxWidth() {
        return maxWidth;
    }

    public static void setMaxWidth(int maxWidth) {
        TabularStructure.maxWidth = maxWidth;
    }

}
