package com.example.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_test.*;
import org.json.JSONArray
import org.json.JSONObject
import android.widget.TextView;

class testActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        var count = 0
        val json = assets.open("jsons/busaninfoapp-restaurant.json").reader().readText()
        val jsonArray = JSONArray(json)

        count = jsonArray.length()

        val jsonObject = jsonArray.getJSONObject(0)
        val lat = jsonObject.getDouble("lat")
        val lng = jsonObject.get("lng")

        textView.text = "위도: " + lat + "경도: " + lng

        Toast.makeText(applicationContext, "" + count + "입니다.", Toast.LENGTH_LONG).show()
    }



}
