import java.util.*;
public class Test {
    public static void main(String args[]){
        Test test = new Test();
        Helper help = new Helper();
        //Get and Print the task set -AND- SORT
        ArrayList<Task> taskSet = test.testGetAndPrintTaskSet(help);

        //Get and Print the resource set
        ArrayList<Resource> resourceSet = test.testGetAndPrintResourceSet(help, taskSet);

    }
    public ArrayList<Task> testGetAndPrintTaskSet(Helper help){
        String fileName = "input.txt";
        ArrayList<Task> taskSet = help.readFromFile("..\\"+fileName, "pcp");
        help.sortByTimePeriod(taskSet);
        help.printTaskSet(taskSet);
        return taskSet;
    }
    public ArrayList<Resource> testGetAndPrintResourceSet(Helper help, ArrayList<Task> taskSet){
        String fileName = "input2.txt";
        ArrayList<Resource> resourceSet = help.readResourcesFromFile("..\\"+fileName, taskSet);
        help.calculatePriorityCeiling(taskSet, resourceSet);
        help.printResourceSet(resourceSet);
        return resourceSet;
    }
}