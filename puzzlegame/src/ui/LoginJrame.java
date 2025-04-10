package ui;

import javax.swing.*;
import java.awt.*;

//登入介面
public class LoginJrame extends JFrame {

    public LoginJrame() {
        this.setSize(488,430);
        this.setTitle("登入");
        this.setAlwaysOnTop(true);
        //設置總是居中
        this.setLocationRelativeTo(null);
        //點選關閉就結束程式
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
