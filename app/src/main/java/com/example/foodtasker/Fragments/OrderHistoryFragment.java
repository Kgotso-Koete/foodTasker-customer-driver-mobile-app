
package com.example.foodtasker.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderHistoryFragment extends Fragment {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    private JSONArray ordersJSONArray;

    List<String> TestDataHeader;


    public OrderHistoryFragment() {
        // Required empty public constructor
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
        ordersJSONArray = new JSONArray();
        TestDataHeader = new ArrayList<String>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_main, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // get the listview
        expListView = (ExpandableListView) getActivity().findViewById(R.id.lvExp);

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
                                String orderId = orderObject.getString("id");
                                String header = orderObject.getJSONObject("restaurant").getString("name");
                                listDataHeader.add("Order number: " + orderId + " " + header);

                                // add child data
                                /////////////////////////////////////////////////////////////////////////////////
                                List<String> Header = new ArrayList<String>();
                                try {
                                    JSONArray trayJSONArray = orderObject.getJSONArray("order_details");

                                    for (int j = 0; j < trayJSONArray.length(); j++) {
                                        JSONObject trayObject = trayJSONArray.getJSONObject(j);
                                        String trayItem = trayObject.getJSONObject("meal").getString("name");
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