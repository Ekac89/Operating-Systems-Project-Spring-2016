import java.util.concurrent.ThreadLocalRandom;

/**
 *
 *
 *
 */
public class OSProcess{
    int PROCESS_SIZE; //memory size of process (can be 1-8MB in size)

    //OSProcess Control Block (PCB) *****************************
    int processNumber; //unique identifier for each process (at most 60 processes)
    int pointerIndex; //array index that represents location in memory
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
    double TIME_NEEDED; //total amount of time this process needs in seconds
    double timeUsed; //amount of time this process has already used
    double timeLeft; //amount of time this process has left
                //timeLeft = TIME_NEEDED - timeUsed


    int priority; //queue position
    //*************************************

    int ARRIVAL_TIME; //will be set once when it arrives in RoundRobin readyQueue
    int CYCLES; //amount of CYCLES this process needs (can be 10-950)
                        //each cycle is 0.1 seconds, so 10 CYCLES per timeslice/timeQuantum in RoundRobin

    int cyclesUsed;
    int cyclesLeft;

    boolean complete;


    public OSProcess(int memorySize, int ioRequests, int cycles, int arrivalTime, int processID){
        this.state = 1; //process starts as 1 = new
        this.processNumber = processID; //no more than 60, should be incremented by current processes entered
        //TODO: instantiate pointer index
        //TODO: instantiate program counter

        this.PROCESS_SIZE = memorySize;
        this.IO_REQUESTS = ioRequests;
        this.CYCLES = cycles;
        this.ARRIVAL_TIME = arrivalTime;

        this.TIME_NEEDED =  cycles * 0.1;   //each cycle is 0.1 seconds
        this.timeLeft = cycles * 0.1;   //initializes amount of timeLeft
        this.cyclesUsed = cycles;

        this.ioRequestsSatisfied = 0;
        this.ioRequestsUnsatisfied = ioRequests; //all I/O Requests are unsatisfied on process create
    }


    public void runOneCycle(){
        //first, checks for I/O interrupt
        for(int i =0; i<IO_REQUESTS; i++){
            if(ioRequests[i].getPRCS_CYCLE_LAUNCH() == this.cyclesUsed){
                ioRequests[i].runIO();
                ioRequestsUnsatisfied--;
                ioRequestsSatisfied++;
            }
        }

        this.cyclesUsed++;
        this.cyclesLeft--;
        this.timeUsed = timeUsed + 0.1;
        this.timeLeft = timeLeft - 0.1;

        OSClock.clock += 0.1; //adds one cycle to overall system clock

        if(cyclesLeft == 0){    //TODO: doesn't check if I/O requests done
            complete = true;
        }
    }


    //Setters

    public void setPROCESS_SIZE(int PROCESS_SIZE) {
        this.PROCESS_SIZE = PROCESS_SIZE;
    }

    public void setProcessNumber(int processNumber) {
        this.processNumber = processNumber;
    }

    public void setPointerIndex(int pointerIndex) {
        this.pointerIndex = pointerIndex;
    }

    public void setState(int state) {
        this.state = state;
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

    public void setIO_REQUESTS(int IO_REQUESTS) {
        this.IO_REQUESTS = IO_REQUESTS;
    }

    public void setIoRequestsSatisfied(int ioRequestsSatisfied) {
        this.ioRequestsSatisfied = ioRequestsSatisfied;
    }

    public void setIoRequestsUnsatisfied(int ioRequestsUnsatisfied) {
        this.ioRequestsUnsatisfied = ioRequestsUnsatisfied;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setARRIVAL_TIME(int arrival_time){this.ARRIVAL_TIME = arrival_time;}

    public void setCyclesUsed(int cyclesUsed){this.cyclesUsed = cyclesUsed;}


    //Getters
    public int getPROCESS_SIZE() {
        return PROCESS_SIZE;
    }

    public int getProcessNumber() {
        return processNumber;
    }

    public int getPointerIndex() {
        return pointerIndex;
    }

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

    public int getPriority() {
        return priority;
    }

    public int getARRIVAL_TIME(){return ARRIVAL_TIME;}

    public int getCyclesUsed(){return cyclesUsed;}


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
