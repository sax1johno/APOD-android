package com.johnwoconnor.experiments.apod.com.johnwoconnor.experiments.apod.dao;

import com.johnwoconnor.experiments.apod.models.ScienceImage;

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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by john on 9/4/15.
 */
public class ApodDAO implements IScienceImageDAO {
    private JSONObject apodData;

    private final String APOD_URL = "https://api.data.gov/nasa/planetary/apod?api_key=RCxj0hENMHTlgdK6Q83Q0W3HH15TiJMogvHotbe9";
    private final String JSON_URL = "url";
    private final String JSON_EXPLANATION = "explanation";
    private final String JSON_TITLE = "title";

    @Override
    public List<ScienceImage> retrieve() {
        URL apod = null;
        ArrayList<ScienceImage> returnList = new ArrayList<ScienceImage>();

        try {
            apod = new URL(APOD_URL);
            HttpURLConnection urlConnection = (HttpURLConnection) apod.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String jsonContent = br.readLine();
            apodData = new JSONObject(jsonContent);

            ScienceImage img = new ScienceImage();
            img.setImageUrl(apodData.getString(JSON_URL))
                    .setTitle(apodData.getString(JSON_TITLE))
                    .setDescription(apodData.getString(JSON_EXPLANATION));
            returnList.add(img);
//            mImageUrl = apodData.getString(JSON_URL);
//            mTitle = apodData.getString(JSON_TITLE);
//            mDescription = apodData.getString(JSON_EXPLANATION);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnList;
    }
}
