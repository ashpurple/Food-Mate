package com.example.foodmate;

//import com.google.type.Date;
import java.util.Date;

public class WriteInfo {
    private String title;
    private String contents;
    private String publisher;
    private Integer numOfRecruits;
    private Date createdAt;



    public WriteInfo(String title, String contents, String publisher, Integer numOfRecuits, Date createdAt){
        this.title = title;
        this.contents = contents;
        this.publisher = publisher;
        this.numOfRecruits = numOfRecuits;
        this.createdAt = createdAt;


    }



    public String getTitle(){ return this.title;}
    public void setTitle(String title){ this.title = title;}
    public String getContents(){ return this.contents;}
    public void setContents(String contents){ this.contents = contents;}
    public String getPublisher(){ return this.publisher;}
    public void setPublisher(String publisher){ this.publisher = publisher;}
    public Integer getNumOfRecruits() { return numOfRecruits; }
    public void setNumOfRecruits(Integer numOfRecruits) { this.numOfRecruits = numOfRecruits; }
    public Date getCreatedAt(){ return this.createdAt;}
    public void setCreatedAt(Date createdAt){ this.createdAt = createdAt;}

}