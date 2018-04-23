import java.util.*;
import java.io.*;
public class Helper {
    public ArrayList<Task> readFromFile(String fileName, String scheduler){
        int n;
        String[] token;
        ArrayList<Task> taskSet = new ArrayList<Task>();
        try{
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            n = Integer.parseInt(bufferedReader.readLine());
            for(int i=0;i<n;i++){
                token = bufferedReader.readLine().split(" ");
                if(!scheduler.equals("HVDF")) {
                    checkIfContainsNonMultipleOfFour(ipi(token[1]), ipi(token[2]), ipi(token[3]));
                    taskSet.add(new Task(ipi(token[0]), ipi(token[1]), ipi(token[2]), ipi(token[3])));
                }
                else{
                    taskSet.add(new Task(ipi(token[0]), ipi(token[1]), ipi(token[2]), ipi(token[3]), (ipi(token[4])/ipi(token[1]))));
                }
            }
            config.simulationTime = ipi(bufferedReader.readLine().split(" ")[1]);
            bufferedReader.close();
        }
        catch(FileNotFoundException fnfe){
            System.err.println("FileNotFoundException : "+fnfe.getMessage());
        }
        catch(IOException ioe){
            System.err.println("IOException : "+ioe.getMessage());
        }
        return taskSet;
    }

    public ArrayList<Resource> readResourcesFromFile(String fileName, ArrayList<Task> taskSet){
        int n;
        int numberOfTasksPerResource;
        String[] token;
        ArrayList<Resource> resourceSet = new ArrayList<Resource>();
        try{
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            bufferedReader.readLine();
            n = ipi(bufferedReader.readLine());
            for(int i=0;i<n;i++){
                token = bufferedReader.readLine().split(" ");
                resourceSet.add(new Resource(ipi(token[0])));
                numberOfTasksPerResource = ipi(token[1]);

                for(int j=0;j<numberOfTasksPerResource;j++){
                    Task t = getTask(taskSet,ipi(token[j+2]));
                    t.setResource(ipi(token[0]));
                }
            }
            bufferedReader.close();
        }
        catch(FileNotFoundException fnfe){
            System.err.println("FileNotFoundException : "+fnfe.getMessage());
        }
        catch(IOException ioe){
            System.err.println("IOException : "+ioe.getMessage());
        }
        return resourceSet;
    }

    public int ipi(String str){
        return Integer.parseInt(str);
    }

    public void printTaskSet(ArrayList<Task> taskSet){
        for(Task t:taskSet){
            t.printTask();
        }
    }

    public void printResourceSet(ArrayList<Resource> resourceSet){
        for(Resource r:resourceSet){
            r.printResource();
        }
    }

    public void sortByDeadline(ArrayList<Task> taskSet, int time){
        Collections.sort(taskSet, new Comparator<Task>(){
            @Override
            public int compare(Task t1, Task t2){
                return (t1.getDeadline()-time) - (t2.getDeadline()-time);
            }
        });
    }

    public void sortByDensity(ArrayList<Task> taskSet, int time){
        Collections.sort(taskSet, new Comparator<Task>(){
            @Override
            public int compare(Task t1, Task t2){
                double x = t1.getDensity() - t2.getDensity();
                if(x>0.0)      return -1;
                else if(x<0.0) return  1;
                else           return (t1.getDeadline()-time) - (t2.getDeadline()-time);
            }
        });

        int i = 1;
        for(Task t:taskSet){
            t.setPriority(i++);
        }
    }

    public void sortByTimePeriod(ArrayList<Task> taskSet){
        Collections.sort(taskSet, new Comparator<Task>(){
            @Override
            public int compare(Task t1, Task t2){
                return t1.getTimePeriod() - (t2.getTimePeriod());
            }
        });

        int i = 1;
        for(Task t:taskSet){
            t.setPriority(i++);
        }
    }

    public void printInfo(ArrayList<Task> taskSet, int time, int label){
        Task t = getTask(taskSet, label);
        if(config.containsNonMultipleOfFour) {
            double newTime = (double) time / 4;
            if (!t.getCriticalSection()) {
                System.out.println("Time = " + String.format("%.2f", newTime) + " - ID = T" + label + " - Priority = P" + t.getPriority());
            }
            else {
                if (t.getInheritanceFlag()) {
                    System.out.println("Time = " + String.format("%.2f", newTime) + " - ID = T" + label + " - Resource ID = R" + t.getResource() + " - Priority = P" + config.cs_star);
                }
                else {
                    System.out.println("Time = " + String.format("%.2f", newTime) + " - ID = T" + label + " - Resource ID = R" + t.getResource() + " - Priority = P" + t.getPriority());
                }
            }
        }
        else{
            if(!t.getCriticalSection()){
                System.out.println("Time = "+time+" - ID = T"+label+" - Priority = P"+t.getPriority());
            }
            else{
                if(t.getInheritanceFlag()) {
                    System.out.println("Time = "+time+" - ID = T"+label+" - Resource ID = R"+t.getResource()+" - Priority = P"+config.cs_star);
                }
                else {
                    System.out.println("Time = "+time+" - ID = T"+label+" - Resource ID = R"+t.getResource()+" - Priority = P"+t.getPriority());
                }
            }
        }
    }

    public void printInfo(int time){
        if(config.containsNonMultipleOfFour){
            double newTime = (double)time/4;
            System.out.println("Time = "+String.format("%.2f",newTime)+" - IDLE");
        }
        else{
            System.out.println("Time = "+time+" - IDLE");
        }
    }

    public void missedDeadline(int time, int label){
        if(config.containsNonMultipleOfFour){
            double newTime = (double)time/4;
            System.out.println("Time = "+String.format("%.2f",newTime)+" - Deadline Missed for ID = T"+label);
        }
        else{
            System.out.println("Time = "+time+" - Deadline Missed for ID = T"+label);
        }
    }

    public void taskPreempted(int time, int label1, int label2){
        if(config.containsNonMultipleOfFour) {
            double newTime = (double) time / 4;
            System.out.println("Time = " + String.format("%.2f",(newTime - 0.25)) + " - Task T" + label1 + " preempted by Task T" + label2);
        }
        else{
            System.out.println("Time = " + (time - 1) + " - Task T" + label1 + " preempted by Task T" + label2);
        }
    }

    public void completedExecution(int label){
        System.out.println("Task T"+label+" has completed execution");
    }

    public void printDetails(ArrayList<Task> taskSet){
        for(int i=0;i<taskSet.size();i++){
            Task t = getTask(taskSet,i+1);
            System.out.println("--Task T"+t.getID()+"--");
            System.out.println("Number of Deadlines Missed: "+t.getNumberOfDeadlinesMissed());
            config.totalDeadlinesMissed += t.getNumberOfDeadlinesMissed();
            System.out.println("Number of Times Preempted : "+t.getNumberOfTimesPreempted());
            config.totalTasksPreempted += t.getNumberOfTimesPreempted();
        }
        System.out.println("\nTotal Number of Deadlines Missed : "+config.totalDeadlinesMissed);
        System.out.println("Total Number of Preemptions : "+config.totalTasksPreempted);
    }

    public void initGraph(int time,ArrayList<Task> taskSet){
        for(Task t: taskSet){
            t.graph = new String[time];
            Arrays.fill(t.graph,"-");
        }
    }

    public void displayGraph(ArrayList<Task> taskSet){
        System.out.println();
        for(int i=0; i<taskSet.size(); i++){
            System.out.print("T"+(i+1)+": ");
            for(String a:getTask(taskSet, i+1).graph){
                System.out.print(a);
            }
            System.out.println();
        }
    }

    public Task getTask(ArrayList<Task> taskSet, int id){
        for(Task t: taskSet){
            if(t.getID() == id){
                return t;
            }
        }
        return null;
    }

    public Task getTaskFromPriority(ArrayList<Task> taskSet, int priority){
        for(Task t: taskSet){
            if(t.getPriority() == priority){
                return t;
            }
        }
        return null;
    }

    public Resource getResource(ArrayList<Resource> resourceSet, int id){
        for(Resource r: resourceSet){
            if(r.getID() == id){
                return r;
            }
        }
        return null;
    }

    public void calculatePriorityCeiling(ArrayList<Task> taskSet, ArrayList<Resource> resourceSet){
        //get task by id and then min of that
        for(Resource r:resourceSet){
            for(Task t:taskSet){
                if(t.getResource()==r.getID()){
                    r.setPriorityCeiling(t.getPriority());
                    break;
                }
            }
        }
    }

    public void checkIfContainsNonMultipleOfFour(int a, int b, int c){
        if(a%4!=0 || b%4!=0 || c%4!=0){
            config.containsNonMultipleOfFour = true;
        }
    }

}