package test;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MyJframe2 extends JFrame implements MouseListener {

    //創建按鈕
    JButton jb = new JButton("Click Me");

    public MyJframe2() {
        this.setSize(603, 680);
        this.setTitle("TestButton");
        this.setAlwaysOnTop(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);

        jb.setBounds(0, 0, 100, 50);
        jb.addMouseListener(this);

        this.getContentPane().add(jb);

        this.setVisible(true);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("點");
    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("按住");
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        System.out.println("放");
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        System.out.println("移入");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        System.out.println("移出");
    }
}

