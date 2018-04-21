public class Task {
    private int id;
    private int deadline;
    private int executed;
    private int resource;
    private int priority;
    private int timePeriod;
    private int timePeriod2;
    private int executionTime;
    private int numberOfTimesPreempted;
    private int numberOfDeadlinesMissed;
    private boolean flag;
    private boolean criticalSection;
    public String[] graph;

    public Task(int label, int et, int period, int dl){
        id            = label;
        executionTime = et;
        timePeriod    = period;
        timePeriod2   = period;
        deadline      = dl;
        executed      = 0;
        flag          = true;
        numberOfDeadlinesMissed = 0;
        numberOfTimesPreempted  = 0;
        resource = -1;
        priority = 0;
        criticalSection = false;
    }

    public void printTask(){
        System.out.println("ID: "+id+" Execution Time: "+executionTime+" Time Period: "
                +timePeriod+" Deadline: "+deadline+" Priority: "+priority);
    }

    public int getID(){
        return id;
    }

    public int getExecutionTime(){
        return executionTime;
    }

    public int getExecutedTime(){
        return executed;
    }

    public int getTimePeriod(){
        return timePeriod;
    }

    public int getTimePeriod2(){
        return timePeriod2;
    }

    public int getDeadline(){
        return deadline;
    }

    public int getNumberOfDeadlinesMissed(){
        return numberOfDeadlinesMissed;
    }

    public int getNumberOfTimesPreempted(){
        return numberOfTimesPreempted;
    }

    public int getPriority(){
        return priority;
    }

    public int getResource(){
        return resource;
    }

    public boolean getFlag(){
        return flag;
    }

    public boolean getCriticalSection(){
        return criticalSection;
    }

    public void setExecutedTime(int num){
        executed = num;
    }

    public void setPriority(int priorityValue){
        priority = priorityValue;
    }

    public void setResource(int resourceValue){
        resource = resourceValue;
    }

    public void setFlag(boolean flagValue){
        flag = flagValue;
    }

    public void setCriticalSection(boolean isInCriticalSection){
        criticalSection = isInCriticalSection;
    }

    public void incrementNumberOfDeadlinesMissed(){
        numberOfDeadlinesMissed++;
    }

    public void incrementNumberOfTimesPreempted(){
        numberOfTimesPreempted++;
    }

    public void updateDeadline(){
        deadline = deadline + timePeriod2;
    }

    public void updateTimePeriod(){
        timePeriod += timePeriod2;
    }

}