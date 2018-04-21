import java.util.*;
public class Scheduler {
    public void sched(ArrayList<Task> taskSet, ArrayList<Resource> resourceSet, int simulationTime, String scheduler){
        int time = 0;
        Helper h = new Helper();
        h.initGraph(simulationTime,taskSet);
        Task currentTask = new Task(0,0,0,0);
        Stack<Task> tasksWithResources = new Stack<Task>();
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
            if(scheduler.equals("HVDF")) {
                h.sortByDeadline(taskSet, time);
            }
            currentTask = pickTask(taskSet,resourceSet, currentTask, time, h, tasksWithResources);
            if(currentTask == null){
                h.printInfo(time);
                this.checkForDeadlines(taskSet, resourceSet, time,h, tasksWithResources);
                this.updateTaskSet(taskSet,time);
                continue;
            }
            h.printInfo(time,currentTask.getID());
            this.updateCurrentTask(currentTask,time,h);
            this.checkForDeadlines(taskSet, resourceSet, time,h, tasksWithResources);
            this.updateTaskSet(taskSet,time);

            //valid for task with a resource value greater than -1
            // if executed time of the current task == 1/4th of eT then critical section = true
            if(currentTask.getResource()>-1 && currentTask.getExecutedTime()==(currentTask.getExecutionTime()/4)){
                currentTask.setCriticalSection(true);
            }
            // if executed time of the current task == 3/4th of eT then critical section = false
            if(currentTask.getCriticalSection() && currentTask.getExecutedTime()==(3*currentTask.getExecutionTime()/4)){
                currentTask.setCriticalSection(false);
                h.getResource(resourceSet, currentTask.getResource()).setIsLocked(false);
                tasksWithResources.pop();

                if(tasksWithResources.empty()){
                    config.cs_star = Integer.MAX_VALUE;
                }
                else{
                    config.cs_star = h.getResource(resourceSet, tasksWithResources.peek()
                            .getResource()).getPriorityCeiling();
                }
            }
        }
        System.out.println();
        h.printDetails(taskSet);
        h.displayGraph(taskSet);
    }

    public Task pickTask(ArrayList<Task> taskSet, ArrayList<Resource> resourceSet, Task prevTask, int time, Helper h,
                         Stack<Task> tasksWithResources){
        for(Task t:taskSet){
            if(t.getFlag()){

                //CHECK IF IT IS IN CS
                if(t.getCriticalSection()){
                    if(t.getPriority()<config.cs_star && !h.getResource(resourceSet, t.getResource()).getIsLocked()){
                        h.getResource(resourceSet, t.getResource()).setIsLocked(true);
                        tasksWithResources.push(t);
                        config.cs_star = h.getResource(resourceSet, t.getResource()).getPriorityCeiling();
                        return t;
                    }
                    else{
                        return tasksWithResources.peek();
                    }
                }

                if(prevTask!=null && prevTask.getFlag() && prevTask.getID()!=0 && prevTask != t &&
                        prevTask.getTimePeriod() != (prevTask.getTimePeriod2()+time - 1)){
                    h.taskPreempted(time,prevTask.getID(),t.getID());
                    prevTask.incrementNumberOfTimesPreempted();
                }
                return t;
            }
        }
        return null;
    }

    public void updateCurrentTask(Task t, int time, Helper h){
        t.setExecutedTime(t.getExecutedTime()+config.increaseTime);
        //GRAPH
        t.graph[time-1] = config.displayChar;
        if(t.getExecutedTime() == t.getExecutionTime()){
            t.setExecutedTime(0);
            t.setFlag(false);
            h.completedExecution(t.getID());
        }
    }

    public void checkForDeadlines(ArrayList<Task> taskSet, ArrayList<Resource> resourceSet, int time, Helper h, Stack<Task> tasksWithResources){
        for(Task t:taskSet){
            if(t.getFlag() && t.getDeadline() == time){
                h.missedDeadline(time,t.getID());
                t.incrementNumberOfDeadlinesMissed();
                t.setExecutedTime(0);
                t.setFlag(false);
                t.setCriticalSection(false);
                if(!tasksWithResources.empty() && t.equals(tasksWithResources.peek())){
                    h.getResource(resourceSet, t.getResource()).setIsLocked(false);
                    tasksWithResources.pop();
                    if(tasksWithResources.empty()){
                        config.cs_star = Integer.MAX_VALUE;
                    }
                    else{
                        config.cs_star = h.getResource(resourceSet, tasksWithResources.peek()
                                .getResource()).getPriorityCeiling();
                    }
                }
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
                //t.setCriticalSection(false);
            }
        }
    }

}