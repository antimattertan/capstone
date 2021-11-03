package com.example.test;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

public class GalmaetgilParser {
    private static String JSON_FILE_NAME = "jsons/gmgcourse.json";

    private Context context;

    public GalmaetgilParser(Context context) {
        this.context = context;
    }

    public ArrayList<Galmaetgil> getDatas() {
        ArrayList<Galmaetgil> datas = new ArrayList<>();

        try {
            InputStream is_location = this.context.getAssets().open(JSON_FILE_NAME);
            int fileSize_location = is_location.available();

            byte[] buffer_location = new byte[fileSize_location];
            is_location.read(buffer_location);
            is_location.close();

            String str_location = new String(buffer_location, "UTF-8");
            JSONObject json_location = new JSONObject(str_location);

            JSONObject body = json_location.getJSONObject("getgmgcourseinfo");
            JSONArray dataJsonArray = body.getJSONArray("item");
            for (int i = 0; i < dataJsonArray.length(); i++) {
                JSONObject data = dataJsonArray.getJSONObject(i);

                Galmaetgil galmaetgil = new Galmaetgil(data.getString("gugan_nm"), data.getString("start_addr"), data.getString("gm_course"), data.getString("gm_text"));
                datas.add(galmaetgil);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return datas;
    }
}
