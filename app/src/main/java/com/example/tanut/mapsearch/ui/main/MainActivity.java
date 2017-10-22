package com.example.tanut.mapsearch.ui.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.tanut.mapsearch.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Load Main Fragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, MainFragment.newInstance(), MainFragment.TAG).commit();
    }
}
