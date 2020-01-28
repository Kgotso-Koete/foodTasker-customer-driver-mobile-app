package com.example.foodtasker.Activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.foodtasker.AppDatabase;
import com.example.foodtasker.R;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;
import com.stripe.android.view.CardInputWidget;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import com.example.foodtasker.BuildConfig;

public class PaymentActivity extends AppCompatActivity {

    private Button buttonPlaceOrder;

    private String STRIPE_API_KEY = BuildConfig.STRIPE_API_KEY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        getSupportActionBar().setTitle("");

        final CardInputWidget mCardInputWidget = (CardInputWidget) findViewById(R.id.card_input_widget);

        buttonPlaceOrder = (Button) findViewById(R.id.button_place_order);
        buttonPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onClick(View v) {
                final Card card = mCardInputWidget.getCard();
                if (card == null) {
                    // Do not continue token creation.
                    Toast.makeText(getApplicationContext(), "Card cannot be blank", Toast.LENGTH_LONG).show();
                } else {


                    new AsyncTask<Void, Void, Void>() {
                        @Override
                        protected Void doInBackground(Void... voids) {

                            Stripe stripe = new Stripe(getApplicationContext(), STRIPE_API_KEY);
                            stripe.createToken(
                                    card,
                                    new TokenCallback() {
                                        public void onSuccess(Token token) {
                                            // Send token to server
                                            Toast.makeText(getApplicationContext(),
                                                    token.getId(),
                                                    Toast.LENGTH_LONG
                                            ).show();

                                        }

                                        public void onError(Exception error) {
                                            // Show localized error message
                                            Toast.makeText(getApplicationContext(),
                                                    error.getLocalizedMessage(),
                                                    Toast.LENGTH_LONG
                                            ).show();
                                        }
                                    }
                            );

                            return null;
                        }
                    }.execute();
                }
            }
        });
    }
}
