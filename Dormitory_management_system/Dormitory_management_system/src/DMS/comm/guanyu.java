package DMS.comm;

import javax.swing.*;
import java.awt.*;

public class guanyu extends JPanel {

    JLabel j1,j2,j3,j4,j5,j6;
    Font font = new Font("宋体", Font.BOLD|Font.ITALIC, 20);
    public guanyu() {
        setLayout(null);
        j1=new JLabel("系统名称:校园宿舍管理系统");
        j2=new JLabel("实现方式:javaSE，Swing，JDBC技术");
        j3=new JLabel("实现人:XX");
        j4=new JLabel("实现平台:郑州轻工业大学");
//        j5=new JLabel("指导老师:王晨羽老师");
//        j6=new JLabel("实现时间:2020/06/13");

        j1.setBounds(180,40,400,25);
        j2.setBounds(180,100,400,25);
        j3.setBounds(180,160,400,25);
        j4.setBounds(180,220,400,25);
//        j5.setBounds(180,280,400,25);
//        j6.setBounds(180,340,400,25);
        j1.setFont(font);
        j2.setFont(font);
        j3.setFont(font);
        j4.setFont(font);
//        j5.setFont(font);
//        j6.setFont(font);
        add(j1);
        add(j2);
        add(j3);
        add(j4);
//        add(j5);
//        add(j6);

    }
}
