package com.johnwoconnor.experiments.apod;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.widget.FrameLayout;

/**
 * Created by john on 9/11/15.
 */
public class EntryActivity extends Activity {
    FrameLayout content;
    FrameLayout toolbar;
    Fragment mMainFragment;
    Fragment mDetailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);

        mMainFragment = new MainFragment();
        mDetailFragment = new DetailFragment();

        getFragmentManager()
                .beginTransaction()
                .add(R.id.content, mMainFragment, "MAIN")
                .commit();
    }
}