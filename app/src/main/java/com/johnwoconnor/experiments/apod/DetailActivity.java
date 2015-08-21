package com.johnwoconnor.experiments.apod;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class DetailActivity extends Activity {

    TextView mDetailName;
    TextView mDetailDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mDetailName = (TextView)findViewById(R.id.detailName);
        mDetailDescription = (TextView) findViewById(R.id.detailDescription);

        Intent i = getIntent();
        mDetailName.setText(i.getStringExtra("Title"));
        mDetailDescription.setText(i.getStringExtra("Explanation"));


    }

}
