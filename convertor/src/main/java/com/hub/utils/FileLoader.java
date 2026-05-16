package com.hub.utils;

import com.google.gson.Gson;
import com.hub.models.RootData;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class FileLoader {

    public static RootData loadData(String path) {
        try {
            // Force the path to look inside the resource folder if it isn't already
            if (!path.startsWith("/")) {
                path = "/" + path;
            }

            // Read the file straight out of the running application package classpath
            InputStream inputStream = FileLoader.class.getResourceAsStream(path);

            if (inputStream == null) {
                System.err.println("CRITICAL ERROR: Could not find resource file at path: " + path);
                return null;
            }

            Gson gson = new Gson();
            try (InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {
                return gson.fromJson(reader, RootData.class);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}