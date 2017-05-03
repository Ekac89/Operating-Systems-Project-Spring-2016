import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.awt.BorderLayout;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import static com.sun.javafx.fxml.expression.Expression.add;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;

/**
 * Houses Round Robin algorithm methods and display
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


    //checking if there's room for a new process; returns true if room AND if no next outside process
    public static boolean checkForMemory(){
        //if there are any more outsideProcesses

      if(newProcesses.size()>0) {

          AddProcessFromMem(newProcesses.get(0).PROCESS_SIZE, memory, newProcesses.get(0).PROCESS_ID);

          PrintProcessFromMem(memory);
          if (hit == 1) { //TODO: check for memory
              //hit = 0;
              return true; //found a new process
          }else{
              //don't enter in process because not enough memor
              return false; //no new process
          }
      }else{
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
        }
        return (memory);
    }


    //entering in next process on outside queue; returns false if no more outside processes
    public boolean enterProcess(){
        if(outsideProcesses.size()>0 && newProcesses.size() == 0) {
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
            RoundRobin.checkForMemory();
            if (hit == 1) {
                readyQueue.add(newProcesses.remove(0)); //removing from new and adding to ready queue
                readyQueue.get(readyQueue.size() - 1).setState(2); //setting newly added ready to ready
                hit = 0 ;
                getDisplayPanel();
                return true; //new process was found
            }
            return false;
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
            getDisplayPanel();
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
            getDisplayPanel();
            //I/O request done, so add back to running
            running.add(blocked.remove(0)); //done with I/O, add back to running
            running.get(0).setState(3); //setting state to running, updates process display
            getDisplayPanel();
        }
    }

//    //gets only process in running list and runs it
    public void run10Cycles(){
        //run 10 cycles unless process complete
        getDisplayPanel();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        for(int c=1; c<=10; c++){
            //first checks for I/O interrupt

            runningIOCheck();

            //then keep running
            getDisplayPanel();

            running.get(0).runOneCycle();

            //checking if process is done after this cycle
            if(running.get(0).complete == true){
                RemoveProcessFromMem(running.get(0).PROCESS_ID, memory);
                exited.add(running.remove(0)); //process exits
                exited.get(exited.size()-1).setState(5); //sets this last exited process to state exited, updates display
                getDisplayPanel();
                break;
            }
        } //process ran for 10 cycles + I/O ran
        //now bring in any unentered processes
        //also checks Memory

        this.newProcessToReady(); //makes sure to bring in new processes after this one is done running
        getDisplayPanel();
        //now we can place process that just finished running to the bottom of ready
        //if the process that just finished running completed, simply leave running empty
        if(running.size()>0) {
            readyQueue.add(running.remove(0)); //removes process that just finished running and places into readyQueue
            readyQueue.get(readyQueue.size() - 1).setState(2); //setting state back to ready
            getDisplayPanel();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }
    }


    /**Display for OS**/

    static JFrame displayFrame = new JFrame("Group 3 Operating System Simulator");
    static JPanel displayPanel = new JPanel();

    static JButton buttonUntilDone = new JButton();
    static JButton buttonStep1 = new JButton();
    static JButton buttonNew = new JButton();
    static JButton buttonReady = new JButton();
    // add the panel to a JScrollPane


    public static JFrame getDisplayPanel(){
        displayPanel.removeAll();
        displayPanel.revalidate();
        displayPanel.repaint();



        buttonUntilDone.setText("Run Until Full Stop");
        displayPanel.add(buttonUntilDone);
        buttonStep1.setText("Step Forward Once");
        displayPanel.add(buttonStep1);
        buttonNew.setText("Display Unentered");
        displayPanel.add(buttonNew);
        buttonReady.setText("Display Exited");
        displayPanel.add(buttonReady);

        displayPanel.setLayout(new BoxLayout(displayPanel, BoxLayout.PAGE_AXIS));

        displayPanel.add(new JLabel(Arrays.toString(memory)));

        displayPanel.add(new JLabel("Clock Time: " + OSClock.clock));

        JLabel newPLabel = new JLabel("**NEW PROCESSES**");
        newPLabel.setFont(newPLabel.getFont().deriveFont(15));
        newPLabel.setForeground(Color.BLUE);
        displayPanel.add(newPLabel);
        for(OSProcess newProcess : newProcesses){
            displayPanel.add(newProcess.getProcessDisplay());
        }

        JLabel readyLabel = new JLabel("**READY QUEUE**");
        readyLabel.setFont(readyLabel.getFont().deriveFont(15));
        readyLabel.setForeground(Color.BLUE);
        displayPanel.add(readyLabel);
        for(OSProcess readyProcess : readyQueue){
            displayPanel.add(readyProcess.getProcessDisplay());
        }

        JLabel blockedLabel = new JLabel("**BLOCKED PROCESSES**");
        blockedLabel.setFont(blockedLabel.getFont().deriveFont(15));
        blockedLabel.setForeground(Color.BLUE);
        displayPanel.add(blockedLabel);
        for(OSProcess blockedProcess : blocked){
            displayPanel.add(blockedProcess.getProcessDisplay());
        }

        JLabel runningLabel = new JLabel("**RUNNING PROCESS**");
        runningLabel.setFont(runningLabel.getFont().deriveFont(15));
        runningLabel.setForeground(Color.BLUE);
        displayPanel.add(runningLabel);
        for(OSProcess runningProcess : running){
            displayPanel.add(runningProcess.getProcessDisplay());
        }

        JLabel exitedLabel = new JLabel("**FINISHED/EXITED PROCESSES**");
        exitedLabel.setFont(exitedLabel.getFont().deriveFont(15));
        exitedLabel.setForeground(Color.BLUE);
        displayPanel.add(exitedLabel);
        for(OSProcess exitedProcess : exited){
            displayPanel.add(exitedProcess.getProcessDisplay());
        }

        displayFrame.add(displayPanel);

        displayFrame.setSize(1000, 1000);
        displayFrame.setLocationRelativeTo(null);
        displayFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        displayFrame.setVisible(true);

        return displayFrame;
    }



}
