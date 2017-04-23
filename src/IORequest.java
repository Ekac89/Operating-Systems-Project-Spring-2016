/**
 * represents a single IO Request and holds:
 * -amount of time and CYCLES an IO Request needs
 * -if it is satisfied or not
 *
 */
public class IORequest{
    int CYCLES; //number of CYCLES needed to be finished (can be 25-50)
    boolean complete;

    double TIME_NEEDED;//calculated from CYCLES (TIME_NEEDED = CYCLES*0.1)

    int PRCS_CYCLE_LAUNCH; //which cycle within its containing processes' lifespan will it launch in
                                //so, it will launch when its processes' reaches this cycle

    public IORequest(int cyclesNeeded, int cycleLaunch){
        this.complete = false;
        this.CYCLES = cyclesNeeded;
        this.PRCS_CYCLE_LAUNCH = cycleLaunch;

        this.TIME_NEEDED = cyclesNeeded * 0.1;
    }

    //I/O request is run to completion
    public void runIO(){
        OSClock.clock += getTIME_NEEDED();    //adds time the I/O needed to overall system clock
        this.complete = true;
    }

    //getters
    public int getCYCLES() {
        return CYCLES;
    }

    public boolean isComplete() {
        return complete;
    }

    public double getTIME_NEEDED() {
        return TIME_NEEDED;
    }

    public int getPRCS_CYCLE_LAUNCH(){return PRCS_CYCLE_LAUNCH;}

    //setters
    public void setCYCLES(int CYCLES) {
        this.CYCLES = CYCLES;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public void setTIME_NEEDED(int TIME_NEEDED) {
        this.TIME_NEEDED = TIME_NEEDED;
    }

    public void setPRCS_CYCLE_LAUNCH(int cycleLaunchTime){this.PRCS_CYCLE_LAUNCH=cycleLaunchTime;}
}
