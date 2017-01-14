package com.example.happy.acmpdfconvertor;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

/**
 * Created by Happy on 11/3/2016.
 */

public class help extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help);

        Toolbar toolbar = (Toolbar) findViewById(R.id.help_toolbar);
        setSupportActionBar(toolbar);
        ActionBar bar = getSupportActionBar();
        bar.setDisplayHomeAsUpEnabled(true);
        bar.setDefaultDisplayHomeAsUpEnabled(true);

    }
}
