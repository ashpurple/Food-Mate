package com.example.foodmate;


import com.google.firebase.Timestamp;
import java.util.Date;


public class WriteInfo {
    private String nickname;
    private String title;
    private String contents;
    private String publisher;
    private String selectedCategory;
    private Integer numOfRecruits;
    private Timestamp createdAt;



    public WriteInfo(String nickname, String title, String contents, String publisher, String selectedCategory, Integer numOfRecuits, Timestamp createdAt){
        this.nickname = nickname;
        this.title = title;
        this.contents = contents;
        this.publisher = publisher;
        this.selectedCategory = selectedCategory;
        this.numOfRecruits = numOfRecuits;
        this.createdAt = createdAt;


    }


    public String getNickname(){ return this.nickname;}
    public void setNickname(String nickname){ this.nickname = nickname;}
    public String getTitle(){ return this.title;}
    public void setTitle(String title){ this.title = title;}
    public String getContents(){ return this.contents;}
    public void setContents(String contents){ this.contents = contents;}
    public String getPublisher(){ return this.publisher;}
    public void setPublisher(String publisher){ this.publisher = publisher;}
    public Integer getNumOfRecruits() { return numOfRecruits; }
    public void setSelectedCategory(String selectedCategory){ this.contents = selectedCategory;}
    public String getSelectedCategory(){ return this.selectedCategory;}
    public void setNumOfRecruits(Integer numOfRecruits) { this.numOfRecruits = numOfRecruits; }
    public Timestamp getCreatedAt(){ return this.createdAt;}
    public void setCreatedAt(Timestamp createdAt){ this.createdAt = createdAt;}

}