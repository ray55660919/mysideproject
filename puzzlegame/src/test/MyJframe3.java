package test;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MyJframe3 extends JFrame implements KeyListener {

    //創建按鈕
    JButton jb = new JButton("Click Me");

    public MyJframe3() {
        this.setSize(603, 680);
        this.setTitle("TestButton");
        this.setAlwaysOnTop(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);

        jb.setBounds(0, 0, 100, 50);
        jb.addKeyListener(this);

        this.getContentPane().add(jb);

        this.setVisible(true);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("按住松");
    }

    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println("鬆開");
        int code = e.getKeyCode();
        System.out.println(code);
    }
}

