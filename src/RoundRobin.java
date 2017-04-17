import java.util.LinkedList;

/**
 *
 */
public class RoundRobin extends OperatingSystem{

    int TIME_QUANTUM = 1; //1 second for timeslice

    //adds a new process, arrival time should be current time
    public void addProcess(OSProcess enteringProcess, int arrivalTime){
        newProcesses.add(enteringProcess);
        enteringProcess.setARRIVAL_TIME(arrivalTime);
    }


}
