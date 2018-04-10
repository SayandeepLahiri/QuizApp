package com.example.sayandeep.quizquotient.Objects;

/**
 * Created by Sayandeep on 08-04-2018.
 */

public class User {
    private String userName;
    private String password;
    private String emailPhone;
    public User(){}

    public User(String userName, String password, String emailPhone) {
        this.userName = userName;
        this.password = password;
        this.emailPhone = emailPhone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmailPhone() {
        return emailPhone;
    }

    public void setEmailPhone(String emailPhone) {
        this.emailPhone = emailPhone;
    }
}
