import java.util.ArrayList;

class Task{
  int ID, time, staff;
  String name;
  int earliestStart, latestFinish;
  ArrayList<Task> outEdges = new ArrayList<Task>();
  ArrayList<Task> invertedEdges = new ArrayList<Task>();
  int status = 0; //Used by isRealizable() and topologicalSorting()

  public Task(int id, int time, int staff, String name){
    this.ID = id;
    this.time = time;
    this.staff = staff;
    this.name = name;
    this.latestFinish = 9999; //set to """""""infinite"""""""" NB! Switched from latestStart to latestFinish!
  }

  public void addEdge(Task t){
    t.outEdges.add(this);
    invertedEdges.add(t);
  }

  public boolean isCritical(){
    if(latestFinish == (earliestStart+time)){
      return true;
    }
    return false;
  }
  
  public String toString(){
    return name;
  }
}
