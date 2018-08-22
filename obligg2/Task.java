import java.util.ArrayList;

class Task {
  int id , time , staff ;
  String name;
  int earliestStart , latestStart, slack, latestfinish = 0;
  ArrayList<Task> outEdges = new ArrayList<Task>();
  ArrayList<Task> inEdges = new ArrayList<Task>();
  int cntPredecessors[];
  int avstand;
  boolean visited = false;
  int status = 0;

  public Task (String[] arr) {
    this.id = Integer.parseInt(arr[0]);
    this.name = arr[1];
    this.time = Integer.parseInt(arr[2]);
    this.staff = Integer.parseInt(arr[3]);
    cntPredecessors = new int[arr.length - 5];
    for (int i = 4; i < arr.length - 1 ; i++) {
      cntPredecessors[i - 4] = Integer.parseInt(arr[i]);
    }
  }

  public int getStaff() {
    return staff;
  }

  public String toString() {
    return (id + " " + name + " " + time + " " + staff);
  }

  public int[] getPredecessors() {
    return cntPredecessors;
  }

  public int doTask() {
    return time;
  }

  public int getEarlieststart() {
    return earliestStart;
  }

  public void printEdges() {
    System.out.println("\nEdges in for " + id);
    for (Task  t: inEdges) {
      System.out.println(t);
    }
    System.out.println("Edges out for " + id);
    for (Task  t: outEdges) {
      System.out.println(t);
    }
  }

  public void visit() {
    visited = true;
  }

  public void reset() {
    visited = false;
  }

  public void setOutEgde(Task task) {
    outEdges.add(task);
  }

  public void setInEdge(Task task) {
    inEdges.add(task);
  }

  public ArrayList<Task> getOutEgde() {
    return outEdges;
  }

  public ArrayList<Task> getinEgde() {
    return inEdges;
  }

  public void slack() {
    slack = latestfinish - time - earliestStart;
  }

  public void printEarliest() {
    System.out.println(earliestStart);
  }
}
