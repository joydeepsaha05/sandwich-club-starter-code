package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static final String TAG = "JsonUtils";

    public static Sandwich parseSandwichJson(String json) {

        String mainName, placeOfOrigin, description, image;
        List<String> alsoKnownAs, ingredients;

        try {
            JSONObject jsonObject = new JSONObject(json);

            JSONObject name = jsonObject.getJSONObject("name");

            mainName = name.getString("mainName");

            alsoKnownAs = jsonArrayToList(name.getJSONArray("alsoKnownAs"));

            placeOfOrigin = jsonObject.getString("placeOfOrigin");

            description = jsonObject.getString("description");

            image = jsonObject.getString("image");

            ingredients = jsonArrayToList(jsonObject.getJSONArray("ingredients"));

            return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description,
                    image, ingredients);
        } catch (JSONException e) {
            Log.e(TAG, String.valueOf(e));
            return null;
        }
    }

    private static List<String> jsonArrayToList(JSONArray jsonArray) {
        if (jsonArray == null || jsonArray.length() == 0) {
            return null;
        } else {
            try {
                List<String> list = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); ++i) {
                    list.add(jsonArray.getString(i));
                }
                return list;
            } catch (JSONException e) {
                Log.e(TAG, String.valueOf(e));
                return null;
            }
        }
    }
}
