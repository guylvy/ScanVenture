package org.tensorflow.demo;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

public class Funcs {
    private static Funcs funcs = null;
    public static Funcs getFuncs() { //Singleton
        if (funcs == null){
            funcs = new Funcs();
        }
        return funcs;
    }
    public void SaveToSharedPrefs(String toSave,String nameToSave ,Context context) {
        SharedPreferences sp = context.getSharedPreferences(context.getString(R.string.SHARED_PREFS),context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = sp.edit();
        prefsEditor.putString(nameToSave,toSave);
        prefsEditor.commit();
    }
    public void SaveToSharedPrefs(Object toSave, String objectName, Context context){
            SharedPreferences sp = context.getSharedPreferences(context.getString(R.string.SHARED_PREFS),context.MODE_PRIVATE);
            SharedPreferences.Editor prefsEditor = sp.edit();
            Gson gson = new Gson();
            String json = gson.toJson(toSave);
            prefsEditor.putString(objectName, json);
            prefsEditor.commit();
        }
    public Object GetFromShredPrefs(String toGet, boolean isObject ,Context context){
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
}
