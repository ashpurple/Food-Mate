package com.example.foodmate;


class User {
    private String key;
    private String nick;

    User(){}

    User(String key, String nick){
        this.key = key;
        this.nick = nick;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key){
        this.key = key;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick){
        this.nick = nick;
    }


}