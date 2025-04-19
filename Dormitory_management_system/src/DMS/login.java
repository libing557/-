package DMS;

import DMS.SYSTEM.CooBuilding;
import DMS.SYSTEM.CooDormitory;
import DMS.SYSTEM.CooStu;
import DMS.SYSTEM.CooUsers;
import DMS.comm.guanyu;
import DMS.comm.help;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**登录界面
 * @author LQ
 * @create 2020-6-9 15:58
 */

public class login extends JFrame implements ActionListener {
    JLabel welcome=new JLabel("欢迎使用校园宿舍管理系统");
    JLabel user, password;
    JTextField username;
    JPasswordField passwordField;
    JButton loginButton;
    JButton button;
    CardLayout cardLayout = new CardLayout();
    JPanel card;
    JPanel cardPanel,cardPanel2,cardPanel3,cardPanel4;
    JTabbedPane jTabbedPane,jTabbedPane2;
    int type=1;
    Users users;
    Font font = new Font("宋体", Font.BOLD|Font.ITALIC, 20);

    public login() {
        init();
    }

    private void init() {

        welcome.setFont(font);
        setTitle("宿舍管理系统");
        setLayout(new BorderLayout());
        user = new JLabel("用户名");
        password = new JLabel("密码");
        button = new JButton("重置");
        card = new JPanel(cardLayout);

        JPanel panel1 = new JPanel(new BorderLayout());

        username = new JTextField();
        passwordField = new JPasswordField();
        loginButton = new JButton("Login!");
        loginButton.addActionListener(this);

        JPanel titlepanel = new JPanel(new FlowLayout());//标题面板
        JPanel loginpanel = new JPanel();//登录面板
        loginpanel.setLayout(null);

        welcome.setBounds(300,100,400,25);
        user.setBounds(340, 170, 50, 20);
        password.setBounds(340, 210, 50, 20);
        username.setBounds(390, 170, 120, 20);
        passwordField.setBounds(390, 210, 120, 20);
        loginButton.setBounds(340, 250, 80, 25);
        button.setBounds(430,250,80,25);

        loginpanel.add(welcome);
        loginpanel.add(user);
        loginpanel.add(password);
        loginpanel.add(username);
        loginpanel.add(passwordField);
        loginpanel.add(loginButton);
        loginpanel.add(button);

        panel1.add(titlepanel, BorderLayout.NORTH);
        panel1.add(loginpanel, BorderLayout.CENTER);
        //panel1.add(loginButton, BorderLayout.SOUTH);

        card.add(panel1, "login");
        //card.add(cardPanel, "info");

        add(card);
        setBounds(300, 100, 900, 600);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        button.addActionListener(new ActionListener(){		//为重置按钮添加监听事件
            //同时清空name、password的数据
            public void actionPerformed(ActionEvent arg0) {
                // TODO 自动生成方法存根
                username.setText("");
                passwordField.setText("");
            }
        });
    }

    public static void main(String[] args) {
        new login();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        boolean flag=false;//用来标志用户是否正确

        if (e.getSource() == loginButton) {
            ArrayList<Users> list = new CheckUsers().getUsers();//获得所有用户信息
            for (int i = 0; i < list.size(); i++) {//遍历所有用户信息，以此来判断输入的信息是否正确
                users = list.get(i);
                String passwordStr = new String(passwordField.getPassword());
                if (username.getText().equals(users.getName()) && passwordStr.equals(users.getPassword())) {
                    if(users.getType()==1){//如果时学生
                        type=users.getType();
                        System.out.println("登录人员类别"+type);
                        JOptionPane.showMessageDialog(null, "欢迎(学生)"+username.getText()+"登录", "学生宿舍管理系统", JOptionPane.PLAIN_MESSAGE);
                        //当输入的信息正确时，就开始加载选项卡界面，并把选项卡界面加入到卡片布局器中
                        StudentInfo studentInfo=new StudentInfo(users,type);//学生信息
                        //DormitoryInfo dormitoryInfo = new DormitoryInfo(users,type);//宿舍信息
                        AddLater later = new AddLater(type,users);//晚归信息
                        guanyu guanyu=new guanyu();
                        help help=new help();
                        Exit exit=new Exit();
                        cardPanel = new JPanel();
                        cardPanel2 = new JPanel();
                        cardPanel3 = new JPanel();
                        cardPanel4 = new JPanel();

                        jTabbedPane = new JTabbedPane(JTabbedPane.LEFT);
                        //jTabbedPane.add("宿舍信息", dormitoryInfo);
                        jTabbedPane.add("学生信息",studentInfo);
                        //jTabbedPane.add("学生离校与返校", outAndIn);
                        jTabbedPane.add("缺寝记录", later);
                        //jTabbedPane.add("宿舍物品", things);
                        jTabbedPane.add("退出登录", exit);
                        cardPanel.add(jTabbedPane);

                        jTabbedPane2 = new JTabbedPane(JTabbedPane.TOP);
                        jTabbedPane2.add("系统操作页面",cardPanel);
                        jTabbedPane2.add("关于系统",guanyu);
                        jTabbedPane2.add("帮助",help);
                        cardPanel2.add(jTabbedPane2);


                        card.add(cardPanel2, "info");
                        cardLayout.show(card, "info");//输入信息正确就显示操作界面，否则重新输入正确信息
                        ////////////////////////////////////////

                    }else if (users.getType()==2){//如果时宿管
                        type=users.getType();
                        System.out.println("登录人员类别"+type);
                        JOptionPane.showMessageDialog(null, "欢迎(宿管)"+username.getText()+"登录", "学生宿舍管理系统", JOptionPane.PLAIN_MESSAGE);
                        //当输入的信息正确时，就开始加载选项卡界面，并把选项卡界面加入到卡片布局器中
                        StudentInfo studentInfo=new StudentInfo(users,type);//学生信息
                        CooDormitory dormitoryInfo = new CooDormitory(type,users);//宿舍信息
                        AddLater later = new AddLater(type,users);//晚归信息
                        editLater editLater=new editLater(type,users);//修改缺寝信息
                        DeleteLater deleteLater=new DeleteLater(type,users);//删除
                        SeekLater seekLater=new SeekLater(type,users);
                        guanyu guanyu=new guanyu();
                        help help=new help();
                        Exit exit=new Exit();//退出系统
                        cardPanel = new JPanel();
                        cardPanel2 = new JPanel();
                        cardPanel3 = new JPanel();
                        cardPanel4 = new JPanel();

                        cardPanel = new JPanel();
                        jTabbedPane = new JTabbedPane(JTabbedPane.LEFT);
                        jTabbedPane.add("学生信息",studentInfo);
                        jTabbedPane.add("宿舍信息", dormitoryInfo);
                        //jTabbedPane.add("学生离校与返校", outAndIn);
                        jTabbedPane.add("缺寝记录", later);
                        jTabbedPane.add("查询缺寝记录",seekLater);
                        jTabbedPane.add("修改缺寝记录", editLater);
                        jTabbedPane.add("删除缺寝记录",deleteLater);
                        //jTabbedPane.add("宿舍物品", things);
                        jTabbedPane.add("退出登录", exit);
                        cardPanel.add(jTabbedPane);

                        jTabbedPane2 = new JTabbedPane(JTabbedPane.TOP);
                        jTabbedPane2.add("系统操作页面",cardPanel);
                        jTabbedPane2.add("关于系统",guanyu);
                        jTabbedPane2.add("帮助",help);
                        cardPanel2.add(jTabbedPane2);

                        card.add(cardPanel2, "info");
                        cardLayout.show(card, "info");//输入信息正确就显示操作界面，否则重新输入正确信息
                    }else if (users.getType()==3){
                        type=users.getType();
                        System.out.println("登录人员类别"+type);
                        JOptionPane.showMessageDialog(null, "欢迎(超管)"+username.getText()+"登录", "学生宿舍管理系统", JOptionPane.PLAIN_MESSAGE);
                        //当输入的信息正确时，就开始加载选项卡界面，并把选项卡界面加入到卡片布局器中
                        //StudentInfo studentInfo=new StudentInfo(users,type);//学生信息
                        AddLater later = new AddLater(type,users);//晚归信息
                        editLater editLater=new editLater(type,users);//修改缺寝信息
                        DeleteLater deleteLater=new DeleteLater(type,users);//删除
                        SeekLater seekLater=new SeekLater(type,users);
                        CooUsers cooUsers=new CooUsers(type,users);
                        CooStu cooStu=new CooStu(type,users);
                        CooDormitory dormitoryInfo = new CooDormitory(type,users);//宿舍信息
                        CooBuilding cooBuilding=new CooBuilding(type,users);
                        guanyu guanyu=new guanyu();
                        help help=new help();
                        Exit exit=new Exit();

                        cardPanel = new JPanel();
                        cardPanel2 = new JPanel();
                        cardPanel3 = new JPanel();
                        cardPanel4 = new JPanel();

                        jTabbedPane = new JTabbedPane(JTabbedPane.LEFT);
                        jTabbedPane.add("缺寝记录", later);
                        //jTabbedPane.add("学生信息",studentInfo);
                        jTabbedPane.add("查询缺寝记录",seekLater);
                        jTabbedPane.add("修改缺寝记录", editLater);
                        jTabbedPane.add("删除缺寝记录",deleteLater);
                        jTabbedPane.add("宿管人员管理",cooUsers);
                        jTabbedPane.add("学生人员管理",cooStu);
                        jTabbedPane.add("宿舍信息管理", dormitoryInfo);
                        jTabbedPane.add("寝楼信息管理",cooBuilding);
                        jTabbedPane.add("退出登录", exit);
                        cardPanel.add(jTabbedPane);
                        jTabbedPane2 = new JTabbedPane(JTabbedPane.TOP);
                        jTabbedPane2.add("系统操作页面",cardPanel);
                        jTabbedPane2.add("关于系统",guanyu);
                        jTabbedPane2.add("帮助",help);
                        cardPanel2.add(jTabbedPane2);
                        card.add(cardPanel2, "info");
                        cardLayout.show(card, "info");//输入信息正确就显示操作界面，否则重新输入正确信息
                    }
                    flag = true;
                    break;//如果信息正确就退出遍历，提高效率
                }
            }
            if(!flag){//信息不正确，重新输入
                JOptionPane.showMessageDialog(null, "请输入正确的用户名或密码", "警告",JOptionPane.WARNING_MESSAGE);
                username.setText("");
                passwordField.setText("");
            }
            }
        }
    }
