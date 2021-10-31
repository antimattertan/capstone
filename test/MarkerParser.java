package com.example.test;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

public class MarkerParser {
    private static String JSON_FILE_NAME = "restrooms.json";

    private Context context;

    public MarkerParser(Context context) {
        this.context = context;
    }

    public ArrayList<Marker> getMarkers() {
        ArrayList<Marker> markers = new ArrayList<>();

        try {
            InputStream is_location = this.context.getAssets().open(JSON_FILE_NAME);
            int fileSize_location = is_location.available();

            byte[] buffer_location = new byte[fileSize_location];
            is_location.read(buffer_location);
            is_location.close();

            String str_location = new String(buffer_location, "UTF-8");
            JSONObject json_location = new JSONObject(str_location);

            JSONObject body = json_location.getJSONObject("response").getJSONObject("body");
            JSONArray markerJsonArray = body.getJSONObject("items").getJSONArray("item");
            for (int i = 0; i < markerJsonArray.length(); i++) {
                JSONObject data = markerJsonArray.getJSONObject(i);

                Marker marker = new Marker(data.getString("name"), data.getString("cf_addr"), data.getDouble("lat"), data.getDouble("lng"));
                markers.add(marker);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return markers;
    }
}
