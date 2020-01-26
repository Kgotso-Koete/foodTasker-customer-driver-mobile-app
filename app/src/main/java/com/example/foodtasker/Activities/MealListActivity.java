package com.example.foodtasker.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.AdapterView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.foodtasker.Adapters.MealAdapter;
import com.example.foodtasker.Objects.Meal;
import com.example.foodtasker.BuildConfig;
import com.example.foodtasker.R;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class MealListActivity extends AppCompatActivity {

    private ArrayList<Meal> mealArrayList;
    private MealAdapter adapter;

    String LOCAL_API_URL = BuildConfig.LOCAL_API_URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_list);

        Intent intent = getIntent();
        String restaurantId = intent.getStringExtra("restaurantId");
        String restaurantName = intent.getStringExtra("restaurantName");

        getSupportActionBar().setTitle(restaurantName);

        mealArrayList = new ArrayList<Meal>();
        adapter = new MealAdapter(this, mealArrayList, restaurantId);

        ListView listView = (ListView) findViewById(R.id.meal_list);
        listView.setAdapter(adapter);

        // Get Meals list
        getMeals(restaurantId);
    }

    private void getMeals(String restaurantId) {
        String url = LOCAL_API_URL + "/customer/meals/" + restaurantId + "/";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("MEAL LIST", response.toString());

                        // Convert JSON data to JSON Array
                        JSONArray mealsJSONArray = null;

                        try {
                            mealsJSONArray = response.getJSONArray("meals");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        // Convert Json Array to Restaurant Array
                        Gson gson = new Gson();
                        Meal[] meals = gson.fromJson(mealsJSONArray.toString(), Meal[].class);

                        // Refresh ListView with up-to-date data
                        mealArrayList.clear();
                        mealArrayList.addAll(new ArrayList<Meal>(Arrays.asList(meals)));
                        adapter.notifyDataSetChanged();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObjectRequest);
    }
}
