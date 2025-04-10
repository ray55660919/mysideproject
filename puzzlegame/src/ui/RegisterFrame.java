package ui;

import javax.swing.*;
import java.awt.*;

//註冊介面
public class RegisterFrame extends JFrame {
    public RegisterFrame() {
        this.setSize(488,430);
        this.setTitle("註冊");
        this.setAlwaysOnTop(true);
        //設置總是居中
        this.setLocationRelativeTo(null);
        //點選關閉就結束程式
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
