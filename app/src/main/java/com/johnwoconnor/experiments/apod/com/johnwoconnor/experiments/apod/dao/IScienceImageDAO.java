package com.johnwoconnor.experiments.apod.com.johnwoconnor.experiments.apod.dao;

import com.johnwoconnor.experiments.apod.models.ScienceImage;

import java.util.List;

/**
 * Created by john on 9/4/15.
 */
public interface IScienceImageDAO {
    public List<ScienceImage> retrieve();
}
