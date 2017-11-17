/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pers.ryan.personalinfotracer.domain;

import java.util.Date;

/**
 * 
 * @author Ryan Tsang
 */
public class Person implements Comparable{
    private int id;
    private String name;
    private String[] likes;
    private String[] dislikes;
    private Date DOB;

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the likes
     */
    public String[] getLikes() {
        return likes;
    }

    /**
     * @param likes the likes to set
     */
    public void setLikes(String[] likes) {
        this.likes = likes;
    }

    /**
     * @return the dislikes
     */
    public String[] getDislikes() {
        return dislikes;
    }

    /**
     * @param dislikes the dislikes to set
     */
    public void setDislikes(String[] dislikes) {
        this.dislikes = dislikes;
    }

    /**
     * @return the DOB
     */
    public Date getDOB() {
        return DOB;
    }

    /**
     * @param DOB the DOB to set
     */
    public void setDOB(Date DOB) {
        this.DOB = DOB;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    public int compareTo(Object o) {
        Person p = null;
        if(o instanceof Person){
            p = (Person)o;
        }
        return name.compareTo(p.name);
    }
    
}
