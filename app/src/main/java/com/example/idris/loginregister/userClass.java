package com.example.idris.loginregister;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by idris on 4.8.2017.
 */

public class userClass {

    private Context context;
    private String ISIM_KEY="com.example.idris.sharedpreference.ISIM";
    private String SOYAD_KEY="com.example.idris.sharedpreference.SOYAD";
    private String YAS_KEY="com.example.idris.sharedpreference.YAS";
    private String ID_KEY="com.example.idris.sharedpreference.ID";
    private String MAIN_KEY="com.example.idris.sharedpreference.MAIN_DATA";

    public userClass(Context context){
        this.context = context;
    }

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;



    public boolean saveUser(int id,String userName,String name){
        sharedPreferences=this.context.getSharedPreferences(MAIN_KEY,context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
        editor.putString(SOYAD_KEY,userName);
        editor.putString(ISIM_KEY,name);
        editor.putInt(ID_KEY,id);
        editor.commit();
        return true;
    }

    public int getUserID(){
        return context.getSharedPreferences(MAIN_KEY,MODE_PRIVATE).getInt(ID_KEY,-1);
    }
    public String getName(){
        return context.getSharedPreferences(MAIN_KEY,MODE_PRIVATE).getString(ISIM_KEY,"def");
    }
    public String getUserName(){
        return context.getSharedPreferences(MAIN_KEY,MODE_PRIVATE).getString(SOYAD_KEY,"user001");
    }
    public void userLogOut(){
        sharedPreferences=this.context.getSharedPreferences(MAIN_KEY,context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
        editor.putString(SOYAD_KEY,"us-001");
        editor.putString(ISIM_KEY,"user");
        editor.putInt(ID_KEY,-1);
        editor.commit();
    }
}
