package com.barvius.lab4;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class ConfigManager {

    public static void setSignIn(Context context) {
        SharedPreferences sPref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putBoolean("signin", true);
        ed.commit();
    }

    public static void logOut(Context context) {
        SharedPreferences sPref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putBoolean("signin", false);
        ed.commit();
    }

    public static boolean isSignIn(Context context) {
        SharedPreferences sPref = PreferenceManager.getDefaultSharedPreferences(context);
        return sPref.getBoolean("signin", false);
    }

}
