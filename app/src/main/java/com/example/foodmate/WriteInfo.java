package com.example.foodmate;


import com.google.firebase.Timestamp;
import java.util.ArrayList;


public class WriteInfo {
    private String posts_id;
    private String nickname;
    private String title;
    private String contents;
    private String publisher;
    private String selectedCategory;
    private Integer numOfRecruits;
    private Timestamp createdAt;
    private String status; // (recruiting / recruited / delivered)
    private Integer curRecruits;
    private ArrayList participants;


    public WriteInfo(String posts_id, String nickname, String title, String contents, String publisher,
                     String selectedCategory, Integer numOfRecruits, Timestamp createdAt,
                     String status, Integer curRecruits, ArrayList participants){
        this.posts_id = posts_id;
        this.nickname = nickname;
        this.title = title;
        this.contents = contents;
        this.publisher = publisher;
        this.selectedCategory = selectedCategory;
        this.numOfRecruits = numOfRecruits;
        this.createdAt = createdAt;
        this.status = status;
        this.curRecruits = curRecruits;
        this.participants = participants;

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
    public String getStatus(){return this.status;}
    public void setStatus(String status){this.status=status;}
    public Integer getCurRecruits(){return this.curRecruits;}
    public void setCurRecruits(Integer curRecruits){this.curRecruits=curRecruits;}
    public ArrayList getParticipants() { return participants; }
    public void setParticipants(ArrayList participants) { this.participants = participants; }
    public String getPosts_id() { return posts_id; }
    public void setPosts_id(String posts_id) { this.posts_id = posts_id; }
}