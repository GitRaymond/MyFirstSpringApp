package com.capgemini.coding.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ProgrammingLanguage {

    public long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isRaymondLikesIt() {
        return raymondLikesIt;
    }

    public void setRaymondLikesIt(boolean raymondLikesIt) {
        this.raymondLikesIt = raymondLikesIt;
    }

    @Id
    @GeneratedValue
    private long id;

    private String type;
    private boolean raymondLikesIt;

}
