package com.test2.test2.favRoom;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FavDao {

    @Query("SELECT * FROM fav")
    List<Fav> getAll();

    @Insert
    void insert(Fav fav);

    @Delete
    void delete(Fav fav);

}
