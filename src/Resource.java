public class Resource {
    private int id;
    private int priorityCeiling;
    private boolean isLocked;

    public int getId(){
        return id;
    }

    public int getPriorityCeiling(){
        return priorityCeiling;
    }

    public boolean getIsLocked(){
        return isLocked;
    }

    public void setId(int idValue){
        id = idValue;
    }

    public void setPriorityCeiling(int priorityCeilingValue){
        priorityCeiling = priorityCeilingValue;
    }

    public void setIsLocked(boolean isLockedValue){
        isLocked = isLockedValue;
    }

    public void printResource(){
        System.out.println("ID: "+id+" Priority Ceiling: "+priorityCeiling);
    }
}
