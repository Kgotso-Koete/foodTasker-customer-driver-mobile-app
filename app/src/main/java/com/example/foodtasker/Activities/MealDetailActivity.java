package com.example.foodtasker.Activities;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

//import com.example.foodtasker.AppDatabase;
//import com.example.foodtasker.Objects.Tray;
import com.example.foodtasker.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MealDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_detail);

        Intent intent = getIntent();
        String restaurantId = intent.getStringExtra("restaurantId");
        String mealId = intent.getStringExtra("mealId");
        String mealName = intent.getStringExtra("mealName");
        String mealDescription = intent.getStringExtra("mealDescription");
        Float mealPrice = intent.getFloatExtra("mealPrice", 0);
        String mealImage = intent.getStringExtra("mealImage");

        getSupportActionBar().setTitle(mealName);

        TextView name = (TextView) findViewById(R.id.meal_name);
        TextView desc = (TextView) findViewById(R.id.meal_desc);
        TextView price = (TextView) findViewById(R.id.meal_price);
        ImageView image = (ImageView) findViewById(R.id.meal_image);

        name.setText(mealName);
        desc.setText(mealDescription);
        price.setText("$" + mealPrice);
        Picasso.with(getApplicationContext()).load(mealImage).fit().into(image);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.meal_details, menu);
        return super.onCreateOptionsMenu(menu);
    }

}
