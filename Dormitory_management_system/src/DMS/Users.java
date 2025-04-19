package DMS;

/**用户信息
 * @author LQ
 * @create 2020-06-19 15:38
 */
public class Users {
    private String name;
    private String password;
    private int type;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}