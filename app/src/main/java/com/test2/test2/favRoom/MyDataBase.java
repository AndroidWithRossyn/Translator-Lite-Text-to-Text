package com.test2.test2.favRoom;


import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Fav.class}, version = 1, exportSchema = true)
public abstract class MyDataBase extends RoomDatabase {
    public abstract FavDao favDao();
}
