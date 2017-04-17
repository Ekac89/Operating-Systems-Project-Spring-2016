import javax.swing.*;
import java.awt.*;

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
public class Display extends Frame{

//    public static void displayActiveProcess(OperatingSystem systemCurrent){ //takes in current state of operating system
//
//    }
//
//
//    public static void main(String[] args) {
//        JFrame frame = new JFrame("Group 3 Operating System Simulator");
//
//        JPanel panel = new JPanel();
//        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));  //layout
//
//       // JLabel label = new JLabel("label");
//
//        JButton button = new JButton();
//        button.setText("worthless button that will eventually make button");
//
//        panel.add(new JLabel("OSProcess Size: "));
//        panel.add(new JTextField(2));
//        panel.add(new JLabel("Time needed for process: "));
//        panel.add(new JTextField(2));
//        panel.add(new JLabel("I/O Requests Needed: "));
//        panel.add(new JTextField(2));
//        panel.add(new JLabel("OSProcess Size: "));
//        panel.add(new JTextField(2));
//
//
//        panel.add(button);
//
//
//        frame.add(panel);
//        frame.setSize(400, 500);
//        frame.setLocationRelativeTo(null);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setVisible(true);
//
//    }
}
