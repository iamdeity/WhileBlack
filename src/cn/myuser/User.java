package cn.myuser;

public class User {
    private int id;
    private String username;
    private String pass;



    public User() {
        super();
    }
    public User(String username, String pass) {
        super();
        this.username = username;
        this.pass = pass;


    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPass() {
        return pass;
    }
    public void setPass(String pass) {
        this.pass = pass;
    }

}
