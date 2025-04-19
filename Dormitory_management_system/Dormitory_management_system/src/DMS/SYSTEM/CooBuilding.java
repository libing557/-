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

/**学生缺寝
 * @author LQ
 * @create 2020-6-11 16:44
 */
public class CooBuilding extends JPanel implements ActionListener {
    Connection connection = new GetConnection().GetConnection();
    int type;
    Users user;
    JTable table = new JTable();
    //JButton button = new JButton("");
    String[] col = {"楼名", "层数", "管理人","容纳人数"};
    DefaultTableModel mm = new DefaultTableModel(col, 0); // 定义一个表的模板

    JLabel Bname,Bfloor,Bmager,Bcap,SP;
    JTextField BnameText,BfloorText,BmagerText,BcapText;
    JButton seek, add, delete, edit;
    JPanel student;

    public CooBuilding(int type, Users user) {
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
                state = connection.prepareStatement("select*from building");
                resultSet = state.executeQuery();
                while (resultSet.next()) {
                    String Bname=resultSet.getString(1);
                    String Bfloor=resultSet.getString(2);
                    String Bmager=resultSet.getString(3);
                    String Bcap=resultSet.getString(4);
                    String[] data={Bname,Bfloor,Bmager,Bcap};
                    mm.addRow(data);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void coop() {
        Bname = new JLabel("填入对应进行操作的楼名:");
        BnameText = new JTextField(8);
        Bfloor = new JLabel("层数(不可修改):");
        BfloorText = new JTextField(8);
        Bmager=new JLabel("管理人:");
        BmagerText=new JTextField(8);
        Bcap=new JLabel("容纳人数:");
        BcapText=new JTextField(8);
        /*Dno=new JLabel("寝室号:");
        DnoText=new JTextField(10);
        Bbu=new JLabel("寝楼:");
        BbuText=new JTextField(10);*/
        SP=new JLabel("                                        ");
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
        student.add(Bname);
        student.add(BnameText);
        student.add(Bfloor);
        student.add(BfloorText);
        student.add(Bmager);
        student.add(BmagerText);
        student.add(Bcap);
        student.add(BcapText);
        student.add(SP);
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
                PreparedStatement statement = connection.prepareStatement("insert into building values(?,?,?,?)");
                statement.setString(1, BnameText.getText());
                statement.setString(2, BfloorText.getText());
                statement.setString(3, BmagerText.getText());
                statement.setString(4, BcapText.getText());
                statement.executeUpdate();

                PreparedStatement state = connection.prepareStatement("select*from building");
                ResultSet resultSet = state.executeQuery();
                while (mm.getRowCount() > 0) {//把表格进行刷新，下次显示的时候重头开始显示
                    //System.out.println(model.getRowCount());
                    mm.removeRow(mm.getRowCount() - 1);
                }

                while (resultSet.next()) {
                    String Bname=resultSet.getString(1);
                    String Bfloor=resultSet.getString(2);
                    String Bmager=resultSet.getString(3);
                    String Bcap=resultSet.getString(4);
                    String[] data={Bname,Bfloor,Bmager,Bcap};
                    mm.addRow(data);
                }

            } catch (SQLException e1) {
                e1.printStackTrace();
            }

            JOptionPane.showMessageDialog(this, "添加成功");
        }
        if (e.getSource() == delete && type == 3){
            try {
                PreparedStatement statement = connection.prepareStatement("DELETE FROM building WHERE Bname=" + "'" + BnameText.getText() + "'");
                statement.executeUpdate();
                PreparedStatement state = connection.prepareStatement("select*from building");
                ResultSet resultSet = state.executeQuery();
                while (mm.getRowCount() > 0) {//把表格进行刷新，下次显示的时候重头开始显示
                    //System.out.println(model.getRowCount());
                    mm.removeRow(mm.getRowCount() - 1);
                }
                while (resultSet.next()) {
                    String Bname=resultSet.getString(1);
                    String Bfloor=resultSet.getString(2);
                    String Bmager=resultSet.getString(3);
                    String Bcap=resultSet.getString(4);
                    String[] data={Bname,Bfloor,Bmager,Bcap};
                    mm.addRow(data);
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            JOptionPane.showMessageDialog(this, "删除成功");
        }
        if (e.getSource() == edit && type == 3){
            try {
                if (BmagerText.getText().length()>0&&BcapText.getText().length()==0) {//只修改管理人
                    Statement statement = connection.createStatement();
                    String sql = "update building set Bmager=" + "'" + BmagerText.getText() + "'" + "where Bname" + "=" + "'" + BnameText.getText() + "'";
                    statement.executeUpdate(sql);
                    PreparedStatement state;
                    ResultSet resultSet;
                    state = connection.prepareStatement("select *from building");
                    resultSet = state.executeQuery();
                    while (mm.getRowCount() > 0) {//把表格进行刷新，下次显示的时候重头开始显示
                        //System.out.println(model.getRowCount());
                        mm.removeRow(mm.getRowCount() - 1);
                    }
                    while (resultSet.next()) {//把更新后的数据重新显示到表格中，下同
                        String Bname=resultSet.getString(1);
                        String Bfloor=resultSet.getString(2);
                        String Bmager=resultSet.getString(3);
                        String Bcap=resultSet.getString(4);
                        String[] data={Bname,Bfloor,Bmager,Bcap};
                        mm.addRow(data);

                    }
                }
                if (BcapText.getText().length()>0&&BmagerText.getText().length()==0) {//只修改容量

                    Statement statement = connection.createStatement();
                    String sql = "update building set Bcap=" + "'" + BcapText.getText() + "'" + "where Bname" + "=" + "'" + BnameText.getText() + "'";
                    statement.executeUpdate(sql);
                    PreparedStatement state;
                    ResultSet resultSet;
                    state = connection.prepareStatement("select *from building");
                    resultSet = state.executeQuery();
                    while (mm.getRowCount() > 0) {//把表格进行刷新，下次显示的时候重头开始显示
                        //System.out.println(model.getRowCount());
                        mm.removeRow(mm.getRowCount() - 1);
                    }
                    while (resultSet.next()) {//把更新后的数据重新显示到表格中，下同
                        String Bname=resultSet.getString(1);
                        String Bfloor=resultSet.getString(2);
                        String Bmager=resultSet.getString(3);
                        String Bcap=resultSet.getString(4);
                        String[] data={Bname,Bfloor,Bmager,Bcap};
                        mm.addRow(data);
                    }
                }

                if (BcapText.getText().length()>0&&BmagerText.getText().length()>0) {//全部修改
                    Statement statement = connection.createStatement();
                    String sql = "update building set Bcap="+"'"+BcapText.getText()+"'"+", Bmager="+ "'"+BmagerText.getText()+  "'"   +"where Bname"+"="+"'"+BnameText.getText()+"'";
                    statement.executeUpdate(sql);
                    PreparedStatement state;
                    ResultSet resultSet;
                    state = connection.prepareStatement("select *from building");
                    resultSet = state.executeQuery();
                    while (mm.getRowCount() > 0) {                           //把表格进行刷新，下次显示的时候重头开始显示
                        //System.out.println(model.getRowCount());
                        mm.removeRow(mm.getRowCount() - 1);
                    }
                    while (resultSet.next()) {                              //把更新后的数据重新显示到表格中，下同
                        String Bname=resultSet.getString(1);
                        String Bfloor=resultSet.getString(2);
                        String Bmager=resultSet.getString(3);
                        String Bcap=resultSet.getString(4);
                        String[] data={Bname,Bfloor,Bmager,Bcap};
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
                state = connection.prepareStatement("select Bfloor,Bmager,Bcap from building where Sno =" + "'" + Bname.getText() + "'");
                resultSet = state.executeQuery();
                while (resultSet.next()) {
                    //suse.setText("电话");
                    BfloorText.setText(resultSet.getString("Bfloor"));
                    BmagerText.setText(resultSet.getString("Bmager"));
                    BcapText.setText(resultSet.getString("Bcap"));
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

            JOptionPane.showMessageDialog(this, "查询成功");
        }

    }
}




