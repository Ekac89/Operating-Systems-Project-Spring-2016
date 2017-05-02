import javax.swing.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * MAY DELETE THIS CLASS
 */
public class RoundRobin{

    int TIME_QUANTUM = 1; //1 second for timeslice

    static int processesEntered = 0; //counts incoming processes, should increment for each new process entered in system

    //int currentProcessNumber; //PROCESS_ID for current process, this is only process in running ArrayList
    static List<OSProcess> ALL_PROCESSES; //all processes and their state/info before being entered

    static List<OSProcess> outsideProcesses; //processes that haven't been entered into the system
    static List<OSProcess> newProcesses; //processes that have just been entered into the system
    static List<OSProcess> readyQueue; //processes that are ready
    static List<OSProcess> running; //current running process (can be 0-1 processes)
    static List<OSProcess> blocked; //current blocked process (can be 0-1 processes)
    static List<OSProcess> exited //processes that are finished and have left the system
            = new ArrayList<OSProcess>();


    public RoundRobin(){
        this.ALL_PROCESSES = new ArrayList<>();

        this.outsideProcesses = new ArrayList<>();
        this.newProcesses = new ArrayList<>();
        this.readyQueue = new ArrayList<>();
        this.blocked = new ArrayList<>();
        this.exited = new ArrayList<>();
        this.running = new ArrayList<>();

        OSClock.clock = 0;
    }
    //checking if it's time to enter a process
//    public OSProcess checkForNewProcess(){
//        for(OSProcess outsideProcess : outsideProcesses){
//
//        }
//    }

    //entering in next process
    public void enterProcess(){
                processesEntered++;
                newProcesses.add(outsideProcesses.remove(0)); //entering process in system
                newProcesses.get(newProcesses.size()-1).setState(2);
               // displayOS.updateFrame(); //TODO:not sure if this will work/update display correctly
    }


    //going through new processes to set them to ready
    public void newProcessToReady(){
//        for(OSProcess newProcess : newProcesses){
            newProcesses.get(0).setState(2); //setting to ready
            readyQueue.add(newProcesses.get(0)); //adding to ready queue
            newProcesses.remove(newProcesses.get(0)); //removing from new process queue

           // displayOS.updateFrame(); //TODO:not sure if this will work/update display correctly
//        }
    }

//    //moves process from readyQueue to run
//    public void readyQueueToRun(){
//        running.add(readyQueue.remove(0));
//        running.get(0).setState(3); //setting state to running, updates process display
//       // displayOS.updateFrame(); //TODO:not sure if this will work/update display correctly
//        runningIOCheck(); //checks if process has an I/O interrupt right out of the gate.
//    }

    //checks running process for IO interrupt
    public void runningIOCheck(){
        //first, checks for I/O interrupt, even if there is multiple
        while(running.get(0).checkForIO()>=0){
            blocked.add(running.remove(0)); //removes current process and places it in blocked queue
            blocked.get(0).runIO(blocked.get(0).checkForIO()); //runs I/O interrupt and changes process state to blocked
                                                                            //also updates process display, clock (when running IO, time passes)
            getDisplayPanel();
            //displayOS.getDisplayPanel(); //TODO:not sure if this will work/update display correctly
            running.add(blocked.remove(0)); //done with I/O, add back to running
            running.get(0).setState(3); //setting state to running, updates process display
           // displayOS.updateFrame(); //TODO:not sure if this will work/update display correctly
            getDisplayPanel();
        }
    }

//    //gets only process in running list and runs it
    public void runRunningProcess(){
        //run 10 cycles unless process complete
        for(int c=1; c<=10; c++){
            //first checks for I/O interrupt
            runningIOCheck();
            //then keep running
            running.get(0).runOneCycle();
            //checking if process is done after this cycle
            if(running.get(0).complete == true){
                exited.add(running.remove(0)); //process exits
                exited.get(exited.size()).setState(5); //sets this last exited process to state exited, updates display
               // displayOS.updateFrame();//TODO:not sure if this will work/update display correctly
                getDisplayPanel();
                break;
            }
        }
        running.get(0).setState(2); //setting state back to ready
        readyQueue.add(running.get(0)); //places process on readyQueue after it's done running
        getDisplayPanel();
    }

    //sets up RR
    public void setUpNextRun(){
        if(running.size()==0){
            running.add(readyQueue.remove(0));
            running.get(0).setState(3); //setting state to ready
            getDisplayPanel();
        }
    }

    //checks through unentered and new queues; these do not use up clock time
//    public void check

    //checks for state of process before running, MUST be called before runOnecycleRoundRobin
    public void runOneCycleSetup(){
        //if nothing is running or blocked, gets next Process from readyQueue
        if(running.size()==0 && blocked.size()==0){
            running.add(readyQueue.remove(0));
            running.get(0).setState(3); //setting state to ready
            getDisplayPanel();
        }
        //if process was blocked last cycle
        else if(running.size()==0 && blocked.size()==1){
            running.add(blocked.remove(0));
            running.get(0).setState(3);
            getDisplayPanel();
        }
    }

    //runs one cycle
    public void runOneCycleRoundRobin(){
        //checks for I/O first, otherwise runs
        if(running.get(0).checkForIO()>=0) {
            blocked.add(running.remove(0)); //moving process from run to block
            blocked.get(0).setState(4); //setting state to blocked
            blocked.get(0).runIO(blocked.get(0).checkForIO()); //runs IO if it's time for IO
            getDisplayPanel();
        }else{
            running.get(0).runOneCycle();
            if (running.get(0).complete){
                exited.add(running.remove(0)); //removing if process is complete
                getDisplayPanel();
            }
        }
    }

    //does all of the round robining
    public void runRoundRobin(){

    }

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
