package com.example.tanut.mapsearch.ui;

import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.tanut.mapsearch.R;
import com.example.tanut.mapsearch.ui.map.MapFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Load MapFragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, MapFragment.newInstance(),MapFragment.TAG).commit();
    }
}
