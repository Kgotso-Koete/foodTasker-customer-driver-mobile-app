// COMPLETED
package com.example.foodtasker;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.foodtasker.Objects.Tray;

@Database(entities = {Tray.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase db;

    public abstract TrayDAO trayDao();

    public static AppDatabase getAppDatabase(Context context) {
        if (db == null) {
            db = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "database-name").build();
        }
        return db;
    }
}
