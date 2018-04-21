import java.util.*;
import java.io.*;
public class Run {
    public static void main(String args[]){
        //Provide the path of the file. If the file is not in the main directory then specify absolute path
        String fileName = "input.txt";

        //Provide the path of the resource file. If the file is not in the main directory then specify absolute path
        String resourceFileName = "input2.tst";

        //Specify the scheduler. Choose between PCP, ICPP and HVDF
        String selectedScheduler = "PCP";
        //String selectedScheduler = "ICPP";
        //String selectedScheduler = "HVDF";

        //outputToAFile(); //UNCOMMENT THE LINE TO SAVE THE OUTPUT TO A FILE "output.txt" and not output to the console

        Helper help = new Helper();
        Scheduler sch = new Scheduler();

        ArrayList<Task> taskSet = help.readFromFile(fileName); //Obtain the tasks from the file to make a set of tasks
        help.printTaskSet(taskSet);

        ArrayList<Resource> resourceSet = help.readResourcesFromFile(fileName); //Obtain the tasks from the file to make a set of tasks
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