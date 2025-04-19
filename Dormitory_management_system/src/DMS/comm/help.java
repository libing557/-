package DMS.comm;

import javax.swing.*;
import java.awt.*;

public class help extends JPanel {

    JLabel j1,j2,j3,j4,j5,j6;
    Font font = new Font("宋体", Font.BOLD|Font.ITALIC, 20);
    public help() {
        setLayout(null);
        j3=new JLabel("没什么可帮助的，按照指示来就行");
        j4=new JLabel("这页是凑数的");

        /*j1.setBounds(150,40,400,25);
        j2.setBounds(150,100,400,25);*/
        j3.setBounds(150,160,400,25);
        j4.setBounds(210,220,400,25);

        /*j1.setFont(font);
        j2.setFont(font);*/
        j3.setFont(font);
        j4.setFont(font);

        add(j3);
        add(j4);


    }
}
