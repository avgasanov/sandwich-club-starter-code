package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        try {
            JSONObject mainObject = new JSONObject(json);

            JSONObject nameObject =  mainObject.getJSONObject("name");
            String mainName = nameObject.getString("mainName");
            List<String> alsoKnownAs = jsonArrayToList(nameObject.getJSONArray("alsoKnownAs"));

            String placeOfOrigin = mainObject.getString("placeOfOrigin");
            String description = mainObject.getString("description");
            String image = mainObject.getString("image");
            List<String> ingridients = jsonArrayToList(mainObject.getJSONArray("ingredients"));

            return new Sandwich(mainName, alsoKnownAs,
                                                placeOfOrigin, description,
                                                image, ingridients );
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static List<String> jsonArrayToList(JSONArray jsonArray) {
        List<String> strings = new ArrayList<>();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                strings.add(jsonArray.getString(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return strings;
    }
}
