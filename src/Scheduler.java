import java.util.*;
public class Scheduler {
    public void sched(ArrayList<Task> taskSet, ArrayList<Resource> resourceSet, int simulationTime, String scheduler){
        int time = 0;
        Helper h = new Helper();
        h.initGraph(simulationTime,taskSet);
        Task currentTask = new Task(0,0,0,0,0);
        System.out.println("\nAfter sorting, we have the following tasks with descending priorities");

        if(scheduler.equals("PCP") || scheduler.equals("ICPP")){
            h.sortByTimePeriod(taskSet);
        }

        //----------------------------------------------CHECK
        if(scheduler.equals("HVDF")){
            h.sortByDeadline(taskSet,time);
        }

        h.printTaskSet(taskSet);
        System.out.println();

        while(time<simulationTime){
            time++;
            if(scheduler.equals("EDF")){
                h.sortByDeadline(taskSet,time);
            }
            currentTask = pickTask(taskSet,currentTask, time, h);
            if(currentTask == null){
                h.printInfo(time);
                this.checkForDeadlines(taskSet,time,h);
                this.updateTaskSet(taskSet,time);
                continue;
            }
            h.printInfo(time,currentTask.getID());
            this.updateCurrentTask(currentTask,time,h);
            this.checkForDeadlines(taskSet,time,h);
            this.updateTaskSet(taskSet,time);
        }
        System.out.println();
        h.printDetails(taskSet);
        h.displayGraph(taskSet);
    }

    public Task pickTask(ArrayList<Task> taskSet, Task prevTask, int time, Helper h){
        for(Task t:taskSet){
            if(t.getFlag()){
                if(prevTask!=null && prevTask.getFlag() && prevTask.getID()!=0 && prevTask != t && prevTask.getTimePeriod() != (prevTask.getTimePeriod2()+time - 1)){
                    h.taskPreempted(time,prevTask.getID(),t.getID());
                    prevTask.incrementNumberOfTimesPreempted();
                }
                return t;
            }
        }
        return null;
    }

    public void updateCurrentTask(Task t, int time, Helper h){
        t.setExecutedTime(t.getExecutedTime()+1);
        //GRAPH
        t.graph[time-1] = config.displayChar;
        if(t.getExecutedTime() == t.getExecutionTime()){
            t.setExecutedTime(0);
            t.setFlag(false);
            h.completedExecution(t.getID());
        }
    }

    public void checkForDeadlines(ArrayList<Task> taskSet, int time, Helper h){
        for(Task t:taskSet){
            if(t.getFlag() && t.getDeadline() == time){
                h.missedDeadline(time,t.getID());
                t.incrementNumberOfDeadlinesMissed();
                t.setExecutedTime(0);
                t.setFlag(false);
            }
        }
    }

    public void updateTaskSet(ArrayList<Task> taskSet,int time){
        for(Task t:taskSet){
            if(time == t.getTimePeriod()){
                t.updateDeadline();
                t.updateTimePeriod();
                t.setFlag(true);
                t.setExecutedTime(0);
            }
        }
    }
}