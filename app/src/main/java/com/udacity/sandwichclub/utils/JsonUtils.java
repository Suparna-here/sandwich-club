package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    /** Returns the specific Sandwitch object by parsing the Json String */
    public static Sandwich parseSandwichJson(String json) {
        final String SANDWITCH_NAME = "name";
        final String SANDWITCH_NAME_MAIN_NAME = "mainName";
        final String SANDWITCH_NAME_ALSO_KNOWN_AS = "alsoKnownAs";
        final String SANDWITCH_PLACE_OF_ORIGIN = "placeOfOrigin";
        final String SANDWITCH_DESCRIPTION = "description";
        final String SANDWITCH_IMAGE = "image";
        final String SANDWITCH_INGREDIENTS = "ingredients";

        Sandwich sandwich= null;
        String mainName= null;
        List<String> alsoKnownAs = null;
        String placeOfOrigin= null;
        String description= null;
        String image= null;
        List<String> ingredients = null;
        try {

            JSONObject jsonObj = new JSONObject(json);
            mainName = jsonObj.getJSONObject(SANDWITCH_NAME).getString(SANDWITCH_NAME_MAIN_NAME);

            JSONArray alsoKnownAsJSONArray = jsonObj.getJSONObject(SANDWITCH_NAME).getJSONArray(SANDWITCH_NAME_ALSO_KNOWN_AS);
            if(alsoKnownAsJSONArray.length()>0) {
                alsoKnownAs=new ArrayList<String>();
                for (int i = 0;i<alsoKnownAsJSONArray.length();i++) {
                    alsoKnownAs.add(alsoKnownAsJSONArray.getString(i));
                }
            }
            placeOfOrigin = jsonObj.getString(SANDWITCH_PLACE_OF_ORIGIN);
            description = jsonObj.getString(SANDWITCH_DESCRIPTION);
            image = jsonObj.getString(SANDWITCH_IMAGE);
            JSONArray ingredientsJSONArray=jsonObj.getJSONArray(SANDWITCH_INGREDIENTS);
            if(ingredientsJSONArray.length()>0) {
                ingredients=new ArrayList<String>();
                for (int i = 0;i<ingredientsJSONArray.length();i++) {
                    ingredients.add(ingredientsJSONArray.getString(i));
                }
            }
            sandwich=new Sandwich(mainName,alsoKnownAs,placeOfOrigin,description,image,ingredients);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return sandwich;
    }
}
