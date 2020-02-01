// COMPLETED: ONLY URL API TO BE CHANGED
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

import com.example.foodtasker.BuildConfig;


public class OrderAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<Order> orderList;

    // TODO: Change API
    String LOCAL_API_URL = BuildConfig.LOCAL_API_URL;

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
                // Show an alert
                AlertDialog.Builder builder = new AlertDialog.Builder((activity));
                builder.setTitle("Pick this order?");
                builder.setMessage("Would you like to take this order?");
                builder.setPositiveButton("Cancel", null);
                builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(activity.getApplicationContext(), "ORDER PICKED", Toast.LENGTH_SHORT).show();

                        pickOrder(order.getId());
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        return view;
    }

    private void pickOrder(final String orderId) {
        String url = LOCAL_API_URL + "/driver/order/pick/";

        StringRequest postRequest = new StringRequest
                (Request.Method.POST, url, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        // Execute code
                        Log.d("ORDER PICKED", response.toString());

                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            if (jsonObj.getString("status").equals("success")) {
                                FragmentTransaction transaction = ((DriverMainActivity) activity).getSupportFragmentManager().beginTransaction();
                                transaction.replace(R.id.content_frame, new DeliveryFragment()).commit();
                            } else {
                                Toast.makeText(activity, jsonObj.getString("error"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Toast.makeText(activity, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                final SharedPreferences sharedPref = activity.getSharedPreferences("MY_KEY", Context.MODE_PRIVATE);
                Map<String, String> params = new HashMap<String, String>();
                params.put("access_token", sharedPref.getString("token", ""));
                params.put("order_id", orderId);

                return params;
            }
        };

        postRequest.setRetryPolicy(
                new DefaultRetryPolicy(
                        0,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        );

        RequestQueue queue = Volley.newRequestQueue(activity);
        queue.add(postRequest);
    }
}