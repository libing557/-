package DMS;

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
public class editLater extends JPanel implements ActionListener {
    Connection connection = new GetConnection().GetConnection();
    int type;
    Users user;
    JTable table = new JTable();
    //JButton button = new JButton("");
    String[] col = {"学号", "姓名", "宿舍号", "缺寝时间", "缺寝原因"};
    DefaultTableModel mm = new DefaultTableModel(col, 0); // 定义一个表的模板

    //String DDno;//宿舍号

    JLabel Atime, Areason, Dno, Sno, Sname;
    JTextField AtimeText, AreasonText, DnoText, SnoText, SnameText;
    JButton submit;

    //JTextField SdeptText,suseText,nameText;
    JPanel suguan;


    public editLater(int type, Users user) {
        this.user = user;
        this.type = type;
        setLayout(new FlowLayout());

        table.setModel(mm);
        table.setRowSorter(new TableRowSorter<>(mm));
        JScrollPane js = new JScrollPane(table);
        add(js);
        JOptionPane.showMessageDialog(this,"即将进入编辑界面，如要修改请务必输入要修改的学生缺寝信息的学号，不需要修改的项不填");
        search();
    }

    private void search() {
        PreparedStatement state;
        ResultSet resultSet;
        if (type == 1) {
            try {
                /*String select="select Dno from student where Sname"+"="+"'"+user.getName()+"'";
                PreparedStatement state=connection.prepareStatement(select);
                ResultSet resultSet=state.executeQuery();
                while (resultSet.next()) {
                    DDno = resultSet.getString("Dno");
                }*/
                state = connection.prepareStatement("select * from absent where Sname" + "=" + "'" + user.getName() + "'");  //这里不好  因为名字可以一样  以后要改
                resultSet = state.executeQuery();
                while (resultSet.next()) {
                    String Sno = resultSet.getString(1);
                    String Sname = resultSet.getString(2);
                    String Dno = resultSet.getString(3);
                    String Atime = resultSet.getString(4);
                    String Areason = resultSet.getString(5);
                    String[] data = {Sno, Sname, Dno, Atime, Areason};
                    mm.addRow(data);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (type == 2||type==3) {
            try {
                xiugai();
                state = connection.prepareStatement("select*from absent");
                resultSet = state.executeQuery();
                while (resultSet.next()) {
                    String Sno = resultSet.getString(1);
                    String Sname = resultSet.getString(2);
                    String Dno = resultSet.getString(3);
                    String Atime = resultSet.getString(4);
                    String Areason = resultSet.getString(5);
                    String[] data = {Sno, Sname, Dno, Atime, Areason};
                    mm.addRow(data);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void xiugai() {//宿管修改学生的宿舍信息
        Sno = new JLabel("请输入被修改学生的学号:");
        SnoText = new JTextField(9);
        /*Sname = new JLabel("姓名");
        SnameText = new JTextField(10);
        Dno = new JLabel("宿舍号");
        DnoText = new JTextField(10);*/
        Atime = new JLabel("缺寝时间:");
        AtimeText = new JTextField(9);
        Areason = new JLabel("缺寝原因:");
        AreasonText = new JTextField(9);
        suguan = new JPanel(new GridLayout(4, 2));
        submit = new JButton("修改");
        submit.addActionListener(this);

        suguan.add(Sno);
        suguan.add(SnoText);
        /*suguan.add(Sname);
        suguan.add(SnameText);
        suguan.add(Dno);
        suguan.add(DnoText);*/
        suguan.add(Atime);
        suguan.add(AtimeText);
        suguan.add(Areason);
        suguan.add(AreasonText);
        suguan.add(submit);
        //这里加一个提示消息
        add(suguan);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submit ) {//如果点击按钮的是宿管
            //try {
                /*if (DnoText.getText().length()>0&&SnoText.getText().length()==0&&SnameText.getText().length()==0){//只修改宿舍号

                    Statement statement = connection.createStatement();
                    String sql="update absent set Dno="+"'"+DnoText.getText()+"'"+"where Sname"+"="+"'"+nameText.getText()+"'";*/

            try {
                if (AtimeText.getText().length()>0&&AreasonText.getText().length()==0) {//只修改缺寝时间

                    Statement statement = connection.createStatement();
                    String sql = "update absent set Atime=" + "'" + AtimeText.getText() + "'" + "where Sno" + "=" + "'" + SnoText.getText() + "'";
                    statement.executeUpdate(sql);
                    PreparedStatement state;
                    ResultSet resultSet;
                    state = connection.prepareStatement("select *from absent");
                    resultSet = state.executeQuery();
                    while (mm.getRowCount() > 0) {//把表格进行刷新，下次显示的时候重头开始显示
                        //System.out.println(model.getRowCount());
                        mm.removeRow(mm.getRowCount() - 1);
                    }
                    while (resultSet.next()) {//把更新后的数据重新显示到表格中，下同
                        String Sno = resultSet.getString(1);
                        String Sname = resultSet.getString(2);
                        String Dno = resultSet.getString(3);
                        String Atime = resultSet.getString(4);
                        String Areason = resultSet.getString(5);
                        String[] data = {Sno, Sname, Dno, Atime, Areason};
                        mm.addRow(data);
                    }

                }
                if (AreasonText.getText().length()>0&&AtimeText.getText().length()==0) {//只修改缺寝原因

                    Statement statement = connection.createStatement();
                    String sql = "update absent set Areason=" + "'" + AreasonText.getText() + "'" + "where Sno" + "=" + "'" + SnoText.getText() + "'";
                    statement.executeUpdate(sql);
                    PreparedStatement state;
                    ResultSet resultSet;
                    state = connection.prepareStatement("select *from absent");
                    resultSet = state.executeQuery();
                    while (mm.getRowCount() > 0) {//把表格进行刷新，下次显示的时候重头开始显示
                        //System.out.println(model.getRowCount());
                        mm.removeRow(mm.getRowCount() - 1);
                    }
                    while (resultSet.next()) {//把更新后的数据重新显示到表格中，下同
                        String Sno = resultSet.getString(1);
                        String Sname = resultSet.getString(2);
                        String Dno = resultSet.getString(3);
                        String Atime = resultSet.getString(4);
                        String Areason = resultSet.getString(5);
                        String[] data = {Sno, Sname, Dno, Atime, Areason};
                        mm.addRow(data);

                    }
                }


                if (AtimeText.getText().length()>0&&AreasonText.getText().length()>0) {//全部修改
                    Statement statement = connection.createStatement();
                    String sql = "update absent set Atime="+"'"+AtimeText.getText()+"'"+", Areason="+ "'"+AreasonText.getText()+  "'"   +"where Sno"+"="+"'"+SnoText.getText()+"'";
                    statement.executeUpdate(sql);
                    PreparedStatement state;
                    ResultSet resultSet;
                    state = connection.prepareStatement("select *from absent");
                    resultSet = state.executeQuery();
                    while (mm.getRowCount() > 0) {                           //把表格进行刷新，下次显示的时候重头开始显示
                        //System.out.println(model.getRowCount());
                        mm.removeRow(mm.getRowCount() - 1);
                    }
                    while (resultSet.next()) {                              //把更新后的数据重新显示到表格中，下同
                        String Sno = resultSet.getString(1);
                        String Sname = resultSet.getString(2);
                        String Dno = resultSet.getString(3);
                        String Atime = resultSet.getString(4);
                        String Areason = resultSet.getString(5);
                        String[] data = {Sno, Sname, Dno, Atime, Areason};
                        mm.addRow(data);

                    }
                }

                /*if (AreasonText.getText().length() != 0) {

                    Statement statement = connection.createStatement();
                    String sql = "update absent set Areason=" + "'" + AreasonText.getText() + "'" + "where Sno" + "=" + "'" + SnoText.getText() + "'";
                    statement.executeUpdate(sql);
                    PreparedStatement state;
                    ResultSet resultSet;
                    state = connection.prepareStatement("select *from absent");
                    resultSet = state.executeQuery();
                    while (mm.getRowCount() > 0) {//把表格进行刷新，下次显示的时候重头开始显示
                        //System.out.println(model.getRowCount());
                        mm.removeRow(mm.getRowCount() - 1);
                    }
                    while (resultSet.next()) {//把更新后的数据重新显示到表格中，下同
                        String Sno = resultSet.getString(1);
                        String Sname = resultSet.getString(2);
                        String Dno = resultSet.getString(3);
                        String Atime = resultSet.getString(4);
                        String Areason = resultSet.getString(5);
                        String[] data = {Sno, Sname, Dno, Atime, Areason};
                        mm.addRow(data);
                    }
                }*/
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        JOptionPane.showMessageDialog(this,"修改成功");
    }

}

            /*try {

            } catch (SQLException ex) {
                ex.printStackTrace();
            }*/


            /*try {

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
*/
            /*try {

            } catch (SQLException ex) {
                ex.printStackTrace();
            }*/


                    /*
                    statement.executeUpdate(sql);
                    PreparedStatement state;
                    ResultSet resultSet;
                    state=connection.prepareStatement("select *from absent");
                    resultSet = state.executeQuery();
                    while(mm.getRowCount()>0){//把表格进行刷新，下次显示的时候重头开始显示
                        //System.out.println(model.getRowCount());
                        mm.removeRow(mm.getRowCount()-1);
                    }
                    while (resultSet.next()){//把更新后的数据重新显示到表格中，下同
                        String Sno=resultSet.getString(1);
                        String Sname=resultSet.getString(2);
                        String Dno=resultSet.getString(3);
                        String Atime=resultSet.getString(4);
                        String Areason=resultSet.getString(5);
                        String[] data={Sno,Sname,Dno,Atime,Areason};
                        mm.addRow(data);
                    }
                }
                if(AtimeText.getText().length()>0&&SnoText.getText().length()==0&&SnameText.getText().length()==0){//只修改缺寝时间
                    Statement statement = connection.createStatement();
                    String sql="update absent set Atime="+"'"+AtimeText.getText()+"'"+"where Sname"+"="+"'"+nameText.getText()+"'";





                    statement.executeUpdate(sql);
                    PreparedStatement state;
                    ResultSet resultSet;
                    state=connection.prepareStatement("select *from student");
                    resultSet = state.executeQuery();
                    while(mm.getRowCount()>0){//把表格进行刷新，下次显示的时候重头开始显示
                        //System.out.println(model.getRowCount());
                        mm.removeRow(mm.getRowCount()-1);
                    }
                    while (resultSet.next()){
                        String Sno=resultSet.getString(1);
                        String Sname=resultSet.getString(2);
                        String Ssex=resultSet.getString(3);
                        String Sdept=resultSet.getString(4);
                        String DDno=resultSet.getString(5);
                        String Scheckin=resultSet.getString(6);
                        String[] data={Sno,Sname,Ssex,Sdept,DDno,Scheckin};
                        mm.addRow(data);
                    }
                }
                if(suseText.getText().length()>0&&SdeptText.getText().length()>0){//同时修改专业和宿舍
                    Statement statement = connection.createStatement();
                    String sql="update student set Sdept="+"'"+SdeptText.getText()+"'"+", Dno="+ "'"+suseText.getText()+"'"   +"where Sname"+"="+"'"+nameText.getText()+"'";




                    statement.executeUpdate(sql);
                    PreparedStatement state;
                    ResultSet resultSet;
                    state=connection.prepareStatement("select *from student");
                    resultSet = state.executeQuery();
                    while(mm.getRowCount()>0){//把表格进行刷新，下次显示的时候重头开始显示
                        //System.out.println(model.getRowCount());
                        mm.removeRow(mm.getRowCount()-1);
                    }
                    while (resultSet.next()){
                        String Sno=resultSet.getString(1);
                        String Sname=resultSet.getString(2);
                        String Ssex=resultSet.getString(3);
                        String Sdept=resultSet.getString(4);
                        String DDno=resultSet.getString(5);
                        String Scheckin=resultSet.getString(6);
                        String[] data={Sno,Sname,Ssex,Sdept,DDno,Scheckin};
                        mm.addRow(data);
                    }
                }

            } catch (Exception e1) {
                e1.printStackTrace();
            }*/

        /*if(e.getSource()==submit&&type==1){//如果是学生的身份进入
            PreparedStatement state;
            ResultSet resultSet;
            try {
                state=connection.prepareStatement("select Dphone from Dormitory where Dno ="+"'"+suseText.getText()+"'");
                resultSet = state.executeQuery();
                while (resultSet.next()){
                    //suse.setText("电话");
                    suseText.setText(resultSet.getString("Dphone"));
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

        }*/








