import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

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



    public static int[][] ReadInProcess(int [][] processes){  // Create Process
        File file = new File("pullFromMe.txt");
        int i = 0;
        try {

            Scanner sc = new Scanner(file);

            while (sc.hasNextLine()) {
                //String i = sc.nextLine();
                //System.out.println(i);
                String[] tokens = sc.nextLine().split(":");
                String last = tokens[tokens.length - 1];
                for (int j = 0; j < tokens.length ; j++) {
                    processes[i][j] = Integer.parseInt(tokens [j]);
                    //System.out.println(tokens [j]);
                }
                i++;

            }
            sc.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("print lbl");
        for (i = 0; i < 60; i++) {
            for (int j = 0; j < 4 ; j++) {
                System.out.print(processes[i][j]);
            }
            System.out.println();
        }



        return (processes);

    }






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
//     */
//
//    static JFrame displayFrame = new JFrame("Group 3 Operating System Simulator");
//    static JPanel displayPanel = new JPanel();
//
//    static JButton step1butt;
//
//
//   static public JFrame getDisplayPanel(){
//
////        displayFrame.setTitle("Group 3 Operating System Simulator");
//       displayPanel.setLayout(new BoxLayout(displayPanel, BoxLayout.PAGE_AXIS));
//
//       displayPanel.add(new JLabel("Clock Time: " + OSClock.clock));
//       System.out.println("Clock Time: " + OSClock.clock);
//        System.out.println("**UNENTERED PROCESSES**");
//       displayPanel.add(new JLabel("**UNENTERED PROCESSES**"));
//        for(OSProcess unenteredProcess : outsideProcesses){
//            displayPanel.add(unenteredProcess.getProcessDisplay());
//            System.out.println(unenteredProcess.processDisplayToString());
//            System.out.println();
//        }
//       System.out.println("**NEW PROCESSES**");
//       displayPanel.add(new JLabel("**NEW PROCESSES**"));
//        for(OSProcess newProcess : newProcesses){
//            displayPanel.add(newProcess.getProcessDisplay());
//            System.out.println(newProcess.processDisplayToString());
//            System.out.println();
//        }
//       System.out.println("**READY QUEUE**");
//       displayPanel.add(new JLabel("**READY QUEUE**"));
//        for(OSProcess readyProcess : readyQueue){
//            displayPanel.add(readyProcess.getProcessDisplay());
//            System.out.println(readyProcess.processDisplayToString());
//            System.out.println();
//        }
//       System.out.println("**BLOCKED PROCESSES**");
//       displayFrame.add(new JLabel("**BLOCKED PROCESSES**"));
//        for(OSProcess blockedProcess : blocked){
//            displayPanel.add(blockedProcess.getProcessDisplay());
//            System.out.println(blockedProcess.processDisplayToString());
//            System.out.println();
//        }
//       System.out.println("**RUNNING PROCESS**");
//       displayFrame.add(new JLabel("**RUNNING PROCESS**"));
//        for(OSProcess runningProcess : running){
//            displayPanel.add(runningProcess.getProcessDisplay());
//            System.out.println(runningProcess.processDisplayToString());
//            System.out.println();
//        }
//       System.out.println("**FINISHED/EXITED PROCESSES**");
//       displayFrame.add(new JLabel("**FINISHED/EXITED PROCESSES**"));
//        for(OSProcess exitedProcess : exited){
//            displayPanel.add(exitedProcess.getProcessDisplay());
//            System.out.println(exitedProcess.processDisplayToString());
//            System.out.println();
//        }
//       System.out.println("*********END OF DISPLAY METHOD******************");
//       System.out.println();
//        step1butt = new JButton();
//        step1butt.setText("1 Step Forward");
//       displayPanel.add(step1butt);
//
//
//        displayFrame.add(displayPanel);
//        displayFrame.revalidate();
//        displayFrame.repaint();
//
//       displayFrame.setSize(400, 500);
//       displayFrame.setLocationRelativeTo(null);
//        displayFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        displayFrame.setVisible(true);
//
//        return displayFrame;
//    }
//
//    //updates the entire OS frame
//    public void updateFrame(){
//        //      this.displayFrame.removeAll();
////        this.displayFrame.add(this.displayPanel);
//        displayFrame.revalidate();
//        displayFrame.repaint();
//    }


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
        //Creating process array
        int[][] processes = new int[60][60];
        ReadInProcess(processes);

        //setting up OS
        osRoundRobin = new RoundRobin();
        System.out.println("******SET UP EMPTY ROUND ROBIN****");
        getDisplayPanel();

        //test process 1
        OSProcess testProcess1 = new OSProcess(1, 16, 1, 100);
        IORequest testIO = new IORequest(25, 50);
        testProcess1.ioRequests[0] = testIO;
        //test process 2
        OSProcess testProcess2 = new OSProcess(2, 16, 5, 100);
        IORequest testIO2 = new IORequest(25, 70);
        IORequest testIO3 = new IORequest(40, 20);
        IORequest testIO4 = new IORequest(30, 80);
        IORequest testIO5 = new IORequest(100, 99);
        testProcess2.ioRequests[0] = testIO;
        testProcess2.ioRequests[1] = testIO2;
        testProcess2.ioRequests[2] = testIO3;
        testProcess2.ioRequests[3] = testIO4;
        testProcess2.ioRequests[4] = testIO5;

        for(int i = 0; i < 60; i++) {
            OSProcess testProcess = new OSProcess(processes[i][0], processes[i][1],processes[i][3],processes[i][2]);
            IORequest testIO5 = new IORequest(25,50);
            testProcess.ioRequests[0] = testIO;


            System.out.println("***** added to outside process****");
            outsideProcesses.add(testProcess);
        }
        RoundRobin.checkForMemory();
        /**setting up processes */
        outsideProcesses.add(testProcess1);
        outsideProcesses.add(testProcess2);
        ALL_PROCESSES.add(testProcess1);
        ALL_PROCESSES.add(testProcess2);
        System.out.println("***** add to outside process****");
        getDisplayPanel();

//        /**Manual Round Robin**/
//        //osRoundRobin.checkForNewProcess(); //checks if theres memory for new process //TODO: not implemented in RoundRobin.java (commented out)
//        osRoundRobin.enterProcess(); //enters testProcess1
//        osRoundRobin.newProcessToReady(); //moves testProcess1 to ready
//        osRoundRobin.enterProcess(); //enters testProcess2
//        osRoundRobin.newProcessToReady(); //moves testProcess2 to ready
//        osRoundRobin.readyQueueToRun(); //moving next ready process to run
//        osRoundRobin.run10Cycles(); //running the testProcess1, should update display
//        //running is now empty
//        osRoundRobin.readyQueueToRun(); //moving testProcess2 to run
//        osRoundRobin.run10Cycles(); //running testProcess 2
//        //all processes should have exited
////        for(int i=0; i<500; i++){
////            System.out.println(i);
////            if(newProcesses.size()>0) {
////                osRoundRobin.newProcessToReady();
////            }
////            osRoundRobin.runOneCycleSetup();
////            getDisplayPanel();
////            osRoundRobin.runOneCycleRoundRobin();
////            getDisplayPanel();
////        }

        /**Automated Round Robin*/
        //trying out automated round robin algorithm, should go through everything
        int whileLoopCount =0;
        while (exited.size() < ALL_PROCESSES.size()){ //while there are processes that haven't been entered
            System.out.println();
            System.out.println("While loop count: " + whileLoopCount);
            System.out.println();
            //TODO: implement method to check for memory, not just add them in
//         if(checkForNewProcess()){ //if enough memory
            /**Fetch*/
                osRoundRobin.enterProcess(); //increments processesEntered and takes top outsideProcess to new
                osRoundRobin.newProcessToReady(); //takes new process and puts in ready
            /**Check*/
                osRoundRobin.readyQueueToRun(); //takes ready process and puts in run
            /**Execute*/
                osRoundRobin.run10Cycles(); //runs the process for 10 cycles + any I/O scheduled in those cycles
                //running now empty, last run process at bottom of ready
//          }else{ //if not enough memory
//              osRoundRobin.newProcessToReady(); //if there is a new process, newProcessToReady() will set it to ready
//              osRoundRobin.readyQueueToRun();
//              osRoundRobin.run10Cycles();
//          }
//
        }






//        //setting up OS Display
//        JFrame displayFrameOS = new JFrame("Group 3 Operating System Simulator");

//        Display displayOS = new Display();

//        displayFrameOS.setSize(400, 500);
//        displayFrameOS.setLocationRelativeTo(null);
//        displayFrameOS.add(displayOS.getDisplayPanel());
//        displayFrameOS.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        displayFrameOS.setVisible(true);


        //if step forward once button is pressed
//        if(displayOS.step1butt.getModel().isPressed()){
//
//        }









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
