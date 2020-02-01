package com.example.foodtasker.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.foodtasker.Activities.CustomerMainActivity;
import com.example.foodtasker.Activities.DriverMainActivity;
import com.example.foodtasker.Activities.MealDetailActivity;
import com.example.foodtasker.Fragments.DeliveryFragment;
import com.example.foodtasker.Objects.Order;
import com.example.foodtasker.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OrderAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<Order> orderList;

    public OrderAdapter(Activity activity, ArrayList<Order> orderList) {
        this.activity = activity;
        this.orderList = orderList;
    }

    @Override
    public int getCount() {
        return orderList.size();
    }

    @Override
    public Object getItem(int i) {
        return orderList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view == null) {
            view = LayoutInflater.from(activity).inflate(R.layout.list_item_order, null);
        }

        final Order order = orderList.get(i);

        TextView restaurantName = (TextView) view.findViewById(R.id.restaurant_name);
        TextView customerName = (TextView) view.findViewById(R.id.customer_name);
        TextView customerAddress = (TextView) view.findViewById(R.id.customer_address);
        ImageView customerImage = (ImageView) view.findViewById(R.id.customer_image);

        restaurantName.setText(order.getRestaurantName());
        customerName.setText(order.getCustomerName());
        customerAddress.setText(order.getCustomerAddress());
        Picasso.with(activity.getApplicationContext()).load(order.getCustomerImage()).fit().into(customerImage);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return view;
    }


}