package com.example.smart_app;

public class User {

    public String name, age, email,status, set_temp;
    public Double temp, set_temperature, set_gas, set_humidity;
    public Integer set_fire, water;

    public User(){

    }

    public User(String name, String age, String email, Double temp, String status, String set_temp, Integer water, Integer set_fire, Double set_temperature, Double set_gas, Double set_humidity){
        this.name = name;
        this.age = age;
        this.email = email;
        this.temp = temp;
        this.status = status;
        this.set_temp = set_temp;
        this.water = water;
        this.set_fire = set_fire;
        this.set_temperature = set_temperature;
        this.set_gas = set_gas;
        this.set_humidity = set_humidity;

    }

}
