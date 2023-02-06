package Supplementary;

import java.util.List;

public class Epic extends Task {

    private List<Integer> relatedSubtaskIds;


    public Epic(String name, String description, TaskStatus status, Integer ID, Integer epicID, List<Integer> relatedSubtaskIds) {
        super(name, description, status, ID, epicID);
        this.relatedSubtaskIds = relatedSubtaskIds;
    }

    public List<Integer> getRelatedSubtaskIds() {
        return relatedSubtaskIds;
    }

    public void addRelatedSubtaskIds(int id){
        relatedSubtaskIds.add(id);
    }

    public void removeRelatedSubtaskIds(){
        relatedSubtaskIds.clear();
    }
    public void removeRelatedSubtaskIds(int id){
        relatedSubtaskIds.remove(id);
    }

    public void setRelatedSubtaskIds(List<Integer> relatedSubtaskIds) {
        this.relatedSubtaskIds = relatedSubtaskIds;
    }

    @Override
    public void setStatus(TaskStatus status) {
        super.setStatus(status);
    }

    @Override
    public TaskType getType() {
        return TaskType.EPIC;
    }

    @Override
    public String toString() {
        return "Epic{" +
                "name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status=" + getStatus() +
                ", ID=" + getId() +
                ", type='" + getType() + '\'' +
                ", relatedSubTaskIds'" + getRelatedSubtaskIds() + '\'' +
                '}';
    }
}
