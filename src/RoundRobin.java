import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * MAY DELETE THIS CLASS
 */
public class RoundRobin {

    int TIME_QUANTUM = 1; //1 second for timeslice
    static int hit = 0;
    static int processesEntered = 0; //counts incoming processes, should increment for each new process entered in system

    static int[] memory = new int[16];
    static List<OSProcess> ALL_PROCESSES; //all processes and their state/info before being entered

    static List<OSProcess> outsideProcesses; //processes that haven't been entered into the system
    static List<OSProcess> newProcesses; //processes that have just been entered into the system
    static List<OSProcess> readyQueue; //processes that are ready
    static List<OSProcess> running; //current running process (can be 0-1 processes)
    static List<OSProcess> blocked; //current blocked process (can be 0-1 processes)
    static List<OSProcess> exited //processes that are finished and have left the system
            = new ArrayList<OSProcess>();

    //sets up OS
    public RoundRobin() {
        this.ALL_PROCESSES = new ArrayList<>();

        this.outsideProcesses = new ArrayList<>();
        this.newProcesses = new ArrayList<>();
        this.readyQueue = new ArrayList<>();
        this.blocked = new ArrayList<>();
        this.exited = new ArrayList<>();
        this.running = new ArrayList<>();

        OSClock.clock = 0;
    }

    //TODO: set up check to see if there's memory enough for a new process, THEN call enterProcess()
    //checking if there's room for a new process; returns true if room AND if no next outside process
    public static boolean checkForMemory(){
        //if there are any more outsideProcesses

      if(outsideProcesses.size()>0) {

          AddProcessFromMem(outsideProcesses.get(0).PROCESS_SIZE, memory, outsideProcesses.get(0).PROCESS_ID);

          PrintProcessFromMem(memory);
          if (hit == 1) { //TODO: check for memory
              hit = 0;
              PrintProcessFromMem(memory);
              return true; //found a new process
          }else{
              PrintProcessFromMem(memory);
              //don't enter in process because not enough memor
              return false; //no new process
          }
      }else{

          PrintProcessFromMem(memory);
          return true; //returns true if no processes
      }

    }
    public static int[]  AddProcessFromMem(int Size, int[] memory, int id) {
        int MemCount = 0;
        if (Size <= 15) {
            for (int c = 0; c <= 15; c++) {
                if (memory[c] == 0) {
                    MemCount++;
                    if (MemCount == Size) {
                        for (int i = 0; i < MemCount; i++) {
                            hit = 1; // found room in mem
                            memory[c - i] = id;
                        }
                        c = 17;
                    }
                }else if (memory[c] != 0) {
                    MemCount = 0;
                }
            }
        }
        return (memory);
    }
    //remove
    public static int[] RemoveProcessFromMem(int id, int[] memory) {

        for (int c = 0; c <= 15; c++){
            if (memory[c] == id) {
                memory[c] = 0;
            }
            if (memory[c] == 0) {
            }
        }
        return (memory);
    }
    //print
    public static int[] PrintProcessFromMem(int[] memory) {

        for (int c = 0; c <= 15; ++c) {

            System.out.println(memory[c] + " | ");
        }

        System.out.println();

        return (memory);
    }

    //entering in next process on outside queue; returns false if no more outside processes
    public boolean enterProcess(){
        if(outsideProcesses.size()>0) {
            processesEntered++;
            newProcesses.add(outsideProcesses.remove(0)); //entering process in system
            newProcesses.get(newProcesses.size() - 1).setState(2); //setting recently entered process to ready
            getDisplayPanel(); //TODO:not sure if this will work/update display correctly
            return true; //a process is able to be entered
        }else{
            return false; //no more outside processes
        }
    }


    //gets oldest new Process (top of newQueue) and adds to ready; Does not increment clock
    // returns false if no new processes, true if new process was moved to ready
    public boolean newProcessToReady(){
        if(newProcesses.size()>0) {
            readyQueue.add(newProcesses.remove(0)); //removing from new and adding to ready queue
            readyQueue.get(readyQueue.size() - 1).setState(2); //setting newly added ready to ready

            getDisplayPanel(); //TODO:not sure if this will work/update display correctly
            return true; //new process was found
        }else{ //no new processes found
            //doesn't update display
            return false;
        }
    }

    //moves process from readyQueue to run, returns false if no Ready, otherwise true
    public boolean readyQueueToRun(){
        if(readyQueue.size()>0) {
            running.add(readyQueue.remove(0)); //removing from ready and adding to run
            running.get(0).setState(3); //setting state to running, updates process display
            getDisplayPanel(); //TODO:not sure if this will work/update display correctly
            return true; //process ran
        }else{ //if no processes in readyQueue
            return false;
        }
    }

    //checks running process for IO interrupt
    public void runningIOCheck(){
        //first, checks for I/O interrupt
        if(running.get(0).checkForIO()>=0){
            blocked.add(running.remove(0)); //removes current process and places it in blocked queue
            blocked.get(0).runIO(blocked.get(0).checkForIO()); //runs I/O interrupt and changes process state to blocked
                                                                //also updates process display, clock (when running IO, time passes)
            getDisplayPanel();//TODO:not sure if this will work/update display correctly
            //I/O request done, so add back to running
            running.add(blocked.remove(0)); //done with I/O, add back to running
            running.get(0).setState(3); //setting state to running, updates process display
            getDisplayPanel();//TODO:not sure if this will work/update display correctly
        }
    }

//    //gets only process in running list and runs it
    public void run10Cycles(){
        //run 10 cycles unless process complete
        for(int c=1; c<=10; c++){
            //first checks for I/O interrupt
            runningIOCheck();
            //then keep running
            running.get(0).runOneCycle();
            //checking if process is done after this cycle
            if(running.get(0).complete == true){
                exited.add(running.remove(0)); //process exits
                exited.get(exited.size()-1).setState(5); //sets this last exited process to state exited, updates display
                getDisplayPanel();//TODO:not sure if this will work/update display correctly
                break;
            }
        } //process ran for 10 cycles + I/O ran
        //now bring in any unentered processes
//      if(this.checkForMemory()){
//          this.enterProcess; //if enough room enter process   //TODO: method not implemented
//          getDisplayPanel();
//      }
//
        this.newProcessToReady(); //makes sure to bring in new processes after this one is done running
        getDisplayPanel();
        //now we can place process that just finished running to the bottom of ready
        //if the process that just finished running completed, simply leave running empty
        if(running.size()>0) {
            readyQueue.add(running.remove(0)); //removes process that just finished running and places into readyQueue
            readyQueue.get(readyQueue.size() - 1).setState(2); //setting state back to ready
            getDisplayPanel();
        }
    }


    /**Display for OS*/

    static JFrame displayFrame = new JFrame("Group 3 Operating System Simulator");
    static JPanel displayPanel = new JPanel();

    static JButton step1butt;


    static public JFrame getDisplayPanel(){

//        displayFrame.setTitle("Group 3 Operating System Simulator");
        displayPanel.setLayout(new BoxLayout(displayPanel, BoxLayout.PAGE_AXIS));

        displayPanel.add(new JLabel("Clock Time: " + OSClock.clock));
        System.out.println("Clock Time: " + OSClock.clock);
        System.out.println("**UNENTERED PROCESSES**");
        displayPanel.add(new JLabel("**UNENTERED PROCESSES**"));
        for(OSProcess unenteredProcess : outsideProcesses){
            displayPanel.add(unenteredProcess.getProcessDisplay());
            System.out.println(unenteredProcess.processDisplayToString());
            System.out.println();
        }
        System.out.println("**NEW PROCESSES**");
        displayPanel.add(new JLabel("**NEW PROCESSES**"));
        for(OSProcess newProcess : newProcesses){
            displayPanel.add(newProcess.getProcessDisplay());
            System.out.println(newProcess.processDisplayToString());
            System.out.println();
        }
        System.out.println("**READY QUEUE**");
        displayPanel.add(new JLabel("**READY QUEUE**"));
        for(OSProcess readyProcess : readyQueue){
            displayPanel.add(readyProcess.getProcessDisplay());
            System.out.println(readyProcess.processDisplayToString());
            System.out.println();
        }
        System.out.println("**BLOCKED PROCESSES**");
        displayFrame.add(new JLabel("**BLOCKED PROCESSES**"));
        for(OSProcess blockedProcess : blocked){
            displayPanel.add(blockedProcess.getProcessDisplay());
            System.out.println(blockedProcess.processDisplayToString());
            System.out.println();
        }
        System.out.println("**RUNNING PROCESS**");
        displayFrame.add(new JLabel("**RUNNING PROCESS**"));
        for(OSProcess runningProcess : running){
            displayPanel.add(runningProcess.getProcessDisplay());
            System.out.println(runningProcess.processDisplayToString());
            System.out.println();
        }
        System.out.println("**FINISHED/EXITED PROCESSES**");
        displayFrame.add(new JLabel("**FINISHED/EXITED PROCESSES**"));
        for(OSProcess exitedProcess : exited){
            displayPanel.add(exitedProcess.getProcessDisplay());
            System.out.println(exitedProcess.processDisplayToString());
            System.out.println();
        }
        System.out.println("*********END OF DISPLAY METHOD******************");
        System.out.println();
        step1butt = new JButton();
        step1butt.setText("1 Step Forward");
        displayPanel.add(step1butt);


        displayFrame.add(displayPanel);
        displayFrame.revalidate();
        displayFrame.repaint();

        displayFrame.setSize(400, 500);
        displayFrame.setLocationRelativeTo(null);
        displayFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        displayFrame.setVisible(true);

        return displayFrame;
    }

    //updates the entire OS frame
    public void updateFrame(){
        //      this.displayFrame.removeAll();
//        this.displayFrame.add(this.displayPanel);
        displayFrame.revalidate();
        displayFrame.repaint();
    }

}
