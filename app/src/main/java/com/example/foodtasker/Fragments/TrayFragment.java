package com.example.foodtasker.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.os.AsyncTask;
import android.util.Log;
import android.annotation.SuppressLint;
import android.widget.Toast;

import com.example.foodtasker.Activities.PaymentActivity;
//import com.example.foodtasker.Adapters.TrayAdapter;
import com.example.foodtasker.AppDatabase;
import com.example.foodtasker.Objects.Tray;
import com.example.foodtasker.Activities.PaymentActivity;
import com.example.foodtasker.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TrayFragment extends Fragment {

    private AppDatabase db;


    public TrayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tray, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Initialise DB
        db = AppDatabase.getAppDatabase(getContext());
        listTray();

        ListView restaurantListView = (ListView) getActivity().findViewById(R.id.tray_list);
        restaurantListView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return 4;
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
                return LayoutInflater.from(getActivity()).inflate(R.layout.list_item_tray, null);
            }
        });

        Button buttonLogin = (Button) getActivity().findViewById(R.id.button_add_payment);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(), PaymentActivity.class);
                startActivity(intent);

            }
        });
    }

    @SuppressLint("StaticFieldLeak")
    private void listTray() {
        new AsyncTask<Void, Void, List<Tray>>() {
            @Override
            protected List<Tray> doInBackground(Void... voids) {
                return db.trayDao().getAll();
            }

            @Override
            protected void onPostExecute(List<Tray> trays) {
                super.onPostExecute(trays);
                for (Tray tray : trays) {
                    Log.d("TRAY ITEM", tray.getMealName() + "-" + tray.getMealQuantity());
                }
            }
        }.execute();

    }

}
