package DMS;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//import com.sun.org.apache.bcel.internal.generic.NEW;


/**宿舍信息(学生)
 * @author LQ
 * @create 2020-06-09 21:27
 */
public class StudentInfo extends JPanel{ //implements ActionListener {
    Connection connection = new GetConnection().GetConnection();
    Users users;//当前用户
    int type;//用户类型
    //String Dno="";//宿舍号
    JTable table=new JTable();
    String[] col = { "学号", "姓名", "性别","专业","宿舍号","寝楼" };
    DefaultTableModel mm = new DefaultTableModel(col, 0); // 定义一个表的模板
    /*JLabel Sdept,suse,name;
    JTextField SdeptText,suseText,nameText;
    JButton submit;
    JPanel suguan;*/
    public StudentInfo(Users users, int type){//从登录界面传回，用户名和用户类型
        this.type=type;
        this.users=users;
        setLayout(new FlowLayout());

        table.setModel(mm);
        table.setRowSorter(new TableRowSorter<>(mm));//排序
        JPanel jPanel=new JPanel(new FlowLayout());
        JScrollPane js=new JScrollPane(table);
        jPanel.add(js);

        add(jPanel);
        search();
    }

    private void search(){
        PreparedStatement state;
        ResultSet resultSet;
        if(type==1){//如果是学生，只显示学生自己的信息
            try {
                //inquire();
                String select="select * from student where Sname"+"="+"'"+users.getName()+"'";
                state=connection.prepareStatement(select);
                resultSet=state.executeQuery();
//                while (resultSet.next()){
//                    Dno=resultSet.getString("Dno");
//                }
//                System.out.println(users.getName()+users.getName().length());
//                select="select*from student where Dno"+"="+"'"+Dno+"'";
//                state=connection.prepareStatement(select);
//                resultSet = state.executeQuery();
                while (resultSet.next()){
                    String Sno=resultSet.getString(1);
                    String Sname=resultSet.getString(2);
                    String Ssex=resultSet.getString(3);
                    String Sdept=resultSet.getString(4);
                    String Dno=resultSet.getString(5);
                    String Bbu =resultSet.getString(6);
                    String[] data={Sno,Sname,Ssex,Sdept,Dno,Bbu};
                    mm.addRow(data);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else if(type==2||type==3){//如果是宿管，则显示全部学生的宿舍
            try {
                ////////////////////////////////////////////////////////////////////////////////是不是在这搞一个查询的功能
                state=connection.prepareStatement("select *from student");
                resultSet = state.executeQuery();
                while (resultSet.next()){
                    String Sno=resultSet.getString(1);
                    String Sname=resultSet.getString(2);
                    String Ssex=resultSet.getString(3);
                    String Sdept=resultSet.getString(4);
                    String Dno=resultSet.getString(5);
                    String Bbu =resultSet.getString(6);
                    String[] data={Sno,Sname,Ssex,Sdept,Dno,Bbu};
                    mm.addRow(data);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

//    private void inquire(){//学生只能查询任意宿舍的电话
//        suse=new JLabel("宿舍号");
//        suseText=new JTextField(10);
//        submit=new JButton("查询");
//        submit.addActionListener(this);
//        suguan=new JPanel(new GridLayout(2, 2));
//        suguan.add(suse);suguan.add(suseText);suguan.add(submit);
//        add(suguan);
//    }

}