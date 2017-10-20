package com.example.tanut.mapsearch.data.db;

import com.example.tanut.mapsearch.data.db.model.MyItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by tanut on 10/12/2017.
 */

public class MyItemReader {

    /*
   * This matches only once in whole input,
   * so Scanner.next returns whole InputStream as a String.
   * http://stackoverflow.com/a/5445161/2183804
   */
    private static final String REGEX_INPUT_BOUNDARY_BEGINNING = "\\A";

    public List<MyItem> read(InputStream inputStream)  {

        List<MyItem> items = new ArrayList<MyItem>();
        String json = new Scanner(inputStream).useDelimiter(REGEX_INPUT_BOUNDARY_BEGINNING).next();
        JSONArray array = null;
        try {
            array = new JSONArray(json);

        for (int i = 0; i < array.length(); i++) {
            String title = null;
            String snippet = null;
            JSONObject object = array.getJSONObject(i);
            double lat = object.getDouble("lat");
            double lng = object.getDouble("lng");
            if (!object.isNull("title")) {
                title = object.getString("title");
            }
            if (!object.isNull("snippet")) {
                snippet = object.getString("snippet");
            }
            items.add(new MyItem(lat, lng, title, snippet));
        }
        } catch (JSONException e) {
            throw new RuntimeException(e);
            //e.printStackTrace();
        }
        return items;
    }
}
