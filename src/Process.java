/**
 *
 *
 *
 */
public class Process {
    int PROCESS_SIZE; //memory size of process (can be 1-8MB in size)

    //Process Control Block (PCB) *****************************
    int processNumber; //unique identifier for each process
    int pointerIndex; //array index that represents location in memory
    int state; //number represents process' current state:
                    //1 = new
                    //2 = ready
                    //3 = running
                    //4 = blocked
                    //5 = exit
    int programCounter; //represents next memory location
    //**********************************************************
    //Display -- this information needs to be displayed for each process
    int TIME_NEEDED; //total amount of time this process needs
    int timeUsed; //amount of time this process has already used
    int timeLeft; //amount of time this process has left
                //timeLeft = TIME_NEEDED - timeUsed

    int IO_REQUESTS; //number of I/O requests this process will execute (can be 0-5)
    int satisfiedIORequests; //number of satisfied I/O requests
    int outstandingIORequests; //number of I/O requests left

    int priority; //queue position
    //*************************************


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

    public void setSatisfiedIORequests(int satisfiedIORequests) {
        this.satisfiedIORequests = satisfiedIORequests;
    }

    public void setOutstandingIORequests(int outstandingIORequests) {
        this.outstandingIORequests = outstandingIORequests;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }


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

    public int getTIME_NEEDED() {
        return TIME_NEEDED;
    }

    public int getTimeUsed() {
        return timeUsed;
    }

    public int getTimeLeft() {
        return timeLeft;
    }

    public int getIO_REQUESTS() {
        return IO_REQUESTS;
    }

    public int getSatisfiedIORequests() {
        return satisfiedIORequests;
    }

    public int getOutstandingIORequests() {
        return outstandingIORequests;
    }

    public int getPriority() {
        return priority;
    }

}
