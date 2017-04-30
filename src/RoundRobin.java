import java.util.LinkedList;

/**
 * MAY DELETE THIS CLASS
 */
public class RoundRobin extends OperatingSystem{

    int TIME_QUANTUM = 1; //1 second for timeslice

//    //adds a new process, arrival time should be current time
//    public void addProcess(OSProcess enteringProcess, int arrivalTime){
//        newProcesses.add(enteringProcess);
//        enteringProcess.setARRIVAL_TIME(arrivalTime);
//        enteringProcess.setState(1); //makes sure process is set to new
//    }
//
//    public void setReady(OSProcess readyProcess){
//        readyQueue.add(readyProcess);
//        readyProcess.setState(2); //sets process to ready
//    }
//


    //going through processes and checking if it's time for them to enter
    public void arrivalCheck(){
        for(OSProcess outsideProcess : outsideProcesses) {
            if (outsideProcess.getARRIVAL_TIME() == OSClock.clock) {
                processesEntered++;
                outsideProcess.setPriority(processesEntered); //setting the priority if process is arriving based on how many processes have entered already (FCFS)
                newProcesses.add(outsideProcess); //entering process in system
                outsideProcesses.remove(outsideProcess); //removing from outside process list
            }
        }
    }

    //going through new processes to set them to ready
    public void newProcessCheck(){
        for(OSProcess newProcess : newProcesses){
            newProcess.setState(2); //setting to ready
            readyQueue.add(newProcess); //adding to ready queue
            newProcesses.remove(newProcess); //removing from new process queue
        }
    }

    //going through ready processes to figure out what to run
    public void readyQueueCheck(){
        for(OSProcess readyProcess : readyQueue){
//            if(readyProcess.priority<)
        }
    }
}
