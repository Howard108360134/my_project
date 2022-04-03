package com.example.smart_app;

public class User {

    public String name, age, email,status, set_temp;
    public Double temp;


    public User(){

    }

    public User(String name, String age, String email, Double temp, String status, String set_temp){
        this.name = name;
        this.age = age;
        this.email = email;
        this.temp = temp;
        this.status = status;
        this.set_temp = set_temp;

    }

}
