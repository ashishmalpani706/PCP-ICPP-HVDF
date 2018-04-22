import java.util.*;
public class Scheduler {
    public void sched(ArrayList<Task> taskSet, ArrayList<Resource> resourceSet, int simulationTime, String scheduler){
        int time = 0;
        Helper h = new Helper();

        Task currentTask = new Task(0,0,0,0);
        Stack<Task> tasksWithResources = new Stack<Task>();
        System.out.println("\nAfter sorting, we have the following tasks with descending priorities");

        if(scheduler.equals("PCP") || scheduler.equals("ICPP")){
            h.sortByTimePeriod(taskSet);
        }

        if(scheduler.equals("HVDF")){
            h.sortByDensity(taskSet,time);
        }

        h.printTaskSet(taskSet);

        //If there are any non-multiples of 4 in the task set, then:
        if(config.containsNonMultipleOfFour){
            config.simulationTime*=4;
            simulationTime = config.simulationTime;
            for(Task t:taskSet){
                t.setDeadline(4*t.getDeadline());
                t.setTimePeriod(4*t.getTimePeriod());
                t.setTimePeriod2(4*t.getTimePeriod2());
                t.setExecutionTime(4*t.getExecutionTime());
            }
        }

        System.out.println();
        h.initGraph(simulationTime,taskSet);

        while(time<simulationTime){
            time++;
            if(scheduler.equals("HVDF")) {
                h.sortByDensity(taskSet, time);
            }
            currentTask = pickTask(taskSet,resourceSet, currentTask, time, h, tasksWithResources, scheduler);
            if(currentTask == null){
                h.printInfo(time);
                this.checkForDeadlines(taskSet, resourceSet, time,h, tasksWithResources, scheduler);
                this.updateTaskSet(taskSet,time);
                continue;
            }
            h.printInfo(taskSet,time,currentTask.getID());
            this.updateCurrentTask(currentTask,time,h);
            this.checkForDeadlines(taskSet, resourceSet, time,h, tasksWithResources, scheduler);
            this.updateTaskSet(taskSet,time);

            //valid for task with a resource value greater than -1
            // if executed time of the current task == 1/4th of eT then critical section = true
            if(currentTask.getResource()>-1 && currentTask.getExecutedTime()==(currentTask.getExecutionTime()/4)){
                currentTask.setCriticalSection(true);

                if(!currentTask.getInheritanceFlag()){
                    if(!tasksWithResources.isEmpty() && !currentTask.equals(tasksWithResources.peek()) &&
                            currentTask.getPriority()>=config.cs_star){
                        System.out.println("Task T"+tasksWithResources.peek().getID()+" has inherited the " +
                                "priority of Task T"+h.getTaskFromPriority(taskSet,config.cs_star).getID()+" which is "
                                +config.cs_star);
                        currentTask.setInheritanceFlag(true);
                    }

                }

            }
            // if executed time of the current task == 3/4th of eT then critical section = false
            if(currentTask.getCriticalSection() && currentTask.getExecutedTime()==(3*currentTask.getExecutionTime()/4)){
                currentTask.setCriticalSection(false);
                h.getResource(resourceSet, currentTask.getResource()).setIsLocked(false);
                tasksWithResources.peek().setInheritanceFlag(false);
                tasksWithResources.pop();
                System.out.println("Resource R"+currentTask.getResource()+" released by Task T"+currentTask.getID());

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
                         Stack<Task> tasksWithResources, String scheduler){
        Task temp = new Task(0,0,0,0);
        for(Task t:taskSet){
            if(t.getFlag()){

                temp = t;

                //CHECK IF IT IS IN CS
                if(t.getCriticalSection()){
                    if(t.getPriority()<config.cs_star && !h.getResource(resourceSet, t.getResource()).getIsLocked()){
                        h.getResource(resourceSet, t.getResource()).setIsLocked(true);
                        tasksWithResources.push(t);
                        System.out.println("Resource R"+t.getResource()+" acquired by Task T"+t.getID());
                        config.cs_star = h.getResource(resourceSet, t.getResource()).getPriorityCeiling();
                    }
                    else{
                        temp =  tasksWithResources.peek();
                    }
                }

                if(scheduler.equals("ICPP")) {
                    if (!t.getCriticalSection() && t.getPriority() >= config.cs_star) {
                        temp = tasksWithResources.peek();
                    }
                }

                if(prevTask!=null && prevTask.getFlag() && prevTask.getID()!=0 && prevTask != temp &&
                        prevTask.getTimePeriod() != (prevTask.getTimePeriod2()+time - 1)){
                    h.taskPreempted(time,prevTask.getID(),temp.getID());
                    prevTask.incrementNumberOfTimesPreempted();
                }

                return temp;
            }
        }
        return null;
    }

    public void updateCurrentTask(Task t, int time, Helper h){
        t.setExecutedTime(t.getExecutedTime()+config.increaseTime);
        //GRAPH
        if(!t.getCriticalSection()){
            t.graph[time-1] = config.displayChar;
        }
        else{
            t.graph[time-1] = config.displayResourceChar;
        }
        if(t.getExecutedTime() == t.getExecutionTime()){
            t.setExecutedTime(0);
            t.setFlag(false);
            h.completedExecution(t.getID());
        }
    }

    public void checkForDeadlines(ArrayList<Task> taskSet, ArrayList<Resource> resourceSet, int time, Helper h,
                                  Stack<Task> tasksWithResources, String scheduler){
        for(Task t:taskSet){
            boolean willMissDeadline_HVDF = false;

            if(t.getFlag() && scheduler.equals("HVDF")){
                int remainingTime = t.getDeadline() - time;
                int remainingComputationTime = t.getExecutionTime() - t.getExecutedTime();
                if(remainingTime<remainingComputationTime){
                    willMissDeadline_HVDF = true;
                    System.out.println("Dropping Task T"+t.getID()+" due to insufficient time available for completion");
                }
            }

            if(t.getFlag() && (t.getDeadline() == time || willMissDeadline_HVDF)){
                h.missedDeadline(time,t.getID());
                t.incrementNumberOfDeadlinesMissed();
                t.setExecutedTime(0);
                t.setFlag(false);
                t.setCriticalSection(false);
                if(!tasksWithResources.empty() && t.equals(tasksWithResources.peek())){
                    h.getResource(resourceSet, t.getResource()).setIsLocked(false);
                    tasksWithResources.peek().setInheritanceFlag(false);
                    tasksWithResources.pop();
                    System.out.println("Resource R"+t.getResource()+" released by Task T"+t.getID());
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