import java.util.*;
import java.io.*;
public class Run {
    public static void main(String args[]){
        //Provide the path of the file. If the file is not in the main directory then specify absolute path
        String fileName = "input.txt";

        //Provide the path of the resource file. If the file is not in the main directory then specify absolute path
        String resourceFileName = "input2.txt";

        //Specify the scheduler. Choose between PCP, ICPP and HVDF
        String selectedScheduler = "PCP";
        //String selectedScheduler = "ICPP";
        //String selectedScheduler = "HVDF";

        //outputToAFile(); //UNCOMMENT THE LINE TO SAVE THE OUTPUT TO A FILE "output.txt" and not output to the console

        Helper help = new Helper();
        Scheduler sch = new Scheduler();

        //Obtain the tasks from the file to make a set of tasks
        ArrayList<Task> taskSet = help.readFromFile("..\\"+fileName);
        help.printTaskSet(taskSet);

        System.out.println("\nAfter Sorting - Based on RM:");
        help.sortByTimePeriod(taskSet);
        help.printTaskSet(taskSet);

        //Obtain the tasks from the file to make a set of tasks
        ArrayList<Resource> resourceSet = help.readResourcesFromFile("..\\"+resourceFileName, taskSet);
        help.calculatePriorityCeiling(taskSet, resourceSet);
        help.printResourceSet(resourceSet);

        System.out.println("\nRunning "+selectedScheduler+" Scheduling on the input data");
        sch.sched(taskSet,resourceSet,config.simulationTime,selectedScheduler);
    }

    public static void outputToAFile(){
        try{
            PrintStream out = new PrintStream(new FileOutputStream("output.txt"));
            System.setOut(out);
        }
        catch(FileNotFoundException fnfe){
            System.err.println("FileNotFoundException : "+fnfe.getMessage());
        }
    }
}