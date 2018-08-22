import java.util.ArrayList;
import java.util.Stack;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.Scanner;
import java.io.File;

class TaskOrg{
  private Task[] taskList;
  private int totalTime;
  private Task start;
  private Task latest;
  private boolean isRealizable = true;
  //Must be put inside the list by the order of their IDs!
  public TaskOrg(String filename){
    readStructure(filename);
    //isRealizable();
    if(isRealizable == false){
      System.exit(0);
    }
    optimalTime();
    //timeline.printTimeline();
  }

  //reads and creates the structure from a file
  private void readStructure(String filename){
    //Since the tasks does not necessarily exist when we read the dependency list of an Task,
    //we cant add all the dependencies to an task outright. Thus we store the dependency ids in an double
    //array list (First layer is the task, second layer is the dependencies), so we can add
    //the correct dependencies to the correct tasks when all the task have been initialized.
    //This requires that the tasks are listed in order.
    ArrayList<ArrayList<Integer>> idList = new ArrayList<ArrayList<Integer>>();
    try{
      Scanner input = new Scanner(new File(filename));
      taskList = new Task[input.nextInt()];
      int counter = 0;
      while(input.hasNext()){

        int id = input.nextInt();
        idList.add(new ArrayList<Integer>());
        String name = input.next();
        int time = input.nextInt();
        int staff = input.nextInt();
        int idEdge = input.nextInt();

        while(idEdge != 0){
          idList.get(counter).add(new Integer(idEdge));
          idEdge = input.nextInt();
        }
        taskList[counter] = new Task(id, time, staff, name);
        counter++;
      }
      for(int i = 0; i < taskList.length; i++){

        for(int j = 0; j < idList.get(i).size(); j++){
          taskList[i].addEdge(taskList[idList.get(i).get(j)-1]);
        }
      }
    }
    catch(Exception e){
      e.printStackTrace();
      return;
    }
  }

  //Includes one or more cycles
  private void isRealizable(){
    /*
    *Here we use the 'status' int in the task object:
    *0: not marked.
    *1: marking.
    *2: marked.
    */
    /*
    *The ArrayList 'log' logs which Task is visited, and in which order.
    *We use this to print out a potential loop.
    */
    ArrayList<Task> log = new ArrayList<Task>();
    //This for-loop clears the status, so previous results don't interfere with this one.
    for(int i = 0; i < taskList.length; i++){
      taskList[i].status  = 0;
    }
    findLoops(start, log);
  }

  void findLoops(Task t, ArrayList<Task> log){
    log.add(t);
    if(t.status ==  1){
      isRealizable = false;
      System.out.println("Opps! The project is unrealizable due to a loop in Tasks: ");
      //The for-loop loops through the visited Task.
      for(int i = 0; i < log.size(); i++){
        //And if it finds a Task corresponding with the Task we are currently visiting, it is a sure
        //indication of a loop starting, and thus we start printing the log from here.
        if(log.get(i).ID == t.ID){
          for(int j = i; j < log.size()-i; j++){
            System.out.print(log.get(j)+"(ID: "+log.get(j).ID+") ->");
          }
          //Since we've not added any Task to the log since we found the loop, we can simply end
          //with adding the task we are currently visiting to the print.
          System.out.print(t+"(ID: "+t.ID+")(end)\n");
          //And since a single loop makes the project unrealizable, we can simply exit.
          System.exit(0);
        }
      }
    }
    else if(t.outEdges.size() == 0){
      t.status = 2;
      //Since the task has no outward Edges it cannot be part of an loop.
      //Thus we remove it from the log, lest it will be printed as a part of a loop if a predecessors
      //and a neigbour is in a loop.
      log.remove(t);
    }
    else if(t.status  ==  0){
      t.status = 1;
      for(int i = 0; i < t.outEdges.size(); i++){
        findLoops(t.outEdges.get(i), log);
      }
      t.status = 2;
      log.remove(t);
    }
  }



  public void optimalTime(){
    latest = taskList[0];
    start = taskList[0];
    topologicalSorting();
    backwardsSorting();
    printData();
    System.out.println("\n");
    printAll();
  }

  private void backwardsSorting(){
    Stack<Task> stack = new Stack<Task>();
    Task v;
    int counter = 0;

    for(Task t : taskList){
      //Set distance from startnode to .
      t.latestFinish = (latest.earliestStart+latest.time);
      // Setting each node to unvisited. (status = 0: node is unvisited, status = 1: node is visited)
      t.status = t.outEdges.size();
      if(t.outEdges.size() == 0){
        stack.push(t);
      }
    }

    while(!stack.empty()){
      v = stack.pop();
      counter++;

      for(Task w : v.invertedEdges){
        if(v.latestFinish-v.time < w.latestFinish){
          w.latestFinish = v.latestFinish-v.time;
        }
        w.status --;
        if(w.status == 0){
          stack.push(w);
        }
      }
    }
  }

  private void topologicalSorting(){//Kø/stakk-operasjoner tar konstant tid, og hver node og hver kant blir bare behandlet en gang.
    Stack<Task> stack = new Stack<Task>();

    Task v;
    int counter = 0;

    for(Task t : taskList){
      t.earliestStart = 0;
      t.status = t.invertedEdges.size();
      if(t.invertedEdges.size() == 0){
        stack.push(t);
      }
    }

    while(!stack.empty()){
      v = stack.pop();
      counter++;

      for(Task w : v.outEdges){

        if(v.earliestStart+v.time > w.earliestStart){
          w.earliestStart = v.earliestStart+v.time;
        }
        w.status --;
        if(w.status == 0){
          stack.push(w);
        }
      }
      if((v.earliestStart+v.time) > (latest.earliestStart+latest.time)){//Finding the latest task.
        latest = v;
      }
      if((v.earliestStart+v.time) < (start.earliestStart+start.time)){//finding the starting task.
        start = v;
      }
    }

    if(counter < taskList.length){
      isRealizable();
    }
  }


  private void printData(){
    PriorityQueue<Task> sortStart = new PriorityQueue<Task>(taskList.length, new Comparator<Task>() {
      @Override
      public int compare(Task a, Task b){
        if(a.earliestStart > b.earliestStart){
          return 1;
        }
        else if(a.earliestStart < b.earliestStart){
          return -1;
        }
        return 0;
      }
		});

    PriorityQueue<Task> sortFinish = new PriorityQueue<Task>(taskList.length, new Comparator<Task>() {
      @Override
      public int compare(Task a, Task b){
        if(a.earliestStart+a.time > b.earliestStart+b.time){
          return 1;
        }
        else if(a.earliestStart+a.time < b.earliestStart+b.time){
          return -1;
        }
        return 0;
      }
		});

    for(Task t : taskList){
      sortStart.add(t);
      sortFinish.add(t);
    }

    int staff = 0;
    int counter = 0;
    boolean printStaff = false;

    //Simulating the timeline, from time 0 to latest finish
    for(int i = 0; i <= (latest.earliestStart+latest.time); i++){
      printStaff = false;
      /*
       *this test is to insure that we dont peek at a non-exisiting task.
       *We only need to to this at the stack of "task-starts", as the latest event
       *in all possible cases is a task ending.
       */
      if(sortStart.peek() != null){
        //we use this test to seperate the "timeslices where something is happening from the ones where nothing is happening"
        if(sortStart.peek().earliestStart == i || (sortFinish.peek().earliestStart+sortFinish.peek().time) == i){
          System.out.println("Time: "+i);
          /*
           *This boolean ensures that the program prints the current staff.
           *We cant print it here together with the current time, as it is
           *computed in the following for-loops.
           */
          printStaff = true;
        }
      }
      //Special case: all tasks have started (sortStart is empty), but not all task have ended.
      else if((sortFinish.peek().earliestStart+sortFinish.peek().time) == i){
        System.out.println("Time: "+i);
        printStaff = true;
      }

      /*As the size of the sort stacks change during the loop, we have to buffer
       *the size, here I chose to do it in the int counter.
       */
      counter = sortStart.size();
      //Complexity = O(|N|), N = number of Tasks.
      for(int j = 0; j < counter; j++){
        if(sortStart.peek().earliestStart == i){
          staff += sortStart.peek().staff;
          System.out.println("    starting task "+sortStart.poll().ID);
        }
      }

      counter = sortFinish.size();
      //Complexity = O(|N|), N = number of Tasks.
      for(int j = 0; j < counter; j++){
        if((sortFinish.peek().earliestStart+sortFinish.peek().time) == i){
          staff -= sortFinish.peek().staff;
          System.out.println("    ending task "+sortFinish.poll().ID);
        }
      }
      if(printStaff){
        System.out.println("  current staff: "+staff);
      }
    }
  }

  public void printAll(){
    for(Task t : taskList){
      System.out.println("task ID: "+t.ID);
      System.out.println("  Name                  : "+t);
      System.out.println("  Required time         : "+t.time);
      System.out.println("  Required manpower     : "+t.staff);
      System.out.println("  Earliest start        : "+t.earliestStart);
      System.out.println("  Latest starting time  : "+(t.latestFinish - t.time));
      if(t.isCritical()){
        System.out.println("  Slack                 : is critical");
      }
      else{
        System.out.println("  Slack                 : "+(t.latestFinish-(t.earliestStart+t.time)));
      }
      if(t.outEdges.size() == 0){
        System.out.println("  Dependend tasks       : none");
      }
      else{
        System.out.println("  Dependend tasks       :");
        for(Task n : t.outEdges){
          System.out.println("    ID  : "+n.ID);
        }
      }
    }
  }

}
