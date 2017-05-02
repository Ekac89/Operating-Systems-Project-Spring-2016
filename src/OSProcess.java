import javax.swing.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 *
 *
 */
public class OSProcess{
    int PROCESS_SIZE; //memory size of process (can be 1-8MB in size)

    //OSProcess Control Block (PCB) *****************************
    int PROCESS_ID; //unique identifier for each process (at most 60 processes) (will simply be order in which entered)
    int POINTER_INDEX; //array index that represents location in memory
    int state; //number represents process' current state:
                    //1 = new
                    //2 = ready
                    //3 = running
                    //4 = blocked
                    //5 = exit
    int programCounter; //represents next memory location
    //PCB must also record I/O request information
    IORequest[] ioRequests = new IORequest[5]; //Holds IO Request objects (there can be 0-5 I/O requests per program)
    int IO_REQUESTS; //number of I/O requests this process will execute (can be 0-5)
    int ioRequestsSatisfied; //number of satisfied I/O requests
    int ioRequestsUnsatisfied; //number of I/O requests left
    Integer[] cyclesForIOs; //CYCLES needed for each io request
                            // this assumes number of CYCLES needed for each IO request is known on process create
    Integer[] startCycleTimeIO; //when, by cycle, each IO will start on
    //**********************************************************
    //Display -- this information needs to be displayed for each process
    JLabel processDisplay = new JLabel();

    double TIME_NEEDED; //total amount of time this process needs in seconds
    double timeUsed; //amount of time this process has already used
    double timeLeft; //amount of time this process has left
                //timeLeft = TIME_NEEDED - timeUsed

//not needed in our Round Robin (priority is first come first serve)
//    int priority; //queue position
    //*************************************

    double ARRIVAL_TIME; //will be set once when it arrives in RoundRobin readyQueue
    int CYCLES; //amount of CYCLES this process needs (can be 10-950)
                        //each cycle is 0.1 seconds, so 10 CYCLES per timeslice/timeQuantum in RoundRobin

    int cycleCurrent; //current cycle that this process is on (has completed cycleCurrent-1 cycles)
    int cyclesLeft;

    boolean complete;



    public OSProcess(int processID, int memorySize, int ioRequests, int cycles){
        this.state = 1; //process starts as 1 = new
        this.PROCESS_ID = processID; //no more than 60, should be incremented by current processes entered
        //TODO: instantiate pointer index
        //TODO: instantiate program counter

        this.PROCESS_SIZE = memorySize;
        this.IO_REQUESTS = ioRequests;
        this.CYCLES = cycles;
        this.ARRIVAL_TIME = OSClock.clock;

        this.TIME_NEEDED =  cycles * 0.1;   //each cycle is 0.1 seconds
        this.timeLeft = cycles * 0.1;   //initializes amount of timeLeft
        this.cycleCurrent = cycles;

        this.ioRequestsSatisfied = 0;
        this.ioRequestsUnsatisfied = ioRequests; //all I/O Requests are unsatisfied on process create

        this.processDisplay.setText("<html>" +
                "Process ID: " + processID
                + "<br>Total CPU Time Needed: " + TIME_NEEDED
                + "<br>CPU Time Used: " + timeLeft
                + "<br>Number of I/O requests satisfied: " + this.ioRequestsSatisfied
                + "<br>Number of I/O requests unsatisfied: " + this.ioRequestsUnsatisfied
                + "</html>");
    }

    //checks if it's time for an I/O interrupt and returns number of IO that needs to execute
    public int checkForIO(){
        for(int i =0; i<IO_REQUESTS; i++){
            //check if it's time for an I/O interrupt (or tech past time)
            if(ioRequests[i].getPRCS_CYCLE_LAUNCH() <= this.cycleCurrent && !ioRequests[i].complete){
                return i; //returns I/O process in array that needs to be executed
            }
        }
        return -1; //returns -1 if no IO is ready
    }

    //runs given I/O interrupt, returns true if I/O was run
    public boolean runIO(int ioNumber) {
        if (ioNumber >= 0 && ioNumber <= 5) {
            setState(4); //process is in block state when interrupted, updates display

            ioRequests[ioNumber].runIO(); //run the I/O, updates clock
            ioRequestsUnsatisfied--;
            ioRequestsSatisfied++;

            updateProcessDisplay(); //updates because process state was changed and I/O request made
            return true; //I/O has been run
        }else{
            return false; //I/O hasn't been run (ioNumber passed in was -1 because no I/O ready from checkforIO())
        }
    }

    //can update process display 3 times
    public void runOneCycle(){
        setState(3); //state set to running, also updates process display

        this.cycleCurrent--;
        this.timeUsed = timeUsed + 0.1;
        this.timeLeft = timeLeft - 0.1;

        OSClock.clock += 0.1; //adds one cycle to overall system clock

        if(cycleCurrent == 0){    //TODO: doesn't check if I/O requests done
            complete = true;
            this.setState(5); //setting state to exited
        }

        updateProcessDisplay(); //updates display after cycle is done
    }

    //update display for this particular process
    public void updateProcessDisplay(){
        this.processDisplay.setLayout(new BoxLayout(processDisplay, SwingConstants.CENTER));
        this.processDisplay.setText("<html>" +
                "Process ID: " + this.PROCESS_ID
                + "<br>Total CPU time needed: " + this.TIME_NEEDED
                + "<br>CPU time left: " + this.timeLeft
                + "<br>CPU current cycle: " + this.cycleCurrent
                + "<br>Number of I/O requests satisfied: " + this.ioRequestsSatisfied
                + "<br>Number of I/O requests unsatisfied: " + this.ioRequestsUnsatisfied
                + "<br>Current state: " + stateToString()
                + "</html>");

    }

    //returns simple string of all process information (instead of JLabel)
    public String processDisplayToString(){
       return this.processDisplay.getText();
    }



    //converts current state to a readable string
    public String stateToString(){
        switch(this.state){
            case 1:
                return "NEW";
            case 2:
                return "READY";
            case 3:
                return "RUNNING";
            case 4:
                return "BLOCKED BY I/O REQUEST";
            case 5:
                return "EXITED";
            default:
                return "INVALID STATE: " + state;
        }
    }

    //default constructor
    //creating Random OSProcess, pass in current clock time
    public OSProcess(double currentTime, int currentProcessCount){
        this.PROCESS_ID = currentProcessCount;
        //process' arrival time will be its creation
        this.ARRIVAL_TIME = currentTime;
        //process size can be 1-8
        this.PROCESS_SIZE = ThreadLocalRandom.current().nextInt(1, 8);
        //process can have 0 to 5 I/O requests
        this.IO_REQUESTS = ThreadLocalRandom.current().nextInt(1,5);
        //process takes between 10-950 CYCLES to complete
        this.CYCLES = ThreadLocalRandom.current().nextInt(10,950);

        //making the I/O requests that will interrupt it
        for(int i=0; i<ioRequests.length; i++){
            //I/O requests can take 25-50 cycles to complete
            int ioCyclesNeeded = ThreadLocalRandom.current().nextInt(25,50);
            //randomly chooses a cycle within the cycles the process has to interrupt
            int ioCycleLaunch = ThreadLocalRandom.current().nextInt(1,this.CYCLES);  //starts at cycle 1 until start of last cycle

            this.ioRequests[i] = new IORequest(ioCyclesNeeded,ioCycleLaunch);
        }

        this.TIME_NEEDED =  CYCLES * 0.1;   //each cycle is 0.1 seconds
        this.timeLeft = CYCLES * 0.1;   //initializes amount of timeLeft
        this.cycleCurrent = CYCLES;

        this.ioRequestsSatisfied = 0;
        this.ioRequestsUnsatisfied = IO_REQUESTS; //all I/O Requests are unsatisfied on process create

        this.processDisplay.setText("<html>" +
                "Process ID: " + PROCESS_ID
                + "<br>Total CPU Time Needed: " + TIME_NEEDED
                + "<br>CPU Time Used: " + timeLeft
                + "<br>Number of I/O requests satisfied: " + this.ioRequestsSatisfied
                + "<br>Number of I/O requests unsatisfied: " + this.ioRequestsUnsatisfied
                + "<br>Current state: " + stateToString()
                + "</html>");
    }

    //Setters

    public void setPROCESS_SIZE(int PROCESS_SIZE) {
        this.PROCESS_SIZE = PROCESS_SIZE;
    }

    public void setPROCESS_ID(int PROCESS_ID) {
        this.PROCESS_ID = PROCESS_ID;
    }

    public void setPOINTER_INDEX(int POINTER_INDEX) {
        this.POINTER_INDEX = POINTER_INDEX;
    }

    public void setState(int state) {
        this.state = state;
        updateProcessDisplay();
    }

    public void setProgramCounter(int programCounter) {
        this.programCounter = programCounter;
    }

    public void setTIME_NEEDED(int TIME_NEEDED) {
        this.TIME_NEEDED = TIME_NEEDED;
    }

    public void setTimeUsed(int timeUsed) {
        this.timeUsed = timeUsed;
    }

    public void setTimeLeft(int timeLeft) {
        this.timeLeft = timeLeft;
    }

    public void setIO_REQUESTS(int IO_REQUESTS) {this.IO_REQUESTS = IO_REQUESTS;}

    public void setIoRequestsSatisfied(int ioRequestsSatisfied) {
        this.ioRequestsSatisfied = ioRequestsSatisfied;
    }

    public void setIoRequestsUnsatisfied(int ioRequestsUnsatisfied) {
        this.ioRequestsUnsatisfied = ioRequestsUnsatisfied;
    }

    //not needed in our Round Robin (priority is first come first serve)
//    public void setPriority(int priority) {
//        this.priority = priority;
//    }

    public void setARRIVAL_TIME(int arrival_time){this.ARRIVAL_TIME = arrival_time;}

    public void setCycleCurrent(int cycleCurrent){this.cycleCurrent = cycleCurrent;}


    //Getters
    public JLabel getProcessDisplay(){return processDisplay;}

    public int getPROCESS_SIZE() {
        return PROCESS_SIZE;
    }

    public int getPROCESS_ID() {
        return PROCESS_ID;
    }

    public int getPOINTER_INDEX() {return POINTER_INDEX;}

    public int getState() {
        return state;
    }

    public int getProgramCounter() {
        return programCounter;
    }

    public double getTIME_NEEDED() {
        return TIME_NEEDED;
    }

    public double getTimeUsed() {
        return timeUsed;
    }

    public double getTimeLeft() {
        return timeLeft;
    }

    public int getIO_REQUESTS() {
        return IO_REQUESTS;
    }

    public int getIoRequestsSatisfied() {
        return ioRequestsSatisfied;
    }

    public int getIoRequestsUnsatisfied() {
        return ioRequestsUnsatisfied;
    }

    //not needed in our Round Robin (priority is first come first serve)
//    public int getPriority() {
//        return priority;
//    }

    public double getARRIVAL_TIME(){return ARRIVAL_TIME;}

    public int getCycleCurrent(){return cycleCurrent;}


    public IORequest[] getIoRequests() {
        return ioRequests;
    }

    public Integer[] getCyclesForIOs() {
        return cyclesForIOs;
    }

    public int getCYCLES() {
        return CYCLES;
    }

}
