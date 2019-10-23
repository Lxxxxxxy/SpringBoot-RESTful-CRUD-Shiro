package cn.lixingyu.springmybatisthymeleaf.entity;

import java.io.Serializable;

/**
 * @author lxxxxxxy
 * @time 2019/08/09 16:28
 */
public class User implements Serializable {
    private String id;
    private String username;
    private String password;
    private String mailAddress;
    private Integer status;

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", mailAddress='" + mailAddress + '\'' +
                ", status=" + status +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public User(String id, String username, String password, String mailAddress, Integer status) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.mailAddress = mailAddress;
        this.status = status;
    }

    public User() {
    }
}
