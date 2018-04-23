import java.util.*;
import java.io.*;
public class Run {
    public static void main(String args[]) throws IOException {
        //Provide the path of the file. If the file is not in the main directory then specify absolute path
        String fileName = "input.txt";

        //Provide the path of the resource file. If the file is not in the main directory then specify absolute path
        String resourceFileName = "input2.txt";

        //Provide the path for HVDF scheduler. If the file is not in the main directory then specify absolute path
        String hvdfFileName = "input3.txt";

        //Specify the scheduler. Choose between PCP, ICPP and HVDF
        //String selectedScheduler = "PCP";
        //String selectedScheduler = "ICPP";
        //String selectedScheduler = "HVDF";

        String selectedScheduler = args[0].toUpperCase();

        outputToAFile("..\\output.txt"); //UNCOMMENT THE LINE TO SAVE THE OUTPUT TO A FILE "output.txt" in root directory and
        // not output to the console

        Helper help = new Helper();
        Scheduler sch = new Scheduler();

        if(!selectedScheduler.equals("HVDF")) {
            //Obtain the tasks from the file to make a set of tasks
            ArrayList<Task> taskSet = help.readFromFile("..\\" + fileName, selectedScheduler);
            help.printTaskSet(taskSet);

            //Obtain the tasks from the file to make a set of tasks
            ArrayList<Resource> resourceSet = help.readResourcesFromFile("..\\" + resourceFileName, taskSet);
            help.sortByTimePeriod(taskSet);
            help.calculatePriorityCeiling(taskSet, resourceSet);
            System.out.println();
            help.printResourceSet(resourceSet);

            System.out.println("\nRunning "+selectedScheduler+" Scheduling on the input data");
            sch.sched(taskSet,resourceSet,config.simulationTime,selectedScheduler);
        }
        else{
            ArrayList<Task> hvdfTaskSet = help.readFromFile("..\\" + hvdfFileName, selectedScheduler);

            System.out.println("\nRunning "+selectedScheduler+" Scheduling on the input data");
            sch.sched(hvdfTaskSet ,null,config.simulationTime,selectedScheduler);
        }
        printLegend();



        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));

        Files.printToConsoleFromFile(selectedScheduler);

    }

    public static void outputToAFile(String file){
        try{
            PrintStream out = new PrintStream(new FileOutputStream(file));
            System.setOut(out);
        }
        catch(FileNotFoundException fnfe){
            System.err.println("FileNotFoundException : "+fnfe.getMessage());
        }
    }

    public static void printLegend(){
        System.out.println("\nTask = "+config.displayChar+" -AND- Resource = "+config.displayResourceChar);
        if(config.containsNonMultipleOfFour){
            System.out.println("Each unit time = 0.25\n");
        }
        else{
            System.out.println("Each unit time = 1\n");
        }
    }
}