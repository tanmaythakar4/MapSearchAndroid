package com.example.tanut.mapsearch.ui.main;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.tanut.mapsearch.R;
import com.example.tanut.mapsearch.ui.base.BaseActivity;

public class MainActivity extends BaseActivity {

    private MainFragment mainFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // find the retained fragment on activity restarts
        FragmentManager fm = getSupportFragmentManager();
        mainFragment = (MainFragment) fm.findFragmentByTag(MainFragment.TAG);

        // create the fragment and data the first time
        if (mainFragment == null) {
            // add the fragment
            mainFragment = mainFragment.newInstance();
            fm.beginTransaction().add(R.id.fragment_container,mainFragment, MainFragment.TAG).commit();

        }


       /* // Load Main Fragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, MainFragment.newInstance(), MainFragment.TAG).commit();*/

    }


    @Override
    protected void onPause() {
        super.onPause();
        if(isFinishing()) {
            FragmentManager fm = getSupportFragmentManager();
            // we will not need this fragment anymore, this may also be a good place to signal
            // to the retained fragment object to perform its own cleanup.
            fm.beginTransaction().remove(mainFragment).commit();
        }
    }


    @Override
    public void onBackPressed() {

        Log.d("BackPrise","BackPrise");
        if(getSupportFragmentManager().findFragmentByTag(MainFragment.TAG).getChildFragmentManager().getBackStackEntryCount()>0){

            getSupportFragmentManager().findFragmentByTag(MainFragment.TAG).getChildFragmentManager().popBackStack();
        }
        else{
            super.onBackPressed();
        }

    }
}
