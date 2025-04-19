package DMS;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 */
public class Exit extends JPanel {
    JButton exit=new JButton("安全退出");

    JLabel j1,j2,j3,j4,j5,j6;
    public Exit(){
        setLayout(null);
        exit.setBounds(260,230,100,25);
        j3=new JLabel("z z u l i");
        j3.setFont(new Font("方正舒体", Font.ITALIC, 100));
        j3.setBounds(180,0,400,300);
        add(exit);
        add(j3);



        exit.addActionListener(new ActionListener(){		//为重置按钮添加监听事件
            //同时清空name、password的数据
            public void actionPerformed(ActionEvent arg0) {
                // TODO 自动生成方法存根
                //login.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                System.exit(0);//很明显   这里是不够完善的................不知道怎么关闭上个页面
            }
        });
    }


}

