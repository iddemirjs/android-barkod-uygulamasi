package com.example.idris.loginregister;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by idris on 27.8.2017.
 */

public class ProductDataRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL="http://test.dcdemir.com/getproduct.php";
    private Map<String,String> params;


    public ProductDataRequest(String barcode, Response.Listener<String> listener){
        super(Method.POST,REGISTER_REQUEST_URL,listener,null);
        params =new HashMap<>();
        params.put("barcode",barcode);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
