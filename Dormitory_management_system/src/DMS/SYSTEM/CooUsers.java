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
public class CooUsers extends JPanel implements ActionListener {
    Connection connection = new GetConnection().GetConnection();
    int type;
    Users user;
    JTable table = new JTable();
    //JButton button = new JButton("");
    String[] col = {"账号", "密码", "用户类型"};
    DefaultTableModel mm = new DefaultTableModel(col, 0); // 定义一个表的模板

    //String DDno;//宿舍号

    JLabel Uname, Upassword, Utype,SP;
    JTextField UnameText, UpasswordText, UtypeText;
    JButton seek, add, delete, edit;

    JPanel student;


    public CooUsers(int type, Users user) {
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
                coop();
                state = connection.prepareStatement("select*from Users");
                resultSet = state.executeQuery();
                while (resultSet.next()) {
                    String Uname = resultSet.getString(1);
                    String Upassword = resultSet.getString(2);
                    String Utype = resultSet.getString(3);
                    /*String Atime=resultSet.getString(4);
                    String Areason=resultSet.getString(5);*/
                    String[] data = {Uname, Upassword, Utype};
                    mm.addRow(data);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void coop() {                  //这就是宿管添加晚归记录的功能

        Uname = new JLabel("填入对应进行操作的账号:");
        UnameText = new JTextField(10);
        Upassword = new JLabel("密码:");
        UpasswordText = new JTextField(10);
        Utype=new JLabel("用户类型:");
        UtypeText=new JTextField(10);
        SP=new JLabel("                                   ");
        /*Atime=new JLabel("缺寝时间:");
        AtimeText=new JTextField(10);
        Areason=new JLabel("缺寝原因:");
        AreasonText=new JTextField(10);*/
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
        student.add(Uname);
        student.add(UnameText);
        student.add(Upassword);
        student.add(UpasswordText);
        student.add(Utype);
        student.add(UtypeText);
        student.add(SP);
        student.add(add);
        student.add(delete);
        student.add(edit);
        student.add(seek);

        add(student);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == add && type == 3) {               //这就是宿管添加晚归记录的功能
            try {
                PreparedStatement statement = connection.prepareStatement("insert into Users values(?,?,?)");
                statement.setString(1, UnameText.getText());
                statement.setString(2, UpasswordText.getText());
                statement.setString(3, UtypeText.getText());
                    /*statement.setString(4, AtimeText.getText());
                    statement.setString(5, AreasonText.getText());*/
                statement.executeUpdate();

                PreparedStatement state = connection.prepareStatement("select*from Users");
                ResultSet resultSet = state.executeQuery();
                while (mm.getRowCount() > 0) {//把表格进行刷新，下次显示的时候重头开始显示
                    //System.out.println(model.getRowCount());
                    mm.removeRow(mm.getRowCount() - 1);
                }

                while (resultSet.next()) {
                    String Uname = resultSet.getString(1);
                    String Upassword = resultSet.getString(2);
                    String Utype = resultSet.getString(3);
                    String[] data = {Uname, Upassword, Utype};
                    mm.addRow(data);
                }

            } catch (SQLException e1) {
                e1.printStackTrace();
            }

            JOptionPane.showMessageDialog(this, "添加成功");
        }

        if (e.getSource() == delete && type == 3){
            try {
                PreparedStatement statement = connection.prepareStatement("DELETE FROM Users WHERE Uname=" + "'" + UnameText.getText() + "'");
                statement.executeUpdate();

                PreparedStatement state = connection.prepareStatement("select*from Users");
                ResultSet resultSet = state.executeQuery();
                while (mm.getRowCount() > 0) {//把表格进行刷新，下次显示的时候重头开始显示
                    //System.out.println(model.getRowCount());
                    mm.removeRow(mm.getRowCount() - 1);
                }

                while (resultSet.next()) {
                    String Uname = resultSet.getString(1);
                    String Upassword = resultSet.getString(2);
                    String Utype = resultSet.getString(3);
                    String[] data = {Uname, Upassword, Utype};
                    mm.addRow(data);
                }

            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            JOptionPane.showMessageDialog(this, "删除成功");
        }

        if (e.getSource() == edit && type == 3){
            try {
                if (UpasswordText.getText().length() > 0&&UtypeText.getText().length()==0) {           //只修改密码

                    Statement statement = connection.createStatement();
                    String sql = "update Users set Upassword=" + "'" + UpasswordText.getText() + "'" + "where Uname" + "=" + "'" + UnameText.getText() + "'";
                    statement.executeUpdate(sql);
                    PreparedStatement state;
                    ResultSet resultSet;
                    state = connection.prepareStatement("select *from Users");
                    resultSet = state.executeQuery();
                    while (mm.getRowCount() > 0) {//把表格进行刷新，下次显示的时候重头开始显示
                        //System.out.println(model.getRowCount());
                        mm.removeRow(mm.getRowCount() - 1);
                    }
                    while (resultSet.next()) {//把更新后的数据重新显示到表格中，下同
                        String Uname = resultSet.getString(1);
                        String Upassword = resultSet.getString(2);
                        String Utype = resultSet.getString(3);
                        String[] data = {Uname, Upassword, Utype};
                        mm.addRow(data);
                    }
                }
                if (UpasswordText.getText().length() == 0&&UtypeText.getText().length()>0) {           //只修改用户类型

                    Statement statement = connection.createStatement();
                    String sql = "update Users set Utype=" + "'" + UtypeText.getText() + "'" + "where Uname" + "=" + "'" + UnameText.getText() + "'";
                    statement.executeUpdate(sql);
                    PreparedStatement state;
                    ResultSet resultSet;
                    state = connection.prepareStatement("select *from Users");
                    resultSet = state.executeQuery();
                    while (mm.getRowCount() > 0) {//把表格进行刷新，下次显示的时候重头开始显示
                        //System.out.println(model.getRowCount());
                        mm.removeRow(mm.getRowCount() - 1);
                    }
                    while (resultSet.next()) {//把更新后的数据重新显示到表格中，下同
                        String Uname = resultSet.getString(1);
                        String Upassword = resultSet.getString(2);
                        String Utype = resultSet.getString(3);
                        String[] data = {Uname, Upassword, Utype};
                        mm.addRow(data);
                    }
                }
                if (UpasswordText.getText().length() > 0) {                  //全部修改

                    Statement statement = connection.createStatement();
                    String sql = "update Users set Upassword=" + "'" + UpasswordText.getText()+"'"+", Utype="+ "'"+UtypeText.getText()+  "'"   +"where Uname" + "=" + "'" + UnameText.getText() + "'";
                    statement.executeUpdate(sql);
                    PreparedStatement state;
                    ResultSet resultSet;
                    state = connection.prepareStatement("select *from Users");
                    resultSet = state.executeQuery();
                    while (mm.getRowCount() > 0) {//把表格进行刷新，下次显示的时候重头开始显示
                        //System.out.println(model.getRowCount());
                        mm.removeRow(mm.getRowCount() - 1);
                    }
                    while (resultSet.next()) {//把更新后的数据重新显示到表格中，下同
                        String Uname = resultSet.getString(1);
                        String Upassword = resultSet.getString(2);
                        String Utype = resultSet.getString(3);
                        String[] data = {Uname, Upassword, Utype};
                        mm.addRow(data);
                    }
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            JOptionPane.showMessageDialog(this, "修改密码成功");
        }

        if (e.getSource() == seek && type == 3) {
            PreparedStatement state;
            ResultSet resultSet;
            try {
                state = connection.prepareStatement("select Upassword,Utype from users where Uname =" + "'" + UnameText.getText() + "'");
                resultSet = state.executeQuery();
                while (resultSet.next()) {
                   // UnameText.setText(resultSet.getString("Uname"));
                    UpasswordText.setText(resultSet.getString("Upassword"));
                    UtypeText.setText(resultSet.getString("Utype"));
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            JOptionPane.showMessageDialog(this, "查询成功");
        }

    }
}




