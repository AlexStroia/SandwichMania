package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(final String json) {

        final String NAME = "name";
        final String MAIN_NAME = "mainName";
        final String ALSO_KNOWN_AS = "alsoKnownAs";
        final String PLACE_OF_ORIGIN = "placeOfOrigin";
        final String DESCRIPTION = "description";
        final String IMAGE = "image";
        final String INGREDIENTS = "ingredients";
        List<String> alsoKnownAs;
        List<String> ingredients;
        Sandwich sandwich;

        try {
            JSONObject mainJsonObject = new JSONObject(json);

            JSONObject name = mainJsonObject.getJSONObject(NAME);
            String mainName = name.getString(MAIN_NAME);

            JSONArray jsonArrayAlsoKnown = name.getJSONArray(ALSO_KNOWN_AS);
            alsoKnownAs = parseJsonArray(jsonArrayAlsoKnown);

            String placeOfOrigin = mainJsonObject.getString(PLACE_OF_ORIGIN);
            String description = mainJsonObject.getString(DESCRIPTION);
            String image = mainJsonObject.getString(IMAGE);

            JSONArray jsonArrayingredients = mainJsonObject.getJSONArray(INGREDIENTS);
            ingredients = parseJsonArray(jsonArrayingredients);
            sandwich = new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
            return sandwich;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static List<String> parseJsonArray(JSONArray jsonArray) throws JSONException {
        final int count = jsonArray.length();
        List<String> jsonList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            jsonList.add(jsonArray.get(i).toString());
        }
        return jsonList;
    }
}
