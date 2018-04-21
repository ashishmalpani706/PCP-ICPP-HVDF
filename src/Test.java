import java.util.*;
public class Test {
    public static void main(String args[]){
        Test test = new Test();
        Helper help = new Helper();
        //Get and Print the task set
        //test.testGetAndPrintTaskSet(help);
        //Get and Print the resource set
        test.testGetAndPrintResourceSet(help);
    }
    public void testGetAndPrintTaskSet(Helper h){
        String fileName = "input.txt";
        ArrayList<Task> taskSet = h.readFromFile("..\\"+fileName);
        h.printTaskSet(taskSet);
    }
    public void testGetAndPrintResourceSet(Helper h){
        String fileName = "input2.txt";
        ArrayList<Resource> resourceSet = h.readResourcesFromFile("..\\"+fileName);
        h.printResourceSet(resourceSet);
    }
}