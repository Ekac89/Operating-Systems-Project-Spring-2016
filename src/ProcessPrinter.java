import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ProcessPrinter {

    static FileWriter writer = null;
    static FileWriter Pullwriter = null;
    public static void main(String[] args) {

        try {
            writer = new FileWriter("final.txt");
            Pullwriter = new FileWriter("pullFromMe.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Pullwriter = new FileWriter("pullFromMe.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        int tester = 60;
        int id = 0;
        for (int test = 0; test < tester; test++) {
            id ++;
            createProcess(id, writer, Pullwriter);
        }
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }        try {
            Pullwriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int createProcess(int id, FileWriter writer, FileWriter Pullwriter){  // Create Process


        Random randomGenerator = new Random();

        //PrintWriter out = new PrintWriter("filename.txt");
        //process size can be 1-8
        int memorySize = (randomGenerator.nextInt(8) + 1);
        //process can have 0 to 5 I/O requests
        int ioRequests = (randomGenerator.nextInt(6));
        //process takes between 10-950 CYCLES to complete
        int cycles = (randomGenerator.nextInt(941) + 10);


        try {
            Print(id, memorySize, ioRequests, cycles, writer, Pullwriter);
        } catch (IOException e) {
            e.printStackTrace();
        }


        return id;
    }
    public static int Print(int id, int memorySize, int ioRequests, int cycles, FileWriter writer, FileWriter Pullwriter) throws IOException { // Print to doc
        String newLine = System.getProperty("line.separator");
        try {
            writer.write("Process Id: " + id + " memory: " + memorySize + " Cycle: " + cycles  + " IO Request: " + ioRequests + "  " + newLine);

            Pullwriter.write(id + ":" + memorySize + ":" + cycles  + ":" + ioRequests + newLine);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return (id);
    }
    public static int[][] ReadInProcess(int id, FileWriter writer){  // Create Process
        File file = new File("10_Random");
        int [][] processes;
        try {

            Scanner sc = new Scanner(file);

            while (sc.hasNextLine()) {
                int i = sc.nextInt();
                System.out.println(i);
            }
            sc.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }




        return (processes);

    }
}
