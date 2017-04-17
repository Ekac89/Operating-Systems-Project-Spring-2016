import java.util.LinkedList;

/**
 *
 */
public class RoundRobin{

    int TIME_QUANTUM = 1; //1 second for timeslice


    LinkedList<OSProcess> requestQueue = new LinkedList<>();

    public void addProcess(OSProcess enteringProcess, int arrivalTime){
        requestQueue.add(enteringProcess);
        enteringProcess.setARRIVAL_TIME(arrivalTime);


    }


}
