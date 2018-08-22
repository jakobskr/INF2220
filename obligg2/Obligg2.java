import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

class Obligg2 {
int antallTasks = 0;
ArrayList<Task> tasks = new ArrayList<Task>();
ArrayList<Task> uncompletedTasks = new ArrayList<Task>();
ArrayList<Task> currentTasks = new ArrayList<Task>();

Task startTask,sluttTask;
Koe<Task> vei = new Koe<Task>();


  public void lesFraFil(String filnavn) throws Exception {
    File fil = new File(filnavn);
    Scanner filscan = new Scanner(fil);
    String temp = "";
    antallTasks = filscan.nextInt();
    filscan.nextLine();
    filscan.nextLine();
    while(filscan.hasNextLine()) {
      temp = filscan.nextLine();
      tasks.add(new Task(temp.split("\\s+")));
    }
    setEdges();
    //finner start noden
    for (Task t: tasks) {
      if (t.getinEgde().size() == 0 && !t.visited) {
        startTask = t;
      }
    }
  }

  //metode for aa sette kantene, kalles foer hver sortering
  public void setEdges() {
    int[] temparr;
    for (Task t: tasks) {
      t.getOutEgde().clear();
      t.getinEgde().clear();
    }
    for (Task t : tasks) {
      temparr = t.getPredecessors();
      for (int i: temparr) {
        t.inEdges.add(tasks.get(i - 1));
        //t.setInEdge(tasks.get(i - 1));
        tasks.get(i - 1).setOutEgde(t);
      }
    }
  }

  public void print() {
    for (Task t : tasks) {
    System.out.printf("\nID: %d Name: %s Time: %d MP: %d EST: %d LST %d Slack %d",
    t.id,t.name,t.time,t.staff,t.earliestStart,(t.latestfinish - t.time),t.slack);
    }
    System.out.println();
  }

  public void realizability2() {
    for (Task t: tasks) {
      t.status = 0;
    }
    realizability2(startTask, new ArrayList<Task>());
    System.out.println("The project is realizable!");
    for (Task t: tasks) {
      t.status = 0;
    }
  }

  //Shold only print the actual loop now
  public void realizability2(Task t, ArrayList<Task> log) {
    log.add(t);
    if(t.status ==  1){
      System.out.println("Project not realizable due to a loop in tasks. Printing the loop");
      for(int i = 0; i < log.size(); i++){
        if(log.get(i).id == t.id){
          for(int j = i; j < log.size() - 1; j++){
            System.out.print(log.get(j) +  " -> ");
          }
          System.out.print(t);
          System.exit(0);
        }
      }
    }
    else if(t.outEdges.size() == 0){
      t.status = 2;
      log.remove(t);
    }
    else if(t.status  ==  0){
      t.status = 1;
      for(int i = 0; i < t.outEdges.size(); i++){
        realizability2(t.outEdges.get(i), log);
      }
      t.status = 2;
      log.remove(t);
    }
  }

  public void simulateProjecFastest() {
    int teller = 0, manpower = 0;

    String utprint;
    boolean utprintEndret;
    ArrayList<Task> tempL = new ArrayList<Task>();
    for (Task t: tasks) {
      uncompletedTasks.add(t);
    }
    while(!currentTasks.isEmpty() || !uncompletedTasks.isEmpty()) {
      tempL.clear();
      utprint = "\ntime: " + teller;
      utprintEndret = false;

      for (Task t: currentTasks) {
        if (t.getEarlieststart() + t.time == teller) {
          tempL.add(t);
          utprint += ("\nFinished Task " + t.id );
          utprintEndret = true;
        }
      }
      //for aa unngaa concurrentmodification
      for (Task t: tempL) {
        currentTasks.remove(t);
      }
      manpower = 0;
      tempL.clear();
      for (Task t: uncompletedTasks) {
        if (teller == t.getEarlieststart()) {
          currentTasks.add(t);
          tempL.add(t);
          utprint += ("\nStarting Task " + t.id );
          utprintEndret = true;
        }
      }
      //for aa unngaa concurrentmodification
      for (Task t: tempL) {
        uncompletedTasks.remove(t);
      }
      if (utprintEndret) {
        System.out.println(utprint);
        for (Task t: currentTasks) {
          manpower += t.staff;
        }
        System.out.println("Current workers " + manpower);
      }
      teller++;
    }
    System.out.println("Shortest time to complete the task is " + (teller-1));
  }

  void setEarliestTopo() {
    Koe<Task> k = new Koe<Task>();
    for (Task t: tasks) {
      t.status = t.inEdges.size();
      t.earliestStart = 0;
      if (t.status == 0) {
        k.settInn(t);
      }
    }
    while(!k.erTom()) {
      Task v = k.fjern();

      for (Task w: v.outEdges ) {
        if (v.earliestStart + v.time > w.earliestStart) {
          w.earliestStart = v.earliestStart + v.time;
        }
        w.status--;
        if (w.status == 0) {
          k.settInn(w);
        }
      }
    }
  }

  public void latestTask() {
    sluttTask = startTask;
    for (Task t: tasks) {
      if (sluttTask.earliestStart + sluttTask.time < t.earliestStart + t.time) {
        sluttTask = t;
      }
    }
    sluttTask.latestfinish = sluttTask.earliestStart + sluttTask.time;
  }

  public void setSlack() {
    for (Task t: tasks) {
      t.slack();
    }
    for (Task t: tasks) {
    }
  }

  public void latestStart() {
    latestTask();
    latestStart(sluttTask);
  }

  public void latestStart(Task s) {
    Koe<Task> koe = new Koe<Task>();
    Task v;
    for (Task t: tasks) {
      t.latestfinish = s.latestfinish;
      t.status = t.outEdges.size();
      if (t.outEdges.size() == 0) {
        koe.settInn(t);
      }
    }
    while (!koe.erTom()) {
      v = koe.fjern();
      for (Task t: v.inEdges) {
        if (v.latestfinish - v.time < t.latestfinish) {
          t.latestfinish = v.latestfinish - v.time;
        }
        t.status--;
        if (t.status == 0) {
          koe.settInn(t);
        }
      }
    }
  }

  public void printE() {
    for (Task t: vei) {
      System.out.print(t.id + " ");
      t.printEarliest();
      System.out.println();
    }
  }


  public static void main(String[] args) throws Exception{
    Obligg2 test = new Obligg2();
    if (args.length == 0) {
      System.out.println("Wrong usage of program\n correct usage: java Obligg2 <filename>.txt");
      System.exit(0);
    }
    test.lesFraFil(args[0]);
    System.out.println("\n" + args[0]);
    test.realizability2();
    test.setEarliestTopo();
    test.latestStart();
    test.setSlack();
    test.print();
    test.simulateProjecFastest();
  }
}
