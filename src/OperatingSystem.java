/**
 *
 * Overall abstraction for the OS
 */
public class OperatingSystem {
    int clock; //current objective time
    int currentProcessNumber; //processNumber for current process
    Process currentProcess;

    //sets up OS
    public void startUp(){
        clock = 0;
        currentProcessNumber = 0;

    }


    //current process
    public void runningProcess(Process currentProcess){

    }
}
