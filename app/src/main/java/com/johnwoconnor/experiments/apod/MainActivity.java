package com.johnwoconnor.experiments.apod;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
import java.util.logging.Handler;
import java.util.logging.LogRecord;


public class MainActivity extends Activity {
    private static final String TAG = "APOD MainActivity";

    private TextView mName;
    private Button mDetailButton;
    private ImageView mApodImage;
    private Handler mHandler;

    private JSONObject apodData;

    private final String APOD_URL = "https://api.data.gov/nasa/planetary/apod?api_key=RCxj0hENMHTlgdK6Q83Q0W3HH15TiJMogvHotbe9";
    private final String JSON_URL = "url";
    private final String JSON_EXPLANATION = "explanation";
    private final String JSON_TITLE = "title";
    // "balksjfahwer"

    private String mImageUrl;
    private String mDescription;
    private String mTitle;
    private Drawable mImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mName = (TextView)findViewById(R.id.name);
        mDetailButton = (Button)findViewById(R.id.detailButton);
        mApodImage = (ImageView)findViewById(R.id.apodImage);

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

            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
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
                }
            });
        t.start();
    }

    public void setName(String name) {
        mName.setText(name);
    }

}
