import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;


/**
 *
 * Overall abstraction for the OS
 * Contains driver
 */
public class OperatingSystem extends RoundRobin{
    static int x = 0;
    static Object lock=new Object();
    static boolean isRunning = false;

    public static class Thr extends Thread
    {
        @Override
        public void run()
        {


            synchronized(lock)
            {
                isRunning=!isRunning;
            }
            if (isRunning == true)
            {
                while (true)
                {

                    if (exited.size() < ALL_PROCESSES.size()) { //while there are processes that haven't been entered
                        /**Fetch*/
                        osRoundRobin.enterProcess(); //increments processesEntered and takes top outsideProcess to new
                        osRoundRobin.newProcessToReady(); //takes new process and puts in ready
                        /**Check*/
                        osRoundRobin.readyQueueToRun(); //takes ready process and puts in run
                        /**Execute*/
                        osRoundRobin.run10Cycles(); //runs the process for 10 cycles + any I/O scheduled in those cycles
                        //running now empty, last run process at bottom of ready
                    }
                    synchronized(lock)
                    {
                        if (!isRunning)
                        {

                            break;
                        }
                    }
                }
                synchronized(lock)
                {
                    isRunning = false;
                }
            }
            isRunning = true;
        }
    }
    static Thr thread=null;
    static void ActionListener()
    {
        buttonUntilDone.addActionListener(new ActionListener()//ActionListener
        {
            public void actionPerformed(ActionEvent e)//Execute when button is pressed

            {
                if(thread==null)
                {
                    thread = new Thr(); // should add in an executor
                    thread.start();
                }
                else
                {

                    synchronized(lock)
                    {
                        isRunning=false;

                    }

                }
            }


        }); //ActionListenerEnd
    }




    public static int[][] ReadInProcess(int [][] processes){  // Create Process
        File file = new File("pullFromMe.txt");
        int i = 0;
        try {

            Scanner sc = new Scanner(file);

            while (sc.hasNextLine()) {
                String[] tokens = sc.nextLine().split(":");
                String last = tokens[tokens.length - 1];
                for (int j = 0; j < tokens.length ; j++) {
                    processes[i][j] = Integer.parseInt(tokens [j]);
                }
                i++;

            }
            sc.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return (processes);

    }

    public static RoundRobin osRoundRobin;

    public static void main(String[] args) {
        //Creating process array
        int[][] processes = new int[60][60];
        ReadInProcess(processes);

        //setting up OS
        osRoundRobin = new RoundRobin();
        getDisplayPanel();

        for (int i = 0; i < 60; i++) {
            OSProcess testProcess = new OSProcess(processes[i][0], processes[i][1], processes[i][3], processes[i][2]);

            for (int j = 0; j < testProcess.IO_REQUESTS; j++) {
                //I/O requests can take 25-50 cycles to complete
                int ioCyclesNeeded = ThreadLocalRandom.current().nextInt(25, 50);
                //randomly chooses a cycle within the cycles the process has to interrupt
                int ioCycleLaunch = ThreadLocalRandom.current().nextInt(1, testProcess.CYCLES);  //starts at cycle 1 until start of last cycle

                testProcess.ioRequests[j] = new IORequest(ioCyclesNeeded, ioCycleLaunch);
            }
            outsideProcesses.add(testProcess);
            ALL_PROCESSES.add(testProcess);
        }

        ActionListener();


        buttonStep1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (buttonStep1.isEnabled()) {
                    if (exited.size() < ALL_PROCESSES.size()) { //while there are processes that haven't been entered
                        /**Fetch*/
                        if (x == 0){
                        osRoundRobin.enterProcess(); //increments processesEntered and takes top outsideProcess to new
                        osRoundRobin.newProcessToReady(); //takes new process and puts in ready
                        /**Check*/
                        osRoundRobin.readyQueueToRun(); //takes ready process and puts in run
                        x=1;
                        /**Execute*/
                        }else if (x == 1) {
                            osRoundRobin.run10Cycles(); //runs the process for 10 cycles + any I/O scheduled in those cycles
                            //running now empty, last run process at bottom of ready
                        x=0;
                        }
                        }
                }
            }
        });
        buttonNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (buttonNew.isEnabled()) {
                    displayPanel.removeAll();
                    displayPanel.revalidate();
                    displayPanel.repaint();


                    buttonUntilDone.setText("Run Until Full Stop");
                    displayPanel.add(buttonUntilDone);
                    buttonStep1.setText("Step Forward Once");
                    displayPanel.add(buttonStep1);
                    buttonNew.setText("Display Unentered");
                    displayPanel.add(buttonNew);
                    buttonReady.setText("Display Exited");
                    displayPanel.add(buttonReady);

                    displayPanel.setLayout(new BoxLayout(displayPanel, BoxLayout.PAGE_AXIS));
                     JLabel unenteredLabel = new JLabel("**UNENTERED PROCESSES**");
                    unenteredLabel.setFont(unenteredLabel.getFont().deriveFont(15));
                    unenteredLabel.setForeground(Color.BLUE);
        displayPanel.add(unenteredLabel);
        for(OSProcess unenteredProcess : outsideProcesses){
            displayPanel.add(unenteredProcess.getProcessDisplay());
        }
                }

            }
        });

        buttonReady.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (buttonReady.isEnabled()) {
                    displayPanel.removeAll();
                    displayPanel.revalidate();
                    displayPanel.repaint();

                    buttonUntilDone.setText("Run Until Full Stop");
                    displayPanel.add(buttonUntilDone);
                    buttonStep1.setText("Step Forward Once");
                    displayPanel.add(buttonStep1);
                    buttonNew.setText("Display Unentered");
                    displayPanel.add(buttonNew);
                    buttonReady.setText("Display Exited");
                    displayPanel.add(buttonReady);

                    displayPanel.setLayout(new BoxLayout(displayPanel, BoxLayout.PAGE_AXIS));
                    JLabel exitedLabel = new JLabel("**FINISHED/EXITED PROCESSES**");
                    exitedLabel.setFont(exitedLabel.getFont().deriveFont(15));
                    exitedLabel.setForeground(Color.BLUE);
                    displayPanel.add(exitedLabel);
        for(OSProcess exitedProcess : exited){
            displayPanel.add(exitedProcess.getProcessDisplay());
        }        displayPanel.add(new JLabel("**FINISHED/EXITED PROCESSES**"));

                }
            }
        });
    }


}
