package ui;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

//遊戲主介面
public class Gameframe extends JFrame implements KeyListener, ActionListener {
    //子選單
    JMenuItem replayItem = new JMenuItem("重新遊戲");
    JMenuItem loginItem = new JMenuItem("重新登入");
    JMenuItem closeItem = new JMenuItem("關閉遊戲");
    JMenuItem girlItem = new JMenuItem("美女");
    //紀錄遊戲文件夾名、隨機值(先給預設)
    String pathfolder="girl/";
    Random rand = new Random();
    int randompath=1;
    int [][]arr = new int[4][4];
    //紀錄起始方塊在二維數組的位置，默認用第一張圖片
    int x,y=0;
    int count=0;
    int [][]win = {{1,2,3,4},
                   {5,6,7,8},
                   {9,10,11,12},
                   {13,14,15,16}
    };
    public Gameframe() {
        //設置窗口
        initJframe();
        //創建選單
        intiJmenuBar();
        //創建打亂數組
        initData();
        //初始化圖片
        intiImage();
        this.setVisible(true);
    }

    private void initData() {
        //打亂一維數組
        int [] temparr={1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16};
        for(int n=0;n<temparr.length;n++){
            int index = rand.nextInt(temparr.length);
            int temp = temparr[n];
            temparr[n] = temparr[index];
            temparr[index] = temp;
        }
        //將打亂的一維數組放到二維數組
        int index=0;
        for(int i=0;i<arr.length;i++){
            for(int j=0;j<arr[i].length;j++){
                if(temparr[index]==1){
                    x=i;
                    y=j;
                    arr[i][j]=temparr[index];
                }else {
                    arr[i][j]=temparr[index];
                }
                index++;
            }
        }
    }
    public void initJframe(){
        this.setSize(1000,900);
        this.setTitle("拼圖小遊戲");
        this.setAlwaysOnTop(true);
        //設置總是居中
        this.setLocationRelativeTo(null);
        //點選關閉就結束程式
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //取消默認的居中方式為了能夠正常布局在x,y軸
        this.setLayout(null);
        this.addKeyListener(this);
    }

    public void intiJmenuBar(){
        JMenuBar jMenuBar = new JMenuBar();
        JMenu functionjMenu = new JMenu("功能");
        JMenu aboutMenu = new JMenu("關於");
        JMenu typeMenu = new JMenu("類別");

        functionjMenu.add(replayItem);
        functionjMenu.add(loginItem);
        functionjMenu.add(closeItem);

        typeMenu.add(girlItem);

        jMenuBar.add(functionjMenu);
        jMenuBar.add(aboutMenu);
        jMenuBar.add(typeMenu);

        replayItem.addActionListener(this);
        loginItem.addActionListener(this);
        closeItem.addActionListener(this);
        girlItem.addActionListener(this);

        //設置選單
        this.setJMenuBar(jMenuBar);
    }
    public void intiImage(){
        //先清空現有圖片
        this.getContentPane().removeAll();

        if(isWin()){
            ImageIcon winImg = new ImageIcon("C:/ecworkspace/puzzlegame/win.jpg");
            Image scaledwinImage = winImg.getImage().getScaledInstance(100, 150, Image.SCALE_SMOOTH);
            JLabel winLabel = new JLabel(new ImageIcon(scaledwinImage));
            winLabel.setBounds(810, 320, 168, 200);
            this.getContentPane().add(winLabel);
        }
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                int num = arr[i][j];
//                ImageIcon originalIcon = new ImageIcon("C:\\ecworkspace\\puzzlegame\\123\\3\\"+num+".png");
                //路徑簡寫
                ImageIcon originalIcon = new ImageIcon("../puzzlegame/"+pathfolder+randompath+"/"+num+".png");
                Image scaledImage = originalIcon.getImage().getScaledInstance(168, 200, Image.SCALE_SMOOTH);
                JLabel jLabel = new JLabel(new ImageIcon(scaledImage));
                jLabel.setBounds(168*j+160, 165*i+70, 168, 200);
                if(num==1){
                    jLabel.setBorder(BorderFactory.createLineBorder(Color.blue, 6));
                }else {
                    jLabel.setBorder(new BevelBorder(BevelBorder.LOWERED));
                }

                this.getContentPane().add(jLabel);
            }
            JLabel stepCount = new JLabel("步數: "+count);
            stepCount.setBounds(50,30,100,20);
            this.getContentPane().add(stepCount);
        }
        //重新刷新
        this.getContentPane().repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if(code==65){
            this.getContentPane().removeAll();
            ImageIcon originalIcon = new ImageIcon("../puzzlegame/"+pathfolder+randompath+"/all"+".jpg");
            Image scaledImage = originalIcon.getImage().getScaledInstance(168*4, 800, Image.SCALE_SMOOTH);
            JLabel all = new JLabel(new ImageIcon(scaledImage));
            all.setBounds(160, 70, 700, 720);
            this.getContentPane().add(all);
            this.getContentPane().repaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(isWin()){
            return;
        }
        int code = e.getKeyCode();
        //左:37 上:38 右:39 下:40
        if(code==37){
            if(y==0){
                return;
            }
            arr[x][y] = arr[x][y-1];
            arr[x][y-1]=1;
            y--;
            count++;
            intiImage();
        }else if(code==38){
            /*按上這個動作代表把下面的數值往上移，同時X軸加一代表原本下面的圖片被取代成初始用來移動的圖片
              x++是因為下一輪我的初始圖片已經在下面要從這邊移動
             */
            if(x==0){
                return;
            }
            arr[x][y] = arr[x-1][y];
            arr[x-1][y]=1;
            x--;
            count++;
            intiImage();
        }else if(code==39){
            if(y==3){
                return;
            }
            arr[x][y] = arr[x][y+1];
            arr[x][y+1]=1;
            y++;
            count++;
            intiImage();
        }else if(code==40){
            if(x==3){
                return;
            }
            arr[x][y] = arr[x+1][y];
            arr[x+1][y]=1;
            x++;
            count++;
            intiImage();
        }//作弊 w
        else if(code==87) {
            arr = new int[][]{{1, 2, 3, 4},
                    {5, 6, 7, 8},
                    {9, 10, 11, 12},
                    {13, 14, 15, 16}
            };
            intiImage();
        }
    }

    public boolean isWin(){
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                if(arr[i][j]!=win[i][j]){
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        if(obj==replayItem){
            count=0;
            initData();
            intiImage();

        }else if(obj==loginItem){
            this.setVisible(false);
            new LoginJrame();
        }else if(obj==closeItem){
            System.exit(0);
        }else if(obj==girlItem){
            pathfolder ="girl/";
            randompath = rand.nextInt(10)+1;
            System.out.println("show path&number"+randompath+pathfolder);
            initData();
            intiImage();
        }
    }
}


