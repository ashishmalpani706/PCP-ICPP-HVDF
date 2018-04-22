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
    private double density;
    private boolean flag;
    private boolean criticalSection;
    private boolean inheritanceFlag;
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
        inheritanceFlag = false;
        density = 0.0;
    }

    public Task(int label, int et, int period, int dl, double valueFromFile){
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
        inheritanceFlag = false;
        density = valueFromFile;
    }

    public void printTask(){
        if(density==0.0){
            System.out.println("ID: "+id+" Execution Time: "+executionTime+" Time Period: "
                    +timePeriod+" Deadline: "+deadline+" Priority: "+priority);
        }
        else{
            System.out.println("ID: "+id+" Execution Time: "+executionTime+" Time Period: "
                    +timePeriod+" Deadline: "+deadline+" Priority: "+priority+" Density: "+density);
        }
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

    public double getDensity(){
        return density;
    }

    public boolean getFlag(){
        return flag;
    }

    public boolean getCriticalSection(){
        return criticalSection;
    }

    public boolean getInheritanceFlag(){
        return inheritanceFlag;
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

    public void setDeadline(int newDeadline){
        deadline = newDeadline;
    }

    public void setTimePeriod(int newTimePeriod){
        timePeriod = newTimePeriod;
    }

    public void setTimePeriod2(int newTimePeriod2){
        timePeriod2 = newTimePeriod2;
    }

    public void setExecutionTime(int newExecutionTime){
        executionTime = newExecutionTime;
    }

    public void setFlag(boolean flagValue){
        flag = flagValue;
    }

    public void setCriticalSection(boolean isInCriticalSection){
        criticalSection = isInCriticalSection;
    }

    public void setInheritanceFlag(boolean newInheritanceFlag){
        inheritanceFlag = newInheritanceFlag;
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