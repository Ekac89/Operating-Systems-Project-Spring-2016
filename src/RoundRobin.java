import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * MAY DELETE THIS CLASS
 */
public class RoundRobin{

    static Display displayOS;

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

    //entering in next process
    public void enterProcess(){
                processesEntered++;
                newProcesses.add(outsideProcesses.remove(0)); //entering process in system
                newProcesses.get(newProcesses.size()-1).setState(2);
                displayOS.updateFrame(); //TODO:not sure if this will work/update display correctly
    }


    //going through new processes to set them to ready
    public void newProcessToReady(){
//        for(OSProcess newProcess : newProcesses){
            newProcesses.get(0).setState(2); //setting to ready
            readyQueue.add(newProcesses.get(0)); //adding to ready queue
            newProcesses.remove(newProcesses.get(0)); //removing from new process queue

      //      displayOS.updateFrame(); //TODO:not sure if this will work/update display correctly
//        }
    }

    //moves process from readyQueue to run
    public void readyQueueToRun(){
        running.add(readyQueue.remove(0));
        running.get(0).setState(3); //setting state to running, updates process display
     //   displayOS.updateFrame(); //TODO:not sure if this will work/update display correctly
        runningIOCheck(); //checks if process has an I/O interrupt right out of the gate.
    }

    //checks running process for IO interrupt
    public void runningIOCheck(){
        //first, checks for I/O interrupt, even if there is multiple
        while(running.get(0).checkForIO()>=0){
            blocked.add(running.remove(0)); //removes current process and places it in blocked queue
            blocked.get(0).runIO(blocked.get(0).checkForIO()); //runs I/O interrupt and changes process state to blocked
                                                                        //also updates process display, clock (when running IO, time passes)
        //    displayOS.getDisplayPanel(); //TODO:not sure if this will work/update display correctly
            running.add(blocked.get(0)); //done with I/O, add back to running
            running.get(0).setState(3); //setting state to running, updates process display
        //    displayOS.updateFrame(); //TODO:not sure if this will work/update display correctly

        }
    }

    //gets only process in running list and runs it
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
            //    displayOS.updateFrame();//TODO:not sure if this will work/update display correctly
                break;
            }
        }
    }


    //does all of the round robining
    public void runRoundRobin(){

    }

}
