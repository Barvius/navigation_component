package com.barvius.lab4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StatusBarTools.setStatusBarColor(getWindow(),getResources().getColor(R.color.colorPrimaryDark));
    }

    @Override
    public void onBackPressed() {

    }
}

