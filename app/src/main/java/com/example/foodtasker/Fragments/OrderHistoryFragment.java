
package com.example.foodtasker.Fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.foodtasker.Adapters.OrderHistoryAdapter;
import com.example.foodtasker.Objects.Order;
import com.example.foodtasker.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrderHistoryFragment extends Fragment {

    private OrderHistoryAdapter adapter;
    private ArrayList<Order> orderList;

    public OrderHistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order_history, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        orderList = new ArrayList<Order>();
        adapter = new OrderHistoryAdapter(this.getActivity(), orderList);

        ListView orderListView = (ListView) getActivity().findViewById(R.id.order_history_list);
        orderListView.setAdapter(adapter);

        // Get list of ready orders to be delivered
        getReadyOrders();
    }

    private void getReadyOrders() {
        SharedPreferences sharedPref = getActivity().getSharedPreferences("MY_KEY", Context.MODE_PRIVATE);
        String url = getString(R.string.API_URL) + "/driver/order/history/?access_token=" + sharedPref.getString("token", "");

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("ORDER HISTORY LIST", response.toString());

                        // Get orders in JSONArray type
                        try {
                            JSONArray ordersJSONArray = response.getJSONArray("order_history");
                            for (int i = 0; i < ordersJSONArray.length(); i++) {
                                JSONObject orderObject = ordersJSONArray.getJSONObject(i);

                                Order order = new Order(
                                        orderObject.getString("id"),
                                        orderObject.getJSONObject("restaurant").getString("name"),
                                        orderObject.getJSONObject("customer").getString("name"),
                                        orderObject.getString("address"),
                                        orderObject.getJSONObject("customer").getString("avatar")
                                );
                                orderList.add(order);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        // Update the ListView with fresh data
                        adapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(jsonObjectRequest);
    }

}