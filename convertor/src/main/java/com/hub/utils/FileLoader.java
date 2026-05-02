package com.hub.utils;

import com.google.gson.Gson;
import com.hub.models.RootData;

import java.io.FileReader;

public class FileLoader {

    public static RootData loadData(String path) {
        try {
            Gson gson = new Gson();
            return gson.fromJson(new FileReader(path), RootData.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}