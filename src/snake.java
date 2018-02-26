
/**
 * Created by David on 11/24/2014.
 */

import java.awt.*;
import javax.swing.*;


public class snake extends JFrame {

    public snake() {

        add(new board());

        setResizable(false);
        pack();

        setTitle("Slimy the Snake");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    public static void main(String[] args) {
        //Intro Window for User will time out 5 seconds from starting program
        JWindow window = new JWindow();
        window.getContentPane().add(
                new JLabel("", new ImageIcon("Intro.png"), SwingConstants.CENTER));
        window.setBounds(300, 200, 300, 300);
        window.setVisible(true);
        window.setLocationRelativeTo(null);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        window.setVisible(false);

        //JFrame for Instructions Menu
        JFrame frame = new JFrame();
        frame.add(new JTextArea("Instructions:\n" +
                "UP = Up Arrow \n" +
                "DOWN = Down Arrow \n" +
                "LEFT = Left Arrow \n" +
                "RIGHT = Right Arrow"));
        frame.setVisible(true);
        frame.setSize(180, 150);
        frame.setLocation(600, 0);
        window.dispose();

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame ex = new snake();
                ex.setVisible(true);
            }
        });
    }

}
