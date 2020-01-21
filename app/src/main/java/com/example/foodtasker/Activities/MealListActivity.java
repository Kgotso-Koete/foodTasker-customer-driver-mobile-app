package com.example.foodtasker.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

//import com.example.foodtasker.Adapters.MealAdapter;
//import com.example.foodtasker.Objects.Meal;
import com.example.foodtasker.R;

public class MealListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_list);

        Intent intent = getIntent();
        String restaurantId = intent.getStringExtra("restaurantId");
        String restaurantName = intent.getStringExtra("restaurantName");
        
        getSupportActionBar().setTitle(restaurantName);

        ListView listView = (ListView) findViewById(R.id.meal_list);
        listView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                return LayoutInflater.from(MealListActivity.this).inflate(R.layout.list_item_meal, null);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MealListActivity.this, MealDetailActivity.class);
                startActivity(intent);
            }
        });
    }
}
