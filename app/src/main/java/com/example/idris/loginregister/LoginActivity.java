package com.example.idris.loginregister;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        userClass usClass=new userClass(getApplicationContext());


        if(usClass.getUserID()!= (-1)){
            Intent intent=new Intent(LoginActivity.this,UserAreaActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
            LoginActivity.this.finish();
        }else{
            usClass.userLogOut();
        }


        final EditText etUsername=(EditText)findViewById(R.id.etUsername);
        final EditText etPassword=(EditText) findViewById(R.id.etPassword);
        final Button bLogin=(Button) findViewById(R.id.bLogin);
        final TextView tvRegisterLink=(TextView) findViewById(R.id.tvRegisterNow);

        tvRegisterLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent=new Intent(LoginActivity.this,RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });


        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String username=etUsername.getText().toString();
                final String password=etPassword.getText().toString();

                Response.Listener<String> responseListener=new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        tvRegisterLink.setText(response);

                        try {
                            JSONObject jsonResponse=new JSONObject(response);
                            boolean success=jsonResponse.getBoolean("success");
                            if (success){
                                String name=jsonResponse.getString("name");
                                int age=jsonResponse.getInt("age");
                                int id=jsonResponse.getInt("user_id");
                                String username=jsonResponse.getString("surname");

                                userClass us=new userClass(getApplicationContext());
                                us.saveUser(id,username,name);

                                Intent intent=new Intent(LoginActivity.this,UserAreaActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

                                intent.putExtra("name",name);
                                intent.putExtra("username",username);
                                intent.putExtra("age",age);

                                LoginActivity.this.startActivity(intent);
                                LoginActivity.this.finish();

                            }else{
                                AlertDialog.Builder buider=new AlertDialog.Builder(LoginActivity.this);
                                buider.setMessage("Giriş Başarısısız.")
                                        .setNegativeButton("Retry",null)
                                            .create()
                                                .show();
                            }
                        }catch(JSONException e){
                            e.printStackTrace();
                        }
                    }
                };

                LoginRequest loginRequest=new LoginRequest(username,password,responseListener);
                RequestQueue queue= Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);

            }
        });
    }
}
