package com.example.idris.loginregister;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by idris on 8.9.2017.
 */

public class productRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL="http://test.dcdemir.com/ekleguncelle.php";
    private Map<String,String> params;


    public productRequest(String barcodeNo, String proName, String price, String info, Response.Listener<String> listener){
        super(Request.Method.POST,REGISTER_REQUEST_URL,listener,null);
        params =new HashMap<>();
        params.put("barcodeNo",barcodeNo);
        params.put("proName",proName);
        params.put("proPrice",price);
        params.put("info",info);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
