package com.example.idris.loginregister;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class UserAreaActivity extends AppCompatActivity {


    public TextView tvUserName;
    public TextView tvUserID;
    public TextView tvPrice;
    public TextView tvBarcode;
    public TextView tvProName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area);

        Button btnCashRegister=(Button) findViewById(R.id.btnCashRegister);
        Button btnAddProduct=(Button) findViewById(R.id.btn_addProduct);
        tvUserID=(TextView)findViewById(R.id.tvUserID);
        tvUserName=(TextView)findViewById(R.id.tvUserName);
        tvPrice=(TextView)findViewById(R.id.tvPrice);
        tvBarcode=(TextView)findViewById(R.id.tvBarcode);
        tvProName=(TextView)findViewById(R.id.tvProName);


        userClass usClass=new userClass(getApplicationContext());
        int userID=usClass.getUserID();
        String name=usClass.getUserName();
        String username=usClass.getUserName();
        tvUserID.setText(userID+"");
        tvUserName.setText(name+" "+username);

        Intent intent=getIntent();
        String barcode=intent.getStringExtra("barcode");

        final TextView tvNotes=(TextView) findViewById(R.id.tvProName);


        if (barcode==null){
            tvNotes.setText("Hoşgeldiniz");
        }else{
            Response.Listener<String> responseListener=new Response.Listener<String>(){
                @Override
                public void onResponse(String response) {
                    tvNotes.setText(response);
                    try {
                        JSONObject jsonResponse=new JSONObject(response);
                        boolean success=jsonResponse.getBoolean("state");
                        if (success){
                            String price=jsonResponse.getString("pro_price");
                            String name=jsonResponse.getString("pro_name");
                            String barcode=jsonResponse.getString("pro_barcode");
                            tvBarcode.setText(barcode);
                            tvProName.setText(name);
                            tvPrice.setText(price);
                        }else{
                            Toast.makeText(UserAreaActivity.this,"Ürün Bulunamadı.",Toast.LENGTH_LONG).show();
                        }

                    }catch(JSONException e){
                        e.printStackTrace();
                    }

                }
            };

            ProductDataRequest productDataRequest=new ProductDataRequest(barcode,responseListener);
            RequestQueue queue= Volley.newRequestQueue(UserAreaActivity.this);
            queue.add(productDataRequest);
        }

        btnCashRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(UserAreaActivity.this,DashboardActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                intent.putExtra("sourceActivity","userAreaActivity");
                startActivity(intent);
            }
        });
        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(UserAreaActivity.this,ProductActivity.class);
                startActivity(intent);
            }
        });



    }
}
