package com.example.foodtasker.Fragments;

import android.app.ActionBar;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;

//import com.ahmadrosid.lib.drawroutemap.DrawMarker;
//import com.ahmadrosid.lib.drawroutemap.DrawRouteMaps;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.foodtasker.Adapters.TrayAdapter;
import com.example.foodtasker.Objects.Restaurant;
import com.example.foodtasker.Objects.Tray;
import com.example.foodtasker.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import com.example.foodtasker.BuildConfig;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends Fragment {

    private ArrayList<Tray> trayList;
    private TrayAdapter adapter;
    private Button statusView;

    // TODO: Change API
    String LOCAL_API_URL = BuildConfig.LOCAL_API_URL;


    public OrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        trayList = new ArrayList<Tray>();
        adapter = new TrayAdapter(this.getActivity(), trayList);

        ListView listView = (ListView) getActivity().findViewById(R.id.tray_list);
        listView.setAdapter(adapter);

        statusView = (Button) getActivity().findViewById(R.id.status);

        // Get The Latest Order Data
        getLatestOrder();
    }

    private void getLatestOrder() {
        SharedPreferences sharedPref = getActivity().getSharedPreferences("MY_KEY", Context.MODE_PRIVATE);
        String url = LOCAL_API_URL + "/customer/order/latest/?access_token=" + sharedPref.getString("token", "");

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("LATEST ORDER", response.toString());
                        String status = "";

                        // Get Order details in JSONArray type
                        JSONArray orderDetailsArray = null;

                        try {
                            orderDetailsArray = response.getJSONObject("order").getJSONArray("order_details");
                            status = response.getJSONObject("order").getString("status");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        // Check if the current user have no order, then show a message
                        if (orderDetailsArray == null || orderDetailsArray.length() == 0) {
                            TextView alertText = new TextView(getActivity());
                            alertText.setText("You have no order");
                            alertText.setTextSize(17);
                            alertText.setGravity(Gravity.CENTER);
                            alertText.setLayoutParams(
                                    new TableLayout.LayoutParams(
                                            ViewGroup.LayoutParams.WRAP_CONTENT,
                                            ViewGroup.LayoutParams.WRAP_CONTENT,
                                            1
                                    ));

                            LinearLayout linearLayout = (LinearLayout) getActivity().findViewById(R.id.order_layout);
                            linearLayout.removeAllViews();
                            linearLayout.addView(alertText);
                        }

                        // Add this to the ListView. Convert JSON object to Tray object
                        for (int i = 0; i < orderDetailsArray.length(); i++) {
                            Tray tray = new Tray();
                            try {
                                JSONObject orderDetail = orderDetailsArray.getJSONObject(i);
                                tray.setMealName(orderDetail.getJSONObject("meal").getString("name"));
                                tray.setMealPrice(orderDetail.getJSONObject("meal").getInt("price"));
                                tray.setMealQuantity(orderDetail.getInt("quantity"));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            trayList.add(tray);
                        }

                        // Update the ListView with Order Details data
                        adapter.notifyDataSetChanged();

                        // Update Status View
                        statusView.setText(status);
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
