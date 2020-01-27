package com.example.foodtasker;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.foodtasker.Objects.Tray;

import java.util.List;

@Dao
public interface TrayDAO {
    @Query("SELECT * FROM tray")
    List<Tray> getAll();

    @Insert
    void insertAll(Tray... trays);

    @Query("DELETE FROM tray")
    void deleteAll();

    @Query("SELECT * FROM tray WHERE meal_id = :mealId")
    Tray getTray(String mealId);

    @Query("UPDATE tray SET meal_quantity = meal_quantity + :mealQty WHERE id = :trayId")
    void updateTray(int trayId, int mealQty);
}
