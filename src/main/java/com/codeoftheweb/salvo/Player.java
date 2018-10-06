package com.codeoftheweb.salvo;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private String userName;
    private String userMail;
    private String userPassWord;

    public Player() { }

    public Player(String name, String mail, String passWord) {
        this.userName = name;
        this.userMail = mail;
        this.userPassWord = passWord;
    }

    public String getUserName() {
        return userName;
    }

    public void setFirstName(String firstName) {
        this.userName = firstName;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setLastName(String lastName) {
        this.userName = lastName;
    }

    public String getUserPassWord() {
        return userPassWord;
    }

    public void setUserPassWord(String passWord) {
        this.userPassWord = passWord;
    }

    public String toString() {
        return userName + " " + userName;
    }
}