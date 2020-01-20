package com.example.foodtasker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.foodtasker.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
public class SignInActivity extends AppCompatActivity {

    private Button customerButton, driverButton;
    private CallbackManager callbackManager;
    private SharedPreferences sharedPref;
    private Button buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        customerButton = (Button) findViewById(R.id.button_customer);
        driverButton = (Button) findViewById(R.id.button_driver);

        // Handle Customer Button
        customerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customerButton.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                customerButton.setTextColor(getResources().getColor(android.R.color.white));

                driverButton.setBackgroundColor(getResources().getColor(android.R.color.white));
                driverButton.setTextColor(getResources().getColor(R.color.colorAccent));
            }
        });

        // Handle Driver Button
        driverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                driverButton.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                driverButton.setTextColor(getResources().getColor(android.R.color.white));

                customerButton.setBackgroundColor(getResources().getColor(android.R.color.white));
                customerButton.setTextColor(getResources().getColor(R.color.colorAccent));
            }
        });

        // Handle Login Button
        buttonLogin = (Button) findViewById(R.id.button_login);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (AccessToken.getCurrentAccessToken() == null) {
                    LoginManager.getInstance().logInWithReadPermissions(SignInActivity.this, Arrays.asList("public_profile", "email"));
                }

//                ColorDrawable customerbuttonColor = (ColorDrawable) customerButton.getBackground();
//
//                if (customerbuttonColor.getColor() == getResources().getColor(R.color.colorAccent)) {
//                    Intent intent = new Intent(getApplicationContext(), CustomerMainActivity.class);
//                    startActivity(intent);
//                } else {
//                    Intent intent = new Intent(getApplicationContext(), DriverMainActivity.class);
//                    startActivity(intent);
//                }
            }
        });


        callbackManager = CallbackManager.Factory.create();
        sharedPref = getSharedPreferences("MY_KEY", Context.MODE_PRIVATE);

        final Button buttonLogout = (Button) findViewById(R.id.button_logout);
        buttonLogout.setVisibility(View.GONE);

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                        Log.d("FACEBOOK TOKEN", loginResult.getAccessToken().getToken());

                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(
                                            JSONObject object,
                                            GraphResponse response) {
                                        // Application code
                                        Log.d("FACEBOOK DETAILS", object.toString());

                                        SharedPreferences.Editor editor = sharedPref.edit();

                                        try {
                                            editor.putString("name", object.getString("name"));
                                            editor.putString("email", object.getString("email"));
                                            editor.putString("avatar", object.getJSONObject("picture").getJSONObject("data").getString("url"));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        editor.commit();
                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,email,picture");
                        request.setParameters(parameters);
                        request.executeAsync();
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });

        if (AccessToken.getCurrentAccessToken() != null) {
            Log.d("USER", sharedPref.getAll().toString());
            buttonLogin.setText("Continue as " + sharedPref.getString("email", ""));
            buttonLogout.setVisibility(View.VISIBLE);
        }

        // Handle Logout button
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logOut();
                buttonLogin.setText("Login with Facebook");
                buttonLogout.setVisibility(View.GONE);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}
