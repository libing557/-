package DMS.SYSTEM;

import DMS.GetConnection;
import DMS.Users;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

/**学生晚归
 * @author LQ
 * @create 2020-6-11 16:44
 */
public class CooStu extends JPanel implements ActionListener {
    Connection connection = new GetConnection().GetConnection();
    int type;
    Users user;
    JTable table = new JTable();
    //JButton button = new JButton("");
    String[] col = {"学号", "姓名", "性别","专业","宿舍号","寝楼" };
    DefaultTableModel mm = new DefaultTableModel(col, 0); // 定义一个表的模板

    //String DDno;//宿舍号

    JLabel Sno,Sname,Ssex,Sdept,Dno,Bbu,SP,SP2;

    JTextField SnoText,SnameText,SsexText,SdeptText,DnoText,BbuText;
    JButton seek, add, delete, edit;

    JPanel student;


    public CooStu(int type, Users user) {
        this.user = user;
        this.type = type;
        setLayout(new FlowLayout());//整个采用流动式布局   很好的适应了表格带来的影响

        table.setModel(mm);
        table.setRowSorter(new TableRowSorter<>(mm));
        JScrollPane js = new JScrollPane(table);
        add(js);
        search();
    }

    private void search() {
        PreparedStatement state;
        ResultSet resultSet;
        if (type == 2 || type == 3) {
            try {
                /*state = connection.prepareStatement("select*from Users");
                resultSet = state.executeQuery();
                while (resultSet.next()) {
                    String Uname = resultSet.getString(1);
                    String Upassword = resultSet.getString(2);
                    String Utype = resultSet.getString(3);
                    *//*String Atime=resultSet.getString(4);
                    String Areason=resultSet.getString(5);*//*
                    String[] data = {Uname, Upassword, Utype};
                    mm.addRow(data);
                }*/
                coop();
                state = connection.prepareStatement("select*from student");
                resultSet = state.executeQuery();
                while (resultSet.next()) {
                    String Sno=resultSet.getString(1);
                    String Sname=resultSet.getString(2);
                    String Ssex=resultSet.getString(3);
                    String Sdept=resultSet.getString(4);
                    String Dno=resultSet.getString(5);
                    String Bbu =resultSet.getString(6);
                    String[] data={Sno,Sname,Ssex,Sdept,Dno,Bbu};
                    mm.addRow(data);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void coop() {                  //这就是宿管添加晚归记录的功能


        Sno = new JLabel("填对应进行操作的学号:");
        SnoText = new JTextField(6);
        Sname = new JLabel("名字(不可修改):");
        SnameText = new JTextField(6);
        Ssex=new JLabel("性别(不可修改):");
        SsexText=new JTextField(6);
        Sdept=new JLabel("专业:");
        SdeptText=new JTextField(6);
        Dno=new JLabel("寝室号:");
        DnoText=new JTextField(6);
        Bbu=new JLabel("寝楼(不可修改):");
        BbuText=new JTextField(6);
        SP=new JLabel("                             ");
        SP2=new JLabel("                             ");
        add = new JButton("添加");
        delete = new JButton("删除");
        edit = new JButton("修改");
        seek = new JButton("查询");
        add.addActionListener(this);
        edit.addActionListener(this);
        seek.addActionListener(this);
        delete.addActionListener(this);
        student = new JPanel(new GridLayout(14, 2));
        //student.add(tip);
        student.add(Sno);
        student.add(SnoText);
        student.add(Sname);
        student.add(SnameText);
        student.add(Ssex);
        student.add(SsexText);
        student.add(Sdept);
        student.add(SdeptText);
        student.add(Dno);
        student.add(DnoText);
        student.add(Bbu);
        student.add(BbuText);
        student.add(SP);
        student.add(SP2);
        student.add(add);
        student.add(delete);
        student.add(edit);
        student.add(seek);
        add(student);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == add && type == 3) {
            try {
                PreparedStatement statement = connection.prepareStatement("insert into student values(?,?,?,?,?,?)");
                statement.setString(1, SnoText.getText());
                statement.setString(2, SnameText.getText());
                statement.setString(3, SsexText.getText());
                statement.setString(4, SdeptText.getText());
                statement.setString(5, DnoText.getText());
                statement.setString(6, BbuText.getText());

                statement.executeUpdate();

                PreparedStatement state = connection.prepareStatement("select*from student");
                ResultSet resultSet = state.executeQuery();
                while (mm.getRowCount() > 0) {//把表格进行刷新，下次显示的时候重头开始显示
                    //System.out.println(model.getRowCount());

                    mm.removeRow(mm.getRowCount() - 1);
                }

                while (resultSet.next()) {
                    String Sno=resultSet.getString(1);
                    String Sname=resultSet.getString(2);
                    String Ssex=resultSet.getString(3);
                    String Sdept=resultSet.getString(4);
                    String Dno=resultSet.getString(5);
                    String Bbu =resultSet.getString(6);
                    String[] data={Sno,Sname,Ssex,Sdept,Dno,Bbu};
                    mm.addRow(data);

                }

            } catch (SQLException e1) {
                e1.printStackTrace();
            }

            JOptionPane.showMessageDialog(this, "添加成功");
        }

        if (e.getSource() == delete && type == 3){
            try {
                PreparedStatement statement = connection.prepareStatement("DELETE FROM student WHERE Sno=" + "'" + SnoText.getText() + "'");
                statement.executeUpdate();

                PreparedStatement state = connection.prepareStatement("select*from student");
                ResultSet resultSet = state.executeQuery();
                while (mm.getRowCount() > 0) {//把表格进行刷新，下次显示的时候重头开始显示
                    //System.out.println(model.getRowCount());
                    mm.removeRow(mm.getRowCount() - 1);
                }

                while (resultSet.next()) {
                    String Sno=resultSet.getString(1);
                    String Sname=resultSet.getString(2);
                    String Ssex=resultSet.getString(3);
                    String Sdept=resultSet.getString(4);
                    String Dno=resultSet.getString(5);
                    String Bbu =resultSet.getString(6);
                    String[] data={Sno,Sname,Ssex,Sdept,Dno,Bbu};
                    mm.addRow(data);
                }

            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            JOptionPane.showMessageDialog(this, "删除成功");
        }

        if (e.getSource() == edit && type == 3){
            try {
                if (DnoText.getText().length()>0&&SdeptText.getText().length()==0) {//只修改寝室

                    Statement statement = connection.createStatement();
                    String sql = "update student set Dno=" + "'" + DnoText.getText() + "'" + "where Sno" + "=" + "'" + SnoText.getText() + "'";
                    statement.executeUpdate(sql);
                    PreparedStatement state;
                    ResultSet resultSet;
                    state = connection.prepareStatement("select *from student");
                    resultSet = state.executeQuery();
                    while (mm.getRowCount() > 0) {//把表格进行刷新，下次显示的时候重头开始显示
                        //System.out.println(model.getRowCount());
                        mm.removeRow(mm.getRowCount() - 1);
                    }
                    while (resultSet.next()) {//把更新后的数据重新显示到表格中，下同
                        String Sno=resultSet.getString(1);
                        String Sname=resultSet.getString(2);
                        String Ssex=resultSet.getString(3);
                        String Sdept=resultSet.getString(4);
                        String Dno=resultSet.getString(5);
                        String Bbu =resultSet.getString(6);
                        String[] data={Sno,Sname,Ssex,Sdept,Dno,Bbu};
                        mm.addRow(data);
                    }

                }
                if (SdeptText.getText().length()>0&&SsexText.getText().length()==0) {//只修改专业

                    Statement statement = connection.createStatement();
                    String sql = "update student set Sdept=" + "'" + SdeptText.getText() + "'" + "where Sno" + "=" + "'" + SnoText.getText() + "'";
                    statement.executeUpdate(sql);
                    PreparedStatement state;
                    ResultSet resultSet;
                    state = connection.prepareStatement("select *from student");
                    resultSet = state.executeQuery();
                    while (mm.getRowCount() > 0) {//把表格进行刷新，下次显示的时候重头开始显示
                        //System.out.println(model.getRowCount());
                        mm.removeRow(mm.getRowCount() - 1);
                    }
                    while (resultSet.next()) {//把更新后的数据重新显示到表格中，下同
                        String Sno=resultSet.getString(1);
                        String Sname=resultSet.getString(2);
                        String Ssex=resultSet.getString(3);
                        String Sdept=resultSet.getString(4);
                        String Dno=resultSet.getString(5);
                        String Bbu =resultSet.getString(6);
                        String[] data={Sno,Sname,Ssex,Sdept,Dno,Bbu};
                        mm.addRow(data);

                    }
                }


                if (SdeptText.getText().length()>0&&DnoText.getText().length()>0) {//全部修改
                    Statement statement = connection.createStatement();
                    String sql = "update student set Sdept="+"'"+SdeptText.getText()+"'"+", Dno="+ "'"+DnoText.getText()+  "'"   +"where Sno"+"="+"'"+SnoText.getText()+"'";
                    statement.executeUpdate(sql);
                    PreparedStatement state;
                    ResultSet resultSet;
                    state = connection.prepareStatement("select *from student");
                    resultSet = state.executeQuery();
                    while (mm.getRowCount() > 0) {                           //把表格进行刷新，下次显示的时候重头开始显示
                        //System.out.println(model.getRowCount());
                        mm.removeRow(mm.getRowCount() - 1);
                    }
                    while (resultSet.next()) {                              //把更新后的数据重新显示到表格中，下同
                        String Sno=resultSet.getString(1);
                        String Sname=resultSet.getString(2);
                        String Ssex=resultSet.getString(3);
                        String Sdept=resultSet.getString(4);
                        String Dno=resultSet.getString(5);
                        String Bbu =resultSet.getString(6);
                        String[] data={Sno,Sname,Ssex,Sdept,Dno,Bbu};
                        mm.addRow(data);

                    }
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            JOptionPane.showMessageDialog(this, "修改成功");
        }

        if (e.getSource() == seek && type == 3) {
            PreparedStatement state;
            ResultSet resultSet;
            try {
                state = connection.prepareStatement("select Sname,Ssex,Sdept,Dno,Bbu from student where Sno =" + "'" + Sno.getText() + "'");
                resultSet = state.executeQuery();
                while (resultSet.next()) {
                    //suse.setText("电话");
                    SnameText.setText(resultSet.getString("Sname"));
                    SsexText.setText(resultSet.getString("Ssex"));
                    SdeptText.setText(resultSet.getString("Sdept"));
                    DnoText.setText(resultSet.getString("Dno"));
                    BbuText.setText(resultSet.getString("Bbu"));
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

            JOptionPane.showMessageDialog(this, "查询成功");
        }

    }
}




