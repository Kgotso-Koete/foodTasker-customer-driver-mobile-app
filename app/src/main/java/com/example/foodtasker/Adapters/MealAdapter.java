package com.example.foodtasker.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foodtasker.Activities.MealDetailActivity;
import com.example.foodtasker.Objects.Meal;
import com.example.foodtasker.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MealAdapter extends BaseAdapter {
    private Activity activity;
    private ArrayList<Meal> mealList;
    private String restaurantId;

    public MealAdapter(Activity activity, ArrayList<Meal> mealList, String restaurantId) {
        this.activity = activity;
        this.mealList = mealList;
        this.restaurantId = restaurantId;
    }

    @Override
    public int getCount() {
        return mealList.size();
    }

    @Override
    public Object getItem(int i) {
        return mealList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view == null) {
            view = LayoutInflater.from(activity).inflate(R.layout.list_item_meal, null);
        }

        final Meal meal = mealList.get(i);

        TextView mealName = (TextView) view.findViewById(R.id.meal_name);
        TextView mealDesc = (TextView) view.findViewById(R.id.meal_desc);
        TextView mealPrice = (TextView) view.findViewById(R.id.meal_price);
        ImageView mealImage = (ImageView) view.findViewById(R.id.meal_image);

        mealName.setText(meal.getName());
        mealDesc.setText(meal.getShort_description());
        mealPrice.setText("$" + meal.getPrice());
        Picasso.with(activity.getApplicationContext()).load(meal.getImage()).fit().into(mealImage);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, MealDetailActivity.class);
                intent.putExtra("restaurantId", restaurantId);
                intent.putExtra("mealId", meal.getId());
                intent.putExtra("mealName", meal.getName());
                intent.putExtra("mealDescription", meal.getShort_description());
                intent.putExtra("mealPrice", meal.getPrice());
                intent.putExtra("mealImage", meal.getImage());
                activity.startActivity(intent);
            }
        });

        return view;
    }
}
