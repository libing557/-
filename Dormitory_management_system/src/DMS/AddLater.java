package DMS;//测试

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**学生晚归
 * @author LQ
 * @create 2020-6-11 16:44
 */
public class AddLater extends JPanel implements ActionListener {
    Connection connection = new GetConnection().GetConnection();
    int type;
    Users user;
    JTable table = new JTable();
    //JButton button = new JButton("");
    String[] col = {"学号","姓名", "宿舍号", "缺寝时间", "缺寝原因"};
    DefaultTableModel mm = new DefaultTableModel(col, 0); // 定义一个表的模板

    JLabel Atime,Areason,Dno,Sno,Sname,tip;
    JTextField AtimeText,AreasonText,DnoText,SnoText,SnameText;
    JButton submit;
    JPanel student;

    public AddLater(int type, Users user){
        this.user=user;
        this.type=type;
        setLayout(new FlowLayout());//整个采用流动式布局   很好的适应了表格带来的影响

        table.setModel(mm);
        table.setRowSorter(new TableRowSorter<>(mm));
        JScrollPane js=new JScrollPane(table);
        add(js);
        search();
    }
    private void search(){
        PreparedStatement state;
        ResultSet resultSet;
        if(type==1){
            try {
                /*String select="select Dno from student where Sname"+"="+"'"+user.getName()+"'";
                PreparedStatement state=connection.prepareStatement(select);
                ResultSet resultSet=state.executeQuery();
                while (resultSet.next()) {
                    DDno = resultSet.getString("Dno");
                }*/
                state=connection.prepareStatement("select * from absent where Sname"+"="+"'"+user.getName()+"'");  //这里不好  因为名字可以一样  以后要改
                resultSet = state.executeQuery();
                while (resultSet.next()){
                    String Sno=resultSet.getString(1);
                    String Sname=resultSet.getString(2);
                    String Dno=resultSet.getString(3);
                    String Atime=resultSet.getString(4);
                    String Areason=resultSet.getString(5);
                    String[] data={Sno,Sname,Dno,Atime,Areason};
                    mm.addRow(data);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if(type==2||type==3){
            try {
                record();
                 state=connection.prepareStatement("select*from absent");
                 resultSet = state.executeQuery();
                while (resultSet.next()){
                    String Sno=resultSet.getString(1);
                    String Sname=resultSet.getString(2);
                    String Dno=resultSet.getString(3);
                    String Atime=resultSet.getString(4);
                    String Areason=resultSet.getString(5);
                    String[] data={Sno,Sname,Dno,Atime,Areason};
                    mm.addRow(data);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    private void record(){                  //这就是宿管添加晚归记录的功能

        Sno=new JLabel("要添加学生的学号:");
        SnoText=new JTextField(10);
        Sname=new JLabel("姓名:");
        SnameText=new JTextField(10);
        Dno=new JLabel("宿舍号:");
        DnoText=new JTextField(10);
        Atime=new JLabel("缺寝时间:");
        AtimeText=new JTextField(10);
        Areason=new JLabel("缺寝原因:");
        AreasonText=new JTextField(10);
        submit=new JButton("添加");
        submit.addActionListener(this);

        student=new JPanel(new GridLayout(8, 1));

        //student.add(tip);
        student.add(Sno);student.add(SnoText);
        student.add(Sname);student.add(SnameText);
        student.add(Dno);student.add(DnoText);
        student.add(Atime);student.add(AtimeText);
        student.add(Areason);student.add(AreasonText);
        student.add(submit);
        add(student);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==submit){               //这就是宿管添加晚归记录的功能
            try {
                PreparedStatement   statement = connection.prepareStatement("insert into absent values(?,?,?,?,?)");
                statement.setString(1, SnoText.getText());
                statement.setString(2, SnameText.getText());
                statement.setString(3, DnoText.getText());
                statement.setString(4, AtimeText.getText());
                statement.setString(5, AreasonText.getText());
                statement.executeUpdate();

                PreparedStatement state=connection.prepareStatement("select*from absent");
                ResultSet resultSet = state.executeQuery();
                while(mm.getRowCount()>0){//把表格进行刷新，下次显示的时候重头开始显示
                    //System.out.println(model.getRowCount());
                    mm.removeRow(mm.getRowCount()-1);
                }

                while (resultSet.next()){
                    String Sno=resultSet.getString(1);
                    String Sname=resultSet.getString(2);
                    String Dno=resultSet.getString(3);
                    String Atime=resultSet.getString(4);
                    String Areason=resultSet.getString(5);
                    String[] data={Sno,Sname,Dno,Atime,Areason};
                    mm.addRow(data);
                }

            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        JOptionPane.showMessageDialog(this,"添加成功");
    }
}
