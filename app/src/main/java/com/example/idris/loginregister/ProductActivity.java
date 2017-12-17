package com.example.idris.loginregister;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class ProductActivity extends AppCompatActivity {

    /* Değişkenler  */
    public String state="insert";
    /* End Değişkenler */
    /* Elementleri Define Etme */
    public ImageButton btnBarScan;
    public TextView tvBarcodeNo;
    public TextView tvProName;
    public TextView tvProPrice;
    public Button btnSubmitForm;
    public Button btnBack;
    public Button btnReadCode;
    /* Elementleri Define Etme Bitiş */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        /* Elementleri Define Etme Kısmı */
        btnBarScan=(ImageButton) findViewById(R.id.btnBarScan);
        tvBarcodeNo=(EditText) findViewById(R.id.tvBarcodeNo);
        tvProName=(EditText) findViewById(R.id.tvProName);
        tvProPrice=(EditText) findViewById(R.id.tvProPrice);
        btnSubmitForm=(Button) findViewById(R.id.btnSubmitForm);
        btnBack=(Button) findViewById(R.id.btnBackUA);
        btnReadCode=(Button) findViewById(R.id.btnReadBarcode);
        /* End Define Kısmı */


        Intent intent=getIntent();
        final String barcode=intent.getStringExtra("barcode");

        if (barcode!=null){
            tvBarcodeNo.setText(barcode);
            Response.Listener<String> responseListener=new Response.Listener<String>(){
                @Override
                public void onResponse(String response) {
                    try {
                        final TextView tvRetText=(TextView) findViewById(R.id.tvRetText);
                        JSONObject jsonResponse=new JSONObject(response);
                        boolean success=jsonResponse.getBoolean("state");
                        if (success){
                            tvRetText.setText(barcode+" barkod numarasına sahip ürün sisteminizde kayıtlıdır. Ürün bilgilerini değiştirerek güncelleme yapabilirsiniz.");
                            String price1=jsonResponse.getString("pro_price");
                            String name2=jsonResponse.getString("pro_name");
                            tvProName.setText(name2);
                            tvProPrice.setText(price1);
                            btnSubmitForm.setText("Ürün Güncelle");
                            state="uptade";

                        }else{
                            btnSubmitForm.setText("Ürün Ekle");
                            state="insert";
                        }

                    }catch(JSONException e){
                        e.printStackTrace();
                    }

                }
            };

            ProductDataRequest productDataRequest=new ProductDataRequest(barcode,responseListener);
            RequestQueue queue= Volley.newRequestQueue(ProductActivity.this);
            queue.add(productDataRequest);
        }

        /* Button Clickleme İşlemlerin Eventleri */
        btnBarScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(ProductActivity.this,DashboardActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                intent.putExtra("sourceActivity","productAdd");
                startActivity(intent);
            }
        });

        final Response.Listener<String> responseListener2=new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                final TextView tvRetText=(TextView) findViewById(R.id.tvRetText);
                tvRetText.setText(response);
                try {
                    JSONObject jsonResponse=new JSONObject(response);
                    boolean success=jsonResponse.getBoolean("success");
                    String proName=String.valueOf(tvProName.getText());
                    String proPrice=String.valueOf(tvProPrice.getText());
                    String barcode=String.valueOf(tvBarcodeNo.getText());
                    String uxMetin="";
                    if (state=="insert"){uxMetin="eklenmiştir.";}else{uxMetin="güncellenmiştir.";}
                    if (success){
                        tvProName.setText("");
                        tvProPrice.setText("");
                        tvBarcodeNo.setText("");
                        btnSubmitForm.setText("Ürün Ekle");
                        tvRetText.setText(barcode+" barkodlu ürün '"+proName+"' adında ve '"+proPrice+"' fiyatinda "+uxMetin);
                        state="insert";

                    }else{
                        tvRetText.setText("Ürün Ekleme Bir Hata Oluştu");
                    }

                }catch(JSONException e){
                    e.printStackTrace();
                }
            }
        };

        btnSubmitForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String price=String.valueOf(tvProPrice.getText());
                String name=String.valueOf(tvProName.getText());
                String barcode=String.valueOf(tvBarcodeNo.getText());
                Toast.makeText(ProductActivity.this,price,Toast.LENGTH_LONG).show();
                InputMethodManager imm = (InputMethodManager)getSystemService(ProductActivity.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                if (TextUtils.isEmpty(name) && TextUtils.isEmpty(price) && TextUtils.isEmpty(barcode) ){
                    if (TextUtils.isEmpty(name)) tvProName.setError("Lütfen Ürün İsmini Giriniz.");
                    if (TextUtils.isEmpty(price)) tvProPrice.setError("Lütfen Ürün Fiyatını Giriniz.");
                    if (TextUtils.isEmpty(barcode)) tvBarcodeNo.setError("Lütfen Ürün Barcodunu Giriniz.");
                }else{
                    productRequest sendPro=new productRequest(barcode,name,price,state,responseListener2);
                    RequestQueue queue2= Volley.newRequestQueue(ProductActivity.this);
                    queue2.add(sendPro);
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ProductActivity.this,UserAreaActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

                ProductActivity.this.startActivity(intent);
                ProductActivity.this.finish();
            }
        });
        btnReadCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(ProductActivity.this,DashboardActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                intent.putExtra("sourceActivity","userAreaActivity");
                startActivity(intent);
            }
        });
    }
}
