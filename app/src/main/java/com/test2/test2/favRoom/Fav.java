package com.test2.test2.favRoom;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.io.Serializable;

@Entity
public class Fav implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "lang1")
    private String lang1Name;

    @ColumnInfo(name = "lang2")
    private String lang2Name;

    @ColumnInfo(name = "lang1c")
    private String lang1Text;

    @ColumnInfo(name = "lang2c")
    private String lang2Text;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLang1Name() {
        return lang1Name;
    }

    public void setLang1Name(String lang1Name) {
        this.lang1Name = lang1Name;
    }

    public String getLang2Name() {
        return lang2Name;
    }

    public void setLang2Name(String lang2Name) {
        this.lang2Name = lang2Name;
    }


    public String getLang1Text() {
        return lang1Text;
    }

    public void setLang1Text(String lang1Text) {
        this.lang1Text = lang1Text;
    }

    public String getLang2Text() {
        return lang2Text;
    }

    public void setLang2Text(String lang2Text) {
        this.lang2Text = lang2Text;
    }
}
