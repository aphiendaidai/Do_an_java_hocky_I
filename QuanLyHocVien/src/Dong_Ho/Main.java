package Dong_Ho;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class Main extends JFrame {
    public Main() {
        setTitle("Simple Clock");
        setSize(200, 150);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        JButton bt = new JButton("button");
        bt.setBounds(50,70,100,30);
        add(bt);
        Clock clock = new Clock();
        add(clock);
        clock.Start();
        bt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("clicked");
            }
        });
    }

    public static void main(String[] args) {
        new Main();
    }
}