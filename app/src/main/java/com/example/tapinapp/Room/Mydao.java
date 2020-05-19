package com.example.tapinapp.Room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.tapinapp.Model.UrlData;

import java.util.List;

@Dao
public interface Mydao {

    @Insert
    public void addLink(RoomUrlData roomUrlData);

    @Query("select * from RoomUrlData")
    public List<RoomUrlData> getAllLinks();

    @Query("DELETE FROM RoomUrlData")
    public void deleteAll();

    @Query("select * from RoomUrlData where id = :id")
    public RoomUrlData chekIfPresent(String id);

    @Delete
    public void deleteLink(RoomUrlData roomUrlData);
}
