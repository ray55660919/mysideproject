package test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyJframe extends JFrame implements ActionListener {

    //創建按鈕
    JButton jb =  new JButton("Click Me");
    JButton jb2 =  new JButton("Click Me!!");

    public MyJframe()  {
        this.setSize(603,680);
        this.setTitle("TestButton");
        this.setAlwaysOnTop(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);

        jb.setBounds(0,0,100,50);
        jb.addActionListener(this);

        jb2.setBounds(100,50,100,50);
        jb2.addActionListener(this);

        this.getContentPane().add(jb);
        this.getContentPane().add(jb2);

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("is clicked");
    }
}
