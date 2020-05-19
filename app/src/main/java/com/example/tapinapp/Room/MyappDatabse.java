package com.example.tapinapp.Room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.tapinapp.Model.UrlData;


@Database(entities = {RoomUrlData.class},version = 1)
public abstract class MyappDatabse extends RoomDatabase {

    public abstract Mydao mydao();

}
