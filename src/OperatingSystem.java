import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * Overall abstraction for the OS
 */
public class OperatingSystem extends RoundRobin{
    //no more than 60 processes entered in 5-state model
//    int processesEntered = 0; //counts incoming processes, should increment for each new process entered in system
//
//    //int currentProcessNumber; //PROCESS_ID for current process, this is only process in running ArrayList
//
//    static List<OSProcess> outsideProcesses; //processes that haven't been entered into the system
//    static List<OSProcess> newProcesses; //processes that have just been entered into the system
//    static List<OSProcess> readyQueue; //processes that are ready
//    static List<OSProcess> running; //current running process (can be 0-1 processes)
//    static List<OSProcess> blocked; //current blocked process (can be 0-1 processes)
//    static List<OSProcess> exited //processes that are finished and have left the system
//                    = new ArrayList<OSProcess>();

//    static Display displayOS = new Display();
//    static JFrame displayFrameOS = new JFrame("Group 3 Operating System Simulator");




    //sets up OS and initializes all needed objects/lists
    public void OSStartUp(){
        outsideProcesses = new ArrayList<>();
        newProcesses = new ArrayList<>();
        readyQueue = new ArrayList<>();
        blocked = new ArrayList<>();
        exited = new ArrayList<>();
        running = new ArrayList<>();

        OSClock.clock = 0;
//        currentProcessNumber = 0;

    }

    /**
     Process handling methods
     */

//    //creating Random OSProcess, pass in current clock time
//    public OSProcess createRandomProcess(int currentTime, int currentProcessCount){
//        //process size can be 1-8
//        int memorySize = ThreadLocalRandom.current().nextInt(1, 8);
//        //process can have 0 to 5 I/O requests
//        int ioRequests = ThreadLocalRandom.current().nextInt(1,5);
//        //process takes between 10-950 CYCLES to complete
//        int cycles = ThreadLocalRandom.current().nextInt(10,950);
//        //process' arrival time will be its creation
//        OSProcess randomProcess = new OSProcess(currentProcessCount, memorySize, ioRequests, cycles);
//
//        //making the I/O requests that will interrupt it
//        for(int i=0; i<ioRequests; i++){
//            //I/O requests can take 25-50 cycles to complete
//            int ioCyclesNeeded = ThreadLocalRandom.current().nextInt(25,50);
//            //randomly chooses a cycle within the cycles the process has to interrupt
//            int ioCycleLaunch = ThreadLocalRandom.current().nextInt(1,cycles);  //starts at cycle 1 until start of last cycle
//
//            randomProcess.ioRequests[i] = new IORequest(ioCyclesNeeded,ioCycleLaunch);
//        }
//
//        return randomProcess;
//    }



    /**
      Display update methods
     */




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

    public static RoundRobin osRoundRobin;

    public static void main(String[] args) {
        /**Setup of OS**/
//        //setting up OS
//        outsideProcesses = new ArrayList<>();
//        newProcesses = new ArrayList<>();
//        readyQueue = new ArrayList<>();
//        blocked = new ArrayList<>();
//        exited = new ArrayList<>();
//        running = new ArrayList<>();

//        OSClock.clock = 0;

        ALL_PROCESSES = new ArrayList<>();

        outsideProcesses = new ArrayList<>();
        newProcesses = new ArrayList<>();
        readyQueue = new ArrayList<>();
        blocked = new ArrayList<>();
        exited = new ArrayList<>();
        running = new ArrayList<>();

        OSClock.clock = 0;

        OSProcess testProcess = new OSProcess(OSClock.clock, 1);




        outsideProcesses.add(testProcess);

        displayOS.getDisplayPanel();


        osRoundRobin.enterProcess();


//        //setting up OS Display
//        JFrame displayFrameOS = new JFrame("Group 3 Operating System Simulator");

//        Display displayOS = new Display();

//        displayFrameOS.setSize(400, 500);
//        displayFrameOS.setLocationRelativeTo(null);
//        displayFrameOS.add(displayOS.getDisplayPanel());
//        displayFrameOS.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        displayFrameOS.setVisible(true);


        //if step forward once button is pressed
        if(displayOS.step1butt.getModel().isPressed()){

        }









//        //making 4 random processes, each has arrival time of 1, 2, 3, 4
//        for(int p=0; p<4; p++){
//            //process size can be 1-8
//            int memorySize = ThreadLocalRandom.current().nextInt(1, 8);
//            //process can have 0 to 5 I/O requests
//            int ioRequests = ThreadLocalRandom.current().nextInt(1,5);
//            //process takes between 10-950 CYCLES to complete
//            int cycles = ThreadLocalRandom.current().nextInt(10,950);
//            //process' arrival time will be its creation
//            OSProcess randomProcess = new OSProcess(memorySize,ioRequests,cycles,p,p);
//
//            //making the I/O requests that will interrupt it
//            for(int i=0; i<ioRequests; i++){
//                //I/O requests can take 25-50 cycles to complete
//                int ioCyclesNeeded = ThreadLocalRandom.current().nextInt(25,50);
//                //randomly chooses a cycle within the cycles the process has to interrupt
//                int ioCycleLaunch = ThreadLocalRandom.current().nextInt(1,cycles);  //starts at cycle 1 until start of last cycle
//
//                randomProcess.ioRequests[i] = new IORequest(ioCyclesNeeded,ioCycleLaunch);
//            }
//
//            randomProcess.setPriority(p); //priority is simply the numerical order (1,2,3,4)
//
//            outsideProcesses.add(randomProcess); //not entered into OS yet
//        }



//        /** Display to make process */
//        JFrame displayFrameOS = new JFrame("Group 3 Operating System Simulator");
//
//        JPanel panel = new JPanel();
//        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));  //layout
//
//        // JLabel label = new JLabel("label");
//
//        JButton button = new JButton();
//        button.setText("worthless button that will eventually make button");
//
//        panel.add(new JLabel("OSProcess Size: "));
//        panel.add(new JTextField(2));
//        panel.add(new JLabel("Time needed for process: "));
//        panel.add(new JTextField(2));
//        panel.add(new JLabel("I/O Requests Needed: "));
//        panel.add(new JTextField(2));
//        panel.add(new JLabel("OSProcess Size: "));
//        panel.add(new JTextField(2));
//
//
//        panel.add(button);
//
//
//        displayFrameOS.add(panel);
//        displayFrameOS.setSize(400, 500);
//        displayFrameOS.setLocationRelativeTo(null);
//        displayFrameOS.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        displayFrameOS.setVisible(true);
    }
}
