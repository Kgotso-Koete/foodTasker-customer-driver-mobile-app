// WITH HELP FROM: https://www.androidhive.info/2013/07/android-expandable-list-view-tutorial/
package com.example.foodtasker.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodtasker.Adapters.ExpandableListAdapter;
import com.example.foodtasker.R;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;

import android.widget.ExpandableListView;

import android.content.Context;
import android.content.SharedPreferences;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.foodtasker.Objects.Order;
import com.example.foodtasker.Objects.HistoryItem;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderHistoryFragment extends Fragment {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<Order> listDataHeader;
    HashMap<Order, List<HistoryItem>> listDataChild;
    private JSONArray ordersJSONArray;

    List<String> TestDataHeader;


    public OrderHistoryFragment() {
        // Required empty public constructor
        listDataHeader = new ArrayList<Order>();
        listDataChild = new HashMap<Order, List<HistoryItem>>();
        ordersJSONArray = new JSONArray();
        TestDataHeader = new ArrayList<String>();
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

        // get the listview
        expListView = (ExpandableListView) getActivity().findViewById(R.id.order_history_list);

        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

    }

    private void prepareListData() {

        SharedPreferences sharedPref = getActivity().getSharedPreferences("MY_KEY", Context.MODE_PRIVATE);
        String url = getString(R.string.API_URL) + "/driver/order/history/?access_token=" + sharedPref.getString("token", "");

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        // Get orders in JSONArray type
                        try {
                            ordersJSONArray = response.getJSONArray("order_history");

                            for (int i = 0; i < ordersJSONArray.length(); i++) {
                                JSONObject orderObject = ordersJSONArray.getJSONObject(i);
                                // add parent data

                                Order orderListHeading = new Order(
                                        orderObject.getString("id"),
                                        orderObject.getJSONObject("restaurant").getString("name"),
                                        orderObject.getJSONObject("customer").getString("name"),
                                        orderObject.getString("address"),
                                        orderObject.getJSONObject("customer").getString("avatar")
                                );
                                listDataHeader.add(orderListHeading);

                                // add child data
                                /////////////////////////////////////////////////////////////////////////////////
                                List<HistoryItem> Header = new ArrayList<HistoryItem>();
                                try {
                                    JSONArray trayJSONArray = orderObject.getJSONArray("order_details");

                                    for (int j = 0; j < trayJSONArray.length(); j++) {
                                        JSONObject trayObject = trayJSONArray.getJSONObject(j);

                                        HistoryItem trayItem = new HistoryItem(
                                                trayObject.getString("id"),
                                                trayObject.getJSONObject("meal").getString("name"),
                                                trayObject.getString("quantity"),
                                                trayObject.getString("sub_total")
                                        );

                                        Header.add(trayItem);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                listDataChild.put(listDataHeader.get(i), Header);

                                // add child data
                                /////////////////////////////////////////////////////////////////////////
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        listAdapter.notifyDataSetChanged();
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