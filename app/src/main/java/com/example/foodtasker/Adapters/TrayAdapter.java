// ALL UPDATES COMPLETED
package com.example.foodtasker.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.foodtasker.Objects.Tray;
import com.example.foodtasker.R;

import java.util.ArrayList;

public class TrayAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<Tray> trayList;

    public TrayAdapter(Activity activity, ArrayList<Tray> trayList) {
        this.activity = activity;
        this.trayList = trayList;
    }

    @Override
    public int getCount() {
        return trayList.size();
    }

    @Override
    public Object getItem(int i) {
        return trayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view == null) {
            view = LayoutInflater.from(activity).inflate(R.layout.list_item_tray, null);
        }

        TextView mealName = (TextView) view.findViewById(R.id.tray_meal_name);
        TextView mealQuantity = (TextView) view.findViewById(R.id.tray_meal_quantity);
        TextView mealSubTotal = (TextView) view.findViewById(R.id.tray_meal_subtotal);

        Tray tray = trayList.get(i);
        mealName.setText(tray.getMealName());
        mealQuantity.setText(tray.getMealQuantity() + "");
        mealSubTotal.setText("$" + (tray.getMealPrice() * tray.getMealQuantity()));

        return view;
    }
}