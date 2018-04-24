package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich = new Sandwich();
        List<String> alsoKnownAsList;
        List<String> ingredientsList;
        JSONArray array;

        try {
            JSONObject obj = new JSONObject(json);
            JSONObject name = obj.getJSONObject("name");
            String mainName = name.getString("mainName");
            sandwich.setMainName(mainName);

            array = name.getJSONArray("alsoKnownAs");
            alsoKnownAsList = new ArrayList<>();
            for (int i = 0; i < array.length();i++)
            {
                alsoKnownAsList.add(array.getString(i));
            }
            sandwich.setAlsoKnownAs(alsoKnownAsList);

            sandwich.setDescription(obj.getString("description"));
            sandwich.setImage(obj.getString("image"));
            sandwich.setPlaceOfOrigin(obj.getString("placeOfOrigin"));

            // ingredients
            array = obj.getJSONArray("ingredients");
            ingredientsList = new ArrayList<>();
            for (int i = 0; i < array.length();i++)
            {
                ingredientsList.add(array.getString(i));
            }
            sandwich.setIngredients(ingredientsList);

        } catch (Exception e) {
            Log.e("Amit", "Could not parse malformed JSON: \"" + json + "\"");
        }
        return sandwich;
    }
}
