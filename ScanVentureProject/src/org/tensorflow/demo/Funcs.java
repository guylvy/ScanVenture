package org.tensorflow.demo;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

public class Funcs {
    private static Funcs funcs = null;
    public static DatabaseReference DB = FirebaseDatabase.getInstance().getReference();
    public static void SaveToSharedPrefs(String toSave,String nameToSave ,Context context) {
        SharedPreferences sp = context.getSharedPreferences(context.getString(R.string.SHARED_PREFS),context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = sp.edit();
        prefsEditor.putString(nameToSave,toSave);
        prefsEditor.commit();
    }
    public static void SaveToSharedPrefs(Object toSave, String objectName, Context context){
        SharedPreferences sp = context.getSharedPreferences(context.getString(R.string.SHARED_PREFS),context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = sp.edit();
        Gson gson = new Gson();
        String json = gson.toJson(toSave);
        prefsEditor.putString(objectName, json);
        prefsEditor.commit();
    }
    public static Object GetFromShredPrefs(String toGet, boolean isObject ,Context context){
        SharedPreferences sp = context.getSharedPreferences(context.getString(R.string.SHARED_PREFS),context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = sp.edit();
        if (isObject) {
            Gson gson = new Gson();
            String json = sp.getString(toGet, "");
            return gson.fromJson(json, Account.class);
        }
        else {
            return sp.getString(toGet ,"");
        }
    }

    public static Account getAccount(Context context) {
        return (Account)GetFromShredPrefs(context.getString(R.string.user_data), true, context);
    }
}