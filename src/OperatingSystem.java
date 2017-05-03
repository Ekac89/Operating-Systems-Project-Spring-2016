import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

/**
 *
 * Overall abstraction for the OS
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
    static Object lock2=new Object();
    static boolean isRunning2 = false;
    public static class Thr2 extends Thread
    {
       // @Override
        public void run()
        {


            synchronized(lock2)
            {
                isRunning2=!isRunning2;
            }
            if (isRunning2 == true)
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
                    synchronized(lock2)
                    {
                        if (!isRunning2)
                        {


                        }
                    }

                synchronized(lock2)
                {
                    isRunning2 = false;
                }
            }
            isRunning2 = true;
        }
    }
    static Thr2 thread2=null;
    static void ActionListener2() {
        buttonStep1.addActionListener(new ActionListener()//ActionListener
        {
            public void actionPerformed(ActionEvent e)//Execute when button is pressed

            {
                if (thread2 == null) {
                    thread2 = new Thr2(); // should add in an executor
                    thread2.start();
                } else {

                    synchronized (lock2) {
                        isRunning2 = false;

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
                //String i = sc.nextLine();
                //System.out.println(i);
                String[] tokens = sc.nextLine().split(":");
                String last = tokens[tokens.length - 1];
                for (int j = 0; j < tokens.length ; j++) {
                    processes[i][j] = Integer.parseInt(tokens [j]);
                    //System.out.println(tokens [j]);
                }
                i++;

            }
            sc.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("print lbl");
        for (i = 0; i < 60; i++) {
            for (int j = 0; j < 4 ; j++) {
                System.out.print(processes[i][j]);
            }
            System.out.println();
        }



        return (processes);

    }

    //sets up OS and initializes all needed objects/lists
    public void OSStartUp(){
        outsideProcesses = new ArrayList<>();
        newProcesses = new ArrayList<>();
        readyQueue = new ArrayList<>();
        blocked = new ArrayList<>();
        exited = new ArrayList<>();
        running = new ArrayList<>();

        OSClock.clock = 0;

    }


    /**
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

    public static RoundRobin osRoundRobin;

    public void Reload(){
        displayPanel.removeAll();
        displayPanel.revalidate();
        displayPanel.repaint();

        buttonUntilDone.setText("Run Until Full Stop");
        displayPanel.add(buttonUntilDone);
        buttonStep1.setText("Step Forward Once");
        displayPanel.add(buttonStep1);
        buttonNew.setText("Display New");
        displayPanel.add(buttonNew);
        buttonReady.setText("Display ready");
        displayPanel.add(buttonReady);
    }

    public static void main(String[] args) {
        //Creating process array
        int[][] processes = new int[60][60];
        ReadInProcess(processes);

        //setting up OS
        osRoundRobin = new RoundRobin();
        System.out.println("******SET UP EMPTY ROUND ROBIN****");
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

        ActionListener2();





//        /**Automated Round Robin*/
//        //trying out automated round robin algorithm, should go through everything
//        int whileLoopCount =0;
//        while (exited.size() < ALL_PROCESSES.size()){ //while there are processes that haven't been entered
//            System.out.println();
//            System.out.println("While loop count: " + whileLoopCount);
//            System.out.println();
//            //TODO: implement method to check for memory, not just add them in
////         if(checkForNewProcess()){ //if enough memory
//            /**Fetch*/
//                osRoundRobin.enterProcess(); //increments processesEntered and takes top outsideProcess to new
//                osRoundRobin.newProcessToReady(); //takes new process and puts in ready
//            /**Check*/
//                osRoundRobin.readyQueueToRun(); //takes ready process and puts in run
//            /**Execute*/
//                osRoundRobin.run10Cycles(); //runs the process for 10 cycles + any I/O scheduled in those cycles
//                //running now empty, last run process at bottom of ready
////          }else{ //if not enough memory
////              osRoundRobin.newProcessToReady(); //if there is a new process, newProcessToReady() will set it to ready
////              osRoundRobin.readyQueueToRun();
////              osRoundRobin.run10Cycles();
////          }
////
//        }//end auto round robin

//        buttonUntilDone.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                if (buttonUntilDone.isEnabled()) {
//                    /**Automated Round Robin*/
//                    //trying out automated round robin algorithm, should go through everything
//
//                    while (exited.size() < ALL_PROCESSES.size()) { //while there are processes that haven't been entered
//                        System.out.println();
//                        /**Fetch*/
//                        osRoundRobin.enterProcess(); //increments processesEntered and takes top outsideProcess to new
//                        osRoundRobin.newProcessToReady(); //takes new process and puts in ready
//                        /**Check*/
//                        osRoundRobin.readyQueueToRun(); //takes ready process and puts in run
//                        /**Execute*/
//                        osRoundRobin.run10Cycles(); //runs the process for 10 cycles + any I/O scheduled in those cycles
//                        //running now empty, last run process at bottom of ready
//                        try {
//                            Thread.sleep(500);
//                        } catch (InterruptedException e1) {
//                            e1.printStackTrace();
//                        }
//                    }//end auto round robin
//                }
//            }
//        });
//        buttonStep1.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                if (buttonStep1.isEnabled()) {
//                    if (exited.size() < ALL_PROCESSES.size()) { //while there are processes that haven't been entered
//                        /**Fetch*/
//                        osRoundRobin.enterProcess(); //increments processesEntered and takes top outsideProcess to new
//                        osRoundRobin.newProcessToReady(); //takes new process and puts in ready
//                        /**Check*/
//                        osRoundRobin.readyQueueToRun(); //takes ready process and puts in run
//                        /**Execute*/
//                        osRoundRobin.run10Cycles(); //runs the process for 10 cycles + any I/O scheduled in those cycles
//                        //running now empty, last run process at bottom of ready
//                    }
//                }
//            }
//        });
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
                     System.out.println("**UNENTERED PROCESSES**");
                     JLabel unenteredLabel = new JLabel("**UNENTERED PROCESSES**");
                    unenteredLabel.setFont(unenteredLabel.getFont().deriveFont(15));
                    unenteredLabel.setForeground(Color.BLUE);
        displayPanel.add(unenteredLabel);
        for(OSProcess unenteredProcess : outsideProcesses){
            displayPanel.add(unenteredProcess.getProcessDisplay());
           // System.out.println(unenteredProcess.processDisplayToString());
            //System.out.println();
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
        //System.out.println("**FINISHED/EXITED PROCESSES**");
                    JLabel exitedLabel = new JLabel("**FINISHED/EXITED PROCESSES**");
                    exitedLabel.setFont(exitedLabel.getFont().deriveFont(15));
                    exitedLabel.setForeground(Color.BLUE);
                    displayPanel.add(exitedLabel);
        for(OSProcess exitedProcess : exited){
            displayPanel.add(exitedProcess.getProcessDisplay());
           // System.out.println(exitedProcess.processDisplayToString());
            //System.out.println();
        }        displayPanel.add(new JLabel("**FINISHED/EXITED PROCESSES**"));

                }
            }
        });
    }


}
