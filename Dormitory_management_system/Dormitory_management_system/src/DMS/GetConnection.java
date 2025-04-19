package DMS;

import java.sql.Connection;
import java.sql.DriverManager;

/**连接到SQL server
 * @author LQ

 */
public class GetConnection {
    private Connection con=null;
    public Connection GetConnection(){
            String URL="jdbc:mysql://localhost:3306/libing?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";//数据库位置
        String USER="root";
        String KEY="123456";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");//加载驱动，连接数据库，
            con= DriverManager.getConnection(URL, USER, KEY);
        } catch (Exception e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
        return con;
    }
}