package com.johnwoconnor.experiments.apod;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.LogRecord;


public class MainActivity extends Activity {
    private static final String TAG = "APOD MainActivity";

    private TextView mName;
    private Button mDetailButton;
    private ImageView mApodImage;

    private JSONObject apodData;

    private final String APOD_URL = "https://api.data.gov/nasa/planetary/apod?api_key=RCxj0hENMHTlgdK6Q83Q0W3HH15TiJMogvHotbe9";
    private final String JSON_URL = "url";
    private final String JSON_EXPLANATION = "explanation";
    private final String JSON_TITLE = "title";
    private Handler mHandler;

    private String mImageUrl;
    private String mDescription;
    private String mTitle;
    private Drawable mImage;

    private ProgressBar mLoading;
    private LinearLayout mContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mName = (TextView)findViewById(R.id.name);
        mDetailButton = (Button)findViewById(R.id.detailButton);
        mApodImage = (ImageView)findViewById(R.id.apodImage);
        mHandler = new Handler(Looper.getMainLooper());
        mLoading = (ProgressBar)findViewById(R.id.loading);
        mContent = (LinearLayout)findViewById(R.id.content);

        mLoading.setVisibility(View.VISIBLE);
        mLoading.setIndeterminate(true);
        mContent.setVisibility(View.GONE);

        mDetailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, DetailActivity.class);
                i.putExtra("Title", mTitle);
                i.putExtra("Explanation", mDescription);
                startActivity(i);
            }
        });

        for (int i =0; i < 3; i++) {
            Log.d(TAG, Integer.toString(i));
        }
        DownloaderTask task = new DownloaderTask();

        task.execute("Param1", "Param2", "etc");
//        mHandler.post(new Runnable() {
//            //            Thread t = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    URL apod = new URL(APOD_URL);
//                    HttpURLConnection urlConnection = (HttpURLConnection) apod.openConnection();
//                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
//                    BufferedReader br = new BufferedReader(new InputStreamReader(in));
//                    String jsonContent = br.readLine();
//                    apodData = new JSONObject(jsonContent);
//                    mImageUrl = apodData.getString(JSON_URL);
//                    mTitle = apodData.getString(JSON_TITLE);
//                    mDescription = apodData.getString(JSON_EXPLANATION);
//
//                    Log.d(TAG, apodData.getString(JSON_URL));
//                    Log.d(TAG, apodData.getString(JSON_TITLE));
//                    InputStream is = (InputStream) new URL(mImageUrl).getContent();
//                    mImage = Drawable.createFromStream(is, "APOD");
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            try {
//                                mName.setText(apodData.getString(JSON_TITLE));
//                                mApodImage.setImageDrawable(mImage);
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    });
//                } catch (MalformedURLException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//
//        mHandler.post(new Runnable() {
//            @Override
//            public void run() {
//                Log.d(TAG, "This is a log in the main activity");
//            }
//        });
//        t.start();
    }

    public void setName(String name) {
        mName.setText(name);
    }

    private class DownloaderTask extends AsyncTask<String, Double, Void> {
        @Override
        protected Void doInBackground(String... strings) {
            Log.d(TAG, strings[0]);
            try {
                URL apod = new URL(APOD_URL);
                HttpURLConnection urlConnection = (HttpURLConnection) apod.openConnection();
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                String jsonContent = br.readLine();
                apodData = new JSONObject(jsonContent);
                mImageUrl = apodData.getString(JSON_URL);
                mTitle = apodData.getString(JSON_TITLE);
                mDescription = apodData.getString(JSON_EXPLANATION);

                Log.d(TAG, apodData.getString(JSON_URL));
                Log.d(TAG, apodData.getString(JSON_TITLE));
                InputStream is = (InputStream) new URL(mImageUrl).getContent();
                mImage = Drawable.createFromStream(is, "APOD");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            mLoading.setVisibility(View.GONE);
                            mContent.setVisibility(View.VISIBLE);
                            mName.setText(apodData.getString(JSON_TITLE));
                            mApodImage.setImageDrawable(mImage);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
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
