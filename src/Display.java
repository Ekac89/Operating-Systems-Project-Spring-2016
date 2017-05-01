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

    static JFrame displayFrame = new JFrame("Group 3 Operating System Simulator");
    static JPanel displayPanel;

    JButton step1butt;

    public Display(){
        this.displayPanel = new JPanel();
    }

    public JFrame getDisplayPanel(){
//        displayFrame.setTitle("Group 3 Operating System Simulator");
        this.displayPanel.setLayout(new BoxLayout(displayPanel, BoxLayout.PAGE_AXIS));

        this.displayPanel.add(new JLabel("Clock Time: " + OSClock.clock));

        this.displayPanel.add(new JLabel("**UNENTERED PROCESSES**"));
        for(OSProcess unenteredProcess : outsideProcesses){
            this.displayPanel.add(unenteredProcess.getProcessDisplay());
        }

        this.displayPanel.add(new JLabel("**NEW PROCESSES**"));
        for(OSProcess newProcess : newProcesses){
            this.displayPanel.add(newProcess.getProcessDisplay());
        }

        this.displayPanel.add(new JLabel("**READY QUEUE**"));
        for(OSProcess readyProcess : readyQueue){
            this.displayPanel.add(readyProcess.getProcessDisplay());
        }

        this.displayPanel.add(new JLabel("**BLOCKED PROCESSES**"));
        for(OSProcess blockedProcess : blocked){
            this.displayPanel.add(blockedProcess.getProcessDisplay());
        }

        this.displayPanel.add(new JLabel("**RUNNING PROCESS**"));
        for(OSProcess runningProcess : running){
            this.displayPanel.add(runningProcess.getProcessDisplay());
        }

        displayPanel.add(new JLabel("**FINISHED/EXITED PROCESSES**"));
        for(OSProcess exitedProcess : exited){
            this.displayPanel.add(exitedProcess.getProcessDisplay());
        }

        step1butt = new JButton();
        step1butt.setText("1 Step Forward");
        this.displayPanel.add(step1butt);

//        this.displayFrame.removeAll();
        this.displayFrame.add(this.displayPanel);
//        this.displayFrame.revalidate();
//        this.displayFrame.repaint();

        this.displayFrame.setSize(400, 500);
        this.displayFrame.setLocationRelativeTo(null);
        this.displayFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.displayFrame.setVisible(true);

        return this.displayFrame;
    }

    //updates the entire OS frame
    public void updateFrame(){
    }

}
