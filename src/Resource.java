public class Resource {
    private int id;
    private int priorityCeiling;
    private boolean isLocked;

    public Resource(int idValue){
        id = idValue;
        isLocked = false;
    }

    public int getID(){
        return id;
    }

    public int getPriorityCeiling(){
        return priorityCeiling;
    }

    public boolean getIsLocked(){
        return isLocked;
    }

    public void setPriorityCeiling(int priorityCeilingValue){
        priorityCeiling = priorityCeilingValue;
    }

    public void setIsLocked(boolean isLockedValue){
        isLocked = isLockedValue;
    }

    public void printResource(){
        System.out.println("ResourceID: "+id+" Priority Ceiling: "+priorityCeiling);
    }
}
