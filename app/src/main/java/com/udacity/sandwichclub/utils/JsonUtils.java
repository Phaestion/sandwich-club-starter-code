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
            JSONObject job = new JSONObject(json);

            JSONObject nameJob = job.getJSONObject("name");
            String mainName = nameJob.getString("mainName");
            List<String> akaList = jarrToArrayList(nameJob.getJSONArray("alsoKnownAs"));

            String placeOfOrigin = job.getString("placeOfOrigin");
            String description = job.getString("description");
            String image = job.getString("image");

            List<String> ingredientsList = jarrToArrayList(job.optJSONArray("ingredients"));

            return new Sandwich(mainName,
                    akaList,
                    placeOfOrigin,
                    description,
                    image,
                    ingredientsList);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static ArrayList<String> jarrToArrayList(JSONArray jarr) throws JSONException {
        if (jarr == null) {
            return null;
        }
        
        ArrayList<String> arrayList = new ArrayList<>();

        for (int i = 0; i < jarr.length(); i++) {
            arrayList.add(jarr.getString(i));
        }

        return arrayList;
    }
}
