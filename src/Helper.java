import java.util.*;
import java.io.*;
public class Helper {
    public ArrayList<Task> readFromFile(String fileName){
        int n;
        String[] token;
        ArrayList<Task> taskSet = new ArrayList<Task>();
        try{
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            n = Integer.parseInt(bufferedReader.readLine());
            for(int i=0;i<n;i++){
                token = bufferedReader.readLine().split(" ");
                if(token.length == 4){
                    taskSet.add(new Task(ipi(token[0]),ipi(token[1]),ipi(token[2]),ipi(token[3])));
                }
                else{
                    taskSet.add(new Task(ipi(token[0]),ipi(token[1]),ipi(token[2]),ipi(token[3]),ipi(token[4])));
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

    public ArrayList<Resource> readResourcesFromFile(String fileName){
        int n;
        String[] token;
        ArrayList<Resource> resourceSet = new ArrayList<Resource>();
        try{
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            bufferedReader.readLine();
            n = Integer.parseInt(bufferedReader.readLine());
            for(int i=0;i<n;i++){
                token = bufferedReader.readLine().split(" ");

            }
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

    public void sortByTimePeriod(ArrayList<Task> taskSet){
        Collections.sort(taskSet, new Comparator<Task>(){
            @Override
            public int compare(Task t1, Task t2){
                return t1.getTimePeriod() - (t2.getTimePeriod());
            }
        });
    }

    public void printInfo(int time, int label){
        System.out.println("Time = "+time+" - ID = T"+label);
    }

    public void printInfo(int time){
        System.out.println("Time = "+time+" - IDLE");
    }

    public void missedDeadline(int time, int label){
        System.out.println("Time = "+time+" - Deadline Missed for ID = T"+label);
    }

    public void taskPreempted(int time, int label1, int label2){
        //System.out.println("Time = "+(time-1)+" - Task T"+label1+" preempted by Task T"+label2);
        System.out.println("Time = "+(time-1)+" - Task T"+label1+" preempted by Task T"+label2);

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
}