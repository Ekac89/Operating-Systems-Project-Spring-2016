/**
 * Created by Marjorie on 3/18/2017.
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



}
