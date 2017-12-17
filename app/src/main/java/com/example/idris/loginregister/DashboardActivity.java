package com.example.idris.loginregister;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.zxing.Result;
import com.google.zxing.client.result.ProductParsedResult;

import org.json.JSONException;
import org.json.JSONObject;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class DashboardActivity extends Activity implements ZXingScannerView.ResultHandler {


    private ZXingScannerView mScannerView;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        setContentView(mScannerView);                // Set the scanner view as the content view
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(Result rawResult) {
        // Do something with the result here
        Intent intent=getIntent();
        String returnPage=intent.getStringExtra("sourceActivity");
        returnPage=String.valueOf(returnPage);
        Toast.makeText(DashboardActivity.this,returnPage,Toast.LENGTH_LONG).show();
        if (returnPage.equals("userAreaActivity")){
            intent=new Intent(DashboardActivity.this,UserAreaActivity.class);
            intent.putExtra("barcode",rawResult.getText());
            startActivity(intent);
        }else if (returnPage.equals("productAdd")){
            intent=new Intent(DashboardActivity.this,ProductActivity.class);
            intent.putExtra("barcode",rawResult.getText());
            startActivity(intent);
        }

        // If you would like to resume scanning, call this method below:
        mScannerView.resumeCameraPreview(this);
    }

}
