package com.example.notemanagement;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.notemanagement.DAO.AccountDAO;
import com.example.notemanagement.Entity.Account;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Account.class},version = 1, exportSchema = false)
public abstract class RoomDB  extends RoomDatabase {

    //Create database instance
    public abstract AccountDAO accountDAO();

    private static String dbName = "notedatabase";
    private static volatile RoomDB INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;

    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static synchronized RoomDB getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (RoomDB.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RoomDB.class, dbName).build();
                }
            }
        }
        return INSTANCE;
    }
}