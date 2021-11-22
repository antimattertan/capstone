package com.example.busaninfoapp;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

public class TourParser {
    private static String JSON_FILE_NAME = "jsons/Tour.json";

    private Context context;

    public TourParser(Context context) {
        this.context = context;
    }

    public ArrayList<Tour> getDatas() {
        ArrayList<Tour> datas = new ArrayList<>();

        try {
            InputStream is_location = this.context.getAssets().open(JSON_FILE_NAME);
            int fileSize_location = is_location.available();

            byte[] buffer_location = new byte[fileSize_location];
            is_location.read(buffer_location);
            is_location.close();

            String str_location = new String(buffer_location, "UTF-8");
            JSONObject json_location = new JSONObject(str_location);

            JSONObject body = json_location.getJSONObject("getRecommendedKr");

            Log.e("body===", body.toString());

            JSONArray dataJsonArray = body.getJSONArray("item");
            for (int i = 0; i < dataJsonArray.length(); i++) {
                JSONObject data = dataJsonArray.getJSONObject(i);

                Tour tour = new Tour(data.getDouble("LAT"), data.getDouble("LNG"), data.getString("MAIN_TITLE"), data.getString("ADDR1"), data.getString("ITEMCNTNTS"), data.getString("MAIN_IMG_NORMAL"));
                datas.add(tour);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return datas;
    }
}
