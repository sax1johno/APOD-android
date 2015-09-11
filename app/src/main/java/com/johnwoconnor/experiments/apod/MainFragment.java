package com.johnwoconnor.experiments.apod;


import android.app.Fragment;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.johnwoconnor.experiments.apod.com.johnwoconnor.experiments.apod.dao.ApodDAO;
import com.johnwoconnor.experiments.apod.models.ScienceImage;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by john on 9/11/15.
 */
public class MainFragment extends Fragment {
    private static final String TAG = "APOD MainFragment";

    private TextView mName;
    private Button mDetailButton;
    private ImageView mApodImage;

    private Handler mHandler;

    private String mImageUrl;
    private String mDescription;
    private String mTitle;
    private Drawable mImage;

    private ProgressBar mLoading;
    private LinearLayout mContent;

    private Fragment detailFragment;
    private Fragment mainFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DownloaderTask task = new DownloaderTask();
        task.execute("Param1", "Param2", "etc");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        mName = (TextView)view.findViewById(R.id.name);
        mDetailButton = (Button)view.findViewById(R.id.detailButton);
        mApodImage = (ImageView)view.findViewById(R.id.apodImage);
        mHandler = new Handler(Looper.getMainLooper());
        mLoading = (ProgressBar)view.findViewById(R.id.loading);
        mContent = (LinearLayout)view.findViewById(R.id.content);

        mLoading.setVisibility(View.VISIBLE);
        mLoading.setIndeterminate(true);
        mContent.setVisibility(View.GONE);

        mDetailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Toast.makeText(getActivity(), "Clicked on the detail button", Toast.LENGTH_LONG).show();
                detailFragment = new DetailFragment();
                Bundle args = new Bundle();
                args.putString("title", mTitle);
                args.putString("description", mDescription);

                detailFragment.setArguments(args);
                getFragmentManager().beginTransaction()
                        .add(R.id.content, detailFragment, "DETAIL")
                        .commit();

            }
        });
//                Intent i = new Intent(MainActivity.this, DetailActivity.class);
//                i.putExtra("Title", mTitle);
//                i.putExtra("Explanation", mDescription);
//                startActivity(i);
//            }
//        });


        return view;
    }

    public void setName(String name) {
        mName.setText(name);
    }


    private class DownloaderTask extends AsyncTask<String, Double, Void> {
        @Override
        protected Void doInBackground(String... strings) {
            Log.d(TAG, strings[0]);
            ApodDAO apod = new ApodDAO();

            ArrayList<ScienceImage> images = (ArrayList<ScienceImage>) apod.retrieve();

            InputStream is = null;
            try {
                is = (InputStream) new URL(images.get(0).getImageUrl()).getContent();
                mImage = Drawable.createFromStream(is, "APOD");
                mTitle = images.get(0).getTitle();
                mDescription = images.get(0).getDescription();

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mLoading.setVisibility(View.GONE);
                        mContent.setVisibility(View.VISIBLE);
                        mName.setText(mTitle);
                        mApodImage.setImageDrawable(mImage);
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            // Do something that will be run before ASyncTask runs.
        }

        @Override
        protected void onProgressUpdate(Double... values) {

        }
    }
}
