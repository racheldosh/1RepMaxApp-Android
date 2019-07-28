package com.example.fitbod1repmaxapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set tool bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (findViewById(R.id.content_frame) != null) {

            if (savedInstanceState != null) {
                return;
            }

            // Fragment upon mainActivity launch
            ExerciseListFragment firstFragment = new ExerciseListFragment();

            firstFragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'content_frame' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.content_frame, firstFragment).commit();
        }
    }
}


