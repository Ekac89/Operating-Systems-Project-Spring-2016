import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * Overall abstraction for the OS
 */
public class OperatingSystem extends Frame {

    //no more than 60 processes entered in 5-state model
    int processesEntered = 0; //counts incoming processes, should increment for each new process entered in system

    int currentProcessNumber; //processNumber for current process
    OSProcess currentProcess;

    List<OSProcess> newProcesses, readyQueue, running, blocked, exited = new ArrayList<OSProcess>();


    //sets up OS
    public void startUp(){
        OSClock.clock = 0;
        currentProcessNumber = 0;

    }


    public void roundRobin(OSProcess enteringProcess){
        this.readyQueue.add(enteringProcess);



    }

    //creating Random OSProcess, pass in current clock time
    public OSProcess createRandomProcess(int currentTime, int currentProcessCount){
        //process size can be 1-8
        int memorySize = ThreadLocalRandom.current().nextInt(1, 8);
        //process can have 0 to 5 I/O requests
        int ioRequests = ThreadLocalRandom.current().nextInt(1,5);
        //process takes between 10-950 CYCLES to complete
        int cycles = ThreadLocalRandom.current().nextInt(10,950);
        //process' arrival time will be its creation
        OSProcess randomProcess = new OSProcess(memorySize,ioRequests,cycles,currentTime,currentProcessCount);

        //making the I/O requests that will interrupt it
        for(int i=0; i<ioRequests; i++){
            //I/O requests can take 25-50 cycles to complete
            int ioCyclesNeeded = ThreadLocalRandom.current().nextInt(25,50);
            //randomly chooses a cycle within the cycles the process has to interrupt
            int ioCycleLaunch = ThreadLocalRandom.current().nextInt(1,cycles);  //starts at cycle 1 until start of last cycle

            randomProcess.ioRequests[i] = new IORequest(ioCyclesNeeded,ioCycleLaunch);
        }

        return randomProcess;
    }

    //run a process
    public void runProcess(OSProcess currentProcess){
        currentProcessNumber = currentProcess.getProcessNumber();

        //run 10 cycles unless process complete
        for(int c=0; c<=10; c++){
            currentProcess.runOneCycle();
            //checking if process is done after this cycle
            if(currentProcess.complete == true){
                break;
            }
        }
    }


    public static void displayActiveProcess(OperatingSystem systemCurrent){ //takes in current state of operating system

    }

    /**
     *GUI for our OS simulator
     *
     * For each active process the following information displayed:
     *  -amount of CPU time needed to complete
     *  -amount of CPU time already used
     *  -priority (if relevant)
     *  -number of I/O requests satisfied
     *  -number of outstanding I/O requests
     *
     * Display changes for:
     *  -new process entering system
     *  -a processes state change
     *  -I/O request made
     *  -I/O request completed
     *  -memory allocated
     *  -memory deallocated
     *  -process exits system
     */
    public static void main(String[] args) {

        OSProcess[] allProcesses = new OSProcess[4]; //array of all processes to be entered into system

        //making processes
        for(int i=0; i<4; i++){

        }


        /** Display to make process */
        JFrame frame = new JFrame("Group 3 Operating System Simulator");

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));  //layout

        // JLabel label = new JLabel("label");

        JButton button = new JButton();
        button.setText("worthless button that will eventually make button");

        panel.add(new JLabel("OSProcess Size: "));
        panel.add(new JTextField(2));
        panel.add(new JLabel("Time needed for process: "));
        panel.add(new JTextField(2));
        panel.add(new JLabel("I/O Requests Needed: "));
        panel.add(new JTextField(2));
        panel.add(new JLabel("OSProcess Size: "));
        panel.add(new JTextField(2));


        panel.add(button);


        frame.add(panel);
        frame.setSize(400, 500);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
