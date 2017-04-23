import javax.swing.*;
import java.awt.*;

/** Object that holds variables that will be displayed/updated
 *
 *
 *GUI for our OS simulator
 *
 * For each active process the following information displayed:
 *  -amount of CPU time needed to complete
 *  -amount of CPU time already used
 *  -priority (if relevant)
 *  -number of I/O requests satisfied
 *  -number of outstanding I/O requests
 *
 * Display changes for:
 *  -new process entering system
 *  -a processes state change
 *  -I/O request made
 *  -I/O request completed
 *  -memory allocated
 *  -memory deallocated
 *  -process exits system
 */


public class Display extends OperatingSystem{

    JFrame displayFrame;
    JPanel displayPanel;


    public Display(){
        displayFrame.setTitle("Group 3 Operating System Simulator");
        displayPanel.add(new JLabel("Clock Time: " + OSClock.clock));

        displayPanel.add(new JLabel("**UNENTERED PROCESSES**"));
        for(OSProcess unenteredProcess : outsideProcesses){
            displayPanel.add(unenteredProcess.getProcessDisplay());
        }

        displayPanel.add(new JLabel("**NEW PROCESSES**"));
        for(OSProcess newProcess : newProcesses){
            displayPanel.add(newProcess.getProcessDisplay());
        }

        displayPanel.add(new JLabel("**READY QUEUE**"));
        for(OSProcess readyProcess : readyQueue){
            displayPanel.add(readyProcess.getProcessDisplay());
        }

        displayPanel.add(new JLabel("**BLOCKED PROCESSES**"));
        for(OSProcess blockedProcess : blocked){
            displayPanel.add(blockedProcess.getProcessDisplay());
        }

        displayPanel.add(new JLabel("**RUNNING PROCESS**"));
        for(OSProcess runningProcess : running){
            displayPanel.add(runningProcess.getProcessDisplay());
        }

        displayPanel.add(new JLabel("**FINISHED/EXITED PROCESSES**"));
        for(OSProcess exitedProcess : exited){
            displayPanel.add(exitedProcess.getProcessDisplay());
        }
    }

}
