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

    public static void displayActiveProcess(OperatingSystem systemCurrent){ //takes in current state of operating system

    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("G3 Operating System Simulator");

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());  //layout

        JLabel label = new JLabel("label"); //

        JButton button = new JButton();
        button.setText("worthless button");

        panel.add(label);
        panel.add(button);

        frame.add(panel);
        frame.setSize(300, 300);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }
}
