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
public class CooDormitory extends JPanel implements ActionListener {
    Connection connection = new GetConnection().GetConnection();
    int type;
    Users user;
    JTable table = new JTable();
    //JButton button = new JButton("");
    String[] col = {"学号", "位置", "电话","容纳人数"};
    DefaultTableModel mm = new DefaultTableModel(col, 0); // 定义一个表的模板
    //String DDno;//宿舍号
    JLabel Dno,Dpo,Dphone,Dcap,SP;
    JTextField DnoText,DpoText,DphoneText,DcapText;
    JButton seek, add, delete, edit;
    JPanel student;

    public CooDormitory(int type, Users user) {
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
                if ( type == 3) {
                    coop();
                }
                state = connection.prepareStatement("select*from dorm");
                resultSet = state.executeQuery();        //查询的结果返回的是一个结果集，用ResultSet接收
                while (resultSet.next()) {
                    String Dno=resultSet.getString(1);
                    String Dpo=resultSet.getString(2);
                    String Dphone=resultSet.getString(3);
                    String Dcap=resultSet.getString(4);
                    String[] data={Dno,Dpo,Dphone,Dcap};
                    mm.addRow(data);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void coop() {
        Dno = new JLabel("填入对应进行操作的寝室号:");
        DnoText = new JTextField(8);
        Dpo = new JLabel("位置(不可修改):");
        DpoText = new JTextField(8);
        Dphone=new JLabel("电话:");
        DphoneText=new JTextField(8);
        Dcap=new JLabel("容纳人数:");
        DcapText=new JTextField(8);
        /*Dno=new JLabel("寝室号:");
        DnoText=new JTextField(10);
        Bbu=new JLabel("寝楼:");
        BbuText=new JTextField(10);*/
        SP=new JLabel("         ");
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
        student.add(Dno);
        student.add(DnoText);
        student.add(Dpo);
        student.add(DpoText);
        student.add(Dphone);
        student.add(DphoneText);
        student.add(Dcap);
        student.add(DcapText);
        student.add(SP);//*****************************************************
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
                PreparedStatement statement = connection.prepareStatement("insert into dorm values(?,?,?,?)");
                statement.setString(1, DnoText.getText());
                statement.setString(2, DpoText.getText());
                statement.setString(3, DphoneText.getText());
                statement.setString(4, DcapText.getText());
                statement.executeUpdate();
                PreparedStatement state = connection.prepareStatement("select*from dorm");
                ResultSet resultSet = state.executeQuery();
                while (mm.getRowCount() > 0) {//把表格进行刷新，下次显示的时候重头开始显示
                    //System.out.println(model.getRowCount());
                    mm.removeRow(mm.getRowCount() - 1);
                }
                while (resultSet.next()) {
                    String Dno=resultSet.getString(1);
                    String Dpo=resultSet.getString(2);
                    String Dphone=resultSet.getString(3);
                    String Dcap=resultSet.getString(4);
                    String[] data={Dno,Dpo,Dphone,Dcap};
                    mm.addRow(data);
                }

            } catch (SQLException e1) {
                e1.printStackTrace();
            }

            JOptionPane.showMessageDialog(this, "添加成功");
        }

        if (e.getSource() == delete && type == 3){
            try {
                PreparedStatement statement = connection.prepareStatement("DELETE FROM dorm WHERE Dno=" + "'" + DnoText.getText() + "'");
                statement.executeUpdate();
                PreparedStatement state = connection.prepareStatement("select*from dorm");
                ResultSet resultSet = state.executeQuery();
                while (mm.getRowCount() > 0) {//把表格进行刷新，下次显示的时候重头开始显示
                    //System.out.println(model.getRowCount());
                    mm.removeRow(mm.getRowCount() - 1);
                }
                while (resultSet.next()) {
                    String Dno=resultSet.getString(1);
                    String Dpo=resultSet.getString(2);
                    String Dphone=resultSet.getString(3);
                    String Dcap=resultSet.getString(4);
                    String[] data={Dno,Dpo,Dphone,Dcap};
                    mm.addRow(data);
                }

            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            JOptionPane.showMessageDialog(this, "删除成功");
        }

        if (e.getSource() == edit && type == 3){
            try {
                if (DphoneText.getText().length()>0&&DcapText.getText().length()==0) {//只修改电话

                    Statement statement = connection.createStatement();
                    String sql = "update dorm set Dphone=" + "'" + DphoneText.getText() + "'" + "where Dno" + "=" + "'" + DnoText.getText() + "'";
                    statement.executeUpdate(sql);
                    PreparedStatement state;
                    ResultSet resultSet;
                    state = connection.prepareStatement("select *from dorm");
                    resultSet = state.executeQuery();
                    while (mm.getRowCount() > 0) {//把表格进行刷新，下次显示的时候重头开始显示
                        //System.out.println(model.getRowCount());
                        mm.removeRow(mm.getRowCount() - 1);
                    }
                    while (resultSet.next()) {//把更新后的数据重新显示到表格中，下同
                        String Dno=resultSet.getString(1);
                        String Dpo=resultSet.getString(2);
                        String Dphone=resultSet.getString(3);
                        String Dcap=resultSet.getString(4);
                        String[] data={Dno,Dpo,Dphone,Dcap};
                        mm.addRow(data);

                    }

                }
                if (DcapText.getText().length()>0&&DphoneText.getText().length()==0) {//只修改容量

                    Statement statement = connection.createStatement();
                    String sql = "update dorm set Dcap=" + "'" + DcapText.getText() + "'" + "where Dno" + "=" + "'" + DnoText.getText() + "'";
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
                        String Dno=resultSet.getString(1);
                        String Dpo=resultSet.getString(2);
                        String Dphone=resultSet.getString(3);
                        String Dcap=resultSet.getString(4);
                        String[] data={Dno,Dpo,Dphone,Dcap};
                        mm.addRow(data);


                    }
                }


                if (DcapText.getText().length()>0&&DphoneText.getText().length()>0) {//全部修改
                    Statement statement = connection.createStatement();
                    String sql = "update dorm set Dcap="+"'"+DcapText.getText()+"'"+", Dphone="+ "'"+DphoneText.getText()+  "'"   +"where Dno"+"="+"'"+DnoText.getText()+"'";
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
                        String Dno=resultSet.getString(1);
                        String Dpo=resultSet.getString(2);
                        String Dphone=resultSet.getString(3);
                        String Dcap=resultSet.getString(4);
                        String[] data={Dno,Dpo,Dphone,Dcap};

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
                state = connection.prepareStatement("select Dpo,Dphone,Dcap from student where Sno =" + "'" + Dno.getText() + "'");
                resultSet = state.executeQuery();
                while (resultSet.next()) {
                    //suse.setText("电话");
                    DpoText.setText(resultSet.getString("Dpo"));
                    DphoneText.setText(resultSet.getString("Dphone"));
                    DcapText.setText(resultSet.getString("Dcap"));
                    //BbuText.setText(resultSet.getString("Bbu"));
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

            JOptionPane.showMessageDialog(this, "查询成功");
        }
    }
}




