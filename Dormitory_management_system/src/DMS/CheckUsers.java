package DMS;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**获得用户列表
 * @author LQ
 * @create 2020-06-05 15:40
 */
public class CheckUsers {
    GetConnection getConnection=new GetConnection();
    Connection connection=getConnection.GetConnection();
    public ArrayList<Users> getUsers(){
        ArrayList<Users>list = new ArrayList<>();
        try {

            PreparedStatement state=connection.prepareStatement("select *from Users");
            ResultSet res=state.executeQuery();
            while(res.next()){
                Users user = new Users();
                user.setName(res.getString(1));
                user.setPassword(res.getString(2));
                user.setType(res.getInt(3));///////////类型
                list.add(user);
            }

        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
        }
        return list;
    }
}