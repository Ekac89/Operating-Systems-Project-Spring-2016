import java.util.LinkedList;

/**
 * MAY DELETE THIS CLASS
 */
public class RoundRobin extends OperatingSystem{

    int TIME_QUANTUM = 1; //1 second for timeslice

    //adds a new process, arrival time should be current time
    public void addProcess(OSProcess enteringProcess, int arrivalTime){
        newProcesses.add(enteringProcess);
        enteringProcess.setARRIVAL_TIME(arrivalTime);
        enteringProcess.setState(1); //makes sure process is set to new
    }

    public void setReady(OSProcess readyProcess){
        readyQueue.add(readyProcess);
        readyProcess.setState(2); //sets process to ready
    }


}
